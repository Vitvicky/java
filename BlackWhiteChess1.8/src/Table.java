
public class Table implements java.io.Serializable
{
	private int Status=0;
	// ���ÿͻ�������������IP
	private String IP=null;
	// ���ô����Ƿ�����
	private int isFull=0;
	// ���ô����Ƿ���һ���ͻ�������������
	private boolean haveServer=false;
	public boolean isHaveServer() 
	{
		return haveServer;
	}
	public void setHaveServer(boolean haveServer) 
	{
		this.haveServer = haveServer;
	}
	public int getStatus() 
	{
		return Status;
	}
	public void setStatus(int status) 
	{
		Status = status;
	}
	public String getIP() 
	{
		return IP;
	}
	public void setIP(String iP) 
	{
		IP = iP;
	}
	public int getIsFull() 
	{
		return isFull;
	}
	public void setIsFull(int isFull) 
	{
		this.isFull = isFull;
	}
}
