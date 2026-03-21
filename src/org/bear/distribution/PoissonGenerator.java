package org.bear.distribution;

public class PoissonGenerator 
{
	public static int getPoisson(double lambda) 
	{
		double L = Math.exp(-lambda);
		double p = 1.0;
		int k = 0;
		do 
		{
		    k++;
		    p *= Math.random();
		} while (p > L);
		return k - 1;
	}
	public static void main(String args[])
	{
		double lambda = 0.1;
		for (int i = 0; i < 1000; i++)
		{
			double result = PoissonGenerator.getPoisson(lambda);
			System.out.println(result);
		}
	}
	
}
