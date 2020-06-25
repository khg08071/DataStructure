package cse2010.homework4;

import java.util.Arrays;
import java.util.stream.IntStream;

/*
 * CSE2010 Homework #4
 * Problem 2: Fibonacci (Tail-Recursion)
 *
 * Complete the code below.
 *
 */

public class Fibonacci {

    public static int fib(int n) {
        if (n == 0 || n == 1)
            return n;
        else
            return fib(n - 2) + fib(n - 1);
    }

    public static int fibIter(int n) {
        if (n <= 1)
            return n;

        int acc = 1;
        int prev = 0;

        while (n-- > 1) {
            int temp = acc;
            acc += prev;
            prev = temp;
        }

        return acc;
    }
    
    private static int tailrecursive(int n,int acc, int prev){
		if(n<2) return n*acc;
		else return tailrecursive(n-1, acc+prev, acc);
	}
    

    public static int fibTailRec(int n) {
    	
    	return tailrecursive(n, 1, 0);        
    	// Your code goes here ...
        // You may need to define a private, tail-recursive
        // helper method to call here ...

       
    }
    public static void main(String... args) {
        System.out.println(
                Arrays.toString(IntStream.rangeClosed(0, 5).map(Fibonacci::fibIter).toArray())
        );

        System.out.println(
                Arrays.toString(IntStream.rangeClosed(0, 5).map(Fibonacci::fibTailRec).toArray())
        );
    }


}
