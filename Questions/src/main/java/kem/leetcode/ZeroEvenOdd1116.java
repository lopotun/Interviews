package kem.leetcode;

import java.util.function.IntConsumer;

/**
 * You have a function printNumber that can be called with an integer parameter and prints it to the console.
 * <p>
 * For example, calling printNumber(7) prints 7 to the console.
 * You are given an instance of the class ZeroEvenOdd that has three functions: zero, even, and odd. The same instance of ZeroEvenOdd will be passed to three different threads:
 * <p>
 * Thread A: calls zero() that should only output 0's.
 * Thread B: calls even() that should only output even numbers.
 * Thread C: calls odd() that should only output odd numbers.
 * Modify the given class to output the series "010203040506..." where the length of the series must be 2n.
 * <p>
 * Implement the ZeroEvenOdd class:
 * <p>
 * ZeroEvenOdd(int n) Initializes the object with the number n that represents the numbers that should be printed.
 * void zero(printNumber) Calls printNumber to output one zero.
 * void even(printNumber) Calls printNumber to output one even number.
 * void odd(printNumber) Calls printNumber to output one odd number.
 * Q. <a href="https://leetcode.com/problems/print-zero-even-odd/">...</a>
 */
public class ZeroEvenOdd1116 {
	private final int n;
	private static final Object LOCK_ZERO = new Object();
	private static final Object LOCK_EVEN = new Object();
	private static final boolean[] LOCK_ODD = new boolean[]{true};

	public ZeroEvenOdd1116(int n) {
		this.n = n;
	}

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void zero(IntConsumer printNumber) throws InterruptedException {
		boolean firstTime = true;
		for(int i=1; i<n+1; i++) {
			synchronized(LOCK_ZERO) {
				if(!firstTime) {
					LOCK_ZERO.wait();
				}
			}
			firstTime = false;
			printNumber.accept(0);
			if(i % 2 == 0) {
				synchronized(LOCK_EVEN) {
					LOCK_EVEN.notifyAll();
				}
			} else {
				synchronized(LOCK_ODD) {
					LOCK_ODD[0] = false;
					LOCK_ODD.notifyAll();
				}
			}
		}
	}

	public void even(IntConsumer printNumber) throws InterruptedException {
		for(int i = 2; i < n+1; i = i+2) {
			synchronized(LOCK_EVEN) {
				LOCK_EVEN.wait();
				printNumber.accept(i);
				synchronized(LOCK_ZERO) {
					LOCK_ZERO.notifyAll();
				}
			}
		}
	}

	public void odd(IntConsumer printNumber) throws InterruptedException {
		for(int i = 1; i < n+1; i = i+2) {
			synchronized(LOCK_ODD) {
				while(LOCK_ODD[0]) {
					LOCK_ODD.wait();
				}
				LOCK_ODD[0] = true;
				printNumber.accept(i);
				synchronized(LOCK_ZERO) {
					LOCK_ZERO.notifyAll();
				}
			}
		}
	}

	// Driver Code
	public static void main(String[] args) throws InterruptedException {
		final int n = 8;
		final ZeroEvenOdd1116 test = new ZeroEvenOdd1116(n);
		final IntConsumer printer = System.out::print;

		new Thread("A") {
			@Override
			public void run() {
				try {
//					Thread.sleep(2000L);
					test.zero(printer);
				} catch(InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}.start();

		new Thread("B") {
			@Override
			public void run() {
				try {
					test.even(printer);
				} catch(InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}.start();

		new Thread("C") {
			@Override
			public void run() {
				try {
					test.odd(printer);
				} catch(InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}.start();
	}
}

