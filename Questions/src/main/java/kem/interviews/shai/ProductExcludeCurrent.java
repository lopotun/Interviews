package kem.interviews.shai;

import java.util.Arrays;

/**
 * Created by Evgeny Kurtser on 03-May-22 at 8:19 PM.
 * <a href=<a href="lopotun@gmail.com">Eugene Kurtzer</a>
 */
public class ProductExcludeCurrent {
	int[] productExcludeCurrent(int[] input) {
		int n = input.length;
		int[] a = new int[n];
		int[] b = new int[n];

		a[0] = b[n-1] = 1;
		for(int i=1; i<n; i++) {
			a[i] = a[i-1] * input[i-1];
			b[n-1-i] = b[n-i] * input[n-i];
		}

		for(int i=0; i<n-1; i++) {
			a[i] *= b[i];
		}
		return a;
	}

	public static void main(String[] args) {
		/*
		X = [ 2   7   3   4]
		a = [ 1   2  14  42]
		b = [84  12   4   1]
		c = [84  24  56  42]
		 */
		int[] res = new ProductExcludeCurrent().productExcludeCurrent(new int[]{2, 7, 3, 4});
//		int[] res = new ProductExcludeCurrent().productExcludeCurrent(new int[]{2, 3});
		System.out.println(Arrays.toString(res));
	}
}
