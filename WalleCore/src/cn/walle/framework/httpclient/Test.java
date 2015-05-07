package cn.walle.framework.httpclient;

public class Test {

	public static void main(String[] args) throws Exception {

		WalleHttpClient c = new WalleHttpClient();
		c.setBaseUrl("http://58.252.5.166:8080");
		c.login("zhaofw", "zhaofw");
		System.out.println("OK");
	}

}
