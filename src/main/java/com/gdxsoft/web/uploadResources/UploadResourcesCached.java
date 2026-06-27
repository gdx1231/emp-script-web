package com.gdxsoft.web.uploadResources;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadResourcesCached {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadResourcesCached.class);

	private static final UploadResourcesCached INST = new UploadResourcesCached();

	private final ConcurrentHashMap<String, Entry> cache = new ConcurrentHashMap<>();
	private final ScheduledExecutorService cleaner;

	private static class Entry {
		final UploadResource value;
		volatile long expireAt; // System.nanoTime()

		Entry(UploadResource value, long ttlNanos) {
			this.value = value;
			this.expireAt = System.nanoTime() + ttlNanos;
		}

		void touch(long ttlNanos) {
			this.expireAt = System.nanoTime() + ttlNanos;
		}
	}

	private UploadResourcesCached() {
		cleaner = Executors.newSingleThreadScheduledExecutor(r -> {
			Thread t = new Thread(r, "upload-cache-cleaner");
			t.setDaemon(true);
			return t;
		});
		cleaner.scheduleAtFixedRate(this::evict, 1, 1, TimeUnit.MINUTES);
	}

	private void evict() {
		long now = System.nanoTime();
		for (var entry : cache.entrySet()) {
			if (entry.getValue().expireAt < now) {
				String key = entry.getKey();
				cache.compute(key, (k, v) -> {
					if (v != null && v.expireAt < now) {
						LOGGER.debug("{} expired", k);
						return null;
					}
					return v;
				});
			}
		}
	}

	public static UploadResourcesCached getInstance() {
		return INST;
	}

	public void putCache(String key, UploadResource value) {
		LOGGER.info("put cached: {}: {}", key, value);
		cache.put(key, new Entry(value, TimeUnit.MINUTES.toNanos(10)));
	}

	public UploadResource getCache(String key) {
		Entry entry = cache.get(key);
		if (entry == null) {
			return null;
		}
		// 惰性过期
		if (entry.expireAt < System.nanoTime()) {
			cache.remove(key, entry);
			return null;
		}
		// 访问时刷新 TTL
		entry.touch(TimeUnit.MINUTES.toNanos(10));
		return entry.value;
	}
}
