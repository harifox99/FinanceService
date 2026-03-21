package org.bear.distribution;
import java.util.Random;

import org.bear.util.StringUtil;

public class TestPoissonDistribution
{
     public static void main(String[] args)
     {
    	 int t0A = 25;
    	 int t0B = 25;
    	 int tHome = 33;
         double volatileRate = 0.66;
         double resultA = 0;
         double resultB = 0;
         double resultHome = 0;
         System.out.println("t0, " + t0A + ", " + t0B);
         for(int i = 1; i <= 300; i++)
         {
             double variableA = distribute(0.1);
             double variableB = distribute(0.1);
             double variableHome = distribute(0.1);
             System.out.print("t" + i + ",");
             if (i == 1)
             {
            	 resultA = t0A * volatileRate + t0A;
            	 resultB = t0B * volatileRate + t0B;
            	 resultHome = tHome * volatileRate + tHome;
             }
             else if (variableHome == 0.0)
             {
            	 if (variableA == 0.0 && variableB == 0.0)
            	 {
            		 resultA = resultA * volatileRate + t0A;
            		 resultB = resultB * volatileRate + t0B;
            		 resultHome = resultHome * volatileRate + tHome;
            	 }
            	 else if (variableA > 0  && variableB > 0)
            	 {
            		 resultA = resultA * volatileRate;
            		 resultB = resultB * volatileRate;
            		 resultHome = resultHome * volatileRate + tHome;
            	 }
            	 else if (variableA > 0  && variableB == 0)
            	 {
            		 resultA = resultA * volatileRate;
            		 resultB = resultB * volatileRate + t0B;
            		 resultHome = resultHome * volatileRate + tHome;
            	 }
            	 else if (variableA == 0.0  && variableB > 0)
            	 {
            		 resultA = resultA * volatileRate + t0A;
            		 resultB = resultB * volatileRate;
            		 resultHome = resultHome * volatileRate + tHome;
            	 }
             }
             else if (variableHome > 0)
             {
                    resultA = resultA * volatileRate;
            		resultB = resultB * volatileRate;
            		resultHome = resultHome * volatileRate;

             }
             System.out.print("T=" + StringUtil.setPointLength(resultA) + ",");
             System.out.print(variableA + ",");
             System.out.print("T=" + StringUtil.setPointLength(resultB) + ",");
             System.out.print(variableB + ",");
             System.out.print("T=" + StringUtil.setPointLength(resultHome) + ",");
             System.out.print(variableHome);
             System.out.println();
         }
     }

     /**
      * A method to randomize a value based on a Poisson distribution.
      * @param lambda The starting value.
      * @return The integer which forms part of the distribution
      * See pp 65 of Simulation and the Monte Carlo Method by
      * Rubinstein and Kroese
      */
     public static int distribute(double lambda)
     {
         int n = 1;
         double a = 1.0;
         Random r = new Random();

         while(true)
         {
             a *= r.nextDouble();
             if(a < Math.exp((double)-lambda)) break;
             n += 1;
         }
         return n - 1;
     }
}