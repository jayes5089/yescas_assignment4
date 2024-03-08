/* Jason Yescas
   Object-Oriented Programming : CPSC-24500-001
   3/7/2024
   Assignment 4
   Purpose: To read file contents using a 2D array */

import java.util.Scanner;
import java.util.stream.Stream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Assignment4 {
	/** 
	 * Counts the number of lines in a text file
	 * 
	 * @param filename
	 * @return number of lines in a text file
	 * @throws Exception
	 */
	
	public static int getNoLines(String filename) throws Exception {
		// This opens the file and uses Stream to count the lines
		try (Stream<String> fileStream = Files.lines(Paths.get(filename))) {
			// Return the number of lines
			return (int) fileStream.count();
		}
	}
	
	/** 
	 * Creates a 2D array from a text file
	 * 
	 * @param filename
	 * @return 2D array (jagged array), where each row in the array contains 
	 * the values in one line of the file. The length of each row depends on
	 * the number of values in each line of the file
	 * @throws Exception
	 */
	
	public static int[][] create2DArray(String filename) throws Exception {
		// Finds the number of lines in the file to start
		int lines = getNoLines(filename);
		
		// Creates an outer array according to the number of lines
		int[][] arr = new int[lines][];
		
		// Starts reading the actual data with scanner
		try (Scanner scan = new Scanner(new File(filename))) {
			int lineIndex = 0;
			// Reads until the end of the file
			while (scan.hasNextLine()) {
				// This essentially trims the whitespace from the beginning and end of rows
				// and turns the numbers into an array of strings to be able to put them back afterward.
				String[] numbers = scan.nextLine().trim().split("\\s+");
				arr[lineIndex] = new int[numbers.length];
				
				// This converts the String numbers back to integers to be able to put them into arrays
				for (int i = 0; i < numbers.length; i++) {
					arr[lineIndex][i] = Integer.parseInt(numbers[i]);
				}
				// Go to next row
				lineIndex++;
			}
		}
		return arr;
	}
	
	/**
	 * Finds the index of the longest row in a 2D array
	 * 
	 * @param arr the 2D array to search
	 * @return The index of the longest row
	 */
	
	public static int findLongestRow(int[][] arr) {
		int maxLength = 0;
		int longestRowIndex = 0;
		
		// This goes through every row
		for (int i = 0; i < arr.length; i++) {
			// This checks if the row that is being searched is the longest
			if (arr[i].length > maxLength) {
				// If it was the longest row, update it to be recognized as such
				maxLength = arr[i].length;
				longestRowIndex = i;
			}
		}
		return longestRowIndex;
	}
	
	/**
	 * Finds the max value within a row
	 * 
	 * @param arr the 2D array
	 * @param rowIndex the index of the row to search
	 * @return the max value in the row
	 */
	
	public static int findMaxInRow (int[][] arr, int rowIndex) {
		// This assumes the very first value in the row is max
		int max = arr[rowIndex][0];
		// This goes through each value in the row
		for (int value : arr[rowIndex]) {
			// If it finds a larger value, it updates the original max value
			if (value > max) {
				max = value;
			}
		}
		return max;
	}
	
	/**
	 * Finds the max value in the whole file
	 * 
	 * @param arr the 2D array
	 * @return the max value in the array
	 */
	public static int findMax(int[][]arr) {
		// This assumes that the very first value is max
		int max = arr[0][0];
		// This goes through each row
		for(int[] row : arr) {
			// This goes through each value in a row
			for(int value : row) {
				// If it finds a larger value, it updates the original max value
				if (value > max) {
					max = value;
				}
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		String filename = null;
		if (args.length < 1) {
			System.out.println("usage: Assignment4 inputFilename");
			// This terminates the program if there's no file to search
			System.exit(0);
		}
		filename = args[0];
		int[][] arr = null;
		try {
			// Display results from every method.
			System.out.println("Number of lines in the file = "+ getNoLines(filename));
			arr = create2DArray(filename);
			int longestRow = findLongestRow(arr);
			System.out.println("Longest row in the file is: "+ (longestRow + 1)+ " ,with length of: "+ arr[longestRow].length);
			System.out.println("Max value in first row = "+ findMaxInRow(arr, 0));
			System.out.println("Max value in file = "+ findMax(arr));
		}
		catch (Exception e) {
			System.out.print(e);
		}
	}
}