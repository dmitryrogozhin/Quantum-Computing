package Algorithm;

import java.util.ArrayList;
import Utils.Util;

/**
 * Contains the main logic of Shor's Quantum Algorithm for factoring
 * integers into their prime components.
 * 
 * @author dmitryrogozhin
 */
public class Shor
{
	private static boolean[] primeList;
		
	public static ArrayList<Integer> factor(int n)
	{
		return calculatePrimeFactors(n);
	}
		
	public static ArrayList<Integer> turboFactor(int n)
	{
		primeList = Util.generatePrimesUpTo(n);
		return turboCalculatePrimeFactors(n);
	}
	
	/**
	 * Implementation of Shor's Factoring Algorithm without optimization.
	 * 
	 * @param n the number to factor
	 * @return a list of prime factors of n
	 */
	private static ArrayList<Integer> calculatePrimeFactors(int n)
	{
		ArrayList<Integer> primes = new ArrayList<>();
				
		if(n == 2)
		{
			primes.add(n);
		}
		else
		{			
			for(int x = 2; x <= Math.sqrt(n); x++)
			{
				int t = Util.gcd(x, n);
				
				if(t > 1 && t < n)
				{					
					primes.addAll(calculatePrimeFactors(t));
					primes.addAll(calculatePrimeFactors(n / t));
					return primes;
				}
				else
				{
					int k = Util.findOrder(x, n);
					
					if(Util.gcd(k, 2) == 2)
					{
						int dum = (int) Math.pow(x, k / 2);

						// Note: The value of dum is capped by Integer.MAX_VALUE
						// Hence, if dum == Integer.MAX_VALUE, then (dum + 1)
						// will overflow and become a negative number.
						int r1 = dum - 1;
						int r2 = dum + 1;
						
						// Verify that r1 and r2 are both > 0
						// to avoid unnecessary (erroneous) calculations.
						if(r1 > 0 && r2 > 0 && r1 * r2 <= n)
						{
							int t1 = Util.gcd(r1, n);
							int t2 = Util.gcd(r2, n);
							int t1t2 = t1 * t2;
							
							if(t1 > 1 && t2 > 1 && t1t2 < n)
							{
								primes.addAll(calculatePrimeFactors(t1));
								primes.addAll(calculatePrimeFactors(t2));
								primes.addAll(calculatePrimeFactors(n / (t1t2)));
								return primes;
							}
							else if(t1 > 1 && t1 < n)
							{
								primes.addAll(calculatePrimeFactors(t1));
								primes.addAll(calculatePrimeFactors(n / t1));
								return primes;
							}
							else if(t2 > 1 && t2 < n)
							{
								primes.addAll(calculatePrimeFactors(t2));
								primes.addAll(calculatePrimeFactors(n / t2));
								return primes;
							}
						}
					}
				}
			}
			primes.add(n);
		}

		return primes;
	}

	/**
	 * Implementation of Shor's Factoring Algorithm with optimization.
	 * 
	 * @param n the number to factor
	 * @return a list of prime factors of n
	 */
	private static ArrayList<Integer> turboCalculatePrimeFactors(int n)
	{
		ArrayList<Integer> primes = new ArrayList<>();
		
		/*
		 * TURBO MODE
		 *
		 * If the incoming integer is prime there is no need to run the full Shor's algorithm.
		 * Instead, the classical primality check is performed.
		 */
		if(primeList[n])
		{
			primes.add(n);
			return primes; // bail out early!
		}			
		
		// Note that the original Shor's if(n==2) case is not needed anymore
		for(int x = 2; x <= Math.sqrt(n); x++)
		{
			int t = Util.gcd(x, n);
			
			if(t > 1 && t < n)
			{
				primes.addAll(turboCalculatePrimeFactors(t));
				primes.addAll(turboCalculatePrimeFactors(n / t));
				return primes;
			}
			else
			{
				int k = Util.findOrder(x, n);

				if(Util.gcd(k, 2) == 2)
				{
					int dum = (int) Math.pow(x, k / 2);
					
					// Note: The value of dum is capped by Integer.MAX_VALUE
					// Hence, if dum == Integer.MAX_VALUE, then (dum + 1)
					// will overflow and become a negative number.
					int r1 = dum - 1;
					int r2 = dum + 1;
					
					// Verify that r1 and r2 are both > 0
					// to avoid unnecessary (erroneous) calculations.
					if(r1 > 0 && r2 > 0 && r1 * r2 <= n)
					{
						int t1 = Util.gcd(r1, n);
						int t2 = Util.gcd(r2, n);
						int t1t2 = t1 * t2;
						
						if(t1 > 1 && t2 > 1 && t1t2 < n)
						{
							primes.addAll(turboCalculatePrimeFactors(t1));
							primes.addAll(turboCalculatePrimeFactors(t2));
							primes.addAll(turboCalculatePrimeFactors(n / (t1t2)));
							return primes;
						}
						else if(t1 > 1 && t1 < n)
						{
							primes.addAll(turboCalculatePrimeFactors(t1));
							primes.addAll(turboCalculatePrimeFactors(n / t1));
							return primes;
						}
						else if(t2 > 1 && t2 < n)
						{
							primes.addAll(turboCalculatePrimeFactors(t2));
							primes.addAll(turboCalculatePrimeFactors(n / t2));
							return primes;
						}
					}
				}
			}
		}
		primes.add(n);
		return primes;
	}

}
