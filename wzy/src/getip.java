import java.net.*;
import java.util.Scanner;
public class getip {

	
	public static void main(String[] args) {
		InetAddress address = null;
		InetAddress address1 = null;
		Scanner scan=new Scanner(System.in);
		String host=scan.next();
		//用静态方法getLocalHost（）执行主机名的InetAddress
		try {
			address=InetAddress.getLocalHost();
			address1=InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		System.out.println("主机IP地址："+address.toString());
		System.out.println("输入网站IP地址："+address1.toString());
	}

}
