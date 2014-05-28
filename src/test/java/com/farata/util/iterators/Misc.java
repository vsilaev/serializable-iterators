package com.farata.util.iterators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

public class Misc {
	
	static byte[] save(final Object o) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			oos.flush();
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (null != oos) {
				try {
					oos.close();
				} catch (final IOException ex) {
					
				}
			}
		}
		return bos.toByteArray();
	}
	
	static Object load(final byte[] serial) {
		final ByteArrayInputStream bis = new ByteArrayInputStream(serial);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		} catch (final ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (null != ois) {
				try {
					ois.close();
				} catch (final IOException ex) {
					
				}
			}
		}
	}
	
	static BigDecimal number(final long value) {
		return BigDecimal.valueOf(value);
	}
}
