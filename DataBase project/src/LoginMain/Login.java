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
	
	private String[]s={"����","�뿪","�������","����"};//״̬ѡ������
	private JComboBox state = new JComboBox(s);//״̬������
	private JTextField number=new JTextField(16);//�ʺ������
	private JPasswordField password=new JPasswordField(16);//���������
	String drv = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/wzy?useUnicode=true&characterEncoding=utf-8";
	String user = "root";
	String password1 = "12345";
	Connection con;
	PreparedStatement ps;
	ResultSet rs;	
	public Login(){//���췽��
		
		//GUI���߰�
		Toolkit kit = Toolkit.getDefaultToolkit();
	    Dimension screenSize = kit.getScreenSize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    Image image=kit.getImage("image/icon2.jpg");
	    Image image2=kit.getImage("image/��ĸ.jpg");
	    //��ʼ���������	    	        
	    JPanel jpnum=new JPanel();
	    JPanel jppw=new JPanel();
	    JPanel jpnps=new JPanel();
	    JPanel jpbutton=new JPanel();
	    JPanel jpstate=new JPanel();	    
	    jpnum.add(new JLabel("�û�����"));
	    jpnum.add(number);
	    jppw.add(new JLabel("��  ��  ��"));
	    password.setEchoChar('*');
	    jppw.add(password);
	    jpstate.add(new JLabel("״̬��"));
	    state.setBackground(Color.white);
	    jpstate.add(state);
	    jpnps.add(jpnum);
	    jpnps.add(jppw);
	    jpnps.add(jpstate);
	    jpnps.setLayout(new BoxLayout(jpnps, BoxLayout.Y_AXIS));
	    JButton reg = new JButton("ȷ��");
	    listener l=new listener();
	    reg	.addActionListener(l);
	    reg.setContentAreaFilled(false);
	    reg.setBorder(BorderFactory.createEmptyBorder());
	    jpbutton.add(reg);
	    JButton login = new JButton("ȡ��");
	    login.addActionListener(l);
	    login.setContentAreaFilled(false);
	    login.setBorder(BorderFactory.createEmptyBorder());
	    jpbutton.add(login);	    	    
	    
	    jpnum.setOpaque(false);
	    jppw.setOpaque(false);
	    jpstate.setOpaque(false);
	    jpnps.setOpaque(false);
	    jpbutton.setOpaque(false);
	    //�����Ӵ�����
	    ImagePanel panel = new ImagePanel(image2);
	    getLayeredPane().add(panel, new Integer(Integer.MIN_VALUE));   
	    panel.setBounds(-3,-3, screenWidth /3, screenHeight / 3);
	    ((JComponent) getContentPane()).setOpaque(false);
	    add(jpnps);
	    add(jpbutton);
	    setLocation(screenWidth / 3, screenHeight / 3);
	    setSize(380,285);//screenWidth /4-10, screenHeight / 4+20
	    setTitle("ѧ����ѧ����ϵͳ-wlh");
	    setIconImage(image);
	    setLayout(new FlowLayout());
	    setResizable(false);
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setVisible(true);
	    //setUndecorated(true);
}

//���ࣺͼƬ����ࣺ����ͼƬ
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
	if(mm.getActionCommand().equals("ȷ��")){
		System.out.println("aaa");
	String a=new String(number.getText());
	String b=new String(password.getPassword());
	String sql="select * from info where username="+"'"+a+"'"+" and password="+"'"+b+"'";
	//String sql="select * from info";
	
	ResultSet rs=null;
	if(a.trim().equals("")||b.equals("")){
		JOptionPane.showConfirmDialog(null,"�㻹û����д��Ӧ����!","ѯ�ʶԻ���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
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
				//System.out.println("д��Ϣ");
			dispose();
			new Main();	}
			
	}
		else{
			int i=0;
			System.out.println("qqq");
			JOptionPane.showConfirmDialog(null,"�û�����������д����","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
			i=i+1;
			if(i>2){
				 JOptionPane.showConfirmDialog(null, "��¼ʧ��","��ʾ",JOptionPane.DEFAULT_OPTION);
			      System.exit(0);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	}	
	}
	if(mm.getActionCommand().equals("ȡ��")){
		System.exit(0);
	}
}
}
public static void main(String[] args){
	new Login();
}
}
