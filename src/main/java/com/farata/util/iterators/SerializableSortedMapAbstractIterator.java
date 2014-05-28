package com.farata.util.iterators;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.SortedMap;

abstract public class SerializableSortedMapAbstractIterator<K, V, SE, TE> implements SerializableIterator<TE>, Externalizable {
	private SortedMap<K, V> map;
	private K latestKey;
	private boolean hasLatestKey;
	
	transient protected Iterator<SE> delegate;
	
	public SerializableSortedMapAbstractIterator() {}
	
	public SerializableSortedMapAbstractIterator(final SortedMap<K, V> map) {
		this.map = map;
		this.latestKey = null;
		this.delegate = createOriginalIterator(map);
		this.hasLatestKey = false;
	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(map);
		out.writeBoolean(hasLatestKey);
		if (hasLatestKey) {
			out.writeObject(latestKey);
		}
	}

	@SuppressWarnings("unchecked")
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		map = (SortedMap<K, V>)in.readObject();
		hasLatestKey = in.readBoolean();
		if (hasLatestKey) {
			latestKey = (K)in.readObject();
			delegate = createOriginalIterator(map.tailMap(latestKey));
			delegate.next();
		} else {
			delegate = createOriginalIterator(map);
		}
	}

	public boolean hasNext() {
		return delegate.hasNext();
	}

	public void remove() {
		delegate.remove();
	}

	abstract protected Iterator<SE> createOriginalIterator(final SortedMap<K, V> map);
	
	final protected void setLatestKey(final K key) {
		latestKey = key;
		hasLatestKey = true;
	}
}
