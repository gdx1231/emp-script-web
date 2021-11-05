package com.gdxsoft.dev;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 创建 web项目到 tomcat的软连接，用于编辑时加速同步速度 <br>
 * 记得 tomcat的 context.xml的context 下面 加上 <Resources allowLinking="true" />
 * 
 * @author admin
 *
 */
public class CreateWebLinks {

	private static boolean isWin = false;

	public static void main(String[] args) throws IOException {

		if (args.length < 3) {
			System.out.println("USAGE: PrjWebRoot TomcatWebAppRoot linux/mac/win");
			System.out.println(
					"Example: \"/Users/admin/java/workspace/myproject/src/main/webapp\" \"/Users/admin/java/apache-tomcat-8.5.38/webapps/myproject\" linux");
			return;
		}
		String os = args[2];
		if (os.equalsIgnoreCase("win")) {
			isWin = true;
		}

		StringBuilder mk = new StringBuilder();
		StringBuilder rm = new StringBuilder();

		if (isWin) {
			mk.append("@echo off\r\n");
			rm.append("@echo off\r\n");
		}

		// String root = "/Users/admin/java/workspace/myproject/src/main/webapp";
		String root = args[0];
		File fRoot = new File(root);
		System.out.println("FROM: " + fRoot.getAbsolutePath());
		// String target = "/Users/admin/java/apache-tomcat-8.5.38/webapps/myproject";
		String target = args[1];
		File fTarget = new File(target);
		System.out.println("TO  : " + fTarget.getAbsolutePath());

		cmd(fRoot, fTarget, mk, rm, 0);

		File allClassRoot = new File(root + "/../../allclass/classes");
		File allClassTarget = new File(target + "/WEB-INF/classes");

		cmd(allClassRoot, allClassTarget, mk, rm, 0);

		// 配置信息 ewa_conf.xml等
		File prjSrc = new File(fRoot.getAbsolutePath() + "/../target/classes");
		cmd(prjSrc, allClassTarget, mk, rm, 0); // 1-file

		File webInfRoot = new File(fRoot.getAbsolutePath() + "/WEB-INF");
		File webInfTarget = new File(target + "/WEB-INF");
		cmd(webInfRoot, webInfTarget, mk, rm, 2);

		if (isWin) {
			mk.append("if exist after.bat (\r\n");
			mk.append(" call after.bat \"" + fRoot.getAbsolutePath() +"\" \"" + fTarget.getAbsolutePath() +"\" \r\n");
			mk.append(")\r\n");

			createNewTextFile("DelLinks.bat", rm.toString());
			createNewTextFile("MkLinks.bat", mk.toString());
		} else {
			createNewTextFile("dellinks.sh", rm.toString());
			createNewTextFile("mklinks.sh", mk.toString());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	private static String[] createLink(String linkName, String target, boolean isDirectory) {
		String cmdRm, cmdMk;
		if (isWin) {
			// MKLINK [[/D] | [/H] | [/J]] Link Target
			if (isDirectory) {
				// #删除虚拟的链接目录，并不会删除远程文件夹真实文件，
				// 注意千万不能用del，del会删除远程的真实文件。
				cmdRm = "RMDIR " + linkName + "\r\n";
				cmdMk = "MKLINK /D " + linkName + " " + target + "\r\n";

			} else {
				cmdRm = "DEL " + linkName + "\r\n";
				cmdMk = "MKLINK " + linkName + " " + target + "\r\n";
			}
		} else {
			// ln [OPTION]... [-T] TARGET LINK_NAME
			cmdRm = "rm " + linkName + "\n";
			cmdMk = "ln -s " + target + " " + linkName + "\n";
		}

		return new String[] { cmdRm, cmdMk };
	}

	private static void cmd(File fRoot, File target, StringBuilder mk, StringBuilder rm, int type) {
		File[] files = fRoot.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (type == 2 && f.getName().equals("classes")) { // WEB-INF
				continue;
			} else if (type == 1 && f.isDirectory()) { // 仅文件
				continue;
			} else if (type == 0 && f.getName().equals("WEB-INF")) { // 目录和文件
				continue;
			}
			String cmdRm, cmdMk;

			String targetPath = f.getAbsolutePath(); // 原有文件
			String linkName = target.getAbsolutePath() + (isWin ? "\\" : "/") + f.getName(); // 创建的符号文件

			String[] cmds = createLink(linkName, targetPath, f.isDirectory());
			cmdRm = cmds[0]; // 删除
			cmdMk = cmds[1]; // 创建连接

			rm.append(cmdRm);
			mk.append(cmdMk);
		}
	}

	private static void createNewTextFile(String fileName, String content) throws IOException {
		File file = new File(fileName);
		System.out.println(file.getAbsolutePath());

		OutputStreamWriter os = null;
		try {
			os = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			os.write(content);
			os.flush();

		} catch (IOException e) {
			throw e;
		} finally {
			if (os != null)
				os.close();
		}
	}
}
