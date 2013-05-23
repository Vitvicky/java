import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class ChessBoard extends JButton
{
	int row, column;
	char state = ' ';
	boolean taken = false;
	boolean changed = false;
	
	public ChessBoard()
	{
		super();
	}
	
	public ChessBoard(int r, int c)
	{
		super();
		row = r;
		column = c;
		this.setBorder(new LineBorder(Color.white, 1));
//		this.setBackground(new Color(171, 152, 123));
//		this.setBackground(new Color(214, 197, 180));
//		this.setBackground(new Color(215, 200, 179));
//		this.setBackground(new Color(194, 174, 154));
		if ((r + c) % 2 == 0) this.setBackground(new Color(183, 158, 137));
		else this.setBackground(new Color(227, 210, 201));
	}
	
	public int getRow()
	{
		return row; 
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public void ChangeBackground()
	{
//		this.setBackground(new Color(171, 152, 123));
		if (this.state == ' ')
		{
			this.setBackground(Color.lightGray);
			changed = true;
		}
	}
	
	public void ChangeBack()
	{
		if ((this.row + this.column) % 2 == 0) this.setBackground(new Color(183, 158, 137));
		else this.setBackground(new Color(227, 210, 201));
		changed = false;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Point2D center = new Point2D.Float(getWidth() / 2, getHeight() / 2);
		float radius = 20;
		Point2D focus = new Point2D.Float(getWidth() / 2 - 5, getHeight() / 2 - 5);
		float[] dist = new float[2];
		Color[] colors = new Color[2];
		
		if (state == 'ºÚ')
		{
			dist[0] = 0.0f;
			dist[1] = 0.3f;
			colors[0] = Color.WHITE;
			colors[1] = Color.BLACK;
			RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
			Graphics2D g2 = (Graphics2D)(g);
			g2.setPaint(p);
			g2.fillOval(2, 2, getHeight() - 6, getWidth() - 4);
		}
		else if (state == '°×')
		{
			dist[0] = 0.8f;
			dist[1] = 1.0f;
			colors[0] = Color.WHITE;
			colors[1] = Color.GRAY;
			RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
			Graphics2D g2 = (Graphics2D)(g);
			g2.setPaint(p);
			g2.fillOval(2, 2, getHeight() - 6, getWidth() - 4);
		}
	}
}
