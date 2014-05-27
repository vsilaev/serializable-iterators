package com.farata.util.iterators;

import java.io.Serializable;

abstract public interface SerializableIterable<E> extends Iterable<E>, Serializable {
	SerializableIterator<E> iterator();
}

