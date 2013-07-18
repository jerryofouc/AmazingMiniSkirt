package com.netease.amazing.server.functional;


import org.eclipse.jetty.server.Server;
import org.springside.modules.test.jetty.JettyFactory;

/**
 * 
 * @author calvin
 */
public class QuickStartServer {

	public static final int PORT = 8090;
	public static final String CONTEXT = "/MiniSkirtServer";
	public static final String BASE_URL = "http://localhost:8090/MiniSkirtServer";
	public static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web",
			"springside-core" };

	public static void main(String[] args) throws Exception {
		// 璁惧畾Spring鐨刾rofile
		System.setProperty("spring.profiles.active", "production");

		// 鍚姩Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
	//	JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			server.start();

			System.out.println("Server running at " + BASE_URL);
			System.out.println("Hit Enter to reload the application");

	
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
