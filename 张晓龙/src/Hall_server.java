import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;




public class Hall_server {
	boolean started = false;
	ServerSocket ss = null;
	Statement statement = null;
	ResultSet resultSet=null;
	int port = 5200;
	String yonghumingzu="";
	String yonghuming="";
	Chess_server[] chessserver = new Chess_server[9];
	
	List<client> clients = new ArrayList<client>();
	List<Chess_server> servers = new ArrayList<Chess_server>();
	
	public static void main(String[] args){
		new Hall_server().start(5000);
	}
	
	
	public void start(int port){
		try {
			ss=new ServerSocket(port);
			started=true;
			
			Class.forName("org.gjt.mm.mysql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wzy", "root", "12345");
			statement = connection.createStatement();
			
		} catch (BindException e) {
			System.out.println("端口使用中....");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<9;i++){
			chessserver[i] = new Chess_server(this.port+i+1,(i+1));
			new Thread(chessserver[i]).start();
			servers.add(chessserver[i]);
		}
		
		try{
			while(started){
				Socket s = ss.accept();
				client c = new client(s);
				new Thread(c).start();
				clients.add(c);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				ss.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void yonghumingrostring(){
		String temp="";
		for (int i = 0; i < clients.size(); i++) {
			client c = clients.get(i);
			temp=temp+"\t"+c.yonghuming2;
		}
		temp=temp+"\t";
		yonghumingzu=temp;
	}
	
	public boolean jiance(String yonghum){
		String temp="";
		for (int i = 0; i < clients.size(); i++) {
			client c = clients.get(i);
			temp=c.yonghuming2;
			if(temp.equals(yonghum)){
				return true;
			}
		}
		return false;
	}
	
	class client implements Runnable{
		private Socket s=null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		String yonghuming2="";
		
		public client(Socket s){
			this.s=s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				try {
					dis.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clients.remove(this);
				//System.out.println("对方退出了！我从List里面去掉了！");
				// e.printStackTrace();
			}
		}
		
		public String servertostring(){
			String str = "\t";
			for(int i=0;i<9;i++){
				try{
					str=str+"\t"+chessserver[i].clients.size();
				}catch(NullPointerException e){
					str=str+"\t0";
				}
			}
			str=str+"\t";
			return str;
		}
		
		
		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					if(str.substring(0,6).equals("\tzhuce")){
						str=str.substring(6,str.length());
						int a = str.indexOf("\t");
						String yongh=str.substring(0,a);
						String mi=str.substring(a+1,str.length());
						
						resultSet = statement.executeQuery("select username from chessuser where username "+"= "+yongh);
						String result1="";
						while(resultSet.next()){
							result1 = resultSet.getString(1);
						}
						if(result1.equals(yongh)){
							send("\tyonghumingcunzai");
						}else{
							statement.execute("insert into chessuser value('"+yongh+"','"+mi+"')");
							send("\tzhuceok");
						}
					}else{
						if(str.substring(0,1).equals("\t")&&!str.substring(0,6).equals("\tzhuce")){
							str = str.substring(1,str.length());
							int a = str.indexOf("\t");
							yonghuming = str.substring(0,a);
							if(jiance(yonghuming)){
								send("\tyidenglu");
								continue;
							}
							String mima = str.substring(a+1,str.length());
							resultSet = statement.executeQuery("select username,pwd from chessuser where username "+"= "+yonghuming);
							String result1="";
							String result2="";
							while(resultSet.next()){
								result1 = resultSet.getString(1);
								result2 = resultSet.getString(2);
							}
							if(result1.equals(yonghuming)){
								if(result2.equals(mima)){
									send("\tdengluok");
									yonghuming2=yonghuming;
								}else{
									send("\tmimacuowu");
								}
							}else{
								send("\tyonghumingcuowu");
							}
						}else{
							if(str.equals("refresh")){
								yonghumingrostring();
								send(servertostring()+yonghumingzu);
							}
						}
					}
				}
			}catch (EOFException e) {
				System.out.println("Client closed!");
			} catch (IOException e) {
				//e.printStackTrace();
			} catch (SQLException e) {
				//e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (s != null) {
						s.close();
						// s = null;
					}
					for (int i = 0; i < clients.size(); i++) {
						client c = clients.get(i);
						c.send("\tcheck");
					}
				} catch (IOException e1) {
					//e1.printStackTrace();
				}

			}
		}
		
	}
}
