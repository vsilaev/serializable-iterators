package com.farata.util.iterators;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

public class SerializableIterators {
	
	public static <E> Iterable<E> from(final Collection<E> original) {
		if (original instanceof SerializableIterable) {
			return original;
		}
		if (original instanceof List) {
			return wrapList((List<E>)original);
		}
		if (original instanceof SortedSet) {
			return wrapSortedSet((SortedSet<E>)original);
		}
		if (original instanceof EnumSet) {
			@SuppressWarnings("unchecked")
			final Iterable<E> result = (Iterable<E>)wrapEnumSet0(original);
			return result;
		}
		return null;
	}
	
	
	/* <E extends Serializable, C extends List<E> & RandomAccess & Serializable> */
	public static <E> Iterable<E> from(final List<E> list) {
		return wrapList(list);
	}
	
	/* <E extends Serializable, C extends SortedSet<E> & Serializable> */
	public static <E> Iterable<E> from(final SortedSet<E> sortedSet) {
		return wrapSortedSet(sortedSet);
	}
	
	public static <E extends Enum<E>> Iterable<E> from(final EnumSet<E> enumSet) {
		return wrapEnumSet(enumSet);
	}
	
	
	public static <K, V> Iterable<K> fromKeys(final SortedMap<K, V> map) {
		return new SerializableIterable<K>() {
			final private static long serialVersionUID = 1L;

			public SerializableIterator<K> iterator() {
				return new SerializableSortedMapKeysIterator<K, V>(map);	
			}
		}; 	
	}
	
	public static <K, V> Iterable<Map.Entry<K, V>> fromEntries(final SortedMap<K, V> map) {
		return new SerializableIterable<Map.Entry<K, V>>() {
			final private static long serialVersionUID = 1L;

			public SerializableIterator<Map.Entry<K, V>> iterator() {
				return new SerializableSortedMapEntriesIterator<K, V>(map);	
			}
		}; 	
	}
	
	public static <K, V> Iterable<V> fromValues(final SortedMap<K, V> map) {
		return new SerializableIterable<V>() {
			final private static long serialVersionUID = 1L;

			public SerializableIterator<V> iterator() {
				return new SerializableSortedMapValuesIterator<K, V>(map);	
			}
		}; 	
	}
	
	private static <E extends Enum<E>> Iterable<E> wrapEnumSet0(final Collection<?> original) {
		@SuppressWarnings("unchecked")
		final Collection<E> collection = (Collection<E>)original;
		final EnumSet<E> enumSet = (EnumSet<E>)collection;
		return wrapEnumSet(enumSet);
	}
	
	public static <E extends Enum<E>> Iterable<E> wrapEnumSet(final EnumSet<E> sortedSet) {
		return new SerializableIterable<E>() {
			final private static long serialVersionUID = 1L;

			public SerializableIterator<E> iterator() {
				return new SerializableEnumSetIterator<E>(sortedSet);	
			}
		}; 		
	}
	
	protected static <E> Iterable<E> wrapList(final List<E> list) {
		return new SerializableIterable<E>() {
			final private static long serialVersionUID = 1L;

			public SerializableIterator<E> iterator() {
				return new SerializableListIterator<E>(list);	
			}
		}; 		
	}
	
	protected static <E> Iterable<E> wrapSortedSet(final SortedSet<E> sortedSet) {
		return new SerializableIterable<E>() {
			final private static long serialVersionUID = 1L;

			public SerializableIterator<E> iterator() {
				return new SerializableSortedSetIterator<E>(sortedSet);	
			}
		}; 		
	}

}
