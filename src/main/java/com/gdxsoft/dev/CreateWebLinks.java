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

		//FROM: E:\guolei\p1\oa-cm\src\main\webapp
		// TO  : E:\java\apache-tomcat-9.0.43\wtpwebapps\oa-cm
		if (args.length < 3) {
			System.out.println("USAGE: PrjWebApp TomcatWebAppRoot linux/mac/win");
			System.out.println(
					"Example: \"/Users/admin/pf/src/main/webapp\""
					+ " \"/Users/admin/apache-tomcat-8.5.38/webapps/pf\" linux");
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
		} else {
			mk.append("#!/bin/bash\n");
			rm.append("#!/bin/bash\n");
		}

		// E:\guolei\p1\oa-cm\src\main\webapp;
		String root = args[0];
		File fRoot = new File(root);
		// E:\java\apache-tomcat-9.0.43\wtpwebapps\oa-cm";
		String target = args[1];
		File fTarget = new File(target);
		
		System.out.println("FROM: " + fRoot.getAbsolutePath());
		System.out.println("TO  : " + fTarget.getAbsolutePath());

		// Tomcat的项目的WEB-INF/classes
		File allClassTarget = new File(target + "/WEB-INF/classes");

		// WebRoot下的所有文件，不包含WEB-INF
		cmd(fRoot, fTarget, mk, rm, 0);

		// 所有项目输出的classes文件目录
		String allclass = root.indexOf("/src/main/") > 0 ? "/../../../../allclass/classes" : "/../../allclass/classes";
		// 所有项目输出的classes文件目录
		File allClassRoot = new File(root + allclass);
		if (allClassRoot.exists()) {
			cmd(allClassRoot, allClassTarget, mk, rm, 0);
		} else {
			System.out.println("skip " + allClassRoot);
		}

		// WEB-INF下不含classes目录
		File webInfRoot = new File(fRoot.getAbsolutePath() + "/WEB-INF");

		File webInfTarget = new File(target + "/WEB-INF");

		System.out.println(webInfRoot + " -> " + webInfTarget);
		cmd(webInfRoot, webInfTarget, mk, rm, 2);

		// WEB项目本身输出的目录，例如配置信息 ewa_conf.xml等
		String targetClass = root.indexOf("/src/main/") > 0 ? "/../../../target/classes" : "/../target/classes";
		File prjSrc = new File(fRoot.getAbsolutePath() + targetClass);

		System.out.println(prjSrc + " -> " + allClassTarget);
		cmd(prjSrc, allClassTarget, mk, rm, 1); // 1-file

		if (isWin) {
			mk.append("if exist after.bat (\r\n");
			mk.append(" call after.bat \"" + fRoot.getAbsolutePath() + "\" \"" + fTarget.getAbsolutePath() + "\" \r\n");
			mk.append(")\r\n");

			createNewTextFile("DelLinks.bat", rm.toString());
			createNewTextFile("MkLinks.bat", mk.toString());
		} else {
			mk.append("file=after.sh \n");
			mk.append("if [ -f \"$file\" ]; then\n");
			mk.append("    source $file \"" + fTarget.getAbsolutePath() + "\"\n");
			mk.append("fi\n");
			createNewTextFile("dellinks.sh", rm.toString());
			createNewTextFile("mklinks.sh", mk.toString());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	private static void cmd(File fRoot, File target, StringBuilder mk, StringBuilder rm, int type) {
		if (isWin) {
			mk.append("\r\n\r\n\r\nREM from " + fRoot + "\r\nREM to " + target + " type=" + type);
			mk.append("\r\n@echo from " + fRoot + " to " + target + " type=" + type);
			mk.append("\r\n");
		} else {
			mk.append("\n\n\n# from " + fRoot + " to" + target + " type=" + type);
			mk.append("\necho from " + fRoot + " to" + target + " type=" + type);
			mk.append("\n");
		}

		File[] files = fRoot.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (type == 2 && (f.getName().equals("classes") || f.getName().equals("lib"))) { // WEB-INF
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

		return new String[] { cmdRm, (isWin ? "\r\n" : "\n") + cmdRm + cmdMk };
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
			if (!isWin) {
				file.setExecutable(true);
			}
		}

	}
}

