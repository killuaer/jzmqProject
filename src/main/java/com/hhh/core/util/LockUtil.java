package com.hhh.core.util;

import java.util.HashMap;
import java.util.Map;

public class LockUtil {
	private static final Map<String, Boolean> lockMap = new HashMap<>();

	public static boolean isLock(String key) {
		Boolean value = lockMap.get(key);
		return value != null && value;
	}

	public static synchronized void lock(String key) {
		lockMap.put(key, true);
	}

	public static synchronized void release(String key) {
		lockMap.remove(key);
	}
}
