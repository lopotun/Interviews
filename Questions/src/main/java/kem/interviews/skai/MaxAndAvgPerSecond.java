package kem.interviews.skai;

import java.util.LinkedList;
import java.util.List;

/**
 * You have a table with 2 rows, the first one is with ms value, the second row is a value per millisecond. You should return max value, average per each ms . The question is easy, but you should use correct values in the first assignment of counters etc.
 * Example for the table:
 * ms:  200 300 400 1006    2000    3003
 * val: 1   3   -9  20      67      -6
 * <p>
 * Output:
 * sec: 1    2   3  4
 * max: 3    20  67 -6
 * avg: -5/3 20  67 -6
 * <p>
 * <a href="https://www.glassdoor.com/Interview/Skai-Software-Engineer-Interview-Questions-EI_IE437064.0,4_KO5,22.htm#InterviewReview_28244731">Skai</a>
 */
public class MaxAndAvgPerSecond {

	static class T3 {
		private final int second;
		private final int max;
		private final float avg;
		T3(int second, int max, float avg) {
			this.second = second;
			this.max = max;
			this.avg = avg;
		}

		@Override
		public String toString() {
			return String.format("%d, %d, %.1f", second, max, avg);
		}
	}

	public List<T3> maxAndAvg(int[] ms, int[] vals) {
		int second = 1000;
		int max = Integer.MIN_VALUE;
		int sum = 0;
		int innerCount = 0;

		final List<T3> res = new LinkedList<>();

		for(int i=0; i<ms.length; i++) {
			int value = vals[i];
			if(ms[i] >= second) {
				res.add(new T3(second / 1000, max, (float) sum / innerCount));
				max = Integer.MIN_VALUE;
				sum = innerCount = 0;
				second += 1000;
			}
			innerCount++;
			max = Math.max(value, max);
			sum += value;
		}
		if(innerCount > 0) {
			res.add(new T3(second / 1000, max, (float) sum / innerCount));
		}
		return res;
	}

	public static void main(String[] args) {
		final List<T3> res = new MaxAndAvgPerSecond().maxAndAvg(new int[]{200, 300, 400, 1006, 2000, 3003}, new int[]{1, 3, -9, 20, 67, -6});
		System.out.println(res);
	}
}
