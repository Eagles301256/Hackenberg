import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.JPanel;


public class myGamePanel extends JPanel implements ActionListener, MouseListener, KeyListener{
    
    
    
    //* ImageIcons for the various graphics *//
    ImageIcon testplayer = new ImageIcon("images/testplayer.png");
    ImageIcon defender = new ImageIcon("images/defender.png");
    ImageIcon lineman = new ImageIcon("images/lineman.png");
    ImageIcon tackled = new ImageIcon("images/tackle.png");
    ImageIcon fall1 = new ImageIcon("images/fall1.png");
    ImageIcon fall2 = new ImageIcon("images/fall2.png");
    ImageIcon fall3 = new ImageIcon("images/fall3.png");
    ImageIcon fall4 = new ImageIcon("images/fall4.png");
    ImageIcon moxie1 = new ImageIcon("images/moxie1.png");
    ImageIcon moxie2 = new ImageIcon("images/moxie2.png");
    
    
    JButton player = new JButton(testplayer);       //* This is the actual player *//
    JButton fieldpos = new JButton();               //* This is the red box on the left side of the screen *//
    JButton yardageGain = new JButton();            //* This is the button that shows how many yards were gained. It is pressed to reset the players and start the next down *//
    JTextField downandDistance = new JTextField();        //* Button that shows the down and distance on the upper left hand part of the screen *//
    JButton effectwindow = new JButton();
    JButton touchdownbutton = new JButton();          
    
    aiplayer DEF[] = new aiplayer[8];                   //* Array of aiplayers. These are the defenders *//
    aiplayer LIN[] = new aiplayer[5];                   //* Array of aiplayers. These are the linemen *//
    LineBorder line1 = new LineBorder(Color.red, 4);    //* Border for the fieldpos JButton *//

    //* Game variables. Some are not used. *//
    int difficvalue;
    int defense;
    int line;
    int playerspeed = 0;
    int playerx = 753;
    int playery = 678;
    int xdest = playerx;
    int ydest = playery;
    int playerXdir = 0;
    int playerYdir = 0;
    int delay = 20;
    int xrad = 18;
    int xdim = 28;
    int yrad = 40;
    int ydim = 39;
    int zoneswitch = 0;
    int playertackled = 0;
    int animate = 0;
    int touchtimer = 0;
    int pixelsGained = 0;
    int currentPixel = 658;
    int yardsGained = 0;
    int totalyardsGained = 0;
    int yardstogo = 10;
    int downtogo = 1;
    int trucktimer = 0;
    int speedtimer = 0;
    int moxietimer = 0;
    int trucking = 0;
    int speed = 0;
    int touchdowns = 0;
    int goptimer = 0;
    int minutesleft = 0;
    int secondsleft = 0;
    int osuDrive;
    int osuScore = 24;
    int psuScore = 20;
    int timeoffclock = 0;
    int negativeseconds = 0;
    int upfield = 0;
    int gameover = 0;
    double simmodifier = 0;
    int isitover = 0;
    Timer tim;              //* Timer that controls the gameplay *//
    Timer tim1;             //* Timer that controls the tackle animation and after-play effects *//
    Timer tim2;
    Timer tim3;
    Timer tim4;
    Timer tim5;
    Timer tim6;
    Timer tim7;
    
    String yardagetext = " ";
    String nextdown = " ";
    
    JTextField TimeLeft = new JTextField();
    JTextField homeScore = new JTextField();
    JTextField awayScore = new JTextField();
    
    
    //* Listens for a mouse click and then sets the player destination to that location *//
    public void mouseClicked(MouseEvent e) 
	{
            xdest = e.getXOnScreen();
            ydest = e.getYOnScreen();
            requestFocusInWindow();
        }
    public void mouseEntered(MouseEvent e) {}   //* Unused *//
    public void mouseExited(MouseEvent e) {}    //* Unused *//
    
    //* Listens for a mouse press and then sets the player destination to that location *//
    public void mousePressed(MouseEvent e) 
	{
            xdest = e.getXOnScreen();
            ydest = e.getYOnScreen();
        }
    public void mouseReleased(MouseEvent e) {}  //* Unused *//
    
    
    public myGamePanel(int initDiffic, int initDefense, int initline){
        
        difficvalue = initDiffic;                               
        setPlayerSpeed(difficvalue);                            //* Sets the player's speed based on the difficulty *//
        defense = initDefense;
        line = initline;
        setLayout(null);
        downandDistance.setBounds(400,5,100,30);
        downandDistance.setText("1st and 10");
        add(downandDistance);                                   //* Adds the down and distance button *//
        fieldpos.setBounds(12, 469, 485, 191);
        fieldpos.setOpaque(false);
        fieldpos.setContentAreaFilled(false);
        fieldpos.setBorder(line1); 
        add(fieldpos);                                          //* Adds the field position button *//
        effectwindow.setBounds(350,40,150,30);
        add(effectwindow);
        effectwindow.setText("Under no effects");
        TimeLeft.setBounds(695,10,130,30);
        TimeLeft.setText(" ");
        add(TimeLeft);
        homeScore.setBounds(515,5,50,30);
        homeScore.setText("PSU: " + psuScore);
        add(homeScore);
        awayScore.setBounds(940,5,50,30);
        awayScore.setText("OSU: " + osuScore);
        add(awayScore);
        player.setBounds(new Rectangle(753,678,50,65));
        player.setOpaque(false);
        player.setContentAreaFilled(false);
        player.setBorderPainted(false);
        add(player);                                            //* Adds the player *//
        defenseButtons(defense);                                //* Adds the defenders based on the passed value *//
        linemenButtons(line);                                   //* Adds the linemen based on the passed value *//
        setFocusable(true);
        tim = new Timer(delay, this);
        tim1 = new Timer(delay, this);
        tim2 = new Timer(delay, this);
        tim3 = new Timer(delay, this);
        tim4 = new Timer(delay, this);
        tim5 = new Timer(delay, this);
        tim6 = new Timer(1000, this);
        tim7 = new Timer(delay, this);
  	tim.start();    
        tim6.start();                                           //* Starts the game timer *//
        minutesleft = 4;
        secondsleft = 00;
        addMouseListener(this);
        addKeyListener(this);
        
    }
    
    public void playerMove(){
        
        playerXdir = xdest - (playerx + xrad);          //* This determines the player's X distance from the location of the mouse click *//
        playerYdir = ydest - (playery + yrad);          //* This determines the player's Y distance from the location of the mouse click *//
        
        if (xdest <= 530){xdest = 530;}                 //* Doesn't allow the player to run out of bounds *//
        if (xdest >= 990){xdest = 990;}                 //* Doesn't allow the player to run out of bounds *//

        if (playerXdir >= playerspeed){
            playerx = playerx + playerspeed;}
        
        if (playerXdir <= (playerspeed * -1)){
            playerx = playerx - playerspeed;}  
    
        if (playerXdir >= playerspeed){                 //* These statements move the player. I had them written differently
            playerx = playerx + playerspeed;}           //* but the player would jitter constantly. Using these statements,
                                                        //* the player will move to within +/- the playerspeed of the mouse
        if (playerXdir <= (playerspeed * -1)){          //* click location. As long as the player speed is less than 20 or so,
            playerx = playerx - playerspeed;}           //* the person playing the game won't notice that their player didn't
                                                        //* move to their exact mouse location. Hopefully :)
        if (playerYdir >= playerspeed){
            playery = playery + playerspeed;}
        
        if (playerYdir <= (playerspeed * -1)){
            playery = playery - playerspeed;}

    }
    
    //* Handles the actions that occur when the player is tackled *//
    public void playerTackled(int initd){
        
        for (int i = 0; i < initd; i++)
            if (DEF[i].getAICol() == 1){                            //* Checks each defender to see if they have collided with the player *//
                
                if (gameover == 1){
                    gameOverMan();
                }
                else {
                    if (upfield == 1){
                        upfield = 0;
                        yardsGained = ((currentPixel + 727) - playery)/26;
                        currentPixel = playery;
                    }
                    else {
                        yardsGained = (currentPixel - playery)/26;          //* Sets the number of yards gained on the play *//
                        currentPixel = playery;                             //* Sets the position the player will start at on the next down *//
                    }

                    totalyardsGained = totalyardsGained + yardsGained;  //* Unused *//
                    pixelsGained = 658 - playery;                       //* FIX THIS. Causes issues with the positions of the aiplayers *//
                    downtogo = downtogo + 1;                            //* Adds to the down *//
                    animate = 0;
                    tim1.start();                                       //* Starts the tackling animation and after-play effects *//
                    tim.stop();
                    tim6.stop();
                }
            }    
    }
    
    //* Paints the field and changes the right screen when the player moves upfield *//
    public void paintComponent(Graphics g) 
    {
    	super.paintComponent(g); 
    	Image Field = Toolkit.getDefaultToolkit().getImage("images/field.png");
        if (zoneswitch == 0){
        Image FieldZoneOne = Toolkit.getDefaultToolkit().getImage("images/fieldzoneone.png");
    	g.drawImage(Field, 0, 0, this);    
        g.drawImage(FieldZoneOne, 506, 0, this);
        fieldpos.setBounds(12, 469, 485, 191);
        repaint();
        }
        if (zoneswitch == 1){
        Image FieldZoneTwo = Toolkit.getDefaultToolkit().getImage("images/fieldzonetwo.png");
    	g.drawImage(Field, 0, 0, this);    
        g.drawImage(FieldZoneTwo, 506, 0, this);
        fieldpos.setBounds(12, 278, 485, 191);
        repaint();
        }
        if (zoneswitch == 2){
        Image FieldZoneThree = Toolkit.getDefaultToolkit().getImage("images/fieldzonethree.png");
    	g.drawImage(Field, 0, 0, this);    
        g.drawImage(FieldZoneThree, 506, 0, this);
        fieldpos.setBounds(12, 87, 485, 191);
        repaint();
        }
        if (zoneswitch == 3){
        Image FieldZoneFour = Toolkit.getDefaultToolkit().getImage("images/fieldzonefour.png");
    	g.drawImage(Field, 0, 0, this);    
        g.drawImage(FieldZoneFour, 506, 0, this);
        fieldpos.setBounds(12, 0, 485, 191);
        repaint();
        }       
        
    }
    
    //* Listener for the timers and button press *//
    public void actionPerformed(ActionEvent event) 
    {
                Object obj = event.getSource();
		if (obj == tim)
		{
                    playerMove();                                               //* Moves the player *//
                    player.setBounds(new Rectangle(playerx,playery,50,65));     //* Shows the player movement *//
                    fieldCheck();                                               //* Checks to see if the field needs to be repainted *//
                    moveAI(defense, line);                                      //* Moves the AI players *//
                    playerTackled(defense);                                     //* Checks to see if an AI player tackled the player *//
                    
                }
                if (obj == tim1)
                {
                    animate = animate + 1;                                          //* Animates the player tackle *//
                    
                    if (animate == 3){
                        player.setIcon(fall1);
                        player.setBounds(new Rectangle(playerx, playery, 50, 50));
                    }
                    
                    if (animate == 6){
                        player.setIcon(fall2);
                        player.setBounds(new Rectangle(playerx, playery, 50, 50));
                    }
                    
                    if (animate == 9){
                        player.setIcon(fall3);
                        player.setBounds(new Rectangle(playerx, playery, 50, 50));
                    }
                    if (animate == 12){
                        player.setIcon(fall4);
                        player.setBounds(new Rectangle(playerx, playery, 50, 50));
                    }
                    if (animate == 15){
                        player.setIcon(tackled);
                        player.setBounds(new Rectangle(playerx, playery, 50, 50));
                        yardageGain.setBounds(new Rectangle(550, 30, 450, 50));
                        yardagetext = "Christian Hackenberg gains " + yardsGained + " yards on the run";       
                        yardageGain.setText(yardagetext);
                        add(yardageGain);                                       //* Shows how many yards were gained on the play. Used to reset the field *//
                        
                    }
                    if (animate == 50){
                        //* Checks to see if enough yardage was gained for a first down *//
                        if (downtogo == 2){
                             
                            if (yardstogo - yardsGained > 0){ 
                                downandDistance.setText("2nd and " + (yardstogo - yardsGained));
                                yardstogo = yardstogo - yardsGained; 
                                nextdown = "Click here to play 2nd down";
                                yardageGain.addActionListener(this);
                            }
                            else if (yardstogo - yardsGained <= 0){
                                yardstogo = 10;
                                downtogo = 1;
                                downandDistance.setText("1st and 10");
                                nextdown = "Click here to play 1st down";
                                yardageGain.addActionListener(this);
                            }
                        }
                        //* Checks to see if enough yardage was gained for a first down *//
                        if (downtogo == 3){
                            
                            if (yardstogo - yardsGained > 0){
                                downandDistance.setText("3nd and " + (yardstogo - yardsGained));
                                yardstogo = yardstogo - yardsGained; 
                                nextdown = "Click here to play 3rd down";
                                yardageGain.addActionListener(this);
                            }
                            else if (yardstogo - yardsGained <= 0){
                                yardstogo = 10;
                                downtogo = 1;
                                downandDistance.setText("1st and 10");
                                nextdown = "Click here to play 1st down";
                                yardageGain.addActionListener(this);
                            }
                        }
                        //* Checks to see if enough yardage was gained for a first down *//
                        if (downtogo == 4){
                            
                            if (yardstogo - yardsGained > 0){
                                downandDistance.setText("4th and " + (yardstogo - yardsGained));
                                yardstogo = yardstogo - yardsGained; 
                                nextdown = "Click here to play 4th down";
                                yardageGain.addActionListener(this);
                            }
                            else if (yardstogo - yardsGained <= 0){
                                yardstogo = 10;
                                downtogo = 1;
                                downandDistance.setText("1st and 10");
                                nextdown = "Click here to play 1st down";
                                yardageGain.addActionListener(this);
                            }
                        }
                        //* Checks to see if enough yardage was gained for a first down *//
                        if (downtogo == 5){
                            
                            if (yardstogo - yardsGained > 0){
                                downandDistance.setText("Turnover on Downs");
                                yardstogo = yardstogo - yardsGained; 
                                tim1.stop();
                                gameReset();
                                tim5.start();
                            }
                            else if (yardstogo - yardsGained <= 0){
                                yardstogo = 10;
                                downtogo = 1;
                                downandDistance.setText("1st and 10");
                                nextdown = "Click here to play 1st down";
                                yardageGain.addActionListener(this);
                            }
                        }
                    }
                    
                    //* Switches the button text between the yardage gained and the prompt to start the next down *//
                    if (animate == 75){
                        yardageGain.setText(yardagetext);
                    }
                    
                    if (animate == 100){
                        yardageGain.setText(nextdown);
                    }
                    
                    if (animate == 125){
                        yardageGain.setText(yardagetext);
                    }
                    
                    if (animate == 150){
                        yardageGain.setText(nextdown);
                    }
                    
                    if (animate == 175){
                        yardageGain.setText(yardagetext);
                    }
                    
                    if (animate == 200){
                        yardageGain.setText(nextdown);
                    }
                    if (animate == 225){
                        yardageGain.setText(yardagetext);
                    }
                    
                    if (animate == 250){
                        yardageGain.setText(nextdown);
                    }
                    
                    if (animate == 275){
                        yardageGain.setText(yardagetext);
                    }
                    
                    if (animate == 300){
                        yardageGain.setText(nextdown);
                    }
                }
                
                if (obj == yardageGain){
                    yardageGain.removeActionListener(this);
                    if (downtogo <= 4){                         //* Checks to see what the down is *//                                         
                        remove(yardageGain);                    //* Removes the button *//
                        for(int i = 0; i < defense; i++){
                            remove(DEF[i]);                     //* Removes the defenders *//
                        }
                        for(int i =0; i < line; i++){
                            remove(LIN[i]);                     //* Removes the linemen *//
                        }
                        tim1.stop();                            //* Stops the after-play timer *//
                        animate = 0;
                        defenseButtons(defense);                //* Adds new defenders *//
                        linemenButtons(line);                   //* Adds new linemen *//
                        player.setIcon(testplayer);             //* Resets the player icon *//
                        playerx = 753;                          //* Wipes the player's last x position and moves it back to the center of the field *//
                        //playery = playery + 30;                 //* Sets the player's y position slightly less than the line *//
                        player.setBounds(new Rectangle(753, playery, 50, 65));
                        xdest = playerx;                        
                        ydest = playery;
                        repaint();
                        
                        tim.start();                            //* Starts a new play *//
                        tim6.start();
                    }
                }
                
                if (obj == tim2){
                    moxietimer = moxietimer + 1;
                    if (moxietimer == 1){
                    player.setIcon(moxie1);
                    
                    }
                    if (moxietimer == 50){
                        player.setIcon(moxie2);
                        repaint();
                    }
                    if (moxietimer == 100){
                        player.setIcon(moxie1);
                        repaint();
                    }
                    if (moxietimer == 150){
                        player.setIcon(moxie2);
                        repaint();
                    }
                    if (moxietimer == 200){
                        player.setIcon(moxie1);
                        repaint();
                    }
                    if (moxietimer == 250){
                        player.setIcon(moxie2);
                        repaint();
                    }
                    if (moxietimer == 300){
                        player.setIcon(testplayer);
                        repaint();
                        tim2.stop();
                    }
                }
                
                if (obj == tim3){
                    
                    trucktimer = trucktimer + 1;
                    if (trucktimer == 1){
                        for (int i = 0; i < defense; i++){
                            DEF[i].setColMod(1);
                        }
                    }
                    if (trucktimer == 200){
                        for (int i = 0; i < defense; i++){
                            DEF[i].setColMod(0);
                        }
                        effectwindow.setText("Under no effects.");
                        trucking = 2;
                        playerspeed = playerspeed / 2;        
                    }
                    if (trucktimer == 400){
                        playerspeed = playerspeed * 2;
                        tim3.stop();
                        trucktimer = 0;
                    }
                }
                
                if (obj == tim4){
                    
                    speedtimer = speedtimer + 1;
                    if (speedtimer == 1){
                        playerspeed = playerspeed * 2;
                        effectwindow.setText("SEC Speed!");
                    }
                    if (speedtimer >= 200){
                        playerspeed = playerspeed / 2;
                        effectwindow.setText("Under no effects.");
                        speed = 2;
                        tim4.stop();
                        speedtimer = 0;   
                    }
                }
                
                if (obj == tim5){
                    if (gameover == 1){
                        gameOverMan();
                    }
                    touchtimer = touchtimer + 1;
                    if (touchtimer == 1){
                        yardageGain.removeActionListener(this);
                    }
                    if (touchtimer == 35){
                        yardageGain.setText("Simulating Ohio State drive...");
                        
                    }
                    if (touchtimer == 100){
                        
                        OSUDriveTime();
                        simOSUDrive(difficvalue);
                    }
                    if (touchtimer == 150){
                        yardageGain.setText("OSU Drive took " + timeoffclock + " seconds.");
                    }
                    if (touchtimer == 250){
                        yardageGain.setText("Click here to start your next drive.");
                        yardageGain.addActionListener(this);
                    }
                }
                
                if (obj == tim6){
                    if (minutesleft >= 0){
                        if(secondsleft > 0){
                            secondsleft = secondsleft - 1;
                            if (secondsleft < 10){
                                TimeLeft.setText("Time Remaining: " + minutesleft + ":" + "0" + secondsleft);
                            }
                            else if (secondsleft >= 10){
                                TimeLeft.setText("Time Remaining: " + minutesleft + ":" + secondsleft);
                            }
                        }
                        if(secondsleft <= 0){
                            if(minutesleft <= 0){
                                gameover = 1;
                            }
                            if(minutesleft > 0){
                                minutesleft = minutesleft - 1;
                                secondsleft = 59;
                                TimeLeft.setText("Time Remaining: " + minutesleft + ":" + secondsleft);
                            }
                        }
                    }
                }
	}	
     
    //* Creates and adds to the game defenders based on how many were chosen in the options screen *//
    private void defenseButtons (int initd){
        
        if (initd == 1){
            DEF[0] = new aiplayer(difficvalue, 753 , 498 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(753 ,498 - pixelsGained,28,44));
            add(DEF[0]);
            
        }
        if (initd == 2){
            DEF[0] = new aiplayer(difficvalue, 677 , 498 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(677 ,498 - pixelsGained,28,44));
            add(DEF[0]);
            
            DEF[1] = new aiplayer(difficvalue, 832 , 498 - pixelsGained);
            DEF[1].setIcon(defender);
            DEF[1].setOpaque(false);
            DEF[1].setContentAreaFilled(false);
            DEF[1].setBorderPainted(false);
            DEF[1].setBounds(new Rectangle(832 ,498 - pixelsGained,28,44));
            add(DEF[1]);
        }
        if (initd == 3){
            DEF[0] = new aiplayer(difficvalue, 629 , 498 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(629 ,498 - pixelsGained,28,44));
            add(DEF[0]);
            
            DEF[1] = new aiplayer(difficvalue, 753 , 498 - pixelsGained);
            DEF[1].setIcon(defender);
            DEF[1].setOpaque(false);
            DEF[1].setContentAreaFilled(false);
            DEF[1].setBorderPainted(false);
            DEF[1].setBounds(new Rectangle(753 ,498 - pixelsGained,28,44));
            add(DEF[1]);
            
            DEF[2] = new aiplayer(difficvalue, 885 , 498 - pixelsGained);
            DEF[2].setIcon(defender);
            DEF[2].setOpaque(false);
            DEF[2].setContentAreaFilled(false);
            DEF[2].setBorderPainted(false);
            DEF[2].setBounds(new Rectangle(885 ,498 - pixelsGained,28,44));
            add(DEF[2]);
        }
        if (initd == 4){
            DEF[0] = new aiplayer(difficvalue, 634, 525 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(634,525 - pixelsGained,28,44));
            add(DEF[0]);
            
            DEF[1] = new aiplayer(difficvalue, 707, 525 - pixelsGained);
            DEF[1].setIcon(defender);
            DEF[1].setOpaque(false);
            DEF[1].setContentAreaFilled(false);
            DEF[1].setBorderPainted(false);
            DEF[1].setBounds(new Rectangle(707,525 - pixelsGained,28,44));
            add(DEF[1]);
            
            DEF[2] = new aiplayer(difficvalue, 804, 525 - pixelsGained);
            DEF[2].setIcon(defender);
            DEF[2].setOpaque(false);
            DEF[2].setContentAreaFilled(false);
            DEF[2].setBorderPainted(false);
            DEF[2].setBounds(new Rectangle(804,525 - pixelsGained,28,44));
            add(DEF[2]);
            
            DEF[3] = new aiplayer(difficvalue, 881, 525 - pixelsGained);
            DEF[3].setIcon(defender);
            DEF[3].setOpaque(false);
            DEF[3].setContentAreaFilled(false);
            DEF[3].setBorderPainted(false);
            DEF[3].setBounds(new Rectangle(881,525 - pixelsGained,28,44));
            add(DEF[3]);
        }
        if (initd == 5){
            DEF[0] = new aiplayer(difficvalue, 634, 525 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(634,525 - pixelsGained,28,44));
            add(DEF[0]);
            
            DEF[1] = new aiplayer(difficvalue, 707, 525 - pixelsGained);
            DEF[1].setIcon(defender);
            DEF[1].setOpaque(false);
            DEF[1].setContentAreaFilled(false);
            DEF[1].setBorderPainted(false);
            DEF[1].setBounds(new Rectangle(707,525 - pixelsGained,28,44));
            add(DEF[1]);
            
            DEF[2] = new aiplayer(difficvalue, 804, 525 - pixelsGained);
            DEF[2].setIcon(defender);
            DEF[2].setOpaque(false);
            DEF[2].setContentAreaFilled(false);
            DEF[2].setBorderPainted(false);
            DEF[2].setBounds(new Rectangle(804,525 - pixelsGained,28,44));
            add(DEF[2]);
            
            DEF[3] = new aiplayer(difficvalue, 881, 525 - pixelsGained);
            DEF[3].setIcon(defender);
            DEF[3].setOpaque(false);
            DEF[3].setContentAreaFilled(false);
            DEF[3].setBorderPainted(false);
            DEF[3].setBounds(new Rectangle(881,525 - pixelsGained,28,44));
            add(DEF[3]);
            
            DEF[4] = new aiplayer(difficvalue, 753, 435 - pixelsGained);
            DEF[4].setIcon(defender);
            DEF[4].setOpaque(false);
            DEF[4].setContentAreaFilled(false);
            DEF[4].setBorderPainted(false);
            DEF[4].setBounds(new Rectangle(753,435 - pixelsGained,28,44));
            add(DEF[4]);
        }
        if (initd == 6){
            DEF[0] = new aiplayer(difficvalue, 634, 525 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(634,525 - pixelsGained,28,44));
            add(DEF[0]);
            
            DEF[1] = new aiplayer(difficvalue, 707, 525 - pixelsGained);
            DEF[1].setIcon(defender);
            DEF[1].setOpaque(false);
            DEF[1].setContentAreaFilled(false);
            DEF[1].setBorderPainted(false);
            DEF[1].setBounds(new Rectangle(707,525 - pixelsGained,28,44));
            add(DEF[1]);
            
            DEF[2] = new aiplayer(difficvalue, 804, 525 - pixelsGained);
            DEF[2].setIcon(defender);
            DEF[2].setOpaque(false);
            DEF[2].setContentAreaFilled(false);
            DEF[2].setBorderPainted(false);
            DEF[2].setBounds(new Rectangle(804,525 - pixelsGained,28,44));
            add(DEF[2]);
            
            DEF[3] = new aiplayer(difficvalue, 881, 525 - pixelsGained);
            DEF[3].setIcon(defender);
            DEF[3].setOpaque(false);
            DEF[3].setContentAreaFilled(false);
            DEF[3].setBorderPainted(false);
            DEF[3].setBounds(new Rectangle(881,525 - pixelsGained,28,44));
            add(DEF[3]);
            
            DEF[4] = new aiplayer(difficvalue, 696, 435 - pixelsGained);
            DEF[4].setIcon(defender);
            DEF[4].setOpaque(false);
            DEF[4].setContentAreaFilled(false);
            DEF[4].setBorderPainted(false);
            DEF[4].setBounds(new Rectangle(696,435 - pixelsGained,28,44));
            add(DEF[4]);
            
            DEF[5] = new aiplayer(difficvalue, 791, 435 - pixelsGained);
            DEF[5].setIcon(defender);
            DEF[5].setOpaque(false);
            DEF[5].setContentAreaFilled(false);
            DEF[5].setBorderPainted(false);
            DEF[5].setBounds(new Rectangle(791,435 - pixelsGained,28,44));
            add(DEF[5]);
        }
        if (initd == 7){
            DEF[0] = new aiplayer(difficvalue, 634, 525 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(634,525 - pixelsGained,28,44));
            add(DEF[0]);
            
            DEF[1] = new aiplayer(difficvalue, 707, 525 - pixelsGained);
            DEF[1].setIcon(defender);
            DEF[1].setOpaque(false);
            DEF[1].setContentAreaFilled(false);
            DEF[1].setBorderPainted(false);
            DEF[1].setBounds(new Rectangle(707,525 - pixelsGained,28,44));
            add(DEF[1]);
            
            DEF[2] = new aiplayer(difficvalue, 804, 525 - pixelsGained);
            DEF[2].setIcon(defender);
            DEF[2].setOpaque(false);
            DEF[2].setContentAreaFilled(false);
            DEF[2].setBorderPainted(false);
            DEF[2].setBounds(new Rectangle(804,525 - pixelsGained,28,44));
            add(DEF[2]);
            
            DEF[3] = new aiplayer(difficvalue, 881, 525 - pixelsGained);
            DEF[3].setIcon(defender);
            DEF[3].setOpaque(false);
            DEF[3].setContentAreaFilled(false);
            DEF[3].setBorderPainted(false);
            DEF[3].setBounds(new Rectangle(881,525 - pixelsGained,28,44));
            add(DEF[3]);
            
            DEF[4] = new aiplayer(difficvalue, 753, 435 - pixelsGained);
            DEF[4].setIcon(defender);
            DEF[4].setOpaque(false);
            DEF[4].setContentAreaFilled(false);
            DEF[4].setBorderPainted(false);
            DEF[4].setBounds(new Rectangle(753,435 - pixelsGained,28,44));
            add(DEF[4]);
            
            DEF[5] = new aiplayer(difficvalue, 659, 435 - pixelsGained);
            DEF[5].setIcon(defender);
            DEF[5].setOpaque(false);
            DEF[5].setContentAreaFilled(false);
            DEF[5].setBorderPainted(false);
            DEF[5].setBounds(new Rectangle(659,435 - pixelsGained,28,44));
            add(DEF[5]);
            
            DEF[6] = new aiplayer(difficvalue, 838, 435 - pixelsGained);
            DEF[6].setIcon(defender);
            DEF[6].setOpaque(false);
            DEF[6].setContentAreaFilled(false);
            DEF[6].setBorderPainted(false);
            DEF[6].setBounds(new Rectangle(838,435 - pixelsGained,28,44));
            add(DEF[6]);
            
        }
        if (initd == 8){
            DEF[0] = new aiplayer(difficvalue, 634, 525 - pixelsGained);
            DEF[0].setIcon(defender);
            DEF[0].setOpaque(false);
            DEF[0].setContentAreaFilled(false);
            DEF[0].setBorderPainted(false);
            DEF[0].setBounds(new Rectangle(634,525 - pixelsGained,28,44));
            add(DEF[0]);
            
            DEF[1] = new aiplayer(difficvalue, 707, 525 - pixelsGained);
            DEF[1].setIcon(defender);
            DEF[1].setOpaque(false);
            DEF[1].setContentAreaFilled(false);
            DEF[1].setBorderPainted(false);
            DEF[1].setBounds(new Rectangle(707,525 - pixelsGained,28,44));
            add(DEF[1]);
            
            DEF[2] = new aiplayer(difficvalue, 804, 525 - pixelsGained);
            DEF[2].setIcon(defender);
            DEF[2].setOpaque(false);
            DEF[2].setContentAreaFilled(false);
            DEF[2].setBorderPainted(false);
            DEF[2].setBounds(new Rectangle(804,525 - pixelsGained,28,44));
            add(DEF[2]);
            
            DEF[3] = new aiplayer(difficvalue, 881, 525 - pixelsGained);
            DEF[3].setIcon(defender);
            DEF[3].setOpaque(false);
            DEF[3].setContentAreaFilled(false);
            DEF[3].setBorderPainted(false);
            DEF[3].setBounds(new Rectangle(881,525 - pixelsGained,28,44));
            add(DEF[3]);
            
            DEF[4] = new aiplayer(difficvalue, 753, 435 - pixelsGained);
            DEF[4].setIcon(defender);
            DEF[4].setOpaque(false);
            DEF[4].setContentAreaFilled(false);
            DEF[4].setBorderPainted(false);
            DEF[4].setBounds(new Rectangle(753,435 - pixelsGained,28,44));
            add(DEF[4]);
            
            DEF[5] = new aiplayer(difficvalue, 659, 435 - pixelsGained);
            DEF[5].setIcon(defender);
            DEF[5].setOpaque(false);
            DEF[5].setContentAreaFilled(false);
            DEF[5].setBorderPainted(false);
            DEF[5].setBounds(new Rectangle(659,435 - pixelsGained,28,44));
            add(DEF[5]);
            
            DEF[6] = new aiplayer(difficvalue, 838, 435 - pixelsGained);
            DEF[6].setIcon(defender);
            DEF[6].setOpaque(false);
            DEF[6].setContentAreaFilled(false);
            DEF[6].setBorderPainted(false);
            DEF[6].setBounds(new Rectangle(838,435 - pixelsGained,28,44));
            add(DEF[6]);
            
            DEF[7] = new aiplayer(difficvalue, 750, 323 - pixelsGained);
            DEF[7].setIcon(defender);
            DEF[7].setOpaque(false);
            DEF[7].setContentAreaFilled(false);
            DEF[7].setBorderPainted(false);
            DEF[7].setBounds(new Rectangle(750,323 - pixelsGained,28,44));
            add(DEF[7]);
        }

        
        
    }
    
    //* Creates and adds to the game linemen based on how many were chosen in the options screen *//
    public void linemenButtons (int initd){
        
        if (initd == 1){
            LIN[0] = new aiplayer(difficvalue, 735, 600 - pixelsGained);
            LIN[0].setIcon(lineman);
            LIN[0].setOpaque(false);
            LIN[0].setContentAreaFilled(false);
            LIN[0].setBorderPainted(false);
            LIN[0].setBounds(new Rectangle(735,600 - pixelsGained,28,44));
            add(LIN[0]);
            
        }
        if (initd == 2){
            LIN[0] = new aiplayer(difficvalue, 735, 600 - pixelsGained);
            LIN[0].setIcon(lineman);
            LIN[0].setOpaque(false);
            LIN[0].setContentAreaFilled(false);
            LIN[0].setBorderPainted(false);
            LIN[0].setBounds(new Rectangle(735,600 - pixelsGained,28,44));
            add(LIN[0]);
            
            LIN[1] = new aiplayer(difficvalue, 700, 610 - pixelsGained);
            LIN[1].setIcon(lineman);
            LIN[1].setOpaque(false);
            LIN[1].setContentAreaFilled(false);
            LIN[1].setBorderPainted(false);
            LIN[1].setBounds(new Rectangle(700,610 - pixelsGained,28,44));
            add(LIN[1]);
        }
        if (initd == 3){
            LIN[0] = new aiplayer(difficvalue, 735, 600 - pixelsGained);
            LIN[0].setIcon(lineman);
            LIN[0].setOpaque(false);
            LIN[0].setContentAreaFilled(false);
            LIN[0].setBorderPainted(false);
            LIN[0].setBounds(new Rectangle(735,600 - pixelsGained,28,44));
            add(LIN[0]);
            
            LIN[1] = new aiplayer(difficvalue, 700, 610 - pixelsGained);
            LIN[1].setIcon(lineman);
            LIN[1].setOpaque(false);
            LIN[1].setContentAreaFilled(false);
            LIN[1].setBorderPainted(false);
            LIN[1].setBounds(new Rectangle(700,610 - pixelsGained,28,44));
            add(LIN[1]);
            
            LIN[2] = new aiplayer(difficvalue, 770, 610 - pixelsGained);
            LIN[2].setIcon(lineman);
            LIN[2].setOpaque(false);
            LIN[2].setContentAreaFilled(false);
            LIN[2].setBorderPainted(false);
            LIN[2].setBounds(new Rectangle(770,610 - pixelsGained,28,44));
            add(LIN[2]);
        }
        if (initd == 4){
            LIN[0] = new aiplayer(difficvalue, 735, 600 - pixelsGained);
            LIN[0].setIcon(lineman);
            LIN[0].setOpaque(false);
            LIN[0].setContentAreaFilled(false);
            LIN[0].setBorderPainted(false);
            LIN[0].setBounds(new Rectangle(735,600 - pixelsGained,28,44));
            add(LIN[0]);
            
            LIN[1] = new aiplayer(difficvalue, 700, 610 - pixelsGained);
            LIN[1].setIcon(lineman);
            LIN[1].setOpaque(false);
            LIN[1].setContentAreaFilled(false);
            LIN[1].setBorderPainted(false);
            LIN[1].setBounds(new Rectangle(700,610 - pixelsGained,28,44));
            add(LIN[1]);
            
            LIN[2] = new aiplayer(difficvalue, 770, 610 - pixelsGained);
            LIN[2].setIcon(lineman);
            LIN[2].setOpaque(false);
            LIN[2].setContentAreaFilled(false);
            LIN[2].setBorderPainted(false);
            LIN[2].setBounds(new Rectangle(770,610 - pixelsGained,28,44));
            add(LIN[2]);
            
            LIN[3] = new aiplayer(difficvalue, 805, 610 - pixelsGained);
            LIN[3].setIcon(lineman);
            LIN[3].setOpaque(false);
            LIN[3].setContentAreaFilled(false);
            LIN[3].setBorderPainted(false);
            LIN[3].setBounds(new Rectangle(805,610 - pixelsGained,28,44));
            add(LIN[3]);
        }
        if (initd == 5){
            LIN[0] = new aiplayer(difficvalue, 735, 600 - pixelsGained);
            LIN[0].setIcon(lineman);
            LIN[0].setOpaque(false);
            LIN[0].setContentAreaFilled(false);
            LIN[0].setBorderPainted(false);
            LIN[0].setBounds(new Rectangle(735,600 - pixelsGained,28,44));
            add(LIN[0]);
            
            LIN[1] = new aiplayer(difficvalue, 700, 610 - pixelsGained);
            LIN[1].setIcon(lineman);
            LIN[1].setOpaque(false);
            LIN[1].setContentAreaFilled(false);
            LIN[1].setBorderPainted(false);
            LIN[1].setBounds(new Rectangle(700,610 - pixelsGained,28,44));
            add(LIN[1]);
            
            LIN[2] = new aiplayer(difficvalue, 770, 610 - pixelsGained);
            LIN[2].setIcon(lineman);
            LIN[2].setOpaque(false);
            LIN[2].setContentAreaFilled(false);
            LIN[2].setBorderPainted(false);
            LIN[2].setBounds(new Rectangle(770,610 - pixelsGained,28,44));
            add(LIN[2]);
            
            LIN[3] = new aiplayer(difficvalue, 805, 610 - pixelsGained);
            LIN[3].setIcon(lineman);
            LIN[3].setOpaque(false);
            LIN[3].setContentAreaFilled(false);
            LIN[3].setBorderPainted(false);
            LIN[3].setBounds(new Rectangle(805,610 - pixelsGained,28,44));
            add(LIN[3]);
            
            LIN[4] = new aiplayer(difficvalue, 665, 610 - pixelsGained);
            LIN[4].setIcon(lineman);
            LIN[4].setOpaque(false);
            LIN[4].setContentAreaFilled(false);
            LIN[4].setBorderPainted(false);
            LIN[4].setBounds(new Rectangle(665,610 - pixelsGained,28,44));
            add(LIN[4]);
        }
    }
    
    //* The meat of the code and cause for many hours of me crying in the fetal position. This function is passed the number of defenders
    //* and linemen. It first checks for a collision, and then moves the defenders towards the player and the linemen towards the
    //* defenders. It is very long because the person playing the game can choose any combination of defenders and linemen.
    public void moveAI (int initd, int initl){
        if((initd == 1) && (initl == 0)){
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
        }
        if((initd == 1) && (initl == 1)){
            AIcollisionDetection(DEF[0], LIN[0], null, null, null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], null, null, null, null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
        }
        if((initd == 1) && (initl == 2)){
            AIcollisionDetection(DEF[0], LIN[0], LIN[1], null, null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], LIN[1], DEF[0], null, null, null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], LIN[0], DEF[0], null, null, null, null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
        }
        if((initd == 1) && (initl == 3)){
            AIcollisionDetection(DEF[0], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], LIN[1], LIN[2], DEF[0], null, null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], LIN[0], LIN[2], DEF[0], null, null, null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], LIN[0], LIN[1], DEF[0], null, null, null, null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
        }
        if((initd == 1) && (initl == 4)){
            AIcollisionDetection(DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], LIN[1], LIN[2], DEF[0], null, null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], LIN[0], LIN[2], DEF[0], null, null, null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], LIN[0], LIN[1], DEF[0], null, null, null, null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], LIN[0], LIN[1], LIN[2], DEF[0], null, null, null, null, null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
        }
        if((initd == 1) && (initl == 5)){
            AIcollisionDetection(DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], DEF[0], null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], LIN[0], LIN[4], LIN[2], LIN[3], DEF[0], null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], LIN[0], LIN[1], LIN[4], LIN[3], DEF[0], null, null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], LIN[0], LIN[1], LIN[2], LIN[4], DEF[0], null, null, null, null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], LIN[0], LIN[1], LIN[2], LIN[3], DEF[0], null, null, null, null, null, null, null);
            LIN[4].DefenderMove(LIN[4], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
            
        }
        if((initd == 2) && (initl == 0)){
            AIcollisionDetection(DEF[0], DEF[1], null, null, null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], null, null, null, null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
        }
        if((initd == 2) && (initl == 1)){
            AIcollisionDetection(DEF[0], DEF[1], LIN[0], null, null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], LIN[0], null, null, null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], null, null, null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
        }
        if((initd == 2) && (initl == 2)){
            AIcollisionDetection(DEF[0], DEF[1], LIN[0], LIN[1], null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], LIN[0], LIN[1], null, null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], LIN[1], null, null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], LIN[0], null, null, null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
        }
        if((initd == 2) && (initl == 3)){
            AIcollisionDetection(DEF[0], DEF[1], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], LIN[1], LIN[2], null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], LIN[0], LIN[2], null, null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], LIN[0], LIN[1], null, null, null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
        }
        if((initd == 2) && (initl == 4)){
            AIcollisionDetection(DEF[0], DEF[1], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[1], LIN[1], LIN[2], LIN[3], DEF[0], null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[1], LIN[0], LIN[2], LIN[3], DEF[0], null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[1], LIN[0], LIN[1], LIN[3], DEF[0], null, null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[1], LIN[0], LIN[1], LIN[2], DEF[0], null, null, null, null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
        }
        if((initd == 2) && (initl == 5)){
            AIcollisionDetection(DEF[0], DEF[1], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], LIN[1], LIN[2], LIN[3], LIN[4], DEF[1], null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], LIN[0], LIN[2], LIN[3], LIN[4], DEF[1], null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], LIN[0], LIN[1], LIN[3], LIN[4], DEF[1], null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], LIN[0], LIN[1], LIN[2], LIN[4], DEF[1], null, null, null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], DEF[0], LIN[0], LIN[1], LIN[2], LIN[4], DEF[1], null, null, null, null, null, null);
            LIN[4].DefenderMove(LIN[4], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
        }
        if((initd == 3) && (initl == 0)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], null, null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], null, null, null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[0], DEF[1], null, null, null, null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
        }
        if((initd == 3) && (initl == 1)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], LIN[0], null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], LIN[0], null, null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[0], DEF[1], LIN[0], null, null, null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], null, null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
        }
        if((initd == 3) && (initl == 2)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], LIN[0], LIN[1], null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[0], DEF[1], LIN[0], LIN[1], null, null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], LIN[1], null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], LIN[0], null, null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
        }
        if((initd == 3) && (initl == 3)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[0], DEF[1], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], LIN[1], LIN[2], null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], LIN[0], LIN[2], null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], null, null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
        }
        if((initd == 3) && (initl == 4)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[0], DEF[1], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], LIN[1], LIN[2], LIN[3], null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], LIN[0], LIN[2], LIN[3], null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[3], null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
        }
        if((initd == 3) && (initl == 5)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[0], DEF[1], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], LIN[0], LIN[2], LIN[3], LIN[4], null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[3], LIN[4], null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[2], LIN[4], null, null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], DEF[0], DEF[1], DEF[2], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null);
            LIN[4].DefenderMove(LIN[4], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
            
        }
        if((initd == 4) && (initl == 0)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], null, null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], null, null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], null, null, null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], null, null, null, null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
        }
        if((initd == 4) && (initl == 1)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], LIN[0], null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], LIN[0], null, null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], LIN[0], null, null, null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], null, null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
        }
        if((initd == 4) && (initl == 2)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], LIN[0], LIN[1], null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], LIN[0], LIN[1], null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], LIN[0], LIN[1], null, null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], LIN[1], null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], null, null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
        }
        if((initd == 4) && (initl == 3)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], LIN[0], LIN[1], LIN[2], null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], LIN[1], LIN[2], null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[2], null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], null, null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
        }
        if((initd == 4) && (initl == 4)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], LIN[1], LIN[2], LIN[3], null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[2], LIN[3], null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[3], null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], null, null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
        }
        if((initd == 4) && (initl == 5)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[2], LIN[3], LIN[4], null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[3], LIN[4], null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], LIN[4], null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], DEF[0], DEF[1], DEF[2], DEF[3], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null);
            LIN[4].DefenderMove(LIN[4], DEF[3].getAIX(), DEF[3].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
            
        }
        if((initd == 5) && (initl == 0)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], null, null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], null, null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], null, null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], null, null, null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], null, null, null, null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
        }
        if((initd == 5) && (initl == 1)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], LIN[0], null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], LIN[0], null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], LIN[0], null, null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], LIN[0], null, null, null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], null, null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
        }
        if((initd == 5) && (initl == 2)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], LIN[0], LIN[1], null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], LIN[0], LIN[1], null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], LIN[0], LIN[1], null, null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], null, null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
        }
        if((initd == 5) && (initl == 3)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], LIN[0], LIN[1], LIN[2], null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], LIN[0], LIN[1], LIN[2], null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], null, null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
        }
        if((initd == 5) && (initl == 4)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], null, null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
        }       
        if((initd == 5) && (initl == 5)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], LIN[4], null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[4].getAIX(), DEF[4].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], LIN[4], null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], LIN[4], null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[4], null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null);
            LIN[4].DefenderMove(LIN[4], DEF[3].getAIX(), DEF[3].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
        }
        if((initd == 6) && (initl == 0)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], null, null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], null, null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], null, null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], null, null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], null, null, null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], null, null, null, null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
        }
        if((initd == 6) && (initl == 1)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], LIN[0], null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], LIN[0], null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], LIN[0], null, null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], LIN[0], null, null, null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], null, null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[4].getAIX(), DEF[4].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
        }
        if((initd == 6) && (initl == 2)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], LIN[0], LIN[1], null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], LIN[0], LIN[1], null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], LIN[0], LIN[1], null, null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], DEF[5], null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], DEF[5], null, null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
        }
        if((initd == 6) && (initl == 3)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], LIN[0], LIN[1], LIN[2], null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], LIN[0], LIN[1], LIN[2], null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], DEF[5], null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], DEF[5], null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], DEF[5], null, null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
        }
        if((initd == 6) && (initl == 4)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], DEF[5], null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], DEF[5], null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], DEF[5], null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], DEF[5], null, null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
        }
        if((initd == 6) && (initl == 5)){
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], LIN[4], DEF[5], null, null);
            LIN[0].DefenderMove(LIN[0], DEF[4].getAIX(), DEF[4].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], LIN[4], DEF[5], null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], LIN[4], DEF[5], null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[4], DEF[5], null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], DEF[5], null, null);
            LIN[4].DefenderMove(LIN[4], DEF[3].getAIX(), DEF[3].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
            
        }
        if((initd == 7) && (initl == 0)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], null, null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], null, null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], null, null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], null, null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], null, null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], null, null, null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], null, null, null, null, null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
        }
        if((initd == 7) && (initl == 1)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], LIN[0], null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], LIN[0], null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], LIN[0], null, null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], LIN[0], null, null, null, null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], null, null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[4].getAIX(), DEF[4].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
        }
        if((initd == 7) && (initl == 2)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], LIN[0], LIN[1], null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], LIN[0], LIN[1], null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], LIN[0], LIN[1], null, null, null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], DEF[5], DEF[6], null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], DEF[5], DEF[6], null, null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
        }
        if((initd == 7) && (initl == 3)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], LIN[0], LIN[1], LIN[2], null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], LIN[0], LIN[1], LIN[2], null, null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], DEF[5], DEF[6], null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], DEF[5], DEF[6], null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], DEF[5], DEF[6], null, null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
        }
        if((initd == 7) && (initl == 4)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], DEF[5], DEF[6], null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], DEF[5], DEF[6], null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], DEF[5], DEF[6], null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], DEF[5], DEF[6], null, null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
        }
        if((initd == 7) && (initl == 5)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4], null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], LIN[4], DEF[5], DEF[6], null);
            LIN[0].DefenderMove(LIN[0], DEF[4].getAIX(), DEF[4].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], LIN[4], DEF[5], DEF[6], null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], LIN[4], DEF[5], DEF[6], null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[4], DEF[5], DEF[6], null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], DEF[5], DEF[6], null);
            LIN[4].DefenderMove(LIN[4], DEF[3].getAIX(), DEF[3].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
            
        }
        if((initd == 8) && (initl == 0)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], null, null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], null, null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], null, null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], DEF[7], null, null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], DEF[7], null, null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], DEF[7], null, null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], DEF[7], null, null, null, null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[7], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[0], null, null, null, null, null);
            DEF[7].DefenderMove(DEF[7], playerx, playery);
            DEF[7].setBounds(new Rectangle(DEF[7].getAIX(), DEF[7].getAIY(), 28, 44));
            
        }
        if((initd == 8) && (initl == 1)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], null, null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], null, null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], null, null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], null, null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], DEF[7], LIN[0], null, null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], DEF[7], LIN[0], null, null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], DEF[7], LIN[0], null, null, null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[7], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[0], LIN[0], null, null, null, null);
            DEF[7].DefenderMove(DEF[7], playerx, playery);
            DEF[7].setBounds(new Rectangle(DEF[7].getAIX(), DEF[7].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], null, null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[4].getAIX(), DEF[4].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            
            
        }
        
        if((initd == 8) && (initl == 2)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], null, null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], null, null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], null, null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], null, null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], null, null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], DEF[7], LIN[0], LIN[1], null, null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], DEF[7], LIN[0], LIN[1], null, null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[7], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[0], LIN[0], LIN[1], null, null, null);
            DEF[7].DefenderMove(DEF[7], playerx, playery);
            DEF[7].setBounds(new Rectangle(DEF[7].getAIX(), DEF[7].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], DEF[5], DEF[6], DEF[7], null, null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], DEF[5], DEF[6], DEF[7], null, null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
        }
        
        if((initd == 8) && (initl == 3)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], null, null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], null, null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], null, null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], null, null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], null, null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], null, null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], DEF[7], LIN[0], LIN[1], LIN[2], null, null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[7], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[0], LIN[0], LIN[1], LIN[2], null, null);
            DEF[7].DefenderMove(DEF[7], playerx, playery);
            DEF[7].setBounds(new Rectangle(DEF[7].getAIX(), DEF[7].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], DEF[5], DEF[6], DEF[7], null, null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], DEF[5], DEF[6], DEF[7], null, null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], DEF[5], DEF[6], DEF[7], null, null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
        }
        
        if((initd == 8) && (initl == 4)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[7], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], null);
            DEF[7].DefenderMove(DEF[7], playerx, playery);
            DEF[7].setBounds(new Rectangle(DEF[7].getAIX(), DEF[7].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], DEF[5], DEF[6], DEF[7], null);
            LIN[0].DefenderMove(LIN[0], DEF[1].getAIX(), DEF[1].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], DEF[5], DEF[6], DEF[7], null);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], DEF[5], DEF[6], DEF[7], null);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], DEF[5], DEF[6], DEF[7], null);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
        }
        if((initd == 8) && (initl == 5)){
            
            AIcollisionDetection(DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[0].DefenderMove(DEF[0], playerx, playery);
            DEF[0].setBounds(new Rectangle(DEF[0].getAIX(), DEF[0].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[1], DEF[0], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[1].DefenderMove(DEF[1], playerx, playery);
            DEF[1].setBounds(new Rectangle(DEF[1].getAIX(), DEF[1].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[2], DEF[1], DEF[0], DEF[3], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[2].DefenderMove(DEF[2], playerx, playery);
            DEF[2].setBounds(new Rectangle(DEF[2].getAIX(), DEF[2].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[3], DEF[1], DEF[2], DEF[0], DEF[4], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[3].DefenderMove(DEF[3], playerx, playery);
            DEF[3].setBounds(new Rectangle(DEF[3].getAIX(), DEF[3].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[4], DEF[1], DEF[2], DEF[3], DEF[0], DEF[5], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[4].DefenderMove(DEF[4], playerx, playery);
            DEF[4].setBounds(new Rectangle(DEF[4].getAIX(), DEF[4].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[5], DEF[1], DEF[2], DEF[3], DEF[4], DEF[0], DEF[6], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[5].DefenderMove(DEF[5], playerx, playery);
            DEF[5].setBounds(new Rectangle(DEF[5].getAIX(), DEF[5].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[6], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[0], DEF[7], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[6].DefenderMove(DEF[6], playerx, playery);
            DEF[6].setBounds(new Rectangle(DEF[6].getAIX(), DEF[6].getAIY(), 28, 44));
            
            AIcollisionDetection(DEF[7], DEF[1], DEF[2], DEF[3], DEF[4], DEF[5], DEF[6], DEF[0], LIN[0], LIN[1], LIN[2], LIN[3], LIN[4]);
            DEF[7].DefenderMove(DEF[7], playerx, playery);
            DEF[7].setBounds(new Rectangle(DEF[7].getAIX(), DEF[7].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[0], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[1], LIN[2], LIN[3], LIN[4], DEF[5], DEF[6], DEF[7]);
            LIN[0].DefenderMove(LIN[0], DEF[4].getAIX(), DEF[4].getAIY());
            LIN[0].setBounds(new Rectangle(LIN[0].getAIX(), LIN[0].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[1], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[2], LIN[3], LIN[4], DEF[5], DEF[6], DEF[7]);
            LIN[1].DefenderMove(LIN[1], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[1].setBounds(new Rectangle(LIN[1].getAIX(), LIN[1].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[2], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[3], LIN[4], DEF[5], DEF[6], DEF[7]);
            LIN[2].DefenderMove(LIN[2], DEF[2].getAIX(), DEF[2].getAIY());
            LIN[2].setBounds(new Rectangle(LIN[2].getAIX(), LIN[2].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[3], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[4], DEF[5], DEF[6], DEF[7]);
            LIN[3].DefenderMove(LIN[3], DEF[0].getAIX(), DEF[0].getAIY());
            LIN[3].setBounds(new Rectangle(LIN[3].getAIX(), LIN[3].getAIY(), 28, 44));
            
            AIcollisionDetection(LIN[4], DEF[0], DEF[1], DEF[2], DEF[3], DEF[4], LIN[0], LIN[1], LIN[2], LIN[3], DEF[5], DEF[6], DEF[7]);
            LIN[4].DefenderMove(LIN[4], DEF[3].getAIX(), DEF[3].getAIY());
            LIN[4].setBounds(new Rectangle(LIN[4].getAIX(), LIN[4].getAIY(), 28, 44));
            
        }
        
    }
    
    //* Unused. I originally wanted the linemen to seek the closer defender to block. I couldn't figure that out. This is the legacy code *//
    public void HITSOMEBODY (int initd, int initx, int inity){
        int maxX = 1000;
        int maxY = 1000;
        int defenddistX[] = new int[initd];
        int defenddistY[] = new int[initd];
        for(int i=0; i<=initd; i++){
            defenddistX[i] = (DEF[i].getAIX()-initx);
            defenddistY[i] = (DEF[i].getAIY()-inity);
        }
        for (int i = 0; i <=initd; i++){
            if (defenddistX[i] < maxX)
            {
                maxX = defenddistX[i];
            }
            if (defenddistY[i] < maxY)
            {
                maxY = defenddistY[i];
            }
                }
    }
    
    //* Initialized for the maximum number of defenders and linemen. If there are less than 5 linemen and 8 defenders, it will prevent
    //* a null exception. This method must be run for each and every AI player in the game. It checks the chosen AI player's position
    //* against every other AI player. It takes the final outcome of that calculation and passes it back to the aiplayer class, where
    //* movement is restricted or allowed based on the collisions.
    public void AIcollisionDetection(aiplayer a, aiplayer b, aiplayer c, aiplayer d, aiplayer e, aiplayer f, aiplayer g, aiplayer h, aiplayer i, aiplayer j, aiplayer k, aiplayer l, aiplayer m){
        
        int aX, bX, cX, dX, eX, fX, gX, hX, iX, jX, kX, lX, mX;
        int aY, bY, cY, dY, eY, fY, gY, hY, iY, jY, kY, lY, mY;
        
        aX = a.getAIX();
        aY = a.getAIY();
        if (b == null){bX = 0;} else {bX = b.getAIX();}
        if (b == null){bY = 0;} else {bY = b.getAIY();}
        if (c == null){cX = 0;} else {cX = c.getAIX();}
        if (c == null){cY = 0;} else {cY = c.getAIY();}
        if (d == null){dX = 0;} else {dX = d.getAIX();}
        if (d == null){dY = 0;} else {dY = d.getAIY();}
        if (e == null){eX = 0;} else {eX = e.getAIX();}
        if (e == null){eY = 0;} else {eY = e.getAIY();}
        if (f == null){fX = 0;} else {fX = f.getAIX();}
        if (f == null){fY = 0;} else {fY = f.getAIY();}
        if (g == null){gX = 0;} else {gX = g.getAIX();}
        if (g == null){gY = 0;} else {gY = g.getAIY();}
        if (h == null){hX = 0;} else {hX = h.getAIX();}
        if (h == null){hY = 0;} else {hY = h.getAIY();}
        if (i == null){iX = 0;} else {iX = i.getAIX();}
        if (i == null){iY = 0;} else {iY = i.getAIY();}
        if (j == null){jX = 0;} else {jX = j.getAIX();}
        if (j == null){jY = 0;} else {jY = j.getAIY();}
        if (k == null){kX = 0;} else {kX = k.getAIX();}
        if (k == null){kY = 0;} else {kY = k.getAIY();}
        if (l == null){lX = 0;} else {lX = l.getAIX();}
        if (l == null){lY = 0;} else {lY = l.getAIY();}
        if (m == null){mX = 0;} else {mX = m.getAIX();}
        if (m == null){mY = 0;} else {mY = m.getAIY();}
      //  int fX = f.getAIX();
      //  int fY = f.getAIY();
      //  int gX = g.getAIX();
      //  int gY = g.getAIY();
      //  int hX = h.getAIX();
       // int hY = h.getAIY();
       // int iX = i.getAIX();
       // int iY = i.getAIY();
        //int jX = j.getAIX();
        //int jY = j.getAIY();
       
        int finaloutcomeX = 
                AIcollisionRunX(aX, aY, bX, bY) + 
                AIcollisionRunX(aX, aY, cX, cY) +
                AIcollisionRunX(aX, aY, dX, dY) +
                AIcollisionRunX(aX, aY, eX, eY) +
                AIcollisionRunX(aX, aY, fX, fY) +
                AIcollisionRunX(aX, aY, gX, gY) +
                AIcollisionRunX(aX, aY, hX, hY) +
                AIcollisionRunX(aX, aY, iX, iY) +
                AIcollisionRunX(aX, aY, jX, jY) +
                AIcollisionRunX(aX, aY, kX, kY) +
                AIcollisionRunX(aX, aY, lX, lY) +
                AIcollisionRunX(aX, aY, mX, mY);
        a.setAIColX(finaloutcomeX);
        
        int finaloutcomeY = 
                AIcollisionRunY(aX, aY, bX, bY) + 
                AIcollisionRunY(aX, aY, cX, cY) +
                AIcollisionRunY(aX, aY, dX, dY) +
                AIcollisionRunY(aX, aY, eX, eY) +
                AIcollisionRunY(aX, aY, fX, fY) +
                AIcollisionRunY(aX, aY, gX, gY) +
                AIcollisionRunY(aX, aY, hX, hY) +
                AIcollisionRunY(aX, aY, iX, iY) +
                AIcollisionRunY(aX, aY, jX, jY) +
                AIcollisionRunY(aX, aY, kX, kY) +
                AIcollisionRunY(aX, aY, lX, lY) +
                AIcollisionRunY(aX, aY, mX, mY);
        a.setAIColY(finaloutcomeY);
    
    }
    
    //* Determines whether a collision has occured between two aiplayers on the X axis. Returns a value of 1
    //* for a collision on the left and a value of 10 for a collision on the right.
    public int AIcollisionRunX(int a, int b, int c, int d){
              
        int outcomeX;
        int collisionleft = a - (c + xdim);
        int collisionright = c - (a + xdim);
        int collisionup = b - (d + ydim);
        int collisiondown = d - (b + ydim);
        
        if ((-4 <= collisionleft && collisionleft <= 6) && (collisionup <= 0 && collisiondown <= 0)){
            outcomeX = 1;
            
        }
        else if ((-4 <= collisionright && collisionright <= 6) && (collisionup <= 5 && collisiondown <= 5)){
            outcomeX = 10;
        }       
        else {
            outcomeX = 0;
        }
        return outcomeX;
     
    }
    
    //* Determines whether a collision has occured between two aiplayers on the Y axis. Returns a value of 1
    //* for a collision up and a value of 10 for a collision down.
    public int AIcollisionRunY(int a, int b, int c, int d){
              
        int outcomeY;
        int collisionleft = a - (c + xdim);
        int collisionright = c - (a + xdim);
        int collisionup = b - (d + ydim);
        int collisiondown = d - (b + ydim);
        
        if ((-4 <= collisionup && collisionup <= 6) && (collisionleft <= 5 && collisionright <= 5)){
            outcomeY = 1;
        }
        else if ((-4 <= collisiondown && collisiondown <= 6) && (collisionleft <= 5 && collisionright <= 5)){
            outcomeY = 10;
        }
        else {
            outcomeY = 0;
        }  
        return outcomeY;
    }      
    
    
    //* Checks to see if the player is close to the top edge of the screen. Repaints the screen and 
    //* moves the AI players if this is true *//
    public void fieldCheck(){
        if(playery <= 30){
            
            for (int i = 0; i < defense; i++){
                DEF[i].setAIY(725 + DEF[i].getAIY());
            }
            
            for (int i = 0; i < line; i++){
                LIN[i].setAIY(725 + LIN[i].getAIY());
            }
            upfield = 1;
            zoneswitch ++;
            playery = 650;
            player.setBounds(playerx, 650, 50,65);
        }
        else if((playery <= 275) && (zoneswitch == 3)){
            
            yardageGain.setBounds(new Rectangle(550, 30, 450, 50));
            //downtogo = 1;
            isitover = 1;
            player.setBounds(753, 1300, 28, 44);
            yardagetext = "TOUCHDOWN PENN STATE";
            yardageGain.setText(yardagetext);
            add(yardageGain);
            psuScore = psuScore + 7;
            homeScore.setText("PSU: " + String.valueOf(psuScore));
            tim.stop();
            tim6.stop();
            touchdowns = touchdowns + 1;
            zoneswitch = 0;
            repaint();
            gameReset();
            tim5.start();
        }
    }
    
    public void moxie(){
        moxietimer = 0;
        tim2.start();
        
    }
    
    public void trucking(){
        if (trucking == 0){
        trucking = 1;
        tim3.start();
        effectwindow.setText("Trucking!");
    }
        if (trucking == 2){
        effectwindow.setText("Cannot use Truck!");
        }  
    }
    
    public void speed(){
        if (speed == 0){
        speed = 1;
        tim4.start();
        }
        if (speed == 2){
        effectwindow.setText("Cannot use Speed!");
        }  
    }
    
    //* Sets the player's speed *//
    public void setPlayerSpeed(int initDif){
        if(initDif == 0){
            playerspeed = 5;
            
        }
        if(initDif == 1){
            playerspeed = 5;
        }
        if(initDif == 2){
            playerspeed = 4;
        }
        if(initDif == 3){
            playerspeed = 4;
        }
        if(initDif == 4){
            playerspeed = 3;
        }
        if(initDif == 5){
            playerspeed = 3;
        }
        if(initDif == 6){
            playerspeed = 3;
        }
        if(initDif == 7){
            playerspeed = 2;
        }
        if(initDif == 8){
            playerspeed = 2;
        }
        if(initDif == 9){
            playerspeed = 2;
        }
        if(initDif == 10){
            playerspeed = 1;
        }
    }
    
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'g'){
            moxie();
        }
        if (e.getKeyChar() == 't'){
            trucking();
        }
        if (e.getKeyChar() == 'f'){
            speed();
        }
        
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'g'){
            moxie();
        }
        if (e.getKeyChar() == 't'){
            trucking();
        }
        if (e.getKeyChar() == 'f'){
            speed();
        }
    }
    
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'g'){
            moxie();
        }
        if (e.getKeyChar() == 't'){
            trucking();
        }
        if (e.getKeyChar() == 'f'){
            speed();
        }
        
    }
    
    public void simOSUDrive(int a){
        osuDrive = 0;
        yardageGain.removeActionListener(this);
        simmodifier = a/5;
        if (simmodifier == 0){
            simmodifier = 1/5;
        }
        
        osuDrive = (int)((Math.random() * 2) * simmodifier);
        if (gameover == 0){
            if (osuDrive == 1){
                osuScore = osuScore + 7;
                awayScore.setText("OSU: " + osuScore);
                yardageGain.setText("OSU Scores!");
            }
            if (osuDrive == 0){
                yardageGain.setText("OSU doesn't score!");
            }
        }
        else if (gameover == 1){
            if (osuDrive == 1){
                osuScore = osuScore + 7;
                awayScore.setText("OSU: " + osuScore);
                yardageGain.setText("OSU Scores as time expires!");
            }
            if (osuDrive == 0){
                yardageGain.setText("OSU doesn't score! Game over!");
            }  
            gameOverMan();
        }
    }
    
    public void OSUDriveTime(){
        timeoffclock = 0;
        timeoffclock = (int)(Math.random() * 30) + 20;
        secondsleft = secondsleft - timeoffclock;
        if ((secondsleft <= 0) && (minutesleft > 0)){
            negativeseconds = secondsleft;
            secondsleft = 59 + (negativeseconds - 1);
            minutesleft = minutesleft - 1;   
        }
        if ((secondsleft <= 0) && (minutesleft == 0)){
            gameover = 1;
            
        }
        
    }
    
    public void gameReset (){
        
        tim4.stop();
        tim5.stop();
        tim3.stop();
        tim2.stop();
        xdest = 0;
        downtogo = 1;
        isitover = 0;
        setPlayerSpeed(difficvalue);
        ydest = 0;
        upfield = 0;
        touchtimer = 0;
        animate = 0;
        yardstogo = 10;
        pixelsGained = 0;
        currentPixel = 658;
        speedtimer = 0;
        trucktimer = 0;
        playerx = 753;
        playery = 678;
        speed = 0;
        trucking = 0;
        effectwindow.setText("Under no effects");
        downandDistance.setText("1st and 10");
        yardageGain.removeActionListener(this);
    }
    
    public void gameOverMan (){
        TimeLeft.setText("TIME IS UP! GAME OVER!");
        remove(yardageGain);
        
        gameover = 1;
        isitover = 1;
        tim.stop();
        tim1.stop();
        tim2.stop();
        tim3.stop();
        tim4.stop();
        tim5.stop();
        tim6.stop();
        
        
    }
    
    public int getTDs (){
        return psuScore;
    }
    
    public int getOSUscore (){
        return osuScore;
    }

}
