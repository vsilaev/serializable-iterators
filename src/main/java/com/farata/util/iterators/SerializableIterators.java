package com.farata.util.iterators;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
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
			final EnumSet<?> eoriginal = asEnumSet(original);
			@SuppressWarnings("unchecked")
			final Iterable<E> result = (Iterable<E>)wrapEnumSet(eoriginal);
			return result;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static <E extends Enum<E>> EnumSet<E> asEnumSet(final Collection<?> collection) {
		return (EnumSet<E>)collection;
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
	
	protected static <E> Iterable<E> wrapList(final List<E> list) {
		return new SerializableIterable<E>() {
			final private static long serialVersionUID = 1L;

			@Override 
			public SerializableIterator<E> iterator() {
				return new SerializableListIterator<E>(list);	
			}
		}; 		
	}
	
	protected static <E> Iterable<E> wrapSortedSet(final SortedSet<E> sortedSet) {
		return new SerializableIterable<E>() {
			final private static long serialVersionUID = 1L;

			@Override 
			public SerializableIterator<E> iterator() {
				return new SerializableSortedSetIterator<E>(sortedSet);	
			}
		}; 		
	}
	
	protected static <E extends Enum<E>> Iterable<E> wrapEnumSet(final EnumSet<E> sortedSet) {
		return new SerializableIterable<E>() {
			final private static long serialVersionUID = 1L;

			@Override 
			public SerializableIterator<E> iterator() {
				return new SerializableEnumSetIterator<E>(sortedSet);	
			}
		}; 		
	}
}
