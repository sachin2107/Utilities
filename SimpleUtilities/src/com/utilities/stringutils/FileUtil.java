package com.utilities.stringutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class FileUtil {

	public static String readFile(String filePath) {
		String result = null;
		try {
			result = readFile(filePath, StandardCharsets.UTF_8, "0");
		} catch (Exception e) {
			System.out.println("Error in readFile()" + e);
		}
		return result;
	}

	public static String readFile(String fileName, Charset charset, String transactionEnable) throws Exception {
		InputStream inputStream = FileUtil.class.getResourceAsStream(fileName);
		String result = fileName;
		try {
			if (null != inputStream) {
				try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
					result = buffer.lines().collect(Collectors.joining(System.lineSeparator()));
				}
			} else {
				File file = new File(fileName);
				if (file.exists()) {
					result = new String(Files.readAllBytes(file.toPath()), charset);
				}
			}
		} catch (Exception e) {
			System.out.println("Error...");
			throw new Exception();
		}
		return result;
	}
	
}
