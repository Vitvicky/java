import java.net.*;
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
public class chatserver {
//保护所有客户端的server
	private List<Socket> sockets=new ArrayList<Socket>();
	public chatserver()throws IOException{
		this.init();
	}
	//启动监听
	public void init()throws IOException{
		ServerSocket s=new ServerSocket(9999);
		System.out.println("服务器已在监听9999端口......");
		while(true){//监听并接受客户端
			Socket ss=s.accept();
			sockets.add(ss);
			
			
			DataInputStream in=new DataInputStream(ss.getInputStream());//读入客户端发来的信息进行验证
			DataOutputStream out=new DataOutputStream(ss.getOutputStream());
			String sq=in.readUTF();
			if(sq.startsWith("/yanzheng")){//若客户端发来信息开头为yanzheng,则为聊天
				StringTokenizer user=new StringTokenizer(sq);
				String x=user.nextToken(),y=user.nextToken(),z=user.nextToken();
				
				try{Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				String dbUrl = "jdbc:odbc:sd";
				Connection con = DriverManager.getConnection(dbUrl);
				Statement stmt = con.createStatement();
				ResultSet r= stmt.executeQuery("select * from info ");
				boolean flag=false;
				while(r.next()){
						if(y.equals(r.getString("用户名"))&&z.equals(Integer.toString(r.getInt("密码"))))//注意要把密码的int类型转换成string类型
						{out.write(1);
							flag=true;
						break;}}
						
				if(!flag)
					{out.write(0);
					flag=false;
					break;
					}
				r.close();
				con.close();
				
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
			/*if(sq.startsWith("1")){//若客户端发来信息开头为1，则为对弈
				StringTokenizer user=new StringTokenizer(sq);
				String x=user.nextToken(),y=user.nextToken(),z=user.nextToken();
				out.writeUTF(y);out.writeUTF(z);
			}*/
			String ip=ss.getInetAddress().getHostAddress();
			System.out.println("有客户端进来，他的ip地址是："+ip);
			Thread t=new Thread(new ServerRunner(sockets,ss));
			t.start();}
			
		
	}
	public static void main(String[] args) {
		try{new chatserver();
		
		}
		catch(IOException e){
		e.printStackTrace();	
		}
	}
//服务器端和客户端通信的线程类
//把某个客户端发过来的数据转发给所有的客户端
	class ServerRunner implements Runnable{
		private List<Socket> sockets;
		private Socket currentSocket;
		public ServerRunner(List<Socket> sockets,Socket currentSocket){
			this.sockets=sockets;
			this.currentSocket=currentSocket;
		}
	@Override
	public void run() {
		String ip=currentSocket.getInetAddress().getHostAddress();
		BufferedReader bf=null;
		try{
			bf=new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
			String str=null;
			while((str=bf.readLine())!=null){
				System.out.println(ip+"say:"+str);
				//往所有客户端的Socket写信息
				for(Socket t:sockets){
					PrintWriter pw=new PrintWriter(new OutputStreamWriter(t.getOutputStream()));
					pw.println(str);
					pw.flush();
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
		
	}
}
