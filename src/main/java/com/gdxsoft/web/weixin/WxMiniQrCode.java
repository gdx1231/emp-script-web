/**
 * 
 */
package com.gdxsoft.web.weixin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.gdxsoft.easyweb.utils.UAes;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UImages;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.easyweb.utils.UNet;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;

/**
 * 微信小程序码
 * 
 * @author admin
 *
 */
public class WxMiniQrCode {

	private WeiXin weixin;

	/**
	 * 
	 */
	public WxMiniQrCode(WeiXin weixin) {
		this.weixin = weixin;
	}

	/**
	 * 替换小程序码的logo和添加文字
	 * 
	 * @param codeBuf         小程序码
	 * @param replaceLogoPath 需要替换的logo文件
	 * @param addText         添加的文字
	 * @param outputPath      输出文件
	 */
	public boolean replaceLogoAndAddText(byte[] codeBuf, String replaceLogoPath, String addText, File outputPath) {
		if (StringUtils.isEmpty(addText) && StringUtils.isEmpty(replaceLogoPath)) {
			return false;
		}
		// 二维码的宽高
		int codeImageWidth = 430;
		int codeImageHeight = 430;

		int width = codeImageWidth;
		int height = codeImageHeight;

		// logo的宽高
		int logoImageWidth = 140;
		int logoImageHeight = 140;
		// 水平居中 x轴需要偏移的量
		int xPath = (width - codeImageWidth) / 2;
		InputStream codeImg = new ByteArrayInputStream(codeBuf);
		try {
			Image image = ImageIO.read(codeImg);
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bi.createGraphics();
			// 构建一个传入宽高的白色背景
			g2.setBackground(Color.WHITE);
			g2.clearRect(0, 0, width, height);
			// 将二维码添加到背景中
			g2.drawImage(image, xPath, 0, codeImageWidth, codeImageHeight, null);

			// 如果未上传logo，则不进行覆盖logo。
			if (StringUtils.isNotEmpty(replaceLogoPath)) {

				g2.fillOval(113, 113, 204, 204);

				String smallLogo = UImages.createSmallImage(replaceLogoPath, logoImageWidth, logoImageHeight);
				InputStream logoImg = new FileInputStream(new File(smallLogo));
				BufferedImage logo = ImageIO.read(logoImg);
				// 由于图片居中，所以x轴需要进行一定的偏移
				g2.drawImage(logo.getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH),
						(codeImageWidth - logoImageWidth) / 2 + xPath, (codeImageHeight - logoImageHeight) / 2, null);
			}

			if (StringUtils.isNotEmpty(addText)) {
				// 添加的字 字体为黑体20号
				Font font = new Font("黑体", Font.BOLD, 20);
				g2.setFont(font);
				g2.setPaint(Color.BLACK);
				FontRenderContext context = g2.getFontRenderContext();
				// 计算文字的位置 x轴水平居中（图片宽度-字体长度）/2
				Rectangle2D bounds = font.getStringBounds(addText, context);
				double x = (width - bounds.getWidth()) / 2;
				double y = (height - bounds.getHeight()) / 2 + 140 / 2;
				double ascent = -bounds.getY();
				double baseY = y + ascent;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				// 设置字的位置
				g2.drawString(addText, (int) x, (int) baseY);
			}

			ImageIO.write(bi, "PNG", outputPath);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 创建或获取小程序码
	 * 
	 * @param scene         场景
	 * @param isTransparent 是否透明
	 * @return
	 * @throws Exception
	 */
	public JSONObject createOrGetQrCodeImg(String scene, boolean isTransparent) throws Exception {
		return this.createOrGetQrCodeImg(scene, isTransparent, null, null);
	}

	/**
	 * 创建或获取小程序码
	 * 
	 * @param scene           场景
	 * @param isTransparent   是否透明
	 * @param replaceLogoPath 替换的logo文件的路径
	 * @param addText         增加的文字
	 * @return
	 * @throws Exception
	 */
	public JSONObject createOrGetQrCodeImg(String scene, boolean isTransparent, String replaceLogoPath, String addText)
			throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("scene", scene);

		// 用于提交腾讯的参数
		JSONObject postData = new JSONObject();
		postData.put("scene", scene);

		if (isTransparent) { // 生成透明底色的小程序码
			obj.put("is_hyaline", true);
			postData.put("is_hyaline", true);
		}

		if (StringUtils.isNotEmpty(replaceLogoPath)) {
			obj.put("replaceLogoPath", replaceLogoPath);
		}

		if (StringUtils.isNotEmpty(addText)) {
			obj.put("addText", addText);
		}

		String name = "/weixin/xcx/" + weixin.getWxCfgNo() + "/" + scene + "." + obj.toString().hashCode() + ".png";
		// 文件的保存路径
		String path = UPath.getPATH_IMG_CACHE() + name;
		// 文件hash的保存路径
		String path_hash = path + ".hash.txt";
		// 网址
		String url1 = UPath.getPATH_IMG_CACHE_URL() + name;

		obj.put("URL", url1);
		obj.put("XCX", true);
		obj.put("FILE", path);

		boolean cachedRst = this.checkCached(path, path_hash);
		if (cachedRst) {
			UJSon.rstSetTrue(obj, "");
			obj.put("is_cached", true);
			return obj;
		}

		// 获取错误或不存在，重新创建

		// 获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。
		String token = weixin.getWeiXinCfg().getAccessToken();
		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + Utils.textToUrl(token);
		/*
		 * scene String
		 * 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，
		 * 中文无法使用 urlencode 处理，请使用其他编码方式） page String 必须是已经发布的小程序存在的页面（否则报错），例如
		 * "pages/index/index" ,根路径前不要填加'/',不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面 width
		 * Int 430 二维码的宽度 auto_color Bool false 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
		 * line_color Object {"r":"0","g":"0","b":"0"} auto_color 为 false 时生效，使用 rgb
		 * 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示 is_hyaline Bool false 是否需要透明底色，
		 * is_hyaline 为true时，生成透明底色的小程序码
		 */
		UNet net = new UNet();
		String postMsg = postData.toString();
		/*
		 * 注意 POST 参数需要转成 JSON 字符串，不支持 form 表单提交。 接口只能生成已发布的小程序的二维码
		 * 调用分钟频率受限（5000次/分钟），如需大量小程序码，建议预生成
		 */
		byte[] buf = net.postMsgAndDownload(url, postMsg);

		String str = new String(buf, "utf-8");
		JSONObject rst;
		try {
			// 只有出错的时候,才会出现 json,否则为图片的二进制
			rst = new JSONObject(str);
			// {“errcode”:40001,”errmsg”:”invalid credential, access_token is invalid or not
			// latest hint: [NFQX4a00441513]”}
			if (rst.has("errcode") && rst.optString("errcode").equals("40001")) {
				// 强制设置过期
				weixin.getWeiXinCfg().setEndTime(0);
			}
			System.out.println(str);
			UJSon.rstSetTrue(obj, str);
			return obj;
		} catch (Exception err) {

		}

		File outputPath = new File(path);
		boolean replaceLogoRst = this.replaceLogoAndAddText(buf, replaceLogoPath, addText, outputPath);

		String md5;
		if (!replaceLogoRst) {
			UFile.createBinaryFile(path, buf, true);
		}
		md5 = UFile.createMd5(outputPath);
		this.createMd5File(path_hash, md5);

		obj.put("md5", md5);

		UJSon.rstSetTrue(obj, "");
		obj.put("is_cached", false);
		return obj;
	}

	/**
	 * 创建小程序码的md5校验
	 * 
	 * @param path_hash
	 * @param md5
	 * @throws Exception
	 */
	private void createMd5File(String path_hash, String md5) throws Exception {
		// 获取文件的md5
		JSONObject hashJson = new JSONObject();
		hashJson.put("md5", md5);
		// aes加密hash值，避免被篡改
		String hash_enc = UAes.getInstance().encrypt(hashJson.toString());
		// 保存hash文件
		UFile.createNewTextFile(path_hash, hash_enc);
	}

	/**
	 * 检查缓存文件
	 * 
	 * @param path      小程序图片文件
	 * @param path_hash 小程序md5文件
	 * @return
	 */
	private boolean checkCached(String path, String path_hash) {
		File f1 = new File(path);
		File f2 = new File(path_hash);
		if (f1.exists() && f2.exists()) {
			try {
				String hash_enc = UFile.readFileText(f2.getAbsolutePath());
				String hash = UAes.getInstance().decrypt(hash_enc);
				JSONObject hashJson = new JSONObject(hash);
				// 保存文件的md5
				String md5 = hashJson.getString("md5");
				// 文件本身的md5
				String md5File = UFile.createMd5(f1);
				if (md5.equals(md5File)) {
					// 记录的md5和文件当前的md5一致，没有被篡改
					return true;
				}
			} catch (Exception err) {

			}
		}
		return false;
	}

}
