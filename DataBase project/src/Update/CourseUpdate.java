package Update;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class CourseUpdate extends JFrame {
	JLabel j = new JLabel("��������Ҫ���µĿγ̺�:");
	JLabel jj = new JLabel("��ѡ����Ҫ���µ�ѡ��:");
	JLabel jjj = new JLabel("���������޸ĺ������:");
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
		setTitle("�γ���Ϣ�޸�");
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

		JButton j2 = new JButton("ȷ��");
		j2.setBounds(380, 50, 80, 20);
		a.add(j2);
		j2.addActionListener(l);
		JButton j3 = new JButton("ȡ��");
		j3.setBounds(380, 140, 80, 20);
		a.add(j3);
		j3.addActionListener(l);
		// z.setBounds(30,200,150,50);
		a.add(w);
		r.addItem("�γ���");
		r.addItem("���п�");
		r.addItem("ѧ��");
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
			if (e.getActionCommand().equals("ȷ��")) {

				try {
					Class.forName(drv).newInstance();
					con = DriverManager.getConnection(url, username, password);
				} catch (Exception m) {
					m.printStackTrace();
				}
				//System.out.println(in);
				if (shuxing.equals("�γ���")) {
					try {
						String sql = "Update course set CourseName="
							+ "'" + after + "' where CourseNumber="
							+ "'" + number + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.executeUpdate();
//						if(!rs.next()){
//							JOptionPane.showConfirmDialog(null,"���������ѧ����","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
//						}
						JOptionPane.showConfirmDialog(null,"�γ������޸ĳɹ���","��ʾ�Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (shuxing.equals("���п�")) {
					try {
						String sql = "Update course set FormalCourse="
							+ "'" + after + "' where CourseNumber="
							+ "'" + number + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.executeUpdate();
//						if(!rs.next()){
//							JOptionPane.showConfirmDialog(null,"���������ѧ����","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
//						}
						
							JOptionPane.showConfirmDialog(null,"���п��޸ĳɹ���","��ʾ�Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						
					}catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (shuxing.equals("ѧ��")) {
					try {
						String sql = "Update course set CourseGrade="
							+ "'" + after + "' where CourseNumber="
							+ "'" + number + "'";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.executeUpdate();
//						if(!rs.next()){
//							JOptionPane.showConfirmDialog(null,"���������ѧ����","����Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
//						}
						
							JOptionPane.showConfirmDialog(null,"ѧ��ѧ���޸ĳɹ���","��ʾ�Ի���", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						
					}catch (SQLException e1) {

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
		//new CourseUpdate();
	}

}
