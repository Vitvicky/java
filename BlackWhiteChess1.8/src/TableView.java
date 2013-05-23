import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TableView extends JPanel implements ActionListener
{
	private Table table = null;
	private int Status;
	JButton jb1, jb2, jb3;
	JPanel jp;
	private Image tablePic = null;
	LobbyView lobbyView = null;
	String playerName;
	String serverIP = "127.0.0.1";
	
	public TableView(String name)
	{
		playerName = name;
		tablePic=new ImageIcon(getClass().getResource("chessBoard.jpg")).getImage();
System.out.println("found");
		setBackground(Color.BLUE);
		jb1 = new JButton("加入");
		jb2 = new JButton("加入");
		jb3 = new JButton("观看");
		setLayout(null);
		add(jb1);
		add(jb2);
		add(jb3);
		jp = new JPanel() 
		{
			{
				setOpaque(false);
			}

			public void paint(Graphics g) 
			{
				super.paint(g);
				g.drawImage(tablePic, 0, 0, 100, 100, this);
			}
		};
		jb1.setBounds(10, 40, 60, 60);
		jb2.setBounds(180, 40, 60, 60);
		jb3.setBounds(280, 40, 60, 60);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		add(jp);
		jp.setBounds(75, 20, 100, 100);
		setSize(240, 140);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == jb1)
		{
			//lobbyView.setVisible(false);
			jb1.setEnabled(false);
			new StartChess(playerName, serverIP).launchChess();
		}
		if (e.getSource() == jb2)
		{
			//lobbyView.setVisible(false);
			jb2.setEnabled(false);
			new StartChess(playerName, serverIP).launchChess();
		}
		if (e.getSource() == jb3)
		{
			new StartChess(playerName, serverIP).launchChess();
		}
	}

}
