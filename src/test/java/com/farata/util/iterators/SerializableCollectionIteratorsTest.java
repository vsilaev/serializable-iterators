package com.farata.util.iterators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import static com.farata.util.iterators.Misc.*;

/**
 * Unit test for serializable iterators.
 */
public class SerializableCollectionIteratorsTest {

	
	@Test
	public void testList() {
		byte[] data;
		
		final List<BigDecimal> numbers = new ArrayList<BigDecimal>(
			Arrays.asList(
				number(1000), 
				number(2000), 
				number(3000), 
				number(4000), 
				number(5000), 
				number(6000)
			)
		);
		
		final Iterator<BigDecimal> i0 = SerializableIterators.from(numbers).iterator();
		
		@SuppressWarnings("unused")
		final BigDecimal k1 = i0.next();
		@SuppressWarnings("unused")
		final BigDecimal k2 = i0.next();

		data = save(i0);
		@SuppressWarnings("unchecked")
		final Iterator<BigDecimal> i1 = (Iterator<BigDecimal>)load(data);
		final BigDecimal k3 = i1.next();
		final BigDecimal k4 = i1.next();
		Assert.assertEquals(number(3000), k3);
		Assert.assertEquals(number(4000), k4);
		
		data = save(i1);
		@SuppressWarnings("unchecked")
		final Iterator<BigDecimal> i2 = (Iterator<BigDecimal>)load(data);
		final BigDecimal k5 = i2.next();
		final BigDecimal k6 = i2.next();
		Assert.assertEquals(number(5000), k5);
		Assert.assertEquals(number(6000), k6);
		
		Assert.assertFalse(i2.hasNext());

	}
	
	@Test
	public void testSortedSet() {
		byte[] data;
		
		final SortedSet<String> strings = new TreeSet<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));
		final Iterator<String> i0 = SerializableIterators.from(strings).iterator();
		
		@SuppressWarnings("unused")
		final String a = i0.next();
		@SuppressWarnings("unused")
		final String b = i0.next();

		data = save(i0);
		@SuppressWarnings("unchecked")
		final Iterator<String> i1 = (Iterator<String>)load(data);
		final String c = i1.next();
		final String d = i1.next();
		Assert.assertEquals("C", c);
		Assert.assertEquals("D", d);
		
		data = save(i1);
		@SuppressWarnings("unchecked")
		final Iterator<String> i2 = (Iterator<String>)load(data);
		final String e = i2.next();
		final String f = i2.next();
		Assert.assertEquals("E", e);
		Assert.assertEquals("F", f);
		
		Assert.assertTrue(i2.hasNext());

	}
	
	@Test
	public void testEnumSet() {
		byte[] data;
		
		final Set<TestEnum> opts = EnumSet.range(TestEnum.A, TestEnum.G);
		final Iterator<TestEnum> i0 = SerializableIterators.from(opts).iterator();
		
		@SuppressWarnings("unused")
		final TestEnum a = i0.next();
		@SuppressWarnings("unused")
		final TestEnum b = i0.next();

		data = save(i0);
		@SuppressWarnings("unchecked")
		final Iterator<TestEnum> i1 = (Iterator<TestEnum>)load(data);
		final TestEnum c = i1.next();
		final TestEnum d = i1.next();
		Assert.assertEquals(TestEnum.C, c);
		Assert.assertEquals(TestEnum.D, d);
		
		data = save(i1);
		@SuppressWarnings("unchecked")
		final Iterator<TestEnum> i2 = (Iterator<TestEnum>)load(data);
		final TestEnum e = i2.next();
		final TestEnum f = i2.next();
		Assert.assertEquals(TestEnum.E, e);
		Assert.assertEquals(TestEnum.F, f);
		
		Assert.assertTrue(i2.hasNext());		
	}

}
