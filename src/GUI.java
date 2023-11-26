import java.awt.Color; 
import javax.swing.ImageIcon; 
import javax.swing.JFrame; 
public class GUI {
    GUI(){
    JFrame frame = new JFrame(); //creates a frame 
    frame.setTitle("JFrame title goes here"); //sets title of frame 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit from application 
    frame.setResizable(false); //prevent frame from being resized 
    frame.setSize(420,420); //sets the x-dimension, and y-dimension of frame 
    frame.setVisible(true); //make frame visible 
    frame.getContentPane().setBackground(new Color(0x123456)); //change color of background }
    }
}
