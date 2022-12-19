package kem.leetcode;

import java.util.concurrent.Semaphore;

/**
 * Five silent philosophers sit at a round table with bowls of spaghetti. Forks are placed between each pair of adjacent philosophers.
 * Each philosopher must alternately think and eat. However, a philosopher can only eat spaghetti when they have both left and right forks. Each fork can be held by only one philosopher and so a philosopher can use the fork only if it is not being used by another philosopher. After an individual philosopher finishes eating, they need to put down both forks so that the forks become available to others. A philosopher can take the fork on their right or the one on their left as they become available, but cannot start eating before getting both forks.
 * Eating is not limited by the remaining amounts of spaghetti or stomach space; an infinite supply and an infinite demand are assumed.
 * Design a discipline of behaviour (a concurrent algorithm) such that no philosopher will starve; i.e., each can forever continue to alternate between eating and thinking, assuming that no philosopher can know when others may want to eat or think.
 * Q. <a href="https://leetcode.com/problems/the-dining-philosophers/">...</a>
 */
public class DiningPhilosophers1226 {

	private final Semaphore FORK_01 = new Semaphore(1);
	private final Semaphore FORK_12 = new Semaphore(1);
	private final Semaphore FORK_23 = new Semaphore(1);
	private final Semaphore FORK_34 = new Semaphore(1);
	private final Semaphore FORK_40 = new Semaphore(1);
	private final Semaphore[][] PHILOSOPHER_TO_FORKS = new Semaphore[][]{
			new Semaphore[]{FORK_01, FORK_40}, // philosopher 0
			new Semaphore[]{FORK_12, FORK_01}, // philosopher 1
			new Semaphore[]{FORK_23, FORK_12}, // philosopher 2
			new Semaphore[]{FORK_34, FORK_23}, // philosopher 3
			new Semaphore[]{FORK_40, FORK_34}, // philosopher 4
	};

	// call the run() method of any runnable to execute its code
	public void wantsToEat(int philosopher,
	                       Runnable pickLeftFork,
	                       Runnable pickRightFork,
	                       Runnable eat,
	                       Runnable putLeftFork,
	                       Runnable putRightFork) throws InterruptedException {

		// If every philosopher takes the left (or right) fork, they will probably be deadlocked.
		// To resolve it, an even philosopher will first take his LEFT fork and then his RIGHT fork.
		// An odd philosopher will first take his RIGHT fork and then his LEFT fork.
		final int first, second;
		if(philosopher % 2 == 0) {
			first = 0;
			second = 1;
		} else {
			first = 1;
			second = 0;
		}
		final Semaphore firstFork = PHILOSOPHER_TO_FORKS[philosopher][first];
		boolean goOn = true;
		while(goOn) {
			// Pick first fork
			firstFork.acquire();
			pickLeftFork.run();

			// Pick second fork
			final Semaphore secondFork = PHILOSOPHER_TO_FORKS[philosopher][second];
			secondFork.acquire();
			pickRightFork.run();

			// Eat
			eat.run();
			goOn = false;

			// Put second fork back
			putRightFork.run();
			secondFork.release();

			// Put first fork back
			putLeftFork.run();
			firstFork.release();
		}
	}

	// Driver Code
	public static void main(String[] args) throws InterruptedException {
		final int n = 3;
		final DiningPhilosophers1226 test = new DiningPhilosophers1226();

		Runnable pickLeftFork = () -> System.out.printf("%s picks up first fork%n", Thread.currentThread().getName());
		Runnable pickRightFork = () -> System.out.printf("%s picks up second fork%n", Thread.currentThread().getName());
		Runnable putLeftFork = () -> System.out.printf("%s puts down first fork%n", Thread.currentThread().getName());
		Runnable putRightFork = () -> System.out.printf("%s puts down second fork%n", Thread.currentThread().getName());
		Runnable eat = () -> {
			try {
				long delay = (long) ((Math.random() * (1500L - 100L)) + 100L);
				System.out.printf("%s eats %d ms%n", Thread.currentThread().getName(), delay);
				Thread.sleep(delay);
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
		};

		new RunNamed(0, n, test, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork).start();
		new RunNamed(1, n, test, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork).start();
		new RunNamed(2, n, test, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork).start();
		new RunNamed(3, n, test, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork).start();
		new RunNamed(4, n, test, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork).start();
	}
}

class RunNamed extends Thread {
	private final int philosopherId;
	private final int n;
	private final DiningPhilosophers1226 man;
	private final Runnable pickLeftFork;
	private final Runnable pickRightFork;
	private final Runnable eat;
	private final Runnable putLeftFork;
	private final Runnable putRightFork;

	RunNamed(int philosopherId, int n, DiningPhilosophers1226 man,
	         Runnable pickLeftFork,
	         Runnable pickRightFork,
	         Runnable eat,
	         Runnable putLeftFork,
	         Runnable putRightFork) {
		setName("Ph-" + philosopherId);
		this.philosopherId = philosopherId;
		this.n = n;
		this.man = man;
		this.pickLeftFork = pickLeftFork;
		this.pickRightFork = pickRightFork;
		this.eat = eat;
		this.putLeftFork = putLeftFork;
		this.putRightFork = putRightFork;
	}

	@Override
	public void run() {
		try {
			for(int i = 0; i < n; i++) {
				man.wantsToEat(philosopherId,
						pickLeftFork,
						pickRightFork,
						eat,
						putLeftFork,
						putRightFork
				);
			}
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}