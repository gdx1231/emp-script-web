package com.gdxsoft.web.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UImages;
import com.gdxsoft.easyweb.utils.UPath;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {
	private static int _maxWidth = 3000;

	public static BufferedImage createQRCodeBufImg(String msg, int width) {
		if (width > _maxWidth) {
			width = _maxWidth;
		}
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
		// hints.put(EncodeHintType.MARGIN, 1);

		int BLACK = 0xff000000;
		int WHITE = 0xFFFFFFFF;

		BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

		MultiFormatWriter barcodeWriter = new MultiFormatWriter();
		BitMatrix matrix;
		try {
			matrix = barcodeWriter.encode(msg, barcodeFormat, width, width, hints);
		} catch (WriterException e) {
			System.out.println(e.getMessage());
			return null;
		}

		int w = matrix.getWidth();
		int h = matrix.getHeight();
		int diff = -1;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				boolean is_black = matrix.get(x, y);
				if (is_black) {
					if (x > y) {
						diff = y;
					} else {
						diff = x;
					}
					break;
				}
			}
		}
		if (diff > 1) {
			diff = diff - 1;
		}

		int new_width = w - diff * 2;
		int new_height = h - diff * 2;
		BufferedImage bi = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < new_width; x++) {
			for (int y = 0; y < new_height; y++) {
				boolean is_black = matrix.get(x + diff, y + diff);
				bi.setRGB(x, y, is_black ? BLACK : WHITE);
			}
		}
		return bi;

	}

	/**
	 * 创建带logo的二维码
	 * 
	 * @param msg
	 * @param width
	 * @param logoFile
	 * @return
	 * @throws IOException
	 */
	public static byte[] createQRCode(String msg, int width, File logoFile) throws IOException {
		BufferedImage qrcode = createQRCodeBufImg(msg, width);
		BufferedImage logo = ImageIO.read(logoFile);

		int logo_max_width = qrcode.getWidth() * 2 / 8;
		int logo_max_height = qrcode.getHeight() * 2 / 8;

		UImages.appendLogo(qrcode, logo, logo_max_width, logo_max_height);

		return UImages.getBytes(qrcode, "JPEG", 0.8f);
	}

	/**
	 * 创建带logo的二维码
	 * 
	 * @param msg
	 * @param width
	 * @param logoBytes logo文件的二进制
	 * @return
	 * @throws IOException
	 */
	public static byte[] createQRCode(String msg, int width, byte[] logoBytes) throws IOException {
		BufferedImage qrcode = createQRCodeBufImg(msg, width);

		ByteArrayInputStream in = new ByteArrayInputStream(logoBytes); // 将b作为输入流；
		BufferedImage logo = ImageIO.read(in); // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
		in.close();

		int logo_max_width = qrcode.getWidth() * 2 / 8;
		int logo_max_height = qrcode.getHeight() * 2 / 8;

		UImages.appendLogo(qrcode, logo, logo_max_width, logo_max_height);

		return UImages.getBytes(qrcode, "JPEG", 0.8f);
	}

	public static byte[] createQRCode(String msg) {
		return createQRCode(msg, 300);
	}

	public static byte[] createQRCode(String msg, int width) {
		BufferedImage bi = createQRCodeBufImg(msg, width);
		return UImages.getBytes(bi, "JPEG", 0.8f);
	}

	/**
	 * 生成WEB使用二维码，返回url路径
	 * 
	 * @param msg 需编码信息
	 * @return
	 */
	public static String createQRCodeWebFile(String msg) {
		return createQRCodeWebFile(msg, 300);
	}

	/**
	 * 获取
	 * 
	 * @param md5
	 * @param ext
	 * @return 二维数组，0 物理文件，1 url
	 */
	public static String[] getQRCodeSavedPath(String paras, String ext) {
		String root = UPath.getPATH_IMG_CACHE() + "/d2code/";
		String web_root = UPath.getPATH_IMG_CACHE_URL() + "/d2code/";

		String path1 = UFile.createSplitDirPath(paras, 6);

		String path = root + path1;
		String web_url = web_root + path1;
		UFile.buildPaths(path);

		// 物理文件
		String name = path + "/" + paras + "." + ext;
		// url
		String url = web_url + "/" + paras + "." + ext;

		url = url.replace("//", "/");
		return new String[] { name, url };
	}

	public static String createQRCodeWebFile(String msg, int width) {
		if (width > _maxWidth) {
			width = _maxWidth;
		}
		String root = UPath.getRealContextPath() + "/d2code/";
		String code = msg.hashCode() + "";
		String path1 = UFile.createSplitDirPath(code, 2);
		String path = root + path1;
		UFile.buildPaths(path);
		String name = path + "/" + code + ".gif";
		// boolean rst = createQRCode(name, msg);
		// if (rst) {
		// return "/d2code/" + path1 + "/" + code + ".gif";
		// } else {
		// return null;
		// }
		try {
			BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;
			MultiFormatWriter barcodeWriter = new MultiFormatWriter();
			BitMatrix matrix = barcodeWriter.encode(msg, barcodeFormat, width, width);
			MatrixToImageWriter.writeToFile(matrix, "gif", new File(name));

			return "/d2code/" + path1 + "/" + code + ".gif";
		} catch (Exception err) {
			System.out.println(err.getMessage());
			return err.getMessage();
		}
	}

	public static boolean createQRCode(String path, String msg) {
		return createQRCode(path, msg, 300);
	}

	public static boolean createQRCode(String path, String msg, int width) {
		BufferedImage bi = createQRCodeBufImg(msg, width);
		File f = new File(path);
		try {
			ImageIO.write(bi, "gif", f);
			return true;
		} catch (Exception err) {
			return false;
		} finally {
			bi = null;
		}
	}

}
