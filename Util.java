package Utils;

import java.util.List;

/**
 * Contains utility functions.
 * 
 * @author dmitryrogozhin
 */
public class Util
{

	public static int findOrder(int x, int n)
	{
		if(x > n)
		{
			return Util.findOrderMod(x, n);
		}
		
		if(Util.gcd(x, n) > 1)
		{
			return Util.findOrderMod(x, n);
		}
		
		int power = (int) Math.floor(2 * Util.log2(n) + 1);
		
		// Numbers > 2^31 won't fit into int data type,
		// Numbers > 2^16 will drain too much of the classical computational
		// resources for not much gain.
		// Hence, we perform the modulus order finding process instead
		if(power > 16)
		{
			return Util.findOrderMod(x, n);
		}

		// If we get here, q is within the int data type limit
		int q = Util.twoTo(power);
				
		Complex[] f = new Complex[q];
		for(int i = 0; i < q; i++)
		{
			double dummy = Util.modPow(x, i + 1, n);
			f[i] = new Complex(dummy, 0);
		}
		
		double[] gAbs = Complex.abs(Fourier.fft(f));
				
		int firstLocalMaxIndex = 1 + Util.findFirstLocalMaxIndex(gAbs);
		
		return (int) Math.round((double) q / firstLocalMaxIndex);
	}
		
	public static int findOrderMod(int x, int n)
	{
		int power = 1;
		
		while(power < n)
		{
			if(Util.modPow(x, power, n) == 1)
			{
				return power;
			}
			
			++power;
		}
		
		return 0;
	}
	
	public static int mod(int x, int n)
	{
		if(n == 0)
		{
			return x;
		}

		return modPow(x, 1, n);
	}
		
	public static int modPow(int x, int power, int n)
	{
		if(n == 0)
		{
			return x;
		}

		int retval = 1;
		
		for(int i = 0; i < power && retval > 0; i++)
		{
			retval = (retval % n) * (x % n) % n;
		}
		
		return retval;
	}
		
	/**
	 * Iterative implementation of Euclidean algorithm.<p>
	 * 
	 * Theorem 1: Subtracting the smaller number from the larger number doesn't change the GCD.<br>
	 * Theorem 2: If the smaller number exactly divides the larger number, the smaller number is the GCD. 
	 * 
	 * @param num1 first number
	 * @param num2 second number
	 * @return Greatest Common Divisor (GCD) of the two numbers.
	 */
	public static int gcd(int num1, int num2)
	{
		int maxNum = Math.max(num1, num2);
		int minNum = Math.min(num1, num2);
		int remainder;
		
		do
		{
			remainder = Util.mod(maxNum, minNum);
			maxNum = minNum;
			minNum = remainder;
		} 
		while(remainder > 0);
		
		return maxNum;
	}
		
	public static double log2(int num)
	{
		return Math.log(num) / Math.log(2);
	}
	
	public static int twoTo(int exp)
	{
		return (int) Math.pow(2, exp);
	}
		
	public static boolean[] generatePrimesUpTo(int n)
	{
		boolean[] primes = new boolean[n + 5];
		
		primes[2] = true;
		primes[3] = true;
		
		// Generate all 6k+1 and 6k-1 prime candidates
		for(int i = 5; i <= n; i += 6)
		{
			primes[i] = true;
			primes[i + 2] = true;
		}
		
		// Strip out all composites
		for(int i = 0; i <= n; i++)
		{
			if(primes[i])
			{
				int k = i;
				for(int j = k * k; j <= n && j > 0; j+= k)
				{
					primes[j] = false;
				}
			}
		}
				
		return primes;
	}
	
	public static String getPrimeStr(List<Integer> primes)
	{
		StringBuilder sb = new StringBuilder();
		if(!primes.isEmpty())
		{
			sb.append(primes.get(0));
			for(int i = 1; i < primes.size(); i++)
			{
				sb.append(", ");
				sb.append(primes.get(i).toString());
			}
			sb.append(".");
		}
		
		return sb.toString();
	}

	public static int findFirstLocalMaxIndex(double[] array)
	{
		for(int i = 1; i < array.length - 1; i++)
		{
			if(array[i] > array[i - 1]
					&& array[i] > array[i + 1])
			{
				return i;
			}
		}
		
		return -1;
	}

}
