package com.prestonb.howto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ApplicationRunner {

	public void run() {
//		readFromConsole();
		resolvePathNames();
	}
	
	private void readFromConsole() {
		boolean exit = false;
		
		// Step 1: Get the console as an "input stream"
		Reader consoleInput = new InputStreamReader(System.in);
		BufferedReader br = null;
		
		try {

			// Step 2: Create a BufferedReader from the InputStreamReader
			// This allows reading full lines at a time
			br = new BufferedReader(consoleInput);

			while (exit == false) {
				
				// Step 3: Print to the console to tell the user what to do
				System.out.print("Enter 'exit' to quit application: ");

				// Step 4: Tell the BufferedReader to tell the InputStreamReader to 
				// wait for the user to enter the next line
				
				// BufferedReader -> InputStreamReader -> console -> [user types something]
				//																|
				//																|
				//																|
				// BufferedReader <- InputStreamReader <- console <-------------- 
				String input = br.readLine();

				// Step 5: Check if the user typed "exit"
				if (input == "exit") {
//				if ("exit".equals(input)) {
					exit = true;
					System.out.println("Fine I'll just leave then..");
				} else {
					
					// Step 6: Print what the user typed
					System.out.println("You typed : " + input);
					System.out.println("-----------\n");
				}
			}
			
		// If something unexpected happens an exception will be thrown and caught here
		} catch (IOException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
		} finally {
			
			// Step 7: VERY IMPORTANT! Close the input stream. If not it will stay open forever.
			// For a console InputStream this isn't a big deal but if reading from a file
			// it could keep a lock on the file and no one could edit it
			if (br != null) {
				try {
					System.out.println("Closing input stream");
					br.close();
				} catch (IOException e) {
					System.out.println("An error occured!");
					e.printStackTrace();
				}
			}
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
}
