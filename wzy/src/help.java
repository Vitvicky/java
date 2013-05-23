import java.awt.*;
import javax.swing.*;
public class help extends JFrame{
	JPanel j1=new JPanel();
	JLabel j2=new JLabel("黑白棋，又叫反棋(Reversi)、奥赛罗棋(Othello)，苹果棋，翻转棋。");
	JLabel j3=new JLabel("黑白棋棋子每颗由黑白两色组成，一面白，一面黑，共64个（包括棋盘中央的4个）。棋子呈圆饼形.");
	JLabel j4=new JLabel("游戏通过相互翻转对方的棋子，最后以棋盘上谁的棋子多来判断胜负。");
	JLabel j5=new JLabel("把自己颜色的棋子放在棋盘的空格上，自己放下的棋子在8个方向内有一个自己的棋子,则被夹在中间的全部成为自己的棋子。");
	JLabel j6=new JLabel("如果玩家在棋盘上没有地方可以下子，则该玩家对手可以连下。");
	JLabel j7=new JLabel("在棋盘还没有下满时，如果一方的棋子已经被对方吃光，则棋局也结束。满子一方获胜。");
	JLabel j8=new JLabel();
	public help(){
		setTitle("帮助");
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
