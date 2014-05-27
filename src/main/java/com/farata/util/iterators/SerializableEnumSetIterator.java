package com.farata.util.iterators;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.EnumSet;
import java.util.Iterator;

public class SerializableEnumSetIterator<E extends Enum<E>> implements SerializableIterator<E>, Externalizable {
	private EnumSet<E> set;
	private int latestElementIdx;
	
	transient private Iterator<E> delegate;
	
	public SerializableEnumSetIterator() {}
	
	public SerializableEnumSetIterator(final EnumSet<E> set) {
		this.set = set;
		this.latestElementIdx = -1;
		this.delegate = set.iterator();
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(set);
		out.writeInt(latestElementIdx);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		set = (EnumSet<E>)in.readObject();
		latestElementIdx = in.readInt();
		delegate = set.iterator();
		if (latestElementIdx >= 0) {
			// Advance iterator
			while (delegate.hasNext()) {
				final E entry = delegate.next();
				if (entry.ordinal() == latestElementIdx) {
					break;
				}
			}
		}
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public E next() {
		final E result = delegate.next();
		latestElementIdx = result.ordinal();
		return result;
	}

	@Override
	public void remove() {
		delegate.remove();
	}

}
