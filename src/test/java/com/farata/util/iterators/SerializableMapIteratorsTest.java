package com.farata.util.iterators;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import static com.farata.util.iterators.Misc.*;

public class SerializableMapIteratorsTest {
	
	@Test
	public void testSortedMapMods() {
		final SortedMap<String, Integer> map = new TreeMap<String, Integer>();
		map.put("A", Integer.valueOf(1));
		map.put("B", Integer.valueOf(2));
		map.put("C", Integer.valueOf(3));
		map.put("D", Integer.valueOf(4));
		map.put("E", Integer.valueOf(5));
		map.put("F", Integer.valueOf(6));
		
		final Iterator<String> ik = SerializableIterators.fromKeys(map).iterator();
		final String a = ik.next();
		Assert.assertEquals(a, "A");
		final String b = ik.next();
		Assert.assertEquals(b, "B");
		
		// remove "B"
		ik.remove();
		
		Assert.assertFalse(map.containsKey("B"));
		
		final Iterator<Integer> iv = SerializableIterators.fromValues(map).iterator();
		final Integer v1 = iv.next();
		Assert.assertEquals(v1, new Integer(1));
		final Integer v3 = iv.next();
		Assert.assertEquals(v3, new Integer(3));
		
		// Remove by value "3", i.e. key is "C"
		iv.remove();
		
		Assert.assertFalse(map.containsKey("C"));
		
		final Iterator<Map.Entry<String, Integer>> ie = SerializableIterators.fromEntries(map).iterator();
		final Map.Entry<String, Integer> e1 = ie.next();
		Assert.assertEquals(e1.getKey(), "A");
		Assert.assertEquals(e1.getValue(), new Integer(1));
		
		e1.setValue(Integer.valueOf(255));
		Assert.assertTrue(map.containsValue(new Integer(255)));

		
		final Map.Entry<String, Integer> e4 = ie.next();
		Assert.assertEquals(e4.getKey(), "D");
		Assert.assertEquals(e4.getValue(), new Integer(4));
		
		ie.remove();
		
		Assert.assertFalse(map.containsValue(Integer.valueOf(4)));

	}
	
	@Test
	public void testSortedMapSerialization() {
		final SortedMap<String, Integer> map = new TreeMap<String, Integer>();
		map.put("A", Integer.valueOf(1));
		map.put("B", Integer.valueOf(2));
		map.put("C", Integer.valueOf(3));
		map.put("D", Integer.valueOf(4));
		map.put("E", Integer.valueOf(5));
		map.put("F", Integer.valueOf(6));
		
		int count = 0;
		final Iterator<Map.Entry<String, Integer>> ie0 = SerializableIterators.fromEntries(map).iterator();
		for (; ie0.hasNext() && count < 3; ) {
			final Map.Entry<String, Integer> e = ie0.next();
			count++;
			Assert.assertEquals(e.getValue(), Integer.valueOf(count));
		}
		
		final byte[] data = save(ie0);
		@SuppressWarnings("unchecked")
		final Iterator<Map.Entry<String, Integer>> ie1 = (Iterator<Map.Entry<String, Integer>>)load(data);
		while (ie1.hasNext()) {
			final Map.Entry<String, Integer> e = ie1.next();
			count++;
			Assert.assertEquals(e.getValue(), Integer.valueOf(count));
		}
	}
}
