package com.farata.util.iterators;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.EnumSet;
import java.util.Iterator;

public class SerializableEnumSetIterator<E extends Enum<E>> implements SerializableIterator<E>, Externalizable {
	private EnumSet<E> set;
	private int latestElementOrdinal;
	
	transient private Iterator<E> delegate;
	
	public SerializableEnumSetIterator() {}
	
	public SerializableEnumSetIterator(final EnumSet<E> set) {
		this.set = set;
		this.latestElementOrdinal = -1;
		this.delegate = set.iterator();
	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(set);
		out.writeInt(latestElementOrdinal);
	}

	@SuppressWarnings("unchecked")
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		set = (EnumSet<E>)in.readObject();
		latestElementOrdinal = in.readInt();
		delegate = set.iterator();
		if (latestElementOrdinal >= 0) {
			// Advance iterator
			while (delegate.hasNext()) {
				final E entry = delegate.next();
				if (entry.ordinal() >= latestElementOrdinal) {
					// Should check for greater or equal -- same as SortedSet.tailSet
					// This way if iterator was serialized after iterator.remove call
					// the deserialized iterator will remain consistent
					break;
				}
			}
		}
	}

	public boolean hasNext() {
		return delegate.hasNext();
	}

	public E next() {
		final E result = delegate.next();
		latestElementOrdinal = result.ordinal();
		return result;
	}

	public void remove() {
		delegate.remove();
	}

}
