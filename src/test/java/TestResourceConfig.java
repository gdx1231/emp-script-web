import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.gdxsoft.easyweb.conf.ConfScriptPath;
import com.gdxsoft.easyweb.script.userConfig.ResourceConfig;
import com.gdxsoft.easyweb.utils.UFile;

public class TestResourceConfig {

	public static void main(String... args) {

		TestResourceConfig t = new TestResourceConfig();
		t.test();

	}

	@Test
	public void test() {
		this.test1();
		// this.test2();
	}

	public void test1() {
		ConfScriptPath cs = new ConfScriptPath();
		cs.setPath("resources:/define.xml");
		
		ResourceConfig.initializeResouces(cs);

		Map<String, Map<String, String>> map = ResourceConfig.RESOURCE_CACHED;

		map.forEach((k, v) -> {
			// System.out.println(k);
			v.forEach((k1, v1) -> {
				System.out.println(k1 + ", " + v1.length());
			});
		});


		ResourceConfig rc1 = new ResourceConfig(cs, "/ewa/ewa.xml", "login");
		System.out.println(rc1.checkConfigurationExists());
	}

	public void test2() {
		URL url = ResourceConfig.class.getResource("/define.xml");
		String protocol = url.getProtocol();
		String path = url.getPath();
		String file = url.getFile();
		System.out.println(protocol + ", " + path + ", " + file);

		if (protocol.equals("jar")) {
			String jarPath = path.split("\\!")[0];
			String dir = url.getPath().split("\\!")[1];
			// remove start with '/'
			String dirZip = dir.substring(1);
			System.out.println(jarPath + ",  " + dirZip);
			try {
				URL url1 = new URL(jarPath);
				File f1 = new File(url1.toURI());
				System.out.println(f1.getAbsolutePath());

				List<String> lst = UFile.getZipList(f1.getAbsolutePath());
				lst.forEach(f -> {
					if (f.startsWith(dirZip)) {
						System.out.println(f);

						try {
							String content = UFile.readZipText(f1.getAbsolutePath(), f);
							System.out.println(content.length());
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}
				});

			} catch (IOException | URISyntaxException e1) {
				System.out.println(e1.getMessage());
			}

		}
		System.out.println(url.getPath());
	}

}
