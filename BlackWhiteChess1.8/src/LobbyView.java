import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Vector;
import javax.swing.JFrame;

public class LobbyView extends JFrame 
{
	private Lobby lobby = null;
	private Vector<TableView> vtv = new Vector<TableView>();
	String playerName;
	
	public LobbyView(String name)
	{
		this.playerName = name;
	}
	
	public void launch()
	{
		for (int i = 0; i < 6; i++) 
		{
			vtv.add(new TableView(playerName));
		}
		setLayout(new GridLayout(3, 3));
		for (int j = 0; j < 6; j++) 
		{
			add(vtv.get(j));
		}
		setTitle("��Ϸ����");
		setSize(1000, 500);
		// ��JFrame����λ������Ϊ��Ļ�м�
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		// �ô����������Ļ����
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
