import java.awt.*;
import javax.swing.*;
public class help extends JFrame{
	JPanel j1=new JPanel();
	JLabel j2=new JLabel("�ڰ��壬�ֽз���(Reversi)����������(Othello)��ƻ���壬��ת�塣");
	JLabel j3=new JLabel("�ڰ�������ÿ���ɺڰ���ɫ��ɣ�һ��ף�һ��ڣ���64�����������������4���������ӳ�Բ����.");
	JLabel j4=new JLabel("��Ϸͨ���໥��ת�Է������ӣ������������˭�����Ӷ����ж�ʤ����");
	JLabel j5=new JLabel("���Լ���ɫ�����ӷ������̵Ŀո��ϣ��Լ����µ�������8����������һ���Լ�������,�򱻼����м��ȫ����Ϊ�Լ������ӡ�");
	JLabel j6=new JLabel("��������������û�еط��������ӣ������Ҷ��ֿ������¡�");
	JLabel j7=new JLabel("�����̻�û������ʱ�����һ���������Ѿ����Է��Թ⣬�����Ҳ����������һ����ʤ��");
	JLabel j8=new JLabel();
	public help(){
		setTitle("����");
		setPreferredSize(new Dimension(750, 500));
		setBounds(100, 100, 750, 500);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);
		j1.setLayout(null);
		j1.setBackground(Color.PINK);
		add(j1);
		j1.add(j2);
		j1.add(j3);
		j1.add(j4);
		j1.add(j5);
		j1.add(j6);
		ImageIcon t = new ImageIcon("1.jpg");
		j2.setBounds(10, 20, 650, 25);
		j3.setBounds(10, 40, 650, 25);
		j4.setBounds(10, 60, 600, 25);
		j5.setBounds(10, 80, 650, 25);
		j6.setBounds(10, 100, 750, 25);
		j7.setBounds(10, 120, 650, 25);
	    j8.setBounds(10, 130, 300, 200);
	    j8.setIcon(t);
	    j1.add(j8);
	}
	public static void main(String[] args) {
		
		help a=new help();
	}

}
