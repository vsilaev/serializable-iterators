package com.farata.util.iterators;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

public class SerializableSortedMapEntriesIterator<K, V> extends SerializableSortedMapAbstractIterator<K, V, Map.Entry<K, V>, Map.Entry<K, V>> {
	
	public SerializableSortedMapEntriesIterator() {}
	
	public SerializableSortedMapEntriesIterator(final SortedMap<K, V> map) {
		super(map);
	}

	public Map.Entry<K, V> next() {
		final Map.Entry<K, V> result = delegate.next();
		setLatestKey(result.getKey());
		return result;
	}

	protected Iterator<Map.Entry<K, V>> createOriginalIterator(SortedMap<K, V> map) {
		return map.entrySet().iterator();
	}
}
