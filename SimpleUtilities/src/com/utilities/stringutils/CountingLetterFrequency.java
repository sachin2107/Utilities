package com.utilities.stringutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountingLetterFrequency {

	public static void main(String[] args) {
		String str = "sachinachsarch";

		char[] charArray = str.toCharArray();
		Set set = new LinkedHashSet<>();
		List list1 = new ArrayList<>();
		for (int i = 0; i < charArray.length; i++) {
			set.add(charArray[i]);
			list1.add(charArray[i]);
		}

		StringBuilder sb = new StringBuilder();
		Iterator itr = set.iterator();
		while(itr.hasNext()) {
			Character c = (Character)itr.next();
			sb.append(c);
			System.out.println(c+" "+Collections.frequency(list1, c));
		}

		System.out.println(sb);
	}
}
