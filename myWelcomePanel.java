import java.awt.*;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class myWelcomePanel extends JPanel{
    
    JLabel helptext = new JLabel(new ImageIcon("images/helptext.png"));
    JButton playgame = new JButton("Play Game");
    
    public myWelcomePanel(){
        
    
    setLayout(null);
    this.setBackground(Color.white);
    helptext.setBounds(0,0,900,581);
    playgame.setBounds(450,625,100,50);
    add(helptext);
    add(playgame);
    }
    
    

}
