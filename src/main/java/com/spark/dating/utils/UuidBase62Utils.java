package com.spark.dating.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class UuidBase62Utils {
	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	// 문자 → 인덱스 매핑 (성능 최적화)
	private static final int[] CHAR_TO_INDEX = new int[128];
	static {
		Arrays.fill(CHAR_TO_INDEX, -1);
		for (int i = 0; i < BASE62.length(); i++) {
			CHAR_TO_INDEX[BASE62.charAt(i)] = i;
		}
	}

	/** UUID → Base62 인코딩 */
	public static String toBase62(UUID uuid) {
		// UUID → 16바이트 배열
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());

		// BigInteger 변환 (양수 보장)
		BigInteger bigInt = new BigInteger(1, bb.array());

		// Base62 인코딩
		if (bigInt.equals(BigInteger.ZERO)) {
			return String.valueOf(BASE62.charAt(0));
		}

		StringBuilder sb = new StringBuilder();
		while (bigInt.compareTo(BigInteger.ZERO) > 0) {
			BigInteger[] divRem = bigInt.divideAndRemainder(BigInteger.valueOf(62));
			sb.append(BASE62.charAt(divRem[1].intValue()));
			bigInt = divRem[0];
		}
		return sb.reverse().toString();
	}

	/** Base62 → UUID 디코딩 (항상 16바이트 보장) */
	public static UUID fromBase62(String base62) {
		if (base62 == null || base62.isEmpty()) {
			throw new IllegalArgumentException("Base62 string must not be null or empty");
		}

		BigInteger bigInt = BigInteger.ZERO;
		for (int i = 0; i < base62.length(); i++) {
			char c = base62.charAt(i);
			int index = (c < 128) ? CHAR_TO_INDEX[c] : -1;
			if (index == -1) {
				throw new IllegalArgumentException("Invalid Base62 character: " + c);
			}
			bigInt = bigInt.multiply(BigInteger.valueOf(62)).add(BigInteger.valueOf(index));
		}

		// BigInteger → byte[]
		byte[] bytes = bigInt.toByteArray();
		byte[] uuidBytes = new byte[16];

		// 항상 뒤에서 16바이트만 복사 (17바이트일 경우 맨 앞 0x00 제거)
		int start = Math.max(0, bytes.length - 16);
		int length = Math.min(16, bytes.length);
		System.arraycopy(bytes, start, uuidBytes, 16 - length, length);

		ByteBuffer bb = ByteBuffer.wrap(uuidBytes);
		return new UUID(bb.getLong(), bb.getLong());
	}
}
