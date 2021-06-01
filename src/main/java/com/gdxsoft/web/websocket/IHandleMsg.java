package com.gdxsoft.web.websocket;

public interface IHandleMsg {

	void run();

	String getMethod();

	void setName(String name);

	void start();
}