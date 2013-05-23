
public class Table implements java.io.Serializable
{
	private int Status=0;
	// 设置客户端做服务器的IP
	private String IP=null;
	// 设置此桌是否人满
	private int isFull=0;
	// 设置此桌是否有一个客户端做服务器了
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
