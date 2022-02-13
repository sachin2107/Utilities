package com.utilities.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ReplaceInFileFromMap {

	public static void main(String[] args) {
		try {
			Map<String, String> templateMap = new HashMap<String, String>();
			templateMap.put("###username###", "Sachin");
			templateMap.put("###password###", "SachinKhachanepwd");

			String templateFile = new String(Files.readAllBytes(
					Paths.get("D:\\Ankita_Programs\\Sachin_Programs_Citi\\SimpleUtilities\\resources\\sample.txt")));
			String finalFile = templateMap.entrySet().stream()
					.map(e -> (Function<String, String>) s -> s.replaceAll(e.getKey(), e.getValue()))
					.reduce(Function.identity(), Function::andThen).apply(templateFile);
			System.out.println(finalFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
