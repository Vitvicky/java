package Register;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CourseRegister extends JFrame implements ActionListener{
	JLabel name = new JLabel("�γ���:");
	JTextField nametxt = new JTextField();
	JLabel number = new JLabel("�γ̺�:");
	JTextField numbertxt = new JTextField();
	JLabel grade = new JLabel("ѧ��:");
	JTextField q = new JTextField();
	JLabel subject = new JLabel("���п�:");
	JTextField formal = new JTextField();
	
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
    String drv = "com.mysql.jdbc.Driver";
    String username="root";
	String password="12345";
	Connection c;
	ResultSet rs;
	//String sql = "select * from info";

	public CourseRegister() {
		setTitle("�¿γ�¼��");
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
		number.setBounds(10, 80, 60, 25);
		a.add(number);
		numbertxt.setBounds(100, 80, 140, 22);
		a.add(numbertxt);
		//���������
		grade.setBounds(10, 110, 60, 25);
		a.add(grade);
		q.setBounds(100, 110, 140, 22);
		a.add(q);
		formal.setBounds(100,140,140,25);
		a.add(formal);
		//�Ա�ѡ��
		
		//רҵѡ��
		subject.setBounds(10, 140, 60, 25);
		a.add(subject);
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
		ImageIcon h = new ImageIcon("image/aa.jpg");
		j1.setIcon(h);
		j1.setBounds(0, 0, 600, 800);
		a.add(j1);
		}
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0){
		int result=JOptionPane.showConfirmDialog(this,"��ȷ���ύ��?","ѯ�ʶԻ���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(result==JOptionPane.OK_OPTION){
			String name = new String(nametxt.getText());
			String number = new String(numbertxt.getText());
			String grade=new String(q.getText());
			String before=new String(formal.getText());
			//System.out.println(name+" "+age+" "+sex+" "+depart+" "+xuehao);
//			String sql="insert into student(Name,Sex,Age,DepartName,StudentNumber) values " +
//			"('"+name+"','"+age+"','"+sex+"','"+depart+"','"+xuehao+"')";
			String sql="insert into course(CourseNumber,CourseName,FormalCourse,CourseGrade) values (?,?,?,?)";
			String q1="select * from course where CourseNumber="+"'"+number+"'";
			try {
				Class.forName(drv);
				c= DriverManager.getConnection(url,username,password);	
				PreparedStatement ps0=c.prepareStatement(q1);
				rs=ps0.executeQuery();
				if(rs.next()){
					JOptionPane.showConfirmDialog(null,"���ſγ��Ѿ����ڣ�","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				}
				else{
					PreparedStatement ps=c.prepareStatement(sql);
					ps.setString(1, number);
				    ps.setString(2,name);
				    ps.setString(3, before);
				    ps.setString(4, grade);
				    
				    ps.executeUpdate();
				    if (rs != null)
				{   rs.close();
					ps.close();
					c.close();
					JOptionPane.showConfirmDialog(null,"���ſγ���ӳɹ�!","��ʾ�Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				}
				}	
				
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			dispose();
			
		}
		//else{dispose();
		}
		
		public static void main(String args[]){
			//new CourseRegister();
		}
		}
		

	
	