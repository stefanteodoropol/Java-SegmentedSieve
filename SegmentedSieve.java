//Creator: Stefan Nicolae Teodoropol
//Date: 14.08.2019
//Version: 1.1

import java.util.*;
import java.lang.*;

public class SegmentedSieve
{
	public static boolean[] sieve;
	public static int[] primes;
	public static boolean[] resultsieve;
    
	public static void solve (int a, int b)
	{
		/* In the search for primes up to b, we only have to check multiples up to the sqrt(b).
		To show this, let's look at a number to check, n, and let's assume it isn't prime.
		If it isn't prime, it can be factored into a and b, such that a * b = n (a > 1 && b > 1).
		Now both a and b can't both be greater than sqrt(n), because then a * b would also be larger than n.
		So at least one factor will be smaller or equal to the sqrt(n). 
		If no factors are found up to the sqrt(n), n is prime.
		*/
		int sroot = (int)Math.sqrt(b);

		//0 isnt prime, neither is 1
		sieve = new boolean[sroot+1];
		sieve[0]=true;
		sieve[1]=true;

		primes = new int[sroot+1];
		int found = 0;

		/*
		In order to efficiently sieve a range from LO to HI but with big numbers, 
		we use a simple sieve to first get primes up to the square root of the biggest number in question.
		*/
		for (int i = 2; i <= sroot; i++) {
			if (sieve[i] == false) {
				primes[found]=i;
				found++;
				for (int j = 2; i*j <= sroot; j++) {
					sieve[i*j] = true; }
			}
		}
		// The primes[] array now has our primes up to the index "found". 
		int diff = b - a;
		resultsieve = new boolean[diff+1];
		// We set up a resultsieve array to have small numbers with regards to array access times.
		int primetocheck = 0;
		int divisor = 0;
		for (int i = 0; i < found; i++) {
			// Loop through the primes[] array
			primetocheck = primes[i];
			/* We start with the smallest multiple of our current prime and only check numbers that are within our range.
			Ex: primetocheck is 2, range is 9 to 13. Divide 9 by 2 to get 4.5, round down to 4. 
			Multiply that back with our primetocheck to get 8.
			8 is the largest multiple of primetocheck just before being in the range of 9 to 13.
			*/	
			divisor = (a/primetocheck);
			// This division can sometimes lead to small results, we need divisor to not go below 1.
			if (divisor == 0) { divisor = 1; }
			divisor *= primetocheck;
			// Sieving the range from a to b, mark the resultsieve[] as true for indices that arn't prime. 
			while(divisor <= b) {
				if(divisor >= a && primetocheck != divisor) {
					resultsieve[divisor - a]=true; }
				divisor += primetocheck;
			}
		}

		// Print the indicies in resultssieve[] that are untouched as they are prime. Add the offset (a).
		for (int i = 0; i <= diff; i++) {
			if (resultsieve[i] == false && (i + a) >= 2) {
				System.out.print((i + a) + " ");
			}
		}
		System.out.println();
	}
	
	public static void main (String[] args) throws java.lang.Exception
	{
		Scanner scn = new Scanner(System.in);
		//n cases
		int n = scn.nextInt();
		int a;
		int b;
		for (int i = 0; i < n; i++){
			a = scn.nextInt();
			b = scn.nextInt();
			if ( a < 0 || b < 0) { System.out.println("Please ensure a & b are non-negative integers"); }
			else { solve(a,b); }
		}
		scn.close();
	}
}