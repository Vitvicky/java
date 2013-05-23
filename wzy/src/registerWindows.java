
import java.awt.*;

import javax.swing.*;

import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.watermark.SubstanceImageWatermark;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class registerWindows extends JFrame implements ActionListener{
	JLabel name = new JLabel("姓名:");
	JTextField nametxt = new JTextField();
	JLabel nicheng = new JLabel("用户名:");
	JTextField nichengtxt = new JTextField();
	JLabel mima = new JLabel("密码:");
	JPasswordField q = new JPasswordField();
	JLabel xingbie = new JLabel("性别");
	ButtonGroup w = new ButtonGroup();
	JRadioButton e1 = new JRadioButton("男", false);
	JRadioButton e2 = new JRadioButton("女", false);
	JLabel subject = new JLabel("专业选择");
	JComboBox r = new JComboBox();
	String dbUrl = "jdbc:odbc:sd";
	String sql = "select * from info";

	public registerWindows() {
		setTitle("用户注册");
		setPreferredSize(new Dimension(400, 600));
		setBounds(100, 100, 500, 700);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);
		

		JPanel a = new JPanel();
		a.setBounds(0,0,500,700);
		a.setLayout(null);
		add(a);
		
		//姓名的输入
		name.setBounds(10, 50, 60, 25);
		a.add(name);
		nametxt.setBounds(100, 50, 140, 22);
		a.add(nametxt);
		//昵称的输入
		nicheng.setBounds(10, 80, 60, 25);
		a.add(nicheng);
		nichengtxt.setBounds(100, 80, 140, 22);
		a.add(nichengtxt);
		//密码的输入
		mima.setBounds(10, 110, 60, 25);
		a.add(mima);
		q.setBounds(100, 110, 140, 22);
		a.add(q);
		//性别选择
		xingbie.setBounds(10, 140, 60, 25);
		a.add(xingbie);
		
		e1.setBounds(100, 140, 50, 25);
		e2.setBounds(160, 140, 50, 25);
		w.add(e1);
		w.add(e2);
		a.add(e1);
		a.add(e2);
		//专业选择
		subject.setBounds(10, 170, 60, 25);
		a.add(subject);
		r.addItem("计算机科学与技术");
		r.addItem("软件工程");
		r.addItem("数字媒体");
		r.addItem("电子商务");
		r.setBounds(100, 170, 140, 22);
		a.add(r);
		JLabel picture = new JLabel();//贴入图片
		picture.setBounds(60, 240, 176, 220);
		a.add(picture);
		ImageIcon t = new ImageIcon("21314VY6-1.gif");
		picture.setIcon(t);
		JButton a1=new JButton("提交");//提交按钮
		a1.setBounds(60,480,70,22);
		a1.addActionListener(this);
		a.add(a1);
		JButton a2=new JButton("重置");
		a2.setBounds(160,480,70,22);
		a.add(a2);
		JLabel j1=new JLabel();
		ImageIcon h = new ImageIcon("mm1.jpg");
		j1.setIcon(h);
		j1.setBounds(0, 0, 500, 700);
		a.add(j1);
		}
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0){
		int result=JOptionPane.showConfirmDialog(this,"你确定提交吗?","询问对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(result==JOptionPane.OK_OPTION){
			String s1 = nametxt.getText();
			String s2 = nichengtxt.getText();
			String s3 = q.getText();
			
			
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection c = DriverManager.getConnection(dbUrl);
				Statement s = c.createStatement();
				//ResultSet set=s.executeQuery("select*from info");
				//while(set.next()){										
					s.executeUpdate("INSERT INTO info(姓名,用户名,密码 ) VALUES('"+s1+"','"+s2+"','"+s3+"')");
				//}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			
		}
		else{dispose();
		}
		
		
		}
		
public static void main(String args[]){
	JFrame.setDefaultLookAndFeelDecorated(true);
	JDialog.setDefaultLookAndFeelDecorated(true);
	try  {
		SubstanceImageWatermark watermark  =   new  SubstanceImageWatermark(chatdengluqi. class .getResourceAsStream( "111.jpg " ));
        //watermark.setKind(ImageWatermarkKind.SCREEN_CENTER_SCALE);
        //SubstanceSkin skin  =   new  OfficeBlue2007Skin().withWatermark(watermark);   //初始化有水印的皮肤

        	UIManager.setLookAndFeel( new SubstanceOfficeBlue2007LookAndFeel()); 
        	//SubstanceLookAndFeel.setSkin(skin);  //设置皮肤
       
    }  catch  (UnsupportedLookAndFeelException ex) {
       
    } 
	
	registerWindows aa=new registerWindows();
}
}
	
	