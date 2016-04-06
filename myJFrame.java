import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import java.io.*;
import java.beans.*;

public class myJFrame extends JFrame implements ActionListener
{
    
    //* Path that the high scores are saved to. Needs to be defined by user *//
    String scorepath;
    

    //* Game Panel on which all of the action occurs *//
    myGamePanel mgp;     
    
    //* Options Panel used for setting the games options *//
    myOptionsPanel mop = new myOptionsPanel();        
    
    //* The splash page. Contains the New Game, Load Game and Quit Game buttons *//
    myWelcomePanel mwp = new myWelcomePanel();                                  
    
    //* All the images used in the game *//
    JLabel background = new JLabel(new ImageIcon("images/hacksplash.jpg"));     
    JLabel titletext = new JLabel(new ImageIcon("images/titletext.png"));
    JLabel gameintro1 = new JLabel(new ImageIcon("images/gameintro1.png"));
    JLabel blacksplash = new JLabel(new ImageIcon("images/blacksplash.png"));
    JLabel buckeyes = new JLabel(new ImageIcon("images/buckeyes.jpg"));
    JLabel gameintro2 = new JLabel(new ImageIcon("images/gameintro2.png"));
    JLabel gameintro3 = new JLabel(new ImageIcon("images/gameintro3.png"));
    JLabel hackopening = new JLabel(new ImageIcon("images/hackopening.jpg"));
    JLabel whiteout = new JLabel(new ImageIcon("images/whiteout.jpg"));
    JLabel gameintro4 = new JLabel(new ImageIcon("images/gameintro4.png"));
    JLabel gameintro5 = new JLabel(new ImageIcon("images/gameintro5.png"));
    JLabel gameintro6 = new JLabel(new ImageIcon("images/gameintro6.png"));
    JLabel huddle = new JLabel(new ImageIcon("images/huddle.jpg"));
    JLabel finalscore = new JLabel(new ImageIcon("images/finalscore.png"));
    JLabel osuscore = new JLabel(new ImageIcon("images/finalosu.png"));
    JLabel psuscore = new JLabel(new ImageIcon("images/finalpsu.png"));
    JLabel twenty = new JLabel(new ImageIcon("images/20.png"));
    JLabel twentyfour = new JLabel(new ImageIcon("images/24.png"));
    JLabel twentyseven = new JLabel(new ImageIcon("images/27.png"));
    JLabel thirtyone = new JLabel(new ImageIcon("images/31.png"));
    JLabel thirtyfour = new JLabel(new ImageIcon("images/34.png"));
    JLabel thirtyeight = new JLabel(new ImageIcon("images/38.png"));
    JLabel fortyone = new JLabel(new ImageIcon("images/41.png"));
    JLabel fortyfive = new JLabel(new ImageIcon("images/45.png"));
    JLabel fortyeight = new JLabel(new ImageIcon("images/48.png"));
    JLabel fiftytwo = new JLabel(new ImageIcon("images/52.png"));
    JLabel fiftyfive = new JLabel(new ImageIcon("images/55.png"));
    JLabel fiftynine = new JLabel(new ImageIcon("images/59.png"));
    JLabel sixtytwo = new JLabel(new ImageIcon("images/62.png"));
    JLabel sixtysix = new JLabel(new ImageIcon("images/66.png"));
    JLabel sixtynine = new JLabel(new ImageIcon("images/69.png"));
    JLabel seventythree = new JLabel(new ImageIcon("images/73.png"));
    JLabel seventysix = new JLabel(new ImageIcon("images/76.png"));
    JLabel eighty = new JLabel(new ImageIcon("images/80.png"));
    JLabel eightythree = new JLabel(new ImageIcon("images/83.png"));
    JLabel eightyseven = new JLabel(new ImageIcon("images/87.png"));
    JLabel ninety = new JLabel(new ImageIcon("images/90.png"));
    JLabel ninetyseven = new JLabel(new ImageIcon("images/97.png"));
    JLabel psuwin = new JLabel(new ImageIcon("images/psuwin.png"));
    JLabel osuwin = new JLabel(new ImageIcon("images/osuwin.png"));
    JLabel kerrycollins = new JLabel(new ImageIcon("images/kerrycollins.jpg"));
    JLabel toddblackledge = new JLabel(new ImageIcon("images/toddblackledge.jpg"));
    JLabel johnhufnagel = new JLabel(new ImageIcon("images/johnhufnagel.jpg"));
    JLabel mrob = new JLabel(new ImageIcon("images/michaelrobinson.jpg"));
    JLabel daryllclark = new JLabel(new ImageIcon("images/daryllclark.jpg"));
    JLabel chuckfusina = new JLabel(new ImageIcon("images/chuckfusina.jpg"));
    JLabel moxie = new JLabel(new ImageIcon("images/mattmcgloin.jpg"));
    JLabel morelli = new JLabel(new ImageIcon("images/anthonymorelli.jpg"));
    JLabel collinstext = new JLabel(new ImageIcon("images/collinstext.png"));
    JLabel blackledgetext = new JLabel(new ImageIcon("images/blackledgetext.png"));
    JLabel hufnageltext = new JLabel(new ImageIcon("images/hufnageltext.png"));
    JLabel mrobtext = new JLabel(new ImageIcon("images/mrobtext.png"));
    JLabel clarktext = new JLabel(new ImageIcon("images/clarktext.png"));
    JLabel fusinatext = new JLabel(new ImageIcon("images/fusinatext.png"));
    JLabel mcglointext = new JLabel(new ImageIcon("images/mcglointext.png"));
    JLabel morellitext = new JLabel(new ImageIcon("images/morellitext.png"));
    JLabel highscorespic = new JLabel(new ImageIcon("images/highscores.png"));
    ImageIcon franklin1 = new ImageIcon("images/franklin1.png");
    ImageIcon franklin2 = new ImageIcon("images/franklin2.png");
    ImageIcon franklin3 = new ImageIcon("images/franklin3.png");
    
    //* Text Field used to accept a high score name *//
    JTextField HighScoreName = new JTextField("Enter name here");
    
    //* Array used to display the high scores *//
    JTextField highscores[] = new JTextField[20];
    
    //* XML Encoder and Decoder taken from the class examples *//
    XMLDecoder de;
    XMLEncoder xe;
    
    //* JLayeredPane helps with organizing everything on screen *//
    JLayeredPane lp = new JLayeredPane();
    GridLayout sp = new GridLayout(); 
    
    //* Timer for animating the game intro *//
    Timer tim5;
    
    //* Timer for animating and controling the post-game information *//
    Timer tim7;
    
    //* Buttons used in the JFrame *//
    JButton PresstoSave = new JButton("Save Your Score");
    JButton FinalButton = new JButton("Please Enter Your Name");
    JButton newGame;                                   
    JButton highScores;                                   
    JButton quitGame;
    JButton backMenu;
    JButton clickskip = new JButton("Click to Skip");
    
    //* Integers used by the JFrame *//
    int difficvalue;                                    
    int defense;                                        
    int line;                                           
    int delay = 20;
    int animate = 0;
    int ggtimer = 0;
    int PSUfinal;
    int OSUfinal;
    int playerfinal;
    int numfiles;
    int newfile;
    int scores[] = new int[10];
    
    String Namefinal;
    String nextscore;
    String names[] = new String[10];
 
    
    public myJFrame ()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize (1030, 727);
        setVisible(true);
        
        Namefinal = "BOB SUX";
        
        //* Setting and adding the buttons on the main menu *//
        newGame = new JButton();
        highScores = new JButton();
        quitGame = new JButton();
        newGame.setBounds(new Rectangle(10,600,200,110));
        newGame.setIcon(franklin1);
        newGame.setOpaque(false);
        newGame.setContentAreaFilled(false);
        newGame.setBorderPainted(false);
        highScores.setBounds(new Rectangle(210,600,200,110));
        highScores.setIcon(franklin2);
        highScores.setOpaque(false);
        highScores.setContentAreaFilled(false);
        highScores.setBorderPainted(false);
        quitGame.setBounds(new Rectangle(410,600,200,110));
        quitGame.setIcon(franklin3);
        quitGame.setOpaque(false);
        quitGame.setContentAreaFilled(false);
        quitGame.setBorderPainted(false);
        background.setBounds(0,0,1030,727);
        titletext.setBounds(0,0,726,170);
        backMenu = new JButton("Click to Return");
        
        //* Adding the other components to the main menu *//
        lp = getLayeredPane();
        lp.add(titletext, new Integer(4));
        lp.add(newGame, new Integer(20));
        lp.add(highScores, new Integer(20));
        lp.add(quitGame, new Integer(20));
        lp.add(background, new Integer(1));
        tim5 = new Timer(delay, this);
        tim7 = new Timer(delay, this);
        newGame.addActionListener(this);
        highScores.addActionListener(this);
        quitGame.addActionListener(this);
        
        //* These text fields need to be initialized before being used *//
        for (int i = 0; i < 20; i++){
            highscores[i] = new JTextField("Took hours to figure this out");
        }
        
        
    }
    
    public void actionPerformed(ActionEvent event) 
    {
        Object obj = event.getSource();
        if (obj == newGame)
        {
            //* Reads the path saved in the XML document and saves it the scorepath String *//
            try {de = new XMLDecoder(new BufferedInputStream(new FileInputStream("highscorepath.xml")));}
                    catch(Exception xx) {xx.printStackTrace();}
                try {
                    
                    scorepath = (String)de.readObject();
                    System.out.println(scorepath);
		}
                    catch(Exception xx) {xx.printStackTrace();}
                try {de.close();}
                    catch(Exception xx) {xx.printStackTrace();}
                
            
            System.out.println("New Game");
            
            //* Removes components from the main menu *//
            lp.remove(background);
            lp.remove(titletext);
            lp.remove(newGame);
            lp.remove(highScores);
            lp.remove(quitGame);
            repaint();
            
            //* Adds the welcome panel *//
            getContentPane().setLayout(sp);
            getContentPane().add(mwp, "Center");
            revalidate();
            mwp.playgame.addActionListener(this); 
        }
        if (obj == highScores)
        {
            System.out.println("High Scores");   
            
            //* Adds the components for the high scores menu *//
            lp.remove(blacksplash);
            revalidate();
            blacksplash.setBounds(0,0,1030,727);
            lp.add(blacksplash, new Integer(50));
            highscorespic.setBounds(400,10,200,60);
            lp.add(highscorespic, new Integer(60));
            repaint();
            
            //* Checks the XML file highscorepath.xml and copies that string to scorepath *//
            try {de = new XMLDecoder(new BufferedInputStream(new FileInputStream("highscorepath.xml")));}
                    catch(Exception xx) {xx.printStackTrace();}
                try {
                    
                    scorepath = (String)de.readObject();
                    System.out.println(scorepath);
		}
                    catch(Exception xx) {xx.printStackTrace();}
                try {de.close();}
                    catch(Exception xx) {xx.printStackTrace();}
                
            //* Reads the number of files in the path *//
            numfiles = new File(scorepath).listFiles().length;
            System.out.println(String.valueOf(numfiles));
            
            //* Sets the nextscore String to equal the next unused file named score *//
            nextscore = "score" + String.valueOf(newfile) + ".xml";
            
            //* Reads each score file and returns the values stored within to the names[] and scores [] arrays *//
            for (int i = 1; i <= numfiles; i++){
                try {de = new XMLDecoder(new BufferedInputStream(new FileInputStream(scorepath + "/score" + i + ".xml")));}
                    catch(Exception xx) {xx.printStackTrace();}
                try {
                    
                    names[i] = (String)de.readObject();
                    scores[i] = (Integer)de.readObject();
                    System.out.println(names[i]);
		}
                    catch(Exception xx) {xx.printStackTrace();}
                try {de.close();}
                    catch(Exception xx) {xx.printStackTrace();}
                
                //* Displays the high scores *//
                highscores[i].setText(names[i] +", " + (String.valueOf(scores[i])));
                highscores[i].setBounds(100,(100 + (30 * i)),200,50);
                lp.add(highscores[i], new Integer(111));
                repaint();
            }
            
            //* Adds a button to go back to the main menu *//
            backMenu.setBounds(450,650,150,50);
            lp.add(backMenu, new Integer(75));
            backMenu.addActionListener(this);
        }
        
        //* Removes all the high scores and returns to the main menu *//
        if (obj == backMenu){
            lp.remove(blacksplash);
            for (int i = 1; i <= numfiles; i++){
                lp.remove(highscores[i]);
            }
            lp.remove(backMenu);
            lp.remove(highscorespic);
            repaint();
        }
        
        //* Quits the game *//
        if (obj == quitGame)
        {
           System.out.println("Quit Game");             
           System.exit(0);
        }
        if (obj == mop.startgame)                       //* Listens for the JButton startgame on mop *//
        {
            difficvalue = mop.getDiffic();              //* Gets the game difficulty from the slider on mop *//
            defense = mop.getDefense();                 //* Gets the number of defenders from the slider on mop *//
            line = mop.getline();                       //* Gets the number of linemen from the slider on mop *//
            closeOptionPanel();
        }
        
        //* Goes to the options menu after displaying the help text *//
        if (obj == mwp.playgame)
        {
            remove(mwp);
            getContentPane().add(mop, "Center");
            revalidate();
            mop.startgame.addActionListener(this);
        }
        
        //* Skips the game intro *//
        if (obj == clickskip)
        {
            animate = 1000;
            tim5.stop();
            lp.remove(blacksplash);
            lp.remove(huddle);
            lp.remove(gameintro5);
            lp.remove(gameintro6);
            lp.remove(gameintro1);
            lp.remove(buckeyes);
            lp.remove(gameintro2);
            lp.remove(hackopening);
            lp.remove(gameintro3);
            lp.remove(whiteout);
            lp.remove(gameintro4);
            lp.remove(clickskip);
            closeWelcomePanel();
        }
        
        //* Animates the game intro *//
        if (obj == tim5)
        {
            animate = animate + 1;
            if (animate == 100){
                gameintro1.setBounds(10,10,747,160);
                clickskip.setBounds(430,650,150,30);
                lp.add(gameintro1, new Integer(3));
                lp.add(clickskip, new Integer(100));
                clickskip.addActionListener(this);
            }
            if (animate == 275){
                buckeyes.setBounds(250,250,500,352);
                lp.remove(gameintro1);
                lp.add(buckeyes, new Integer(25));
            }
            if (animate == 425){
                lp.remove(gameintro1);
                lp.remove(buckeyes);
                repaint();
                gameintro2.setBounds(0,0,629,69);
                gameintro3.setBounds(0,560,829,69);
                lp.add(gameintro2, new Integer(30));
                hackopening.setBounds(0,0,1030,727);
                lp.add(hackopening, new Integer(20));
                
            }
            if (animate == 500){
                lp.add(gameintro3, new Integer(35)); 
            }
            if (animate == 600){
                lp.remove(gameintro2);
                lp.remove(hackopening);
                lp.remove(gameintro3);
                repaint();
                whiteout.setBounds(0,0,1030,727);
                lp.add(whiteout, new Integer(5));
                gameintro4.setBounds(10,10,1000,50);
                lp.add(gameintro4, new Integer(10));
            }
            if (animate == 700){
                lp.remove(whiteout);
                lp.remove(gameintro4);
                repaint();
                huddle.setBounds(0,0,1030,727);
                lp.add(huddle, new Integer(5));
                gameintro5.setBounds(150,25,646,100);
                gameintro6.setBounds(10,550,1000,100);
                lp.add(gameintro5, new Integer(15));
                lp.add(gameintro6, new Integer(15));
            }
            if (animate >= 1000){
                lp.remove(huddle);
                lp.remove(gameintro5);
                lp.remove(gameintro6);
            }
        }
        
        //* Animates the game ending *//
        if (obj == tim7){
            
            //* Ensures that the game is over *//
            if ((mgp.gameover == 1) && (mgp.isitover == 1)){
            ggtimer = ggtimer + 1;
            if (ggtimer == 50){
                
                remove(mgp);
                revalidate();
                lp.add(blacksplash, new Integer(4));
            }
            //* Displays the final score *//
            if (ggtimer == 75){
                finalscore.setBounds(415,150,200,60);
                lp.add(finalscore, new Integer(6));
                psuscore.setBounds(250,250,200,60);
                lp.add(psuscore, new Integer(6));
                osuscore.setBounds(250,350,200,60);
                lp.add(osuscore, new Integer(6));
                PSUfinal = mgp.getTDs();
                OSUfinal = mgp.getOSUscore();
                
                //* Displays the image corresponding with each team's score *//
                if (PSUfinal == 20){
                    twenty.setBounds(450,250,50,60);
                    lp.add(twenty, new Integer(8));
                }
                if (PSUfinal == 27){
                    twentyseven.setBounds(450,250,50,60);
                    lp.add(twentyseven, new Integer(8));
                }
                if (PSUfinal == 34){
                    thirtyfour.setBounds(450,250,50,60);
                    lp.add(thirtyfour, new Integer(8));
                }
                if (PSUfinal == 41){
                    fortyone.setBounds(450,250,50,60);
                    lp.add(fortyone, new Integer(8));
                }
                if (PSUfinal == 48){
                    fortyeight.setBounds(450,250,50,60);
                    lp.add(fortyeight, new Integer(8));
                }
                if (PSUfinal == 55){
                    fiftyfive.setBounds(450,250,50,60);
                    lp.add(fiftyfive, new Integer(8));
                }
                if (PSUfinal == 62){
                    sixtytwo.setBounds(450,250,50,60);
                    lp.add(sixtytwo, new Integer(8));
                }
                if (PSUfinal == 69){
                    sixtynine.setBounds(450,250,50,60);
                    lp.add(sixtynine, new Integer(8));
                }
                if (PSUfinal == 76){
                    seventysix.setBounds(450,250,50,60);
                    lp.add(seventysix, new Integer(8));
                }
                if (PSUfinal == 83){
                    eightythree.setBounds(450,250,50,60);
                    lp.add(eightythree, new Integer(8));
                }
                if (PSUfinal == 90){
                    ninety.setBounds(450,250,50,60);
                    lp.add(ninety, new Integer(8));
                }
                if (PSUfinal == 97){
                    ninetyseven.setBounds(450,250,50,60);
                    lp.add(ninetyseven, new Integer(8));
                }
                if (OSUfinal == 24){
                    twentyfour.setBounds(450,350,50,60);
                    lp.add(twentyfour, new Integer(8));
                }
                if (OSUfinal == 31){
                    thirtyone.setBounds(450,350,50,60);
                    lp.add(thirtyone, new Integer(8));
                }
                if (OSUfinal == 38){
                    thirtyeight.setBounds(450,350,50,60);
                    lp.add(thirtyeight, new Integer(8));
                }
                if (OSUfinal == 45){
                    fortyfive.setBounds(450,350,50,60);
                    lp.add(fortyfive, new Integer(8));
                }
                if (OSUfinal == 52){
                    fiftytwo.setBounds(450,350,50,60);
                    lp.add(fiftytwo, new Integer(8));
                }
                if (OSUfinal == 59){
                    fiftynine.setBounds(450,350,50,60);
                    lp.add(fiftynine, new Integer(8));
                }
                if (OSUfinal == 66){
                    sixtysix.setBounds(450,350,50,60);
                    lp.add(sixtysix, new Integer(8));
                }
                if (OSUfinal == 73){
                    seventythree.setBounds(450,350,50,60);
                    lp.add(seventythree, new Integer(8));
                }
                if (OSUfinal == 80){
                    eighty.setBounds(450,350,50,60);
                    lp.add(eighty, new Integer(8));
                }
                if (OSUfinal == 87){
                    eightyseven.setBounds(450,350,50,60);
                    lp.add(eightyseven, new Integer(8));
                }
                if (PSUfinal > OSUfinal){
                    psuwin.setBounds(375,450,350,60);
                    lp.add(psuwin, new Integer(10));  
                }
                if (PSUfinal < OSUfinal){
                    osuwin.setBounds(375,450,350,60);
                    lp.add(osuwin, new Integer(10));  
                }
            }
            
            //* Ranks the player according to their final score *//
            if (ggtimer == 200){
                lp.removeAll();
                lp.add(blacksplash, new Integer(4));
                revalidate();
                playerScore();
                if (playerfinal >= 110){
                    kerrycollins.setBounds(0,0,1030,727);
                    lp.add(kerrycollins, new Integer(8));
                    collinstext.setBounds(315,150,550,60);
                    lp.add(collinstext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                else if (playerfinal >= 90){
                    toddblackledge.setBounds(0,0,1030,727);
                    lp.add(toddblackledge, new Integer(8));
                    blackledgetext.setBounds(315,150,550,60);
                    lp.add(blackledgetext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                else if (playerfinal >= 80){
                    johnhufnagel.setBounds(0,0,1030,727);
                    lp.add(johnhufnagel, new Integer(8));
                    hufnageltext.setBounds(315,150,550,60);
                    lp.add(hufnageltext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                else if (playerfinal >= 70){
                    mrob.setBounds(0,0,1030,727);
                    lp.add(mrob, new Integer(8));
                    mrobtext.setBounds(315,150,550,60);
                    lp.add(mrobtext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                else if (playerfinal >= 60){
                    chuckfusina.setBounds(0,0,1030,727);
                    lp.add(chuckfusina, new Integer(8));
                    fusinatext.setBounds(315,150,550,60);
                    lp.add(fusinatext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                else if (playerfinal >= 50){
                    daryllclark.setBounds(0,0,1030,727);
                    lp.add(daryllclark, new Integer(8));
                    clarktext.setBounds(315,150,550,60);
                    lp.add(clarktext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                else if (playerfinal >= 40){
                    moxie.setBounds(0,0,1030,727);
                    lp.add(moxie, new Integer(8));
                    mcglointext.setBounds(315,150,550,60);
                    lp.add(mcglointext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                else if (playerfinal >= 0){
                    morelli.setBounds(0,0,1030,727);
                    lp.add(morelli, new Integer(8));
                    morellitext.setBounds(315,150,550,60);
                    lp.add(morellitext, new Integer(15));
                    FinalButton.setBounds(325,500,350,50);
                    HighScoreName.setBounds(425,560,150,50);
                    PresstoSave.setBounds(425,620,150,50);
                    lp.add(FinalButton, new Integer(10));
                    lp.add(HighScoreName, new Integer(10));
                    lp.add(PresstoSave, new Integer(10));
                    PresstoSave.addActionListener(this);
                }
                
                
            }
            
            }
        }
        
        //* Saves the final score in the scores directory *//
        if (obj == PresstoSave){
            Namefinal = HighScoreName.getText();
            numfiles = new File(scorepath).listFiles().length;
            newfile = numfiles + 1;
            nextscore = "score" + String.valueOf(newfile) + ".xml";
            try {
              xe = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(scorepath + "/" + nextscore)));
             }
           catch(Exception xx) {xx.printStackTrace();}

           try {
			 xe.writeObject(Namefinal);
			 xe.writeObject(playerfinal);
            }
           catch(Exception xx) {xx.printStackTrace();}

           try {
             xe.close();
            }
           catch(Exception xx) {xx.printStackTrace();}
           PresstoSave.setText("Score Saved! Thanks for playing!");
           PresstoSave.removeActionListener(this);
        }
        

        
    }
    
    public void closeOptionPanel() {
        remove(mop);
        GameIntro();
        tim5.start();
        mwp.playgame.addActionListener(this);
        revalidate();
        }
    
    public void GameIntro(){
        blacksplash.setBounds(0,0,1030,727);
        lp.add(blacksplash, new Integer(1));
    }
    
    public void closeWelcomePanel(){
        mgp = new myGamePanel(difficvalue, defense, line);      //* Creates the Game Panel mgp and passes the options to it *//
        getContentPane().setLayout(sp);
        getContentPane().add(mgp, "Center");
        tim7.start();
        revalidate();
        
    }
    
    public void playerScore (){
        playerfinal = PSUfinal + (PSUfinal - OSUfinal);
    }
    
}



