package com.farata.util.iterators;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.ListIterator;

public class SerializableListIterator<E> implements SerializableIterator<E>, ListIterator<E>, Externalizable {
	
	private List<E> list;
	private int position;
	
	transient
	private ListIterator<E> delegate;

	public SerializableListIterator() {}
	
	public SerializableListIterator(final List<E> list) {
		this.list = list;
		this.position  = 0;
		this.delegate = list.listIterator();
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(list);
		out.writeInt(position);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		list = (List<E>)in.readObject();
		position = in.readInt();
		delegate = list.listIterator(position);
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public E next() {
		final E result = delegate.next();
		position++;
		return result;
	}

	@Override
	public void remove() {
		delegate.remove();
		
	}

	@Override
	public boolean hasPrevious() {
		return delegate.hasPrevious();
	}

	@Override
	public E previous() {
		final E result = delegate.previous();
		position--;
		return result;
	}

	@Override
	public int nextIndex() {
		return delegate.nextIndex();
	}

	@Override
	public int previousIndex() {
		return delegate.previousIndex();
	}

	@Override
	public void set(final E e) {
		delegate.set(e);
	}

	@Override
	public void add(E e) {
		delegate.add(e);
	}
	
}