package com.gdxsoft.web.uploadResources;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class UploadResourcesCached {
	/**
	 * 
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(UploadResourcesCached.class);

	private static UploadResourcesCached INST;

	public static UploadResourcesCached getInstance() {
		return INST;
	}

	static {
		INST = new UploadResourcesCached();
		INST.init();
	}

	private Cache<String, UploadResource> cached;
	private RemovalListener<String, UploadResource> removalListener;

	private UploadResourcesCached() {

	}

	private void init() {

		// 移除key-value监听器
		removalListener = new RemovalListener<String, UploadResource>() {

			@Override
			public void onRemoval(RemovalNotification<String, UploadResource> notification) {
				RemovalCause cause = notification.getCause();// EXPLICIT、REPLACED、COLLECTED、EXPIRED、SIZE
				LOGGER.info("{}被移除 {}", notification.getKey(), cause);

			}
		};
		cached = CacheBuilder.newBuilder() //
				.maximumSize(100) // 表示cache中保存的key的最大条数
				.removalListener(removalListener) //
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.recordStats() // 记录统计信息
				.build();
	}

	/**
	 * 放缓存
	 * 
	 * @param value
	 */
	public void putCache(String key, UploadResource value) {
		LOGGER.info("put cached: {}: {}", key, value);
		cached.put(key, value);

	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public UploadResource getCache(String key) {
		UploadResource v = cached.getIfPresent(key);
		return v;
	}

}
