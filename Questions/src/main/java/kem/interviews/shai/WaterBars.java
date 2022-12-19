package kem.interviews.shai;

import groovy.lang.Tuple2;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Evgeny Kurtser on 15-Nov-22 at 9:23 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class WaterBars {
	private final Deque<Tuple2<Integer, Integer>> stack = new ArrayDeque<>();

	public int totalWater(int[] bars) {
		if(bars == null || bars.length < 2) return 0;
		int res = 0;
		int max = bars[0];
		int prev = bars[0];
		int i = 1;
		while(i<bars.length && bars[i] >= max) {
			res += prev;
			max = bars[i];
			prev = bars[i];
			i++;
		}
		if(i<bars.length) {
			for(int j=i; j< bars.length; j++) {
				Tuple2<Integer, Integer> item = new Tuple2<>(bars[j], 1);
				if(stack.isEmpty() || bars[j] < stack.peek().getV1()) {
					stack.push(item);
				} else {
					int count = 1;
					while(!stack.isEmpty() && bars[j] >= stack.peek().getV1()) {
						item = stack.pop();
						count += item.getV2();
					}
					stack.push(new Tuple2<>(bars[j], count));
				}
			}
			while(!stack.isEmpty()) {
				Tuple2<Integer, Integer> top = stack.pop();
				res += top.getV1() * top.getV2();
			}
		}
		return res;
	}
}
