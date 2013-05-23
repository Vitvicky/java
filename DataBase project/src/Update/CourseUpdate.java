package Update;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class CourseUpdate extends JFrame {
	JLabel j = new JLabel("请输入您要更新的课程号:");
	JLabel jj = new JLabel("请选择您要更新的选项:");
	JLabel jjj = new JLabel("请输入您修改后的内容:");
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
	String drv = "com.mysql.jdbc.Driver";
	String username = "root";
	String password = "12345";
	Connection con;
	ResultSet rs;
	JPanel a = new JPanel();
	JComboBox r = new JComboBox();
	JTextField j1 = new JTextField();
	JTextField j11 = new JTextField();
	public CourseUpdate() {
		setTitle("课程信息修改");
		setPreferredSize(new Dimension(500, 300));
		setBounds(100, 100, 500, 300);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLayout(null);
		a.setBounds(0, 0, 500, 300);
		a.setLayout(null);
		add(a);
		setLocation(this.getToolkit().getScreenSize().width / 2
				- this.getWidth() / 2, this.getToolkit().getScreenSize().height
				/ 2 - this.getHeight() / 2);

		JLabel w = new JLabel();

		ImageIcon q = new ImageIcon("image/black.gif");
		w.setIcon(q);
		w.setBounds(10, 40, 120, 200);

		a.add(j);
		// a.add(jj);
		// a.add(jjj);
		j.setBounds(150, 30, 180, 30);
		// jj.setBounds(150, 65, 180, 30);
		// jjj.setBounds(150, 120, 180, 30);
		listener l = new listener();

		JButton j2 = new JButton("确定");
		j2.setBounds(380, 50, 80, 20);
		a.add(j2);
		j2.addActionListener(l);
		JButton j3 = new JButton("取消");
		j3.setBounds(380, 140, 80, 20);
		a.add(j3);
		j3.addActionListener(l);
		// z.setBounds(30,200,150,50);
		a.add(w);
		r.addItem("课程名");
		r.addItem("先行课");
		r.addItem("学分");
		r.setBounds(160, 120, 180, 20);
		a.add(r);
		j1.setBounds(160, 60, 120, 25);
		a.add(j1);
		jj.setBounds(150, 90, 150, 25);
		a.add(jj);
		jjj.setBounds(150,150,150,25);
		a.add(jjj);
		j11.setBounds(160,180,120,25);
		a.add(j11);
	}

	class listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String shuxing = (String) r.getSelectedItem();
			String number=new String(j1.getText());
			String after=new String(j11.getText());
			String q1="select * from course where courseNumber="+"'"+number+"'";
			if (e.getActionCommand().equals("确定")) {

				try {
					Class.forName(drv).newInstance();
					con = DriverManager.getConnection(url, username, password);
				} catch (Exception m) {
					m.printStackTrace();
				}
				//System.out.println(in);
				if (shuxing.equals("课程名")) {
					try {
						String sql = "Update course set CourseName="
							+ "'" + after + "' where CourseNumber="
							+ "'" + number + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.executeUpdate();
//						if(!rs.next()){
//							JOptionPane.showConfirmDialog(null,"不存在这个学生！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
//						}
						JOptionPane.showConfirmDialog(null,"课程名名修改成功！","提示对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (shuxing.equals("先行课")) {
					try {
						String sql = "Update course set FormalCourse="
							+ "'" + after + "' where CourseNumber="
							+ "'" + number + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.executeUpdate();
//						if(!rs.next()){
//							JOptionPane.showConfirmDialog(null,"不存在这个学生！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
//						}
						
							JOptionPane.showConfirmDialog(null,"先行课修改成功！","提示对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						
					}catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (shuxing.equals("学分")) {
					try {
						String sql = "Update course set CourseGrade="
							+ "'" + after + "' where CourseNumber="
							+ "'" + number + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.executeUpdate();
//						if(!rs.next()){
//							JOptionPane.showConfirmDialog(null,"不存在这个学生！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
//						}
						
							JOptionPane.showConfirmDialog(null,"学生学号修改成功！","提示对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						
					}catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			}
			if (e.getActionCommand().equals("取消")) {
				dispose();
			}
		}
	}

	public static void main(String[] args) {
		//new CourseUpdate();
	}

}
