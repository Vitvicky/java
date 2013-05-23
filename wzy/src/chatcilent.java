import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
public class chatcilent extends JFrame {
	Socket socket;
	JButton j1;
	String name;
	JTextArea sendarea;
	JTextArea contentarea;
	JScrollPane j4;
	JScrollPane j5;
	public chatcilent(Socket socket,String name){
		this.socket=socket;
		this.name=name;
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.init();
		cilentThread t=new cilentThread(socket,contentarea);
		t.start();
	}
	public void init(){
		this.setTitle("�ҵ�������");
		this.setSize(300,500);
		this.setResizable(false);
		contentarea=new JTextArea();
		contentarea.setLineWrap(false);
		contentarea.setEditable(false);
		//this.setLayout(new BorderLayout());
		j4=new JScrollPane(contentarea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sendarea=new JTextArea();
		sendarea.setLineWrap(true);
		j5=new JScrollPane(sendarea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//�ָ��
		JSplitPane jp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,j4,j5);
		jp.setDividerLocation(250);
		this.add(jp,BorderLayout.CENTER);
		j1=new JButton("����");
		JPanel qq=new JPanel();
		qq.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(qq,BorderLayout.SOUTH);
		
		JLabel jname=new JLabel("�û���:"+this.name+"  ");
		qq.add(jname);
		j1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			//��ȡ��������
				String s=sendarea.getText();
				SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
				String time=f.format(new Date());
				String send=name+" "+time + "˵:"+s;
				//����������
				PrintWriter out=null;
				try{
					out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					out.println(send);
					out.flush();}
					catch(IOException e1){
						e1.printStackTrace();
					}
				sendarea.setText("");
			}
		});
		qq.add(j1);
		
		}
	}
	//�ͻ��ͷ�����ͨ��
	
		
class cilentThread extends Thread{
	private Socket socket;
	private JTextArea area;
	public cilentThread(Socket socket,JTextArea area){
		this.socket=socket;
		this.area=area;
	}
	
	

	public void run() {
		BufferedReader bf=null;
		try{
			
				bf=new BufferedReader(new InputStreamReader(socket.getInputStream()));//��������ȡ������ӵ������¼����
				String str=null;
				while((str=bf.readLine())!=null){
					area.append(str+"\n");
				}
				}
		catch(Exception e){
			e.printStackTrace();
		}	
		finally{
			if(bf!=null){
				try{
					bf.close();
				}
				catch(IOException e){
					
				}
			}
		}
	}	
}		
	
	
	
	