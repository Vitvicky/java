import java.net.*;
import java.util.Scanner;
public class getip {

	
	public static void main(String[] args) {
		InetAddress address = null;
		InetAddress address1 = null;
		Scanner scan=new Scanner(System.in);
		String host=scan.next();
		//�þ�̬����getLocalHost����ִ����������InetAddress
		try {
			address=InetAddress.getLocalHost();
			address1=InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		System.out.println("����IP��ַ��"+address.toString());
		System.out.println("������վIP��ַ��"+address1.toString());
	}

}
