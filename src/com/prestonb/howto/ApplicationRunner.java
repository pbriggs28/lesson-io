package com.prestonb.howto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

@SuppressWarnings("unused")
public class ApplicationRunner {

	public static void main(String[] args) {
		ApplicationRunner app = new ApplicationRunner();
		app.run();
	}

	public void run() {
//		writeToConsole();
//		readFromConsole();
//		readFromConsole();
//		resolvePathNames();
//		writeToFile();
		readFromFile();
	}
	
	private void writeToConsole() {
		System.out.println("Hello world!");
	}
	
	private void readFromConsoleSimple() {
		try {
			InputStreamReader consoleInput = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(consoleInput);
			System.out.print("Type something: ");
			String input = br.readLine();
			System.out.println("You typed : " + input);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private void readFromConsole() {
		boolean exit = false;
		InputStreamReader consoleInput = new InputStreamReader(System.in);
		try {
			BufferedReader br = new BufferedReader(consoleInput);
			while (exit == false) {
				System.out.print("Enter 'exit' to quit application: ");
				String input = br.readLine();

//				if (input == "exit") {
				if (input.equals("exit")) {
					exit = true;
					System.out.println("Fine I'll just leave then..");
				} else {
					
					System.out.println("You typed : " + input);
					System.out.println("-----------\n");
				}
			}
		// If something unexpected happens an exception will be thrown and caught here
		} catch (IOException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
		}
	}
	
	private void resolvePathNames() {
		String noPrefixPath = 		"input/input.txt";
		String dotSlashPrefixPath = "./input/input.txt";
		String dotDotSlashPrefixPath = "../input/input.txt";
		String slashPrefixPath = 	"/input/input.txt";

		File noPrefixFile = new File(noPrefixPath);
		File dotDotSlashPrefixFile = new File(dotDotSlashPrefixPath);
		File dotSlashPrefixFile = new File(dotSlashPrefixPath);
		File slashPrefixFile = new File(slashPrefixPath);

		System.out.println(String.format("[%s]\t normalizes to [%s]", noPrefixFile.getAbsolutePath(), noPrefixFile.getAbsoluteFile().toPath().normalize()));
		System.out.println(String.format("[%s]\t normalizes to [%s]", dotDotSlashPrefixFile.getAbsolutePath(), dotDotSlashPrefixFile.getAbsoluteFile().toPath().normalize()));
		System.out.println(String.format("[%s]\t normalizes to [%s]", dotSlashPrefixFile.getAbsolutePath(), dotSlashPrefixFile.getAbsoluteFile().toPath().normalize()));
		System.out.println(String.format("[%s]\t\t\t\t\t\t\t normalizes to [%s]", slashPrefixFile.getAbsolutePath(), slashPrefixFile.getAbsoluteFile().toPath().normalize()));
		System.out.println();
		System.out.println(String.format("[%s]\t resolves to [%s]", noPrefixFile.getPath(), noPrefixFile.getAbsoluteFile().toPath().normalize()));
		System.out.println(String.format("[%s]\t resolves to [%s]", dotDotSlashPrefixFile.getPath(), dotDotSlashPrefixFile.getAbsoluteFile().toPath().normalize()));
		System.out.println(String.format("[%s]\t resolves to [%s]", dotSlashPrefixFile.getPath(), dotSlashPrefixFile.getAbsoluteFile().toPath().normalize()));
		System.out.println(String.format("[%s]\t resolves to [%s]", slashPrefixFile.getPath(), slashPrefixFile.getAbsoluteFile().toPath().normalize()));
		
	}
	
	private void writeToFile() {

		// Step 1: Create the content to write to the file
		String content = "This is the first line of content\n\tThis is the second line of content which is indented";
		// Backslash n (\n) prints a new line character
		// Backslash t (\t) prints a tab character
		// Result:
		// This is the first line of content
		// 		This is the second line of content which is indented

		// Step 2: Set the file path
		String filePath = "output/output.txt"; // Current directory
//		String filePath = "./output/output.txt"; // Current directory
//		String filePath = "../output/output.txt"; // Up one directory
//		String filePath = "output/../output.txt"; // What about this one??
//		String filePath = "/output/output.txt"; // Root directory (C drive)
//		String filePath = System.getProperty("user.home") + "/output/output.txt"; // C:/Users/pbriggs/output/output.txt

		// Step 3: Create the file *object*
		// This only creates an object *holding information* about a file
		// It does not actually create a file on hard disk
		File file = new File(filePath);
		
		// Step 4: *Define* the FileWriter and BufferedWriter OUTSIDE of the try/catch block
		// This must be defined *outside* or it cannot be accessed (closed) in the finally block
		Writer fileWriter = null;
		Writer bufferedWriter = null;

		try {
			
			// Step 5: Create the file:
				// Step 5.1: Create the directories
			file.getParentFile().mkdirs();
			
				// Step 5.2: Make the file
			file.createNewFile();
			
			// Step 6: Create the FileWriter passing in the File object
			fileWriter = new FileWriter(file);
			
			// Step 7: Create a new BufferedWriter that wraps around our FileWriter
			bufferedWriter = new BufferedWriter(fileWriter);
			
			// Step 8: Write the content to the BufferedWriter which in turn writes it to the FileWriter
			// BufferedWriter -> FileWriter -> [writes to hard disk]
			//											|
			//											|
			//											|
			// BufferedWriter <- FileWriter <------------ 
			bufferedWriter.write(content);

			// Optional: We can manually flush the contents or they will be flushed on .close()
//			bufferedWriter.flush();
			// If an exception occurs during the write, this line will never execute
			System.out.println(String.format("Successfully wrote file to [%s]!", file.getAbsoluteFile().toPath().normalize()));

		} catch (IOException e) {
			System.out.println("An error occurred writing the file!");
			e.printStackTrace();
		} finally {
			// Step 8: Close the BufferedWriter and FileWriter
			// File locks can occur if you don't do this!
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException ex) {
				System.out.println("An error occurred closing the BufferedWriter");
				ex.printStackTrace();
			}
		}
	}
	
	private void readFromFile() {
		// Step 1: Set the file path
		String filePath = "input/input.txt";
		
		// Step 2: Define a BufferedReader and Reader *outside* of try/catch block
		BufferedReader bufferedReader = null;
		Reader fileReader = null;

		try {

			// Step 3: Create a FileReader from the 
			fileReader = new FileReader(filePath);
			
			// Step 4: Create a BufferedReader from the FileReader
			bufferedReader = new BufferedReader(fileReader);

			// Step 5: Read the current line
			String sCurrentLine = bufferedReader.readLine();

			// Step 6: Continue reading the next line until the EOF (End of File)
			while (sCurrentLine != null) {
				System.out.println(sCurrentLine);
				sCurrentLine = bufferedReader.readLine();
			}

		} catch (IOException e) {
			System.out.println("Error reading from file.");
			e.printStackTrace();
		} finally {
			try {
				// Step 7: Close the BufferedReader and FileReader
				if (bufferedReader != null)
					bufferedReader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException ex) {
				System.out.println("Error closing BufferedReader or FileReader");
				ex.printStackTrace();
			}
		}
	}
}
