import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class GUIserver extends JFrame implements ActionListener,Runnable{
	InetAddress add;//��������ͨ�������
	ServerSocket s;
	Socket ss;
	DataInputStream in;
	DataOutputStream out;
	JButton j1;
	JTextField j2;
	TextArea j3=new TextArea();
	JScrollPane j4;
	public GUIserver()throws Exception{
		j1=new JButton("����");
		j2=new JTextField(15);
		j1.addActionListener(this);
		this.setLayout(new BorderLayout());
		
		JPanel qq=new JPanel();
		
		qq.add(j1);
		qq.add(j2);
		
		//���������
		j4=new JScrollPane();
		j4.setViewportView(j3);
		
		this.setTitle("��������");
		this.add(BorderLayout.SOUTH,qq);
		this.add(j4);
		this.setSize(300,500);
		this.setVisible(true);
		add=InetAddress.getByName("wzy-PC");//����ServerSocket��ʼ����
		s=new ServerSocket(10000);
		System.out.println("����������...");
		j3.append("����������..."+"\n");	
	}
	
	public static void main(String[] args) throws Exception{
		//����ǰ����Ϊ�߳�����
		Thread t=new Thread(new GUIserver());
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		try{//��ͻ��˷�����Ϣ
			
			if(arg0.getSource()==j1){
			
			out.writeUTF(j2.getText());
			out.flush();
			j3.append("me:"+j2.getText());
			j3.append("\n");
			j2.setText("");
			}
		}
		catch(Exception ee){
			System.out.println(ee.getMessage());
		}
	}

	@Override
	public void run() {
		//���ܿͻ�����Ϣ
		try{
			while(true){
				ss=s.accept();
				System.out.println("�пͻ�������...");
				j3.append("�пͻ�������..."+"\n");
				//����������
				in=new DataInputStream(ss.getInputStream());
				out=new DataOutputStream(ss.getOutputStream());
				while(true)//������ʱҪ��ѭ��
				j3.append("client:"+in.readUTF()+"\n");
				
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
