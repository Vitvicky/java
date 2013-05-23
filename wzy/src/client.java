import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
public class client {
	Socket ss;
	BufferedReader n;BufferedReader u;
	PrintWriter out;
	
	public static void main(String[] args) {
		
		new client().run();
	}
public void run(){
	try{
		ss=new Socket("wzy",8888);//创建连接指定服务器和端口的Socket
		u=new BufferedReader(new InputStreamReader(System.in));//创建键盘输入的数据流
		out=new PrintWriter(ss.getOutputStream());//创建发送到服务器端的数据流
		String date;
		boolean f=true;
		while(f){
			date=new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());//获取系统时间
			String line=u.readLine();//通过流发送信息给服务器端
			out.println(date+" "+line);
			out.flush();
			if(line.equals("exit"))f=false;}
		}
		catch(Exception e){
			
	}
}
}
