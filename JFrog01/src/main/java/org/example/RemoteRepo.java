package org.example;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Evgeny Kurtser on 21-Nov-22 at 4:53 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public abstract class RemoteRepo {
	protected abstract InputStream getFileFromRemoteServer(String url); // slow potentially unsecure
	protected abstract InputStream getFileFromLocalCache(String url);   //

	private final Map<String, ReentrantLock> LOCKS = new ConcurrentHashMap<>();
	public InputStream doDownload(String url) {
		InputStream res = getFileFromLocalCache(url);
		if(res == null) {
			final ReentrantLock lock = LOCKS.computeIfAbsent(url, (x)-> new ReentrantLock());
			lock.lock();
			try {
				res = getFileFromLocalCache(url);
				if(res == null) {
					res = getFileFromRemoteServer(url);
				}
			} finally {
				lock.unlock();
			}
		}
		return res;
	}
}
