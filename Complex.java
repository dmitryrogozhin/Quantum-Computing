package Utils;

/**
 * Contains the implementation of Complex numbers and necessary operations.
 * 
 * @author dmitryrogozhin
 */
public class Complex
{
	private final double real; // real part of the complex number
	private final double fake; // imaginary part of the complex number
	
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex REAL_UNIT = new Complex(1, 0);
	public static final Complex FAKE_UNIT = new Complex(0, 1);
			
	public Complex(double re, double fa)
	{
		this.real = re;
		this.fake = fa;
	}
	
	public double getReal()
	{
		return this.real;
	}
	
	public double getFake()
	{
		return this.fake;
	}
	
	public String toString()
	{
		String sign = this.fake < 0 ? " - " : " + ";
		return this.real + sign + this.fake + "i";
	}

	public boolean equals(Object input)
	{
		if(input == null)
		{
			return false;
		}
		
		if(!this.getClass().equals(input.getClass()))
		{
			return false;
		}
		
		Complex num = (Complex) input;
		
		return ((this.real == num.real) && (this.fake == num.fake));
	}
	
	public double abs()
	{
		return Math.hypot(this.real, this.fake);
	}
	
	public static double[] abs(Complex[] array)
	{
		double[] retval = new double[array.length];
		
		for(int i = 0; i < retval.length; i++)
		{
			retval[i] = array[i].abs();
		}
		
		return retval;
	}
	
	public Complex conjugate()
	{
		return new Complex(this.real, -this.fake);
	}
	
	public static Complex[] conjugate(Complex[] array)
	{
		Complex[] retval = new Complex[array.length];
		
		for(int i = 0; i < retval.length; i++)
		{
			retval[i] = array[i].conjugate();
		}
		
		return retval;
	}
	
	public Complex normalize(double factor)
	{
		return new Complex(factor * this.real, factor * this.fake);
	}
	
	public static Complex[] normalize(Complex[] array, double factor)
	{
		if(Double.isNaN(factor) || Double.isInfinite(factor))
		{
			System.out.println("ERR: normalize() invalid input factor.");
		}
		
		Complex[] retval = new Complex[array.length];
		
		for(int i = 0; i < retval.length; i++)
		{
			retval[i] = array[i].normalize(factor);
		}
		
		return retval;
	}

	public double phase()
	{
		return Math.atan2(this.fake, this.real);
	}
	
	public Complex add(Complex num)
	{
		double re = this.real + num.real;
		double fa = this.fake + num.fake;
		return new Complex(re, fa);
	}
	
	public Complex subtract(Complex num)
	{
		double re = this.real - num.real;
		double fa = this.fake - num.fake;
		return new Complex(re, fa);
	}
	
	public Complex multiply(Complex num)
	{
		double re = this.real * num.real - this.fake * num.fake;
		double fa = this.real * num.fake + this.fake * num.real;
		return new Complex(re, fa);
	}
	
	public Complex divide(Complex num)
	{
		if(num.equals(ZERO))
		{
			System.out.println("ERR: divide() denominator is zero.");
			return null;
		}
		
		double factor = 1 / (num.real * num.real + num.fake * num.fake);
		Complex recip = new Complex(factor * num.real, -(factor * num.fake));
		return this.multiply(recip);
	}
	
	public Complex exp()
	{
		double realExp = Math.exp(this.real);
		double re = realExp * Math.cos(this.fake);
		double fa = realExp * Math.sin(this.fake); 
		return new Complex(re, fa);
	}
	
	public Complex sin()
	{
		return new Complex(Math.sin(this.real) * Math.cosh(this.fake),
				Math.cos(this.real) * Math.sinh(this.fake));
	}
	
	public Complex cos()
	{
		return new Complex(Math.cos(this.real) * Math.cosh(this.fake),
				-Math.sin(this.real) * Math.sinh(this.fake));
	}
	
	public Complex tan()
	{
		return sin().divide(cos());
	}
	
	public Complex cot()
	{
		return cos().divide(sin());
	}
	
}
