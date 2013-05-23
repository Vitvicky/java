package LoginMain;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class Login extends JFrame{	
	
	private String[]s={"在线","离开","请勿打扰","隐身"};//状态选择数组
	private JComboBox state = new JComboBox(s);//状态下拉框
	private JTextField number=new JTextField(16);//帐号输入框
	private JPasswordField password=new JPasswordField(16);//密码输入框
	String drv = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/wzy?useUnicode=true&characterEncoding=utf-8";
	String user = "root";
	String password1 = "12345";
	Connection con;
	PreparedStatement ps;
	ResultSet rs;	
	public Login(){//构造方法
		
		//GUI工具包
		Toolkit kit = Toolkit.getDefaultToolkit();
	    Dimension screenSize = kit.getScreenSize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    Image image=kit.getImage("image/icon2.jpg");
	    Image image2=kit.getImage("image/航母.jpg");
	    //初始化输入面板	    	        
	    JPanel jpnum=new JPanel();
	    JPanel jppw=new JPanel();
	    JPanel jpnps=new JPanel();
	    JPanel jpbutton=new JPanel();
	    JPanel jpstate=new JPanel();	    
	    jpnum.add(new JLabel("用户名："));
	    jpnum.add(number);
	    jppw.add(new JLabel("密  码  ："));
	    password.setEchoChar('*');
	    jppw.add(password);
	    jpstate.add(new JLabel("状态："));
	    state.setBackground(Color.white);
	    jpstate.add(state);
	    jpnps.add(jpnum);
	    jpnps.add(jppw);
	    jpnps.add(jpstate);
	    jpnps.setLayout(new BoxLayout(jpnps, BoxLayout.Y_AXIS));
	    JButton reg = new JButton("确定");
	    listener l=new listener();
	    reg	.addActionListener(l);
	    reg.setContentAreaFilled(false);
	    reg.setBorder(BorderFactory.createEmptyBorder());
	    jpbutton.add(reg);
	    JButton login = new JButton("取消");
	    login.addActionListener(l);
	    login.setContentAreaFilled(false);
	    login.setBorder(BorderFactory.createEmptyBorder());
	    jpbutton.add(login);	    	    
	    
	    jpnum.setOpaque(false);
	    jppw.setOpaque(false);
	    jpstate.setOpaque(false);
	    jpnps.setOpaque(false);
	    jpbutton.setOpaque(false);
	    //设置视窗属性
	    ImagePanel panel = new ImagePanel(image2);
	    getLayeredPane().add(panel, new Integer(Integer.MIN_VALUE));   
	    panel.setBounds(-3,-3, screenWidth /3, screenHeight / 3);
	    ((JComponent) getContentPane()).setOpaque(false);
	    add(jpnps);
	    add(jpbutton);
	    setLocation(screenWidth / 3, screenHeight / 3);
	    setSize(380,285);//screenWidth /4-10, screenHeight / 4+20
	    setTitle("学生教学管理系统-wlh");
	    setIconImage(image);
	    setLayout(new FlowLayout());
	    setResizable(false);
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setVisible(true);
	    //setUndecorated(true);
}

//内类：图片面板类：加载图片
class ImagePanel extends JPanel{
	public ImagePanel(Image img){
		image = img;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(image==null) return;
		int imageWidth=image.getWidth(this);
		int imageHeight=image.getHeight(this);
		g.drawImage(image,0,0,null);
	}
	private Image image;
}


class listener implements ActionListener{
public void actionPerformed(ActionEvent mm) {
	if(mm.getActionCommand().equals("确定")){
		System.out.println("aaa");
	String a=new String(number.getText());
	String b=new String(password.getPassword());
	String sql="select * from info where username="+"'"+a+"'"+" and password="+"'"+b+"'";
	//String sql="select * from info";
	
	ResultSet rs=null;
	if(a.trim().equals("")||b.equals("")){
		JOptionPane.showConfirmDialog(null,"你还没有填写相应内容!","询问对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
	}
	else
	{
	try{
		Class.forName(drv).newInstance();
		con = DriverManager.getConnection(url, user, password1);
		Statement sta=con.createStatement();
		rs=sta.executeQuery(sql);
		
		if(rs.next()) {
			//System.out.println(a+" "+b);
			if (rs.getString("username").equals(a)
					&& rs.getString("password").equals(b)) {
				//System.out.println("写信息");
			dispose();
			new Main();	}
			
	}
		else{
			int i=0;
			System.out.println("qqq");
			JOptionPane.showConfirmDialog(null,"用户名或密码填写错误！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
			i=i+1;
			if(i>2){
				 JOptionPane.showConfirmDialog(null, "登录失败","提示",JOptionPane.DEFAULT_OPTION);
			      System.exit(0);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	}	
	}
	if(mm.getActionCommand().equals("取消")){
		System.exit(0);
	}
}
}
public static void main(String[] args){
	new Login();
}
}
