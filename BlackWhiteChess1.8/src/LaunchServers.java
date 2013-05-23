
public class LaunchServers 
{
	public static void main(String[] args)
	{
		new LaunchServers().launch();
	}
	
	public void launch()
	{
		new Thread(new LaunchServer6666()).start();
		new Thread(new LaunchServer9999()).start();
		new Thread(new LaunchServer8888()).start();
	}
	
	private class LaunchServer6666 implements Runnable
	{
		public void run()
		{
			try
			{
				new Server6666().launch();
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private class LaunchServer9999 implements Runnable
	{
		public void run()
		{
			try
			{
				new Server9999().launch();
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private class LaunchServer8888 implements Runnable
	{
		public void run()
		{
			try
			{
				new ServerDataBase().launch();
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
