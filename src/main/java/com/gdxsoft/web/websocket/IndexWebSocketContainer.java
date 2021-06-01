package com.gdxsoft.web.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理所有 IndexWebSocket的容器
 * 
 * @author admin
 *
 */
public class IndexWebSocketContainer {
	private static Logger LOGGER = LoggerFactory.getLogger(IndexWebSocketContainer.class);
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，
	// 可以使用Map来存放，其中Key可以为用户标识
	private static ConcurrentHashMap<String, IndexWebSocket> WEB_SOCKETS_MAP = new ConcurrentHashMap<String, IndexWebSocket>();

	/**
	 * 广播消息
	 * 
	 * @param msg
	 */
	public static void broadcast(String msg) {
		for (String key : WEB_SOCKETS_MAP.keySet()) {
			IndexWebSocket socket = WEB_SOCKETS_MAP.get(key);
			broadcastTo(socket, msg);
		}
	}

	/**
	 * 广播消息到指定socket
	 * 
	 * @param socket
	 * @param msg
	 * @return
	 */
	private static boolean broadcastTo(IndexWebSocket socket, String msg) {
		try {
			return socket.sendToClient(msg);
		} catch (Exception e) {
			remove(socket);
			LOGGER.error(e.getMessage());
			try {
				socket.getSession().close();
			} catch (IOException e1) {
				LOGGER.error(e1.getMessage());
			}
			return false;
		}
	}

	/**
	 * 添加链接
	 * 
	 * @param socket
	 */
	public static void add(IndexWebSocket socket) {
		WEB_SOCKETS_MAP.put(socket.getUnid(), socket);
		LOGGER.debug("JOIN [" + socket.getUnid() + "], total online: " + size());
	}

	/**
	 * 移除链接
	 * 
	 * @param socket
	 */
	public static void remove(IndexWebSocket socket) {
		WEB_SOCKETS_MAP.remove(socket.getUnid());
		LOGGER.debug("CLOSE [" + socket.getUnid() + "], total online:" + size());
	}

	/**
	 * 根据unid，获取socket
	 * 
	 * @param socketUnid
	 * @return
	 */
	public static IndexWebSocket getSocketByUnid(String socketUnid) {
		return WEB_SOCKETS_MAP.get(socketUnid);
	}

	/**
	 * 获取数量
	 * 
	 * @return
	 */
	public static int size() {
		return WEB_SOCKETS_MAP.size();
	}

	/**
	 * 获取所有的连接
	 * 
	 * @return
	 */
	public static ConcurrentHashMap<String, IndexWebSocket> getSockets() {
		return WEB_SOCKETS_MAP;
	}

}
