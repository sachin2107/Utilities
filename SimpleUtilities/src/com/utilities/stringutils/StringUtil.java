package com.utilities.stringutils;

import java.util.Collection;

public class StringUtil {

	public static String getTrimmedValue(String property) {
		if (isNotEmpty(property)) {
			return property.trim();
		}
		return null;
	}

	public static boolean isNotEmpty(Object object) {
		boolean flag = true;
		if (null == object) {
			flag = false;
		} else {
			if (object instanceof String) {
				String objString = (String) object;
				if (objString.trim().length() == 0) {
					flag = false;
				}
			}
			if (object instanceof Collection<?>) {
				Collection<?> objList = (Collection<?>) object;
				if (objList.isEmpty()) {
					flag = false;
				}
			}
		}
		return flag;
	}

	public static boolean isEmpty(Object obj) {
		return !isNotEmpty(obj);
	}

	public static boolean checkYNFlag(String value) {
		return (isNotEmpty(value) && ("YES".equalsIgnoreCase(value) || "Y".equalsIgnoreCase(value)
				|| "true".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value)));
	}

	public static String getStringFromObject(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		}
		return null;
	}

	public static void main(String[] args) {

	}
}
