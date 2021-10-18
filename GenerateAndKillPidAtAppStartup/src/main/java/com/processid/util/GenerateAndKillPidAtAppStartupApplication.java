package com.processid.util;

import static com.processid.util.AppUtil.isNotEmpty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class GenerateAndKillPidAtAppStartupApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateAndKillPidAtAppStartupApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void applicationStartEvent() {
		String pidFilePath = AppUtil.getPidFilePath();
		String serviceWithPID = AppUtil.getServiceWithPID();

		if (isNotEmpty(pidFilePath) && isNotEmpty(serviceWithPID)) {
			appendContentToFile(pidFilePath, serviceWithPID);
		}
	}

	public static void appendContentToFile(String filePath, String message) {
		File file = new File(filePath);
		if (!file.exists()) {
			createFile(file);
		}
		try (FileWriter fileWriter = new FileWriter(file, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
			printWriter.println(message);
		} catch (IOException e) {
			System.out.println("Error occur while file writing = " + e);
		}
	}

	private static void createFile(File filePath) {
		try {
			if (!filePath.getParentFile().mkdirs() || !filePath.createNewFile()) {
				System.out.println("Error occur while filePath creation...");
			}
		} catch (IOException e) {
			System.out.println("Error occur while filePath Creation = " + e);
		}
	}

	private void removePIDFromFIle() throws IOException {
		String pidFilePath = AppUtil.getPidFilePath();
		String serviceWithPID = AppUtil.getServiceWithPID();
		if (isNotEmpty(pidFilePath) && isNotEmpty(serviceWithPID)) {
			removeLineFromFile(pidFilePath, serviceWithPID);
		} else {
			System.out.println("PID file path or service name is not found...");
		}
	}

	public static void removeLineFromFile(String filePath, String contentToDelete) throws IOException {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			try (Stream<String> fileStream = Files.lines(file.toPath())) {
				List<String> fileLines = fileStream.filter(line -> !line.equalsIgnoreCase(contentToDelete))
						.collect(Collectors.toList());
				Files.write(file.toPath(), fileLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
			}
		} else {
			System.out.println("File doesn't exist at given filePath...");
		}
	}
}
