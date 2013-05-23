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
//�������пͻ��˵�server
	private List<Socket> sockets=new ArrayList<Socket>();
	public chatserver()throws IOException{
		this.init();
	}
	//��������
	public void init()throws IOException{
		ServerSocket s=new ServerSocket(9999);
		System.out.println("���������ڼ���9999�˿�......");
		while(true){//���������ܿͻ���
			Socket ss=s.accept();
			sockets.add(ss);
			
			
			DataInputStream in=new DataInputStream(ss.getInputStream());//����ͻ��˷�������Ϣ������֤
			DataOutputStream out=new DataOutputStream(ss.getOutputStream());
			String sq=in.readUTF();
			if(sq.startsWith("/yanzheng")){//���ͻ��˷�����Ϣ��ͷΪyanzheng,��Ϊ����
				StringTokenizer user=new StringTokenizer(sq);
				String x=user.nextToken(),y=user.nextToken(),z=user.nextToken();
				
				try{Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				String dbUrl = "jdbc:odbc:sd";
				Connection con = DriverManager.getConnection(dbUrl);
				Statement stmt = con.createStatement();
				ResultSet r= stmt.executeQuery("select * from info ");
				boolean flag=false;
				while(r.next()){
						if(y.equals(r.getString("�û���"))&&z.equals(Integer.toString(r.getInt("����"))))//ע��Ҫ�������int����ת����string����
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
			
			/*if(sq.startsWith("1")){//���ͻ��˷�����Ϣ��ͷΪ1����Ϊ����
				StringTokenizer user=new StringTokenizer(sq);
				String x=user.nextToken(),y=user.nextToken(),z=user.nextToken();
				out.writeUTF(y);out.writeUTF(z);
			}*/
			String ip=ss.getInetAddress().getHostAddress();
			System.out.println("�пͻ��˽���������ip��ַ�ǣ�"+ip);
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
//�������˺Ϳͻ���ͨ�ŵ��߳���
//��ĳ���ͻ��˷�����������ת�������еĿͻ���
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
				//�����пͻ��˵�Socketд��Ϣ
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
