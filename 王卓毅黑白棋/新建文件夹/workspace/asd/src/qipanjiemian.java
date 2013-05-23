import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.*;

public class qipanjiemian extends JPanel implements MouseListener {
	public int[][] qi = { { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, -1, 1, 0, 0, 0 }, { 0, 0, 0, 1, -1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 } }, x = { { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, -1, 1, 0, 0, 0 }, { 0, 0, 0, 1, -1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 } }, y = { { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, -1, 1, 0, 0, 0 }, { 0, 0, 0, 1, -1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 } };

	public int blackcount = 2, whitecount = 2, blackwin = 0, whitewin = 0;
	public boolean iswin = false, isgame = false;
	public gameduan zhu;

	public qipanjiemian(gameduan zhu) throws IOException {
		super();
		setSize(505, 505);
		// setBounds(380,30,520,520);
		this.zhu = zhu;
		addMouseListener(this);
		setBorder((Border) new EtchedBorder(EtchedBorder.RAISED, Color.white,
				new Color(148, 145, 140)));

	}

	public void chessvictory(int chesscolor){
	if(getbai()>gethei()&&checkwin()){
		if(zhu.padpad.isClient&&!zhu.padpad.isServer)
		{zhu.padpad.sendMessage("/result "+zhu.padpad.chessClientName+"1");
		}
		if(!zhu.padpad.isClient&&zhu.padpad.isServer)
		{
		zhu.padpad.sendMessage("/result "+zhu.padpad.chessClientName+"-1");
		}
	}else if(getbai()<gethei()&&checkwin()){
		if(zhu.padpad.isClient&&!zhu.padpad.isServer)
			{zhu.padpad.sendMessage("/result "+zhu.padpad.chessClientName+"-1");
			}
			if(!zhu.padpad.isClient&&zhu.padpad.isServer)
			{zhu.padpad.sendMessage("/result "+zhu.padpad.chessClientName+"1");
			}
	}else if(getbai()==gethei()&&checkwin()){
		if(zhu.padpad.isClient&&!zhu.padpad.isServer)
			{
			}
			if(!zhu.padpad.isClient&&zhu.padpad.isServer)
				{
			}
	}else if(!checkwin()){
		{if(chesscolor==1){zhu.padpad.sendMessage("/result "+zhu.padpad.chessClientName+"-1");
			
		}if(chesscolor==-1){zhu.padpad.sendMessage("/result "+zhu.padpad.chessClientName+"-1");
		
		}		
	}
	}
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Composite c = g2.getComposite();
		Color color = g2.getColor();
		g2.setComposite(AlphaComposite.SrcOver.derive(0.6f));
		g2.setColor(new Color(227, 133, 28));
		g2.fill3DRect(5, 5, 490, 490, true);
		// g.fillRect(15, 15, 490, 490);
		g2.setComposite(c);
		g2.setColor(color);

		g.setColor(Color.BLACK);
		for (int i = 10; i <= 490; i += 60) {
			g.drawLine(10, i, 490, i);
			g.drawLine(i, 10, i, 490);
			// for(int i=1;i<=481;i+=60){
			// g.drawLine(1, i, 481, i);
			// g.drawLine(i, 1, i, 481);
		}
		for (int t = 0, mm = 16; t < 8; t++, mm += 60)
			for (int s = 0, nn = 16; s < 8; s++, nn += 60) {
				if (qi[t][s] == 1) {
					for (int tt = 55, ss = 30; tt >= 0; tt -= 1, ss += 1) {
						g.setColor(new Color(ss, ss, ss));
						g.fillOval(mm + 25 - tt / 2, nn + 25 - tt / 2, tt, tt);
					}
				}
				if (qi[t][s] == -1) {
					for (int tt = 55, ss = 0; tt >= 0; tt -= 1, ss += 1) {
						g.setColor(new Color(ss + 200, ss + 200, ss + 200));
						g.fillOval(mm + 25 - tt / 2, nn + 25 - tt / 2, tt, tt);
					}
				}
			}
	}

	public int getbai() {
		int count = 0;
		for (int i = 0; i < 8; i++)
			for (int t = 0; t < 8; t++)
				if (qi[i][t] == -1)
					count++;
		return count;
	}

	public int gethei() {
		int count = 0;
		for (int i = 0; i < 8; i++)
			for (int t = 0; t < 8; t++)
				if (qi[i][t] == 1)
					count++;
		return count;
	}

	public boolean hefa(int x, int y, int color) {

		boolean bump = false;
		if (qi[x][y] != 0)
			 {JOptionPane.showMessageDialog(null,"这个地方已经有子，请换个地方放子");
			return false;}
		else {

			{
				{
					if (y - 1 >= 0 && qi[x][y - 1] == color * -1) {
						for (int i = 2; y - i >= 0; i++) {
							if (qi[x][y - i] == color) {
								bump = true;
								i = 10;
							}
							if (y - i >= 0 && qi[x][y - i] == 0)
								i = 10;
						}
					}
					if (x + 1 <= 7 && y - 1 >= 0
							&& qi[x + 1][y - 1] == color * -1) {
						for (int i = 2; y - i >= 0 && x + i < 8; i++) {
							if (qi[x + i][y - i] == color) {
								bump = true;
								i = 10;
							}
							if (y - i >= 0 && x + i < 8
									&& qi[x + i][y - i] == 0)
								i = 10;

						}
					}
					if (x + 1 <= 7 && qi[x + 1][y] == color * -1) {
						for (int i = 2; x + i < 8; i++) {
							if (qi[x + i][y] == color) {
								bump = true;
								i = 10;
							}
							if (x + i < 8 && qi[x + i][y] == 0)
								i = 10;

						}
					}
					if (x + 1 <= 7 && y + 1 < 8
							&& qi[x + 1][y + 1] == color * -1) {
						for (int i = 2; y + i < 8 && x + i < 8; i++) {
							if (qi[x + i][y + i] == color) {
								bump = true;
								i = 10;
							}
							if (y + i < 8 && x + i < 8 && qi[x + i][y + i] == 0)
								i = 10;

						}
					}
					if (y + 1 < 8 && qi[x][y + 1] == color * -1) {
						for (int i = 2; y + i < 8; i++) {
							if (qi[x][y + i] == color) {
								bump = true;
								i = 10;
							}
							if (y + i < 8 && qi[x][y + i] == 0)
								i = 10;

						}
					}
					if (x - 1 >= 0 && y + 1 < 8
							&& qi[x - 1][y + 1] == color * -1) {
						for (int i = 2; y + i < 8 && x - 1 >= 0; i++) {
							if (qi[x - i][y + i] == color) {
								bump = true;
								i = 10;
							}
							if (y + i < 8 && x - 1 >= 0
									&& qi[x - i][y + i] == 0)
								i = 10;

						}
					}
					if (x - 1 >= 0 && qi[x - 1][y] == color * -1) {
						for (int i = 2; x - i >= 0; i++) {
							if (qi[x - i][y] == color) {
								bump = true;
								i = 10;
							}
							if (x - i >= 0 && qi[x - i][y] == 0)
								i = 10;

						}
					}
					if (x - 1 >= 0 && y - 1 >= 0
							&& qi[x - 1][y - 1] == color * -1) {
						for (int i = 2; y - i >= 0 && x - i >= 0; i++) {
							if (qi[x - i][y - i] == color) {
								bump = true;
								i = 10;
							}
							if (y - i >= 0 && x - i >= 0
									&& qi[x - i][y - i] == 0)
								i = 10;

						}
					}

				}
			}
		}

		/* else */

		
		if (bump)
			return true;
		else
			return false;

	}

	public void bian(int x, int y, int chess) {

		{
			if (y - 1 >= 0 && qi[x][y - 1] == chess * -1) {
				for (int i = 2; y - i >= 0; i++) {
					if (qi[x][y - i] == chess) {
						for (int t = 1; t < i; t++)
							qi[x][y - t] = chess;
						i = 10;
					}
					if (y - i >= 0 && qi[x][y - i] == 0)
						i = 10;
				}
			}
			if (x + 1 <= 7 && y - 1 >= 0 && qi[x + 1][y - 1] == chess * -1) {
				for (int i = 2; y - i >= 0 && x + i < 8; i++) {
					if (qi[x + i][y - i] == chess) {
						for (int t = 1; t < i; t++)
							qi[x + t][y - t] = chess;
						i = 10;
					}
					if (y - i >= 0 && x + i < 8 && qi[x + i][y - i] == 0)
						i = 10;

				}
			}
			if (x + 1 <= 7 && qi[x + 1][y] == chess * -1) {
				for (int i = 2; x + i < 8; i++) {
					if (qi[x + i][y] == chess) {
						for (int t = 1; t < i; t++)
							qi[x + t][y] = chess;
						i = 10;
					}
					if (x + i < 8 && qi[x + i][y] == 0)
						i = 10;

				}
			}
			if (x + 1 <= 7 && y + 1 < 8 && qi[x + 1][y + 1] == chess * -1) {
				for (int i = 2; y + i < 8 && x + i < 8; i++) {
					if (qi[x + i][y + i] == chess) {
						for (int t = 1; t < i; t++)
							qi[x + t][y + t] = chess;
						i = 10;
					}
					if (y + i < 8 && x + i < 8 && qi[x + i][y + i] == 0)
						i = 10;

				}
			}
			if (y + 1 < 8 && qi[x][y + 1] == chess * -1) {
				for (int i = 2; y + i < 8; i++) {
					if (qi[x][y + i] == chess) {
						for (int t = 1; t < i; t++)
							qi[x][y + t] = chess;
						i = 10;
					}
					if (y + i < 8 && qi[x][y + i] == 0)
						i = 10;

				}
			}
			if (x - 1 >= 0 && y + 1 < 8 && qi[x - 1][y + 1] == chess * -1) {
				for (int i = 2; y + i < 8 && x - 1 >= 0; i++) {
					if (qi[x - i][y + i] == chess) {
						for (int t = 1; t < i; t++)
							qi[x - t][y + t] = chess;
						i = 10;
					}
					if (y + i < 8 && x - 1 >= 0 && qi[x - i][y + i] == 0)
						i = 10;

				}
			}
			if (x - 1 >= 0 && qi[x - 1][y] == chess * -1) {
				for (int i = 2; x - i >= 0; i++) {
					if (qi[x - i][y] == chess) {
						for (int t = 1; t < i; t++)
							qi[x - t][y] = chess;
						i = 10;
					}
					if (x - i >= 0 && qi[x - i][y] == 0)
						i = 10;

				}
			}
			if (x - 1 >= 0 && y - 1 >= 0 && qi[x - 1][y - 1] == chess * -1) {
				for (int i = 2; y - i >= 0 && x - i >= 0; i++) {
					if (qi[x - i][y - i] == chess) {
						for (int t = 1; t < i; t++)
							qi[x - t][y - t] = chess;
						i = 10;
					}
					if (y - i >= 0 && x - i >= 0 && qi[x - i][y - i] == 0)
						i = 10;
				}
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean checkwin() {
		int x = getbai(), y = gethei();
		if (x + y == 64)
			return true;
		else
			return false;
	}
	public void tishi(int a,int b){
		for(int i=0;i<8;i++)   for(int j=0;j<8;j++){
			
		}
			
	}
	public void chesspaint(int a, int b, int color) {
		if (color == 1 && zhu.padpad.isMouseEnabled) {
			if (checkwin() == false) {
				zhu.padpad.sendMessage("/" + zhu.padpad.chessPeerName
						+ " /chess " + a + " " + b + " " + color);
				for(int t=0;t<8;t++) for(int s=0;s<8;s++) x[t][s]=qi[t][s];
				bian(a, b, 1);
				qi[a][b] = color;
				repaint();
				zhu.chat.chatarea.append("黑棋(共" + gethei() + "子)" + "白棋(共"
						+ getbai() + "子)" + ",请白棋下子\n");

				zhu.padpad.isMouseEnabled = false;
			} else {
				zhu.padpad.sendMessage("/" + zhu.padpad.chessPeerName
						+ " /chess " + a + " " + b + " " + color);
				bian(a, b, 1);
				qi[a][b] = color;
				repaint();
				chessvictory(1);
				zhu.padpad.isMouseEnabled = false;

			}
		} else if (color == -1 && zhu.padpad.isMouseEnabled) {
			if (checkwin() == false) {
				for(int t=0;t<8;t++) for(int s=0;s<8;s++) x[t][s]=qi[t][s];
				zhu.padpad.sendMessage("/" + zhu.padpad.chessPeerName
						+ " /chess " + a + " " + b + " " + color);
				bian(a, b, -1);
				qi[a][b] = color;
				repaint();
				zhu.chat.chatarea.append("白棋(共" + getbai() + "子)" + "黑棋(共"
						+ gethei() + "子)" + ",请黑棋下子\n");
				zhu.padpad.isMouseEnabled = false;
			}

			else {
				zhu.padpad.sendMessage("/" + zhu.padpad.chessPeerName
						+ " /chess " + a + " " + b + " " + color);
				bian(a, b, -1);
				qi[a][b] = color;
				repaint();
				chessvictory(-1);
				zhu.padpad.isMouseEnabled = false;
				// System.out.println(a+" "+b);
			}
		}
	}

	public void netchesspaint(int a, int b, int color) {
		if (color == 1) {
			if (checkwin() == false) {
				for(int t=0;t<8;t++) for(int s=0;s<8;s++) y[t][s]=qi[t][s];
				bian(a, b, color);
				qi[a][b] = color;
				repaint();
				zhu.chat.chatarea.append("黑棋(共" + gethei() + "子)" + "白棋(共"
						+ getbai() + "子)" + ",请白棋下子\n");
				zhu.padpad.isMouseEnabled = true;
			} else {
				bian(a, b, color);
				qi[a][b] = color;
				repaint();
				chessvictory(1);
				zhu.padpad.isMouseEnabled = true;
			}
		} else if (color == -1) {
			if (checkwin() == false) {
				for(int t=0;t<8;t++) for(int s=0;s<8;s++) y[t][s]=qi[t][s];bian(a,b,color);
				bian(a, b, color);
				qi[a][b] = color;
				repaint();
				zhu.chat.chatarea.append("白棋(共" + getbai() + "子)" + "黑棋(共"
						+ gethei() + "子)" + ",请黑棋下子\n");
				zhu.padpad.isMouseEnabled = true;
			} else {
				bian(a, b, color);
				qi[a][b] = color;
				repaint();
				chessvictory(-1);
				zhu.padpad.isMouseEnabled = true;
				System.out.println(a + " " + b);
			}
		}
	}

	public void mousePressed(MouseEvent e) {

		int x = (int) e.getX(), y = (int) e.getY(), xx = (x - 10) / 60, yy = (y - 10) / 60;
		if (xx < 8 && yy < 8) {

			if (hefa(xx, yy, zhu.padpad.chessColor)) {
				chesspaint(xx, yy, zhu.padpad.chessColor);
			}
		} else
			return;
	}

	public void mouseReleased(MouseEvent arg0) {
		// 

	}

}
