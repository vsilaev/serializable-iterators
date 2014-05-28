package com.farata.util.iterators;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

public class SerializableSortedMapValuesIterator<K, V> extends SerializableSortedMapAbstractIterator<K, V, Map.Entry<K, V>, V> {
	
	public SerializableSortedMapValuesIterator() {}
	
	public SerializableSortedMapValuesIterator(final SortedMap<K, V> map) {
		super(map);
	}

	public V next() {
		final Map.Entry<K, V> result = delegate.next();
		setLatestKey(result.getKey());
		return result.getValue();
	}

	protected Iterator<Map.Entry<K, V>> createOriginalIterator(SortedMap<K, V> map) {
		return map.entrySet().iterator();
	}
}
