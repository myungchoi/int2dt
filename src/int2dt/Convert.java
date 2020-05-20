package int2dt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Convert {
	public static final String delimiter = "\t";

	private static String dtFormat(String numberedDate) {
		return numberedDate.substring(0, 4) + "-" + numberedDate.substring(4, 6) + "-" + numberedDate.substring(6, 8);
	}

	public static void convert(String inputFile, String outputFile) {
		try {
			File inFile = new File(inputFile);
			FileReader fr = new FileReader(inFile);
			BufferedReader br = new BufferedReader(fr);

			int bufferSize = 8 * 1024;
			File outFile = new File(outputFile);
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile), bufferSize);

			String line = "";
			String[] tempArr;
			int i = 0;
			while ((line = br.readLine()) != null) {
				tempArr = line.split(delimiter, -1);
//				int j = 0;
//				for (String str : tempArr) {
//					System.out.println(j++ + ": " + str);
//				}
//				System.out.println();

				if (i > 0) {
					if ("CONCEPT.csv".equals(inputFile)) {
						// concept table. date columns are 7 and 8 column
						if (tempArr[7] != null && !tempArr[7].isEmpty()) {
							tempArr[7] = dtFormat(tempArr[7]);
						}
						if (tempArr[8] != null && !tempArr[8].isEmpty()) {
							tempArr[8] = dtFormat(tempArr[8]);
						}
					} else if ("CONCEPT_RELATIONSHIP.csv".equals(inputFile)) {
						if (tempArr[3] != null && !tempArr[3].isEmpty()) {
							tempArr[3] = dtFormat(tempArr[3]);
						}
						if (tempArr[4] != null && !tempArr[4].isEmpty()) {
							tempArr[4] = dtFormat(tempArr[4]);
						}
					} else if ("DRUG_STRENGTH.csv".equals(inputFile)) {
						if (tempArr[9] != null && !tempArr[9].isEmpty()) {
							tempArr[9] = dtFormat(tempArr[9]);
						}
						if (tempArr[10] != null && !tempArr[10].isEmpty()) {
							tempArr[10] = dtFormat(tempArr[10]);
						}
					} else {
						// We can't do this. exit
						System.out.println("We only convert datas for CONCEPT.csv, CONCEPT_RELATIONSHIP.csv, or DRUG_STRENGTH.csv");
						System.exit(2);
					}
				}

				String toWrite = "";
				for (String item : tempArr) {
					if (item == null) {
						toWrite = toWrite.concat("" + "\t");
					} else {
						toWrite = toWrite.concat(item + "\t");
					}
				}

				toWrite = toWrite.substring(0, toWrite.length() - 1);
				bw.write(toWrite + "\n");
				bw.flush();
				i++;

				if ((i % 100000) == 0) {
					System.out.print("\rline #" + i + " Completed");
				}
			}
			
			System.out.println("\r" + i + " lines Completed");
			br.close();
			bw.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String inputFile = null;
		String outputFile = null;

		boolean isInputFile = false;
		boolean isOutputFile = false;
		for (String arg : args) {
			if ("-i".equals(arg)) {
				isInputFile = true;
				continue;
			}

			if ("-o".equals(arg)) {
				isOutputFile = true;
				continue;
			}

			if (isInputFile) {
				inputFile = arg;
				isInputFile = false;
			}

			if (isOutputFile) {
				outputFile = arg;
				isOutputFile = false;
			}
		}

		if (inputFile == null || outputFile == null || inputFile.isEmpty() || outputFile.isEmpty()) {
			System.out.println(
					"Error: Both input and output file must exist\nUsage int2dt -i <input file> -o <output file>\n<input file>: must be one of CONCEPT.csv, CONCEPT_RELATIONSHIP.csv, or DRUG_STRENGTH.csv");
			System.exit(1);
		}

		convert(inputFile, outputFile);

	}

}
