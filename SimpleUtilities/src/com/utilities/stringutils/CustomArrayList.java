package com.utilities.stringutils;

import java.util.Arrays;

public class CustomArrayList<E> {

	private int size = 0;
	
	private static final int DEFAULT_CAPACITY = 1;
	
	private Object[] elements;
	
	public CustomArrayList() {
		elements = new Object[DEFAULT_CAPACITY];
	}
	
	public void add(E e) {
		if(size == elements.length) {
			ensureCapacity();
		}
		elements[size++] = e;
	}
	
	public E get(int i) {
		if(i>size || i<0) {
			throw new IndexOutOfBoundsException("index = "+i +"  size="+size);
		}
		return (E)elements[i];
	}
	
	public void ensureCapacity() {
		int newSize = elements.length * 2;
		elements = Arrays.copyOf(elements, newSize);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0; i<elements.length; i++) {
			sb.append(elements[i]);
			if(i<size-1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		CustomArrayList<Integer> i = new CustomArrayList<>();
		i.add(1);
		i.add(2);
		i.add(1);
		i.add(2);
		System.out.println(i);
	}
	
	
}
