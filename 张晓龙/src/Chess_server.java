import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.swing.JOptionPane;

public class Chess_server implements Runnable {
	boolean started = false;
	ServerSocket ss = null;
	ChessData qp = new ChessData();
	private int ids=0;
	private int numbs=0;
	private int te=0;
	private int port=0;
	private boolean canplay=false;
	public int room=0;

	List<client> clients = new ArrayList<client>();
	
	public Chess_server(int port,int room){
		this.port=port;
		this.room=room;
	}
	
	public static void main(String[] args){
		//new trychess2_server().start(5757);
	}
	
	public void disconnect (){
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			ss=new ServerSocket(port);
			started=true;
		} catch (BindException e) {
			//System.out.println("端口使用中....");
			//System.out.println("请关掉相关程序并重新运行服务器！");
			//System.exit(0);
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		try{
			while(started){
				Socket s = ss.accept();
				canplay=true;
				te++;
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
				//e.printStackTrace();
			}catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	
	
	class client implements Runnable{
		private Socket s=null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		private int numsss=0;
		
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
			this.insert();
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
				System.out.println("对方退出了！我从List里面去掉了！");
				// e.printStackTrace();
			}
		}
		
		public String huiqi(int num,int b_w){
			String result1=new String("");
			String result2=new String("");
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessdata", "chessuser", "chess");
				Statement statement = connection.createStatement();
				ResultSet resultSet=null;
				for(int i=num-2;i>0;i--){
					resultSet = statement.executeQuery("select id, data, whoseturn from chessdata"+room+" where id "+"= "+i);
					int temp = 0;
					while(resultSet.next()){
						temp = Integer.parseInt(resultSet.getString(3));
						result1 = resultSet.getString(2);
						result2 = resultSet.getString(3);
					}
					if(temp==b_w){
						numbs=num-i;
						break;
					}
				}
				
				result1=result1+result2;
				connection.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result1;
			
		}
		
		public void insert(){
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessdata", "chessuser", "chess");
				Statement statement = connection.createStatement();
				ResultSet resultSet=null;
				int i =ids-1;
				if(ids==0){
					statement.execute("insert into chessdata"+room+" value('"+ids+"','"+qp.b_or_w+"','"+qp.toString()+"')");
					//statement.executeBatch();
					ids++;
				}else{
					resultSet = statement.executeQuery("select data from chessdata"+room+" where id "+"= "+i);
					String result1="";
					while(resultSet.next()){
						result1 = resultSet.getString(1);
					}
					if(!result1.equals(qp.toString())){
						//statement.addBatch("insert into chessdata value('"+ids+"','"+qp.b_or_w+"','"+qp.toString()+"')");
						statement.execute("insert into chessdata"+room+" value('"+ids+"','"+qp.b_or_w+"','"+qp.toString()+"')");
						//statement.executeBatch();
						ids++;
					}
				}
				connection.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void transfer(int[][] chess,String qps){
			String temp=qps;
			int num=0;
			int tempb_or_w=0;
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					chess[i][j]=Integer.parseInt(temp.substring(0,1));
					temp=temp.substring(2,temp.length());
					if(chess[i][j]==9){
						chess[i][j]=0;
					}
				}
				temp=temp.substring(1,temp.length());
			}
			tempb_or_w=Integer.parseInt(temp.substring(0,1));
			try{
				qp.b_or_w=tempb_or_w;
				num=Integer.parseInt(temp);
			}catch(NumberFormatException e){
				
			}
			
		}
		
		public void delete(int ids2,int numbs){
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessdata", "chessuser", "chess");
				Statement statement = connection.createStatement();
				for(int i=ids2-1;i>(ids2-(numbs+1));i--){
					ids--;
					statement.execute("delete from chessdata"+room+" where id = "+i);
				}
				connection.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void deleteall(){
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chessdata", "chessuser", "chess");
				Statement statement = connection.createStatement();
				statement.execute("delete from chessdata"+room);
				connection.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					System.out.println(str);
					if(str.equals("refresh")){
						qp.chess=qp.check2(qp.chess);
						for (int i = 0; i < clients.size(); i++) {
							client c = clients.get(i);
							c.send(qp.toString()+qp.b_or_w+clients.size()+te);
						}
					}else{
						String tempstr="";
						try{
							tempstr=str.substring(1,2);
						}catch(StringIndexOutOfBoundsException ffe){
							
						}
						if(tempstr.equals("\t")){
							int b_or_w=0;
							int a=0;
							int b=0;
							b_or_w=Integer.parseInt(str.substring(0, 1));
							a=Integer.parseInt(str.substring(2, 3));
							b=Integer.parseInt(str.substring(4, 5));
							qp.b_or_w=b_or_w;
							
							int[][] tempqp= new int[8][8];
							for(int i=0;i<8;i++){
								for(int j=0;j<8;j++){
									if(qp.chess[i][j]==9){
										qp.chess[i][j]=0;
									}
									tempqp[i][j]=qp.chess[i][j];
								}
							}
							
							tempqp=qp.confirm(tempqp, a, b);
							if(tempqp==qp.chess_1||tempqp==qp.chess_2){
								System.out.println("again");
								continue;
							}else{
								if(qp.compare(tempqp)){
									System.out.println("Not here");
									continue;
								}else{
									qp.chess=tempqp;
								}
							}
							qp.chess=qp.check2(qp.chess);
							str=qp.toString();
							te++;
							
							for (int i = 0; i < clients.size(); i++) {
								client c = clients.get(i);
								c.send(str+qp.b_or_w+clients.size()+te);
							}
							
							int nums_of_blank = qp.confirmwin();
							qp.count();
							if(nums_of_blank==0){
								if(qp.num_of_b>qp.num_of_w){
									for (int i = 0; i < clients.size(); i++) {
										client c = clients.get(i);
										c.send("\theisishengli");
										canplay=false;
									}
								}else{
									if(qp.num_of_b<qp.num_of_w){
										for (int i = 0; i < clients.size(); i++) {
											client c = clients.get(i);
											c.send("\tbaisishengli");
											canplay=false;
										}
									}else{
										for (int i = 0; i < clients.size(); i++) {
											client c = clients.get(i);
											c.send("\theqi");
											canplay=false;
										}
									}
								}
							}else{
								int[][] tempqp2= new int[8][8];
								for(int i=0;i<8;i++){
									for(int j=0;j<8;j++){
										if(qp.chess[i][j]==9){
											qp.chess[i][j]=0;
										}
										tempqp2[i][j]=qp.chess[i][j];
									}
								}
								int[][] tempqp3= new int[8][8];
								for(int i=0;i<8;i++){
									for(int j=0;j<8;j++){
										if(qp.chess[i][j]==9){
											qp.chess[i][j]=0;
										}
										tempqp3[i][j]=qp.chess[i][j];
									}
								}
								tempqp2=qp.check(tempqp2);
								if(qp.b_or_w==1){
									qp.b_or_w=2;
								}else{
									qp.b_or_w=1;
								}
								tempqp3=qp.check(tempqp3);
								if(qp.b_or_w==1){
									qp.b_or_w=2;
								}else{
									qp.b_or_w=1;
								}
								if(qp.compare(tempqp2)){
									if(qp.compare(tempqp3)){
										if(qp.num_of_b>qp.num_of_w){
											for (int i = 0; i < clients.size(); i++) {
												client c = clients.get(i);
												c.send("\theisishengli");
												canplay=false;
											}
										}else{
											if(qp.num_of_b<qp.num_of_w){
												for (int i = 0; i < clients.size(); i++) {
													client c = clients.get(i);
													c.send("\tbaisishengli");
													canplay=false;
												}
											}else{
												for (int i = 0; i < clients.size(); i++) {
													client c = clients.get(i);
													c.send("\theqi");
													canplay=false;
												}
											}
										}
									}else{
										qp.chess=qp.check2(qp.chess);
										str=qp.toString();
										
										for (int i = 0; i < clients.size(); i++) {
											client c = clients.get(i);
											if(qp.b_or_w==2){
												c.send(str+"3"+clients.size()+te);//1
											}else{
												c.send(str+"4"+clients.size()+te);//2
											}
										}
									}
								}
							}
						}else{
							String strtemp="";
							try{
								strtemp=str.substring(0,6);
							}catch(StringIndexOutOfBoundsException e){
								
							}
							if(strtemp.equals("\thuiqi")){
								int temp2=Integer.parseInt(str.substring(7,8));
								int temp3=Integer.parseInt(str.substring(8,9));
								if(temp2==1){
									temp2=2;
								}else{
									temp2=1;
								}
								if(temp3!=3){
									if(temp3==2){
										if(temp2==1){
											temp2=2;
										}else{
											temp2=1;
										}
										client ff = clients.get(temp2-1);
										qp.chess=qp.check2(qp.chess);
										ff.send("\thuiqi\tno");
										qp.checkback();
										for (int i = 0; i < clients.size(); i++) {
											if(i!=0&&i!=1){
												client c = clients.get(i);
												c.send("\thuiqi\tno");
											}
										}
									}else{
										client d=null;
										try{
											if(temp2==1){
												d = clients.get(1);
											}else{
												d = clients.get(0);
											}
										}catch(IndexOutOfBoundsException e){
										}
										qp.chess=qp.check2(qp.chess);
										d.send("\thuiqi");
										qp.checkback();
										for (int i = 0; i < clients.size(); i++) {
											if(i!=0&&i!=1){
												client c = clients.get(i);
												c.send("\thuiqi");
											}
										}
									}
								}else{
									if(temp2==1){
										temp2=2;
									}else{
										temp2=1;
									}
									String qps = this.huiqi(ids,temp2);
									this.transfer(qp.chess, qps);
									delete(ids,numbs);
									qp.chess=qp.check2(qp.chess);
									for (int i = 0; i < clients.size(); i++) {
										client c = clients.get(i);
										c.send(qp.toString()+qp.b_or_w+clients.size()+te);
										if(temp2==1){
											c.send("\t黑方悔棋");
										}else{
											c.send("\t白方悔棋");
										}
									}
								}
							}else{
								if(str.equals("\twhitetimeout")){
									for (int i = 0; i < clients.size(); i++) {
										client c = clients.get(i);
										c.send("\theisishengli");
										canplay=false;
									}
								}else{
									if(str.equals("\tblacktimeout")){
										for (int i = 0; i < clients.size(); i++) {
											client c = clients.get(i);
											c.send("\tbaisishengli");
											canplay=false;
										}
									}else{
										try{
											tempstr=str.substring(0,7);
										}catch(StringIndexOutOfBoundsException e){
											
										}
										if(tempstr.equals("\tremove")){
											System.out.println("Client closed!");
											int num = Integer.parseInt(str.substring(7,str.length()));
											if(num==1){
												try{
													client c = clients.get(1);
													c.send("\tduifangtuichu");
												}catch(IndexOutOfBoundsException e){
												}
											}
											if(num==2){
												try{
													client c = clients.get(0);
													if(c.numsss==1){
														c.send("\tduifangtuichu");
													}
												}catch(IndexOutOfBoundsException e){
												}
											}
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
												if(num==1||num==2){
													if(canplay){
														for (int i = 0; i < clients.size(); i++) {
															client c = clients.get(i);
															c.send("\tyifangtuichu");
															canplay=false;
														}
													}
												}
												if(clients.size()==0){
													deleteall();
													qp.reset();
													ids=0;
													te=0;
													numbs=0;
													//System.exit(0);
												}
											} catch (IOException e1) {
												e1.printStackTrace();
											}
										}else{
											if(str.substring(0,3).equals("\n\n\n")){
												numsss=Integer.parseInt(str.substring(3,str.length()));
											}else{
												for (int i = 0; i < clients.size(); i++) {
													client c = clients.get(i);
													c.send(str);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} catch (EOFException e) {
				System.out.println("Client closed!");
			} catch (IOException e) {
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
					System.out.println(clients.size());
					if(clients.size()==0){
						deleteall();
						qp.reset();
						ids=0;
						te=0;
						numbs=0;
						//System.exit(0);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}
		
	}
}
