import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;
import org.jvnet.substance.watermark.SubstanceImageWatermark;

public class WelcomeWindow extends JWindow
{	static AudioClip l;
	private static final long serialVersionUID = 1L;
    public WelcomeWindow(int waitTime) 
    {
        ImageIcon aa = new ImageIcon("Titanic.jpg");
        JLabel l = new JLabel();
        //image add to label l
        l.setIcon(aa);
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width / 2 - (labelSize.width / 2), screenSize.height / 2 - (labelSize.height / 2));
        final int pause = waitTime;
        final Runnable closerRunner = new Runnable() 
        {
            public void run() 
            {
                setVisible(false);
                dispose();
                new dengluqi();
            }
        };
        Runnable waitRunner = new Runnable() 
        {
            public void run() 
            {
                try 
                {
                    Thread.sleep(pause);
                    SwingUtilities.invokeAndWait(closerRunner);
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        };
        setVisible(true);
        Thread waitThread = new Thread(waitRunner, "SplashThread");
        waitThread.start();
    }
    public static void main(String[] args) throws MalformedURLException{
    	JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try  {
			SubstanceImageWatermark watermark  =   new  SubstanceImageWatermark(dengluqi. class .getResourceAsStream( "111.jpg " ));
			//UIManager.setLookAndFeel( new SubstanceOfficeBlue2007LookAndFeel()); 
			UIManager.setLookAndFeel( new SubstanceGreenMagicLookAndFeel()); 
	       
	    }  catch  (UnsupportedLookAndFeelException ex) {
	       
	    } 
	    new WelcomeWindow(2500);
         {
     		File music= new File("E:\\wzy java programe\\wzy\\Ò¹µÄ¸ÖÇÙÇú - Îå.wav"); 
     		URL url=music.toURL();
     		l = java.applet.Applet.newAudioClip(url);
     		l.loop();
     	}
    	 	
    }
}
