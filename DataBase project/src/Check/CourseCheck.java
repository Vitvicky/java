package Check;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class CourseCheck extends JFrame {
	JLabel j = new JLabel("��������Ҫ���ҵ���Ϣ:");
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
	String drv = "com.mysql.jdbc.Driver";
	String username = "root";
	String password = "12345";
	Connection con;
	ResultSet rs;
	JPanel a = new JPanel();
	JComboBox r = new JComboBox();
	JTextField j1 = new JTextField();

	public CourseCheck() {
		setTitle("��ѯ�γ�");
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

		ImageIcon q = new ImageIcon("image/money.gif");
		w.setIcon(q);
		w.setBounds(10, 20, 120, 160);

		a.add(j);
		// a.add(jj);
		// a.add(jjj);
		j.setBounds(150, 10, 180, 30);
		// jj.setBounds(150, 65, 180, 30);
		// jjj.setBounds(150, 120, 180, 30);
		listener l = new listener();

		JButton j2 = new JButton("ȷ��");
		j2.setBounds(380, 50, 80, 20);
		a.add(j2);
		j2.addActionListener(l);
		JButton j3 = new JButton("ȡ��");
		j3.setBounds(380, 100, 80, 20);
		a.add(j3);
		j3.addActionListener(l);
		// z.setBounds(30,200,150,50);
		a.add(w);

		// j1.setBounds(0, 0, 500, 300);
		// a.add(j1);
		// add(z);
		r.addItem("�γ̺�");
		r.addItem("�γ���");
		r.addItem("ѧ��");
		r.setBounds(150, 40, 180, 20);
		a.add(r);
		j1.setBounds(160, 80, 120, 25);
		a.add(j1);

	}

	class listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String in = (String) r.getSelectedItem();
			String what=new String(j1.getText());
			if (e.getActionCommand().equals("ȷ��")) {

				try {
					Class.forName(drv).newInstance();
					con = DriverManager.getConnection(url, username, password);
				} catch (Exception m) {
					m.printStackTrace();
				}
				System.out.println(in);
				if (in.equals("�γ̺�")) {
					try {
						String sql = "select * from course where CourseNumber="
								+ "'" + what + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						if(!rs.next()){
							JOptionPane.showConfirmDialog(null,"���������ſΣ�","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else { // ������ѯ���
							System.out.println(rs.getString("CourseName") + " "
									+ rs.getString("CourseGrade"));
							JLabel result = new JLabel("��ѯ���:");
							a.add(result);
							result.setBounds(10, 160, 80, 30);
							JLabel shuxing = new JLabel(
									"   �γ̱��       �γ���           ���п�           ѧ��");
							a.add(shuxing);
							shuxing.setBounds(5, 200, 480, 20);
							JLabel mm = new JLabel("      "+rs.getInt("CourseNumber")
									+ "                 "
									+ rs.getString("CourseName")
									+ "            " + rs.getInt("FormalCourse")
									+ "                   " + rs.getInt("CourseGrade"));
							a.add(mm);
							mm.setBounds(10, 230, 480, 40);
						}
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (in.equals("�γ���")) {
					try {
						String sql = "select * from course where CourseName="
								+ "'" + what + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						if(!rs.next()){
							JOptionPane.showConfirmDialog(null,"���������ſΣ�","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else{ // ������ѯ���
							System.out.println(rs.getString("CourseName") + " "
									+ rs.getString("CourseGrade"));
							JLabel result = new JLabel("��ѯ���:");
							a.add(result);
							result.setBounds(10, 160, 80, 30);
							JLabel shuxing = new JLabel(
									"   �γ̱��       �γ���           ���п�           ѧ��");
							a.add(shuxing);
							shuxing.setBounds(5, 200, 480, 20);
							JLabel mm = new JLabel("      "+rs.getInt("CourseNumber")
									+ "                 "
									+ rs.getString("CourseName")
									+ "            " + rs.getInt("FormalCourse")
									+ "                   " + rs.getInt("CourseGrade"));
							a.add(mm);
							mm.setBounds(10, 230, 480, 40);
						}
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (in.equals("ѧ��")) {
					try {
						String sql = "select * from course where CourseGrade="
								+ "'" + what + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						if(!rs.next()){
							JOptionPane.showConfirmDialog(null,"���������ſΣ�","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else { // ������ѯ���
							System.out.println(rs.getString("CourseName") + " "
									+ rs.getString("CourseGrade"));
							JLabel result = new JLabel("��ѯ���:");
							a.add(result);
							result.setBounds(10, 160, 80, 30);
							JLabel shuxing = new JLabel(
									"   �γ̱��       �γ���           ���п�           ѧ��");
							a.add(shuxing);
							shuxing.setBounds(5, 200, 480, 20);
							JLabel mm = new JLabel("      "+rs.getInt("CourseNumber")
									+ "                 "
									+ rs.getString("CourseName")
									+ "               " + rs.getInt("FormalCourse")
									+ "                      " + rs.getInt("CourseGrade"));
							a.add(mm);
							mm.setBounds(10, 230, 480, 40);
						}
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			}
			if (e.getActionCommand().equals("ȡ��")) {
				dispose();
			}
		}
	}

	public static void main(String[] args) {
		//snew CourseCheck();
	}

}
