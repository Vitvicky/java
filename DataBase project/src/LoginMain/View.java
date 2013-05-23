package LoginMain;
import java.sql.*;
import java.awt.*;

import javax.swing.*;

//import java.awt.event.*; 
public class View extends JPanel {

	private String user = "root"; // ���ݿ��û�
	private String password = "12345"; // ���ݿ��û�����
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
	private JTable table;

	public View() {
		super(); // ���ø��๹�캯��
		String[] columnNames = { "���", "����", "�Ա�", "����", "Ժϵ", "ѧ��","ѡ��γ�" }; // ����
		Object[][] rowData = new Object[20][7]; // �������
		setBackground(new Color(60, 70, 221));

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);

			String sqlStr = "select * from student"; // ��ѯ���
			PreparedStatement ps = con.prepareStatement(sqlStr); // ��ȡ
																	// PreparedStatement
																	// ����
			ResultSet rs = ps.executeQuery(); // ִ�в�ѯ

			int count = 0;
			while (rs.next()) { // ������ѯ���
				rowData[count][0] = rs.getInt("Number"); // ��ʼ����������

				rowData[count][1] = rs.getString("Name");
				rowData[count][2] = rs.getString("Sex");
				//
				rowData[count][3] = rs.getInt("Age");
				rowData[count][4] = rs.getString("DepartName");
				rowData[count][5] = rs.getInt("StudentNumber");
				rowData[count][6] = rs.getInt("Course");
				count++;
			}
			con.close(); // �ر�����
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		table = new JTable(rowData, columnNames); // ʵ�������
		table.getColumn("���").setMaxWidth(50);
		table.getColumn("����").setMaxWidth(50);
		table.getColumn("�Ա�").setMaxWidth(50);
		table.getColumn("����").setMaxWidth(50);
		table.getColumn("Ժϵ").setMaxWidth(150);
		table.getColumn("ѧ��").setMaxWidth(100);
		table.getColumn("ѡ��γ�").setMaxWidth(50);
		add(new JScrollPane(table), BorderLayout.CENTER); // ������ ��
		// setSize(470, 440); //���ô��ڳߴ�
		setBounds(0, 0, 500, 450);
		setVisible(true); // ���ô��ڿ���
		
	}

}
