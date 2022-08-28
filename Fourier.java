package Utils;

/**
 * Contains the implementation of Fast Fourier Transforms
 * 
 * @author dmitryrogozhin
 */
public class Fourier
{
		
	/**
	 * Fast Fourier Transform (forward)
	 * 
	 * @param vector incoming vector of complex numbers
	 * @return array of fft'd complex numbers
	 */
	public static Complex[] fft(Complex[] vector)
	{
		final int taskLen = vector.length;
		
		if(taskLen == 1)
		{
			return new Complex[] { vector[0] };
		}
		
		if(Integer.highestOneBit(taskLen) != taskLen)
		{
			System.out.println("ERR: fft() taskLen is not a power of 2.");
			return null;
		}
		
		final int halfTaskLen = taskLen / 2;
		
		// 1. Process even elements
		Complex[] even = new Complex[halfTaskLen];
		for(int i = 0; i < halfTaskLen; i++)
		{
			even[i] = vector[2 * i];
		}
		Complex[] fftEven = fft(even);
		
		// 2. Process odd elements
		Complex[] odd = new Complex[halfTaskLen];
		for(int i = 0; i < halfTaskLen; i++)
		{
			odd[i] = vector[1 + 2 * i];
		}
		Complex[] fftOdd = fft(odd);
		
		// 3. Put the even and odd transforms together
		Complex[] retval = new Complex[taskLen];
		for(int index = 0; index < halfTaskLen; index++)
		{
			double theta = (index * (-2 * Math.PI)) / taskLen;
			Complex imageTheta = new Complex(Math.cos(theta), Math.sin(theta));
			
			// Let the magic happen!
			retval[index] = fftEven[index].add(imageTheta.multiply(fftOdd[index]));
			retval[index + halfTaskLen] = fftEven[index].subtract(imageTheta.multiply(fftOdd[index]));
		}
		
		return retval;
	}
	
	/**
	 * Inverse Fast Fourier Transform
	 *  
	 * @param vector incoming vector of complex numbers
	 * @return array of ifft'd complex numbers
	 */
	public static Complex[] ifft(Complex[] vector)
	{
		final int taskLen = vector.length;
		
		if(taskLen == 1)
		{
			return new Complex[] { vector[0] };
		}
		
		if(Integer.highestOneBit(taskLen) != taskLen)
		{
			System.out.println("ERR: ifft() taskLen is not a power of 2.");
			return null;
		}
		
		// Lazy engineer approach
		// 1. Create array of conjugates
		Complex[] retval = Complex.conjugate(vector);

		// 2. Perform la FFT de classique
		retval = fft(retval);
		
		// 3. Conjugate the results
		retval = Complex.conjugate(retval);
		
		// 4. Normalize the result to make it kosher
		retval = Complex.normalize(retval, 1 / taskLen);

		return retval;
	}
	
}
