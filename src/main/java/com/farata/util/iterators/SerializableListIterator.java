package com.farata.util.iterators;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.ListIterator;

public class SerializableListIterator<E> implements SerializableIterator<E>, ListIterator<E>, Externalizable {
	
	private List<E> list;
	
	transient
	private ListIterator<E> delegate;

	public SerializableListIterator() {}
	
	public SerializableListIterator(final List<E> list) {
		this.list = list;
		this.delegate = list.listIterator();
	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(list);
		out.writeInt(delegate.nextIndex());
	}

	@SuppressWarnings("unchecked")
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		list = (List<E>)in.readObject();
		delegate = list.listIterator(in.readInt());
	}

	public boolean hasNext() {
		return delegate.hasNext();
	}

	public E next() {
		return delegate.next();
	}

	public void remove() {
		delegate.remove();
		
	}

	public boolean hasPrevious() {
		return delegate.hasPrevious();
	}

	public E previous() {
		return delegate.previous();
	}

	public int nextIndex() {
		return delegate.nextIndex();
	}

	public int previousIndex() {
		return delegate.previousIndex();
	}

	public void set(final E e) {
		delegate.set(e);
	}

	public void add(E e) {
		delegate.add(e);
	}
	
}