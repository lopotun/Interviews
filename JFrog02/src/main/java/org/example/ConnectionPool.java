package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by Evgeny Kurtser on ${DATE} at ${TIME}.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class ConnectionPool {
	private static final int MAX_CONN_SIZE = 50;

	private final BlockingQueue<PooledDBConnection> queue;

	public DBConnection getConnection() throws InterruptedException {
		return queue.poll(100*365, TimeUnit.DAYS);
	}

	public boolean closeConnection(PooledDBConnection conn) {
		return queue.offer(conn);
	}

	public ConnectionPool() {
		this.queue = new LinkedBlockingQueue<>(MAX_CONN_SIZE);
		IntStream.range(0, MAX_CONN_SIZE)
				.mapToObj(x -> new PooledDBConnection())
				.forEach(queue::add);
	}

	public static void main(String[] args) {
		System.out.println("Hello world!");
	}

	class PooledDBConnection extends DBConnection {
		//private Connection conn;
		public PooledDBConnection(/*Connection conn*/) {
			//	this.conn = conn;
		}

		public void close() {
			closeConnection(this);
		}
	}
}




class DBConnection {
	public void close() {}
}