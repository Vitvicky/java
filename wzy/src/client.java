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
		ss=new Socket("wzy",8888);//��������ָ���������Ͷ˿ڵ�Socket
		u=new BufferedReader(new InputStreamReader(System.in));//�������������������
		out=new PrintWriter(ss.getOutputStream());//�������͵��������˵�������
		String date;
		boolean f=true;
		while(f){
			date=new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());//��ȡϵͳʱ��
			String line=u.readLine();//ͨ����������Ϣ����������
			out.println(date+" "+line);
			out.flush();
			if(line.equals("exit"))f=false;}
		}
		catch(Exception e){
			
	}
}
}
