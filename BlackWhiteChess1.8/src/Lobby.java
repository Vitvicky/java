import java.util.Vector;

public class Lobby implements java.io.Serializable
{
	private Vector<Table> vt=new Vector<Table>();

	public Vector<Table> getVt() 
	{
		return vt;
	}

	public void setVt(Vector<Table> vt) 
	{
		this.vt = vt;
	}
}
