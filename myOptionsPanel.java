import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class myOptionsPanel extends JPanel implements ChangeListener
{
    
    JSlider difficulty = new JSlider(JSlider.HORIZONTAL,0,10,5);        //* Slider for game difficulty *//
    JSlider defenders = new JSlider(JSlider.HORIZONTAL,0,8,5);          //* Slider for number of defenders *//
    JSlider linemen = new JSlider(JSlider.HORIZONTAL,0,5,5);            //* Slider for number of linemen *//
    JButton startgame = new JButton("Start Game");
    int difficvalue = 5;                                                //* Default slider setting *//
    int defense = 5;                                                    //* Default slider setting *//
    int line = 5;                                                       //* Default slider setting *//
    
    
    public myOptionsPanel(){
        
        
        setLayout(new GridLayout(4,3));
        
        
        //* Initialize and add the difficulty slider *//
        difficulty.setBorder(BorderFactory.createTitledBorder("Game Difficulty"));     
        difficulty.setMajorTickSpacing(1);
        difficulty.setPaintTicks(true);
        difficulty.setPaintLabels(true);
        add(difficulty);
        difficulty.addChangeListener(this);
        
        //* Initialize and add the linemen slider *//
        linemen.setBorder(BorderFactory.createTitledBorder("Offensive Linemen"));     
        linemen.setMajorTickSpacing(1);
        linemen.setPaintTicks(true);
        linemen.setPaintLabels(true);
        add(linemen);
        linemen.addChangeListener(this);
        
        //* Initialize and add the defender slider *//
        defenders.setBorder(BorderFactory.createTitledBorder("Defensive Players"));     
        defenders.setMajorTickSpacing(1);
        defenders.setPaintTicks(true);
        defenders.setPaintLabels(true);
        add(defenders);
        defenders.addChangeListener(this);
        
        add(startgame);
        
    }
    
    //* Listener for the sliders *//
    public void stateChanged(ChangeEvent e) 
	{
            Object obj = e.getSource();
	    if(obj == difficulty) {difficvalue = difficulty.getValue();}
            
            if(obj == linemen) {line = linemen.getValue();}
            
            if(obj == defenders) {defense = defenders.getValue();}
	}
    
    //* Methods used by the JFrame to get the options from the options panel *//
    public int getDiffic() 
        {
        return difficvalue;
        }
    
    public int getDefense() 
        {
        return defense;
        }
    
    public int getline() 
        {
        return line;
        }
}
