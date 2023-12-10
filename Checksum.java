/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Trent Gaymore
| Language: Java
|
| To Compile: javac pa02.java
|
| To Execute: java -> java pa02 inputFile.txt 8
|
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2022
| Instructor: McAlpin
| Due Date: per assignment
|
+=============================================================================*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class pa02 {

	String fileName = "";
	int bitSize = 0;
	static int  mod = 0;
	static int val = 0;
	
	public static String readFilename(File file, int bitNum) throws FileNotFoundException {
		
		String textArray = "";
		Scanner fr = new Scanner(file);

		/* Scan all lines from the file */
		while (fr.hasNextLine()) {

			textArray += fr.nextLine();
		}
		textArray += "\n";
		fr.close();

		switch (bitNum) {
		case 8:
			mod = 1;
			break;
		case 16:
			mod = 2;
			break;
		case 32:
			mod = 4;
			break;
		default:
			System.out.println("Valid checksum sizes are 8, 16, or 32");
		}
		return bitPadding(textArray, bitNum);
	}

	public static void print(String text) {
		System.out.println();

		for (int i = 1; i <= text.length(); i++) {
			System.out.print(text.charAt(i - 1));

			if (i % 80 == 0) {
				System.out.println();
			}
		}

		System.out.println();
	}

	public static String bitPadding(String text, int size) {

		if (size == 8) {

		} else if (size == 16) {
			if (text.length() % 2 != 0) {
				text += "X";
			}
		} else if (size == 32) {
			while (text.length() % 4 != 0) {
				text += "X";
			}
		}

        return text;
	}

	public static int checkSum(int size, String text) {
		  int calc = 0;

	        if (size == 8) {
	            for (int i = 0; i < text.length(); i++) {
	                calc += text.charAt(i);
	            }
	            calc %= 256;
	            
	        } else if (size == 16) {
	            for (int i = 0; i < text.length(); i++) {
	                
	                if (i % 2 == 0) {
	                    calc += text.charAt(i) * 256;
	                } else if (i % 2 != 0) {
	                    calc += text.charAt(i) * 1;
	                }
	            }

	            calc %= (int) (Math.pow(2, 16));
	        }

	        else if (size == 32) {
	            int j = 0;

	            for (int i = text.length() - 1; i >= 0; i--) {
	                
	                calc += text.charAt(i) * (int) Math.pow(2, (8 * (j % 4)));
	                ++j;
	            }
	            calc %= (int) (Math.pow(2, 32));
	        }
	        return calc;

	}

	public static void main(String[] args) throws FileNotFoundException {

		File file = new File(args[0]);
		int size = Integer.parseInt(args[1]);

		String text = readFilename(file, size);

		int finalResult = checkSum(size, text);

        print(text);
		System.out.printf("%2d bit checksum is %8x for all %4d chars\n", size, finalResult, text.length());

	}
}

/*=============================================================================
| I Trent Gaymore (tr791260) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+============================================================================*/