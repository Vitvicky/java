import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;
import org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel;
import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;
import org.jvnet.substance.skin.SubstanceMangoLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenLookAndFeel;
import org.jvnet.substance.watermark.SubstanceImageWatermark;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.logging.Logger;
public class chatdengluqi extends JFrame{
	public static final JLabel name= new JLabel("�û���:"); 
	public static final JLabel mima= new JLabel("����:"); 
	JTextField nametxt = new JTextField();
	JLabel picture = new JLabel();//����ͼƬ
	JPasswordField q = new JPasswordField();
	String dbUrl = "jdbc:odbc:sd";
	//static AudioClip l;
	
	public chatdengluqi(){
		setTitle("��½����");
		setPreferredSize(new Dimension(320, 300));
		setBounds(100, 100, 200, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);
		JPanel a = new JPanel();
	    a.setOpaque(true);
		a.setLayout(null);
		ImageIcon t = new ImageIcon("520.jpg");
		picture.setIcon(t);
		
		picture.setBounds(0, 0, 350, 100);
		 add(a);
		//����������
		name.setForeground(Color.BLACK);
		name.setFont(new Font("",Font.BOLD,15));
		name.setBounds(10, 120, 60, 25);
		a.add(name);
		a.add(picture);
		nametxt.setBounds(100, 120, 160, 22);
		a.add(nametxt);
		  //���������
		mima.setForeground(Color.BLACK);
		mima.setFont(new Font("",Font.BOLD,15));
		mima.setBounds(10, 150, 100, 25);
		a.add(mima);
		
		q.setBounds(100, 150, 140, 22);
		a.add(q);
		JButton a1=new JButton("ȷ��");//ȷ����ť
		a1.setBounds(70,220,70,22);
		a1.addActionListener(new actionAdapter());
		a.add(a1);
		JButton a2=new JButton("ȡ��");
		a2.addActionListener(new actionAdapter());
		a2.setBounds(180,220,70,22);
		a.add(a2);
		
	}
	
	class actionAdapter implements ActionListener {
		//�ڼ�������ʵ��dispose����
		
		String name=null;
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("ȷ��")) {
				String s1=nametxt.getText();
				char[] c=q.getPassword();
				String s2="";
				for(int i=0;i<c.length;i++)
					{s2=s2+c[i];}
				System.out.println("�û�����"+s1+",������"+s2);
				
				
				new friends();
					}
					 if(e.getActionCommand().equals("ȡ��"))
					System.exit(0);
		}
	
	}
public static void main(String[]args) throws MalformedURLException{
	
	
	
	JFrame.setDefaultLookAndFeelDecorated(true);
	JDialog.setDefaultLookAndFeelDecorated(true);
	try  {
		//SubstanceImageWatermark watermark  =   new  SubstanceImageWatermark(othello. class .getResourceAsStream( "111.jpg " ));
		//UIManager.setLookAndFeel( new SubstanceOfficeBlue2007LookAndFeel()); 
		UIManager.setLookAndFeel( new SubstanceGreenMagicLookAndFeel()); 
       
    }  catch  (UnsupportedLookAndFeelException ex) {
       
    } 	
    new chatdengluqi();	
		
	
}
}

/*SubstanceAutumnLookAndFeel
SubstanceBusinessBlackSteelLookAndFeel
SubstanceBusinessBlueSteelLookAndFeel
SubstanceBusinessLookAndFeel
SubstanceChallengerDeepLookAndFeel g
SubstanceCremeCoffeeLookAndFeel
SubstanceCremeLookAndFeel
SubstanceDustCoffeeLookAndFeel
SubstanceDustLookAndFeel
SubstanceEmeraldDuskLookAndFeel
SubstanceMagmaLookAndFeel
SubstanceMistAquaLookAndFeel
SubstanceMistSilverLookAndFeel
SubstanceModerateLookAndFeel
SubstanceNebulaBrickWallLookAndFeel
SubstanceNebulaLookAndFeel
SubstanceOfficeBlue2007LookAndFeel
SubstanceOfficeSilver2007LookAndFeel
SubstanceRavenGraphiteGlassLookAndFeel
SubstanceRavenGraphiteLookAndFeel
SubstanceRavenLookAndFeel g2
SubstanceSaharaLookAndFeel
SubstanceTwilightLookAndFeel*/






