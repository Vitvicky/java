import java.net.*;
import java.io.*;
public class server {
	InetAddress add;
	ServerSocket s;//定义服务器ServerSocket
	Socket ss;
	boolean f=true;
	
	
	public static void main(String[] args)throws Exception {
		new server().run();

	}
public void run(){
	try{
		add=InetAddress.getByName("wzy");//创建服务器端的IP等待信息
		s=new ServerSocket(8888,10,add);//创建ServerSocket
		System.out.println("服务器启动");
		ss=s.accept();//监听客户端连接
		System.out.println("有客户端连接");
		BufferedReader input=new BufferedReader(new InputStreamReader(ss.getInputStream()));//获取客户端连接
		while(f){
			System.out.println("client:"+input.readLine());
		}
	}
	catch(Exception e){
		
	}
}
}
