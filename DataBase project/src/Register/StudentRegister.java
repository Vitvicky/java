package Register;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentRegister extends JFrame implements ActionListener{
	JLabel name = new JLabel("����:");
	JTextField nametxt = new JTextField();
	JLabel nicheng = new JLabel("����:");
	JTextField nichengtxt = new JTextField();
	JLabel xuehao = new JLabel("ѧ��:");
	JTextField q = new JTextField();
	JLabel xingbie = new JLabel("�Ա�");
	ButtonGroup w = new ButtonGroup();
	JRadioButton e1 = new JRadioButton("��", false);
	JRadioButton e2 = new JRadioButton("Ů", false);
	JLabel subject = new JLabel("Ժϵ");
	JComboBox r = new JComboBox();
	
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
    String drv = "com.mysql.jdbc.Driver";
    String username="root";
	String password="12345";
	Connection c;
	ResultSet rs;
	//String sql = "select * from info";

	public StudentRegister() {
		setTitle("��ͬѧ����");
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
		
		//����������
		name.setBounds(10, 50, 60, 25);
		a.add(name);
		nametxt.setBounds(100, 50, 140, 22);
		a.add(nametxt);
		//�ǳƵ�����
		nicheng.setBounds(10, 80, 60, 25);
		a.add(nicheng);
		nichengtxt.setBounds(100, 80, 140, 22);
		a.add(nichengtxt);
		//���������
		xuehao.setBounds(10, 110, 60, 25);
		a.add(xuehao);
		q.setBounds(100, 110, 140, 22);
		a.add(q);
		//�Ա�ѡ��
		xingbie.setBounds(10, 140, 60, 25);
		a.add(xingbie);
		
		e1.setBounds(100, 140, 50, 25);
		e2.setBounds(160, 140, 50, 25);
		w.add(e1);
		w.add(e2);
		a.add(e1);
		a.add(e2);
		//רҵѡ��
		subject.setBounds(10, 170, 60, 25);
		a.add(subject);
		r.addItem("�������ѧ�뼼��");
		r.addItem("�������");
		r.addItem("����ý��");
		r.addItem("��������");
		r.setBounds(100, 170, 140, 22);
		a.add(r);
		JLabel picture = new JLabel();//����ͼƬ
		picture.setBounds(60, 240, 176, 220);
		a.add(picture);
		ImageIcon t = new ImageIcon("image/21314VY6-1.gif");
		picture.setIcon(t);
		JButton a1=new JButton("�ύ");//�ύ��ť
		a1.setBounds(60,480,70,22);
		a1.addActionListener(this);
		a.add(a1);
		JButton a2=new JButton("����");
		a2.setBounds(160,480,70,22);
		a.add(a2);
		JLabel j1=new JLabel();
		ImageIcon h = new ImageIcon("image/qq.jpg");
		j1.setIcon(h);
		j1.setBounds(0, 0, 600, 800);
		a.add(j1);
		}
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0){
		int result=JOptionPane.showConfirmDialog(this,"��ȷ���ύ��?","ѯ�ʶԻ���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(result==JOptionPane.OK_OPTION){
			String name = new String(nametxt.getText());
			String age = new String(nichengtxt.getText());
			String xuehao=new String(q.getText());
			String sex = new String(e1.isSelected()?e1.getText():e2.getText());
			String depart=(String) r.getSelectedItem();
			//System.out.println(name+" "+age+" "+sex+" "+depart+" "+xuehao);
//			String sql="insert into student(Name,Sex,Age,DepartName,StudentNumber) values " +
//			"('"+name+"','"+age+"','"+sex+"','"+depart+"','"+xuehao+"')";
			String sql="insert into student(Name,Sex,Age,DepartName,StudentNumber) values (?,?,?,?,?)";
			String q1="select * from student where StudentNumber="+"'"+xuehao+"'";
			try {
				Class.forName(drv);
				c= DriverManager.getConnection(url,username,password);	
				PreparedStatement ps0=c.prepareStatement(q1);
				rs=ps0.executeQuery();
				if(rs.next()){
					JOptionPane.showConfirmDialog(null,"���ѧ���Ѿ�ע�ᣡ","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				}
				else{
					PreparedStatement ps=c.prepareStatement(sql);
					ps.setString(1, name);
				    ps.setString(2,sex);
				    ps.setString(3, age);
				    ps.setString(4, depart);
				    ps.setString(5,xuehao);
				    ps.executeUpdate();
				    if (rs != null)
				{   rs.close();
					ps.close();
					c.close();
					JOptionPane.showConfirmDialog(null,"��ѧ����ӳɹ�!","��ʾ�Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				}
				}	
				
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			//dispose();
			
		}
		//else{dispose();
		}
		
		public static void main(String args[]){
			//new StudentRegister();
		}
		}
		

	
	