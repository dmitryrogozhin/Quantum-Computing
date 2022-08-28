package Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Algorithm.Shor;
import Utils.Util;

public class Test
{

	private static void runtimeTest(int first, int last) throws IOException
	{
		File resFile = new File(String.valueOf(first) + " - " + String.valueOf(last) + ".txt");
		FileWriter text = new FileWriter(resFile);

		File csvFile = new File(String.valueOf(first) + " - " + String.valueOf(last) + ".csv");
		FileWriter csv = new FileWriter(csvFile);
		
		for(int i = first; i <= last; i++) 
		{
			System.out.println(i);
						
			// No optimizations
			long start = System.currentTimeMillis();
			List<Integer> primeFactors = Shor.factor(i);
			long end = System.currentTimeMillis();
			String factorsStr = Util.getPrimeStr(primeFactors);

			// Turbo Mode
			long startTurbo = System.currentTimeMillis();
			List<Integer> primeFactorsTurbo = Shor.turboFactor(i);
			long endTurbo = System.currentTimeMillis();
			String factorsTurboStr = Util.getPrimeStr(primeFactorsTurbo);
			
			text.write("num: " + i + "\n" 
					+ "\t Shor time: " + (end - start) + "\tFactors: " + factorsStr + "\n" 
					+ "\tTurbo time: " + (endTurbo - startTurbo) + "\tFactors: " + factorsTurboStr + "\n");
			
			StringBuilder line = new StringBuilder();
			line.append(i);
			line.append(',');
			line.append(end - start);
			line.append(',');
			line.append(endTurbo - startTurbo);
			line.append("\n");
			
			csv.write(line.toString());

		}
		
		text.flush();
		text.close();
		csv.flush();
		csv.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		for(int i = 1; i <= 11000; i += 100)
		{
			runtimeTest(i + 1, i + 100);
		}
	}

}
