import java.awt.*;  
import javax.swing.*;  
import javax.swing.tree.*;  
import java.awt.event.*;  
import javax.swing.event.*;  
  
public class friends extends JFrame  
{  
  JPanel f=new JPanel();  
  JTree jtree;  
  DefaultMutableTreeNode root;  
  public friends()  
  {  
   this.setSize(300,500);  
   this.setTitle("好友界面");  
   f=(JPanel)this.getContentPane();  
   f.setLayout(new BorderLayout());  
   root=new DefaultMutableTreeNode("QQ");  
   createTree(root);  
   jtree=new JTree(root);  
   f.add(jtree,BorderLayout.CENTER);  
  }  
  public static void main(String[] args)  
  {  
    friends JTree2 = new friends();  
    JTree2.setVisible(true);  
  }  
  private void createTree(DefaultMutableTreeNode root)  
  {  
   DefaultMutableTreeNode friend=new DefaultMutableTreeNode("好友");  
   DefaultMutableTreeNode number=null;
   DefaultMutableTreeNode stranger=new DefaultMutableTreeNode("陌生人");
   DefaultMutableTreeNode badman=new DefaultMutableTreeNode("黑名单");
   root.add(friend);
   root.add(stranger);
   root.add(badman);
   for(int i=1;i!=8;i++)  
   {  
    number=new DefaultMutableTreeNode("No."+String.valueOf(i));  
    friend.add(number);  
   }  
   for(int i=1;i!=7;i++)  
   {  
    number=new DefaultMutableTreeNode("No."+String.valueOf(i));  
    stranger.add(number);  
   }  
   for(int i=1;i!=6;i++)  
   {  
    number=new DefaultMutableTreeNode("No."+String.valueOf(i));  
    badman.add(number);  
   }  
  }  
  protected void processWindowEvent(WindowEvent e)  
  {  
   if(e.getID()==WindowEvent.WINDOW_CLOSING)  
   {  
    System.exit(0);  
   }  
  }  
}  
