package com.gdxsoft.web.uploadResources;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UPath;

public class UploadResources {
	/**
	 * 
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(UploadResources.class);

	/**
	 * Get the EWA static files, js, css, images ...
	 * 
	 * @param resourcePath static path
	 * @return the resource
	 */
	public static UploadResource getResource(String resourcePath) {
		String path = resourcePath;
		// for compatible
		path = path.replace("//", "/").replace("//", "/").replace("//", "/").replace("//", "/");
		String key = path;

		UploadResourcesCached cached = UploadResourcesCached.getInstance();
		UploadResource r = cached.getCache(key);

		// from cached
		if (r != null) {
			return r;
		}

		// load from resources
		r = loadResource(path);
		if (r.getStatus() == 200) {
			cached.putCache(key, r);
		}
		return r;
	}

	private static synchronized UploadResource loadResource(String path) {
		String ext = FilenameUtils.getExtension(path);
		UploadResource r = new UploadResource();

		if (path.indexOf("..") >= 0) {
			r.setPath(path);
			r.setStatus(502);
			LOGGER.error("Invalid path '..', {}", r.toString());
			return r;
		}

		// not allow blank ext or directory
		if (ext.trim().length() == 0 || ext.trim().endsWith("/")) {
			r.setPath(path);
			r.setStatus(403);
			LOGGER.error("Blank ext or directory. {}", r.toString());
			return r;
		}
		if (ext.equalsIgnoreCase("exe") || ext.equalsIgnoreCase("bat") || ext.equalsIgnoreCase("cmd")
				|| ext.equalsIgnoreCase("sh") || ext.equalsIgnoreCase("dmg") || ext.equalsIgnoreCase("java")
				|| ext.equalsIgnoreCase("jsp") || ext.equalsIgnoreCase("class") || ext.equalsIgnoreCase("jar")
				|| ext.equalsIgnoreCase("properties") || ext.equalsIgnoreCase("js")
		) {
			r.setPath(path);
			r.setStatus(500);
			LOGGER.error("Invalid ext. {}", r.toString());
			return r;
		}

		if (path.indexOf("ewa_conf") >= 0 || path.indexOf("appliaction.yml") >= 0) {
			r.setPath(path);
			r.setStatus(501);
			LOGGER.error("Invalid file. {}", r.toString());
			return r;
		}

		String pathWithPrefix = UPath.getPATH_UPLOAD() + path;
		File f = new File(pathWithPrefix);

		r.setPath(path);
		if (!f.exists()) {
			r.setStatus(404);
			LOGGER.debug(r.toString());
			return r;
		}
		boolean binary = false;
		if (ext.equalsIgnoreCase("js")) {
			r.setType("text/javascript");
		} else if (ext.equalsIgnoreCase("htm") || ext.equalsIgnoreCase("html")) {
			r.setType("text/html");
		} else if (ext.equalsIgnoreCase("txt") || ext.equalsIgnoreCase("csv")) {
			r.setType("text/plain");
		} else if (ext.equalsIgnoreCase("json")) {
			r.setType("text/json");
		} else if (ext.equalsIgnoreCase("css")) {
			r.setType("text/css");
		} else if (ext.equalsIgnoreCase("xml")) {
			r.setType("text/xml");
		} else if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png")
				|| ext.equalsIgnoreCase("gif")) {
			r.setType("image/" + ext);
			binary = true;
		} else {
			r.setType("application/octet-stream");
			binary = true;
		}
		r.setBinary(binary);

		try {
			if (binary) {
				byte[] buf = UFile.readFileBytes(pathWithPrefix);
				r.setBuffer(buf);
			} else {
				String text = UFile.readFileText(pathWithPrefix);
				r.setContent(text);
			}
			LOGGER.debug(r.toString());
			return r;
		} catch (IOException e) {
			r.setStatus(500);
			LOGGER.error(r.toString());
			return r;
		}
	}

}
