package com.gdxsoft.web.weixin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

public class WeiXinMediaUtils {
	public static String CMD = "/usr/local/bin/ffmpeg";

	/**
	 * 利用 ffmpeg 程序转换音频到mp3(Console)
	 * 
	 * http://ffmpeg.org
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @return
	 */
	public static boolean changeToMp3_a(String sourcePath, String targetPath) {
		File source = new File(sourcePath);
		File target = new File(targetPath);

		String line = CMD + " -i " + source.getAbsolutePath() + " "
				+ target.getAbsolutePath();
		System.out.println(line);

		CommandLine commandLine = CommandLine.parse(line);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(0);
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		executor.setWatchdog(watchdog);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		executor.setStreamHandler(streamHandler);
		try {
			executor.execute(commandLine);
			String s = outputStream.toString();
			outputStream.close();
			System.out.println(s);
			return true;
		} catch (ExecuteException e) {
			System.out.println("changeToMp3: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("changeToMp3: " + e.getMessage());
		}

		return false;
	}
 
}