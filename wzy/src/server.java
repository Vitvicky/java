import java.net.*;
import java.io.*;
public class server {
	InetAddress add;
	ServerSocket s;//���������ServerSocket
	Socket ss;
	boolean f=true;
	
	
	public static void main(String[] args)throws Exception {
		new server().run();

	}
public void run(){
	try{
		add=InetAddress.getByName("wzy");//�����������˵�IP�ȴ���Ϣ
		s=new ServerSocket(8888,10,add);//����ServerSocket
		System.out.println("����������");
		ss=s.accept();//�����ͻ�������
		System.out.println("�пͻ�������");
		BufferedReader input=new BufferedReader(new InputStreamReader(ss.getInputStream()));//��ȡ�ͻ�������
		while(f){
			System.out.println("client:"+input.readLine());
		}
	}
	catch(Exception e){
		
	}
}
}
