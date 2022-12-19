package kem.interviews.shai;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by Evgeny Kurtser on 07-Nov-22 at 8:36 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class LeakingBasket<E> {
	private final Duration duration;
	private final int capacity;
	// This queue keeps timestamp of last N messages.
	private final Queue<LocalTime> q;
	private final Function<E, Boolean> outFunction;

	/**
	 * Ctor
	 *
	 * @param capacity  max number of messages to send during 'timeFrame' seconds
	 * @param timeFrame no more than 'capacity' messages will be sent during 'timeFrame' seconds
	 * @param outFunction this function is called to send the message out. The function should return <em>true</em> if the messages is sent out successfully
	 */
	public LeakingBasket(int capacity, int timeFrame, Function<E, Boolean> outFunction) {
		assert capacity > 0;
		assert timeFrame > 0;
		assert outFunction != null;
		this.capacity = capacity;
		this.duration = Duration.ofSeconds(timeFrame);
		this.outFunction = outFunction;
		this.q = new LinkedList<>();
	}

	/**
	 * Makes sure that no more than 'capacity' messages will be sent during 'timeFrame' seconds
	 *
	 * @param element     message
	 * @return <em>true</em> if the messages is sent out successfully. If, for any reason the messages wasn't sent out successfully, the method returns <em>false</em>
	 */
	public boolean in(E element) {
		assert element != null;

		boolean res;
		final LocalTime now = LocalTime.now();
		final LocalTime oldest = q.peek();    // Queue head contains timestamp of the oldest message
		if(oldest != null && q.size() >= capacity) {    // The queue is NOT empty AND it reached its max capacity
			Duration oldestInsertedAt = Duration.between(oldest, now);  // How much time has passed since the oldest message insertion
			if(duration.minus(oldestInsertedAt).isNegative()) { // More than 'duration' has passed
				final LocalTime evicted = q.poll();// Remove timestamp of the oldest message
				System.out.printf("[%s]\tEvicted: %s%n", now, evicted); // trace
			} else {    // No more than 'capacity' messages wil be sent
				//System.out.printf("[%s]\tQ size: %s%n", now, q.size());   // trace
				return false;
			}
		}
		q.offer(now);   // Put 'now' timestamp to the queue tail...
		res = outFunction.apply(element);   // ...and call the sending function
		return res;
	}


	public static void main(String[] args) {
		LeakingBasket<Integer> lb = new LeakingBasket<>(5, 3, (x) -> {
			System.out.println(x);
			return true;
		});
		IntStream.range(0, 100)
				.forEach(i -> {
					lb.in(i);
					try {
						Thread.sleep(100L);
					} catch(InterruptedException ignored) {}
				});
	}
}