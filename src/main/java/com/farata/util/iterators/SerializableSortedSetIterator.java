package com.farata.util.iterators;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.SortedSet;

public class SerializableSortedSetIterator<E> implements SerializableIterator<E>, Externalizable {
	private SortedSet<E> set;
	private E latestElement;
	private boolean hasLatestElement;
	
	transient private Iterator<E> delegate;
	
	public SerializableSortedSetIterator() {}
	
	public SerializableSortedSetIterator(final SortedSet<E> set) {
		this.set = set;
		this.latestElement = null;
		this.delegate = set.iterator();
		this.hasLatestElement = false;
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(set);
		out.writeBoolean(hasLatestElement);
		if (hasLatestElement) {
			out.writeObject(latestElement);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		set = (SortedSet<E>)in.readObject();
		hasLatestElement = in.readBoolean();
		if (hasLatestElement) {
			latestElement = (E)in.readObject();
			delegate = set.tailSet(latestElement).iterator();
			delegate.next();
		} else {
			delegate = set.iterator();
		}
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public E next() {
		final E result = delegate.next();
		latestElement = result;
		hasLatestElement = true;
		return result;
	}

	@Override
	public void remove() {
		delegate.remove();
	}
}
