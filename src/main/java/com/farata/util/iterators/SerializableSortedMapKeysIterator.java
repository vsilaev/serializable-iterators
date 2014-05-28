package com.farata.util.iterators;

import java.util.Iterator;
import java.util.SortedMap;

public class SerializableSortedMapKeysIterator<K, V> extends SerializableSortedMapAbstractIterator<K, V, K, K> {
	
	public SerializableSortedMapKeysIterator() {}
	
	public SerializableSortedMapKeysIterator(final SortedMap<K, V> map) {
		super(map);
	}

	public K next() {
		final K result = delegate.next();
		setLatestKey(result);
		return result;
	}

	protected Iterator<K> createOriginalIterator(final SortedMap<K, V> map) {
		return map.keySet().iterator();
	}
}
