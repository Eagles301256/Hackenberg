import javax.swing.*;


public class aiplayer extends JButton{
    
    ImageIcon defender = new ImageIcon("images/defender.png");
    ImageIcon lineman = new ImageIcon("images/lineman.png");
    //JButton ICON;
    private double aiplayerx;
    private double aiplayery;
    private double aiplayerXdir;
    private double aiplayerYdir;
    int xdif = 18;
    int ydif = 40;
    int xdim = 36;
    int ydim = 55;
    int collisionmodifier = 0;
    private int gameDiffic;
    double aiplayerspeed;
    int aiplayercollision;
    int playertype;
    int xcollide = 0;
    int ycollide = 0;
    
public aiplayer (int initDiffic, int initx, int inity) {
    
    
    aiplayerx = initx;
    aiplayery = inity;
    setDefenderSpeed();
    gameDiffic = initDiffic;
    aiplayercollision = 0;
    
}
    //* Moves the AI player. If there is one or more collisions, the player is
    //* restricted from moving in that direction. Collision direction is
    //* differentiated by 1's and 10's.
    public void DefenderMove (JButton def, int initx, int inity){
        
        aiplayerXdir = (initx + xdif) - (aiplayerx + xdif);
        aiplayerYdir = (inity + ydif) - (aiplayery + ydif);
   
        if(xcollide == 0){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }  
        }
        if(ycollide == 0){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        
        if(xcollide == 1){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        if(xcollide == 2){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        if(xcollide == 3){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        if(xcollide == 4){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        if(xcollide == 5){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        if(xcollide == 6){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        if(xcollide == 7){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        if(xcollide == 8){
            if (aiplayerXdir >= aiplayerspeed){
            aiplayerx = aiplayerx + aiplayerspeed;
            }
        }
        
        if(xcollide == 10){
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }
        } 
        if(xcollide == 20){
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }
        }
        if(xcollide == 30){
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }
        }
        if(xcollide == 40){
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }
        }
        if(xcollide == 50){
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }
        }
        if(xcollide == 60){
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }
        }
        if(xcollide == 70){
            if (aiplayerXdir <= (aiplayerspeed * -1)){
            aiplayerx = aiplayerx - aiplayerspeed;
            }
        }
        if(ycollide == 10){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 20){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 30){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 40){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 50){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 60){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 70){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 80){
            if (aiplayerYdir <= (aiplayerspeed * -1)){
            aiplayery = aiplayery - aiplayerspeed;
            } 
        }
        if(ycollide == 1){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
        }
        if(ycollide == 2){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
        }
        if(ycollide == 3){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
        }
        if(ycollide == 4){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
        }
        if(ycollide == 5){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
        }
        if(ycollide == 6){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
        }
        if(ycollide == 7){
            if (aiplayerYdir >= aiplayerspeed){
            aiplayery = aiplayery + aiplayerspeed;
             }
        }      
    }
    
    //* Sets the speed for the defenders *//
    public void setDefenderSpeed(){
        
    
        if(gameDiffic == 0){
            aiplayerspeed = (Math.random() * 1) + 1;           
        }
        if(gameDiffic == 1){
            aiplayerspeed = (Math.random() * 1) + 1;
        }
        if(gameDiffic == 2){
            aiplayerspeed = (Math.random() * 2) + 1;
        }
        if(gameDiffic == 3){
            aiplayerspeed = (Math.random() * 2) + 1;
        }
        if(gameDiffic == 4){
            aiplayerspeed = (Math.random() * 3)+ 1;
        }
        if(gameDiffic == 5){
            aiplayerspeed = (Math.random() * 3)+ 2;
        }
        if(gameDiffic == 6){
            aiplayerspeed = (Math.random() * 4) + 2;
        }
        if(gameDiffic == 7){
            aiplayerspeed = (Math.random() * 4) + 2;
        }
        if(gameDiffic == 8){
            aiplayerspeed = (Math.random() * 5) + 2;
        }
        if(gameDiffic == 9){
            aiplayerspeed = (Math.random() * 5) + 2;
        }
        if(gameDiffic == 10){
            aiplayerspeed = (Math.random() * 6) + 2;
        }
    }
    
    //* Sets the speed for the linemen *//
    public void setLineSpeed(){
        
    
        if(gameDiffic == 0){
            aiplayerspeed = 4;
            
        }
        if(gameDiffic == 1){
            aiplayerspeed = 4;
        }
        if(gameDiffic == 2){
            aiplayerspeed = 4;
        }
        if(gameDiffic == 3){
            aiplayerspeed = 3;
        }
        if(gameDiffic == 4){
            aiplayerspeed = 3;
        }
        if(gameDiffic == 5){
            aiplayerspeed = 3;
        }
        if(gameDiffic == 6){
            aiplayerspeed = 2;
        }
        if(gameDiffic == 7){
            aiplayerspeed = 2;
        }
        if(gameDiffic == 8){
            aiplayerspeed = 2;
        }
        if(gameDiffic == 9){
            aiplayerspeed = 2;
        }
        if(gameDiffic == 10){
            aiplayerspeed = 1;
        }
    }
    
    public int getAIX(){
        return (int)aiplayerx;
    }
    
    public void setAIX(int a){
        aiplayerx = a;
    }
    
    public void setAIY(int a){
        aiplayery = a;
    }
    
    public int getAIY(){
        return (int)aiplayery;
    }
    
    public int getAICol(){
        if (collisionmodifier == 0){
        if (((Math.abs(aiplayerXdir) + xdif) <= 45) && ((Math.abs(aiplayerYdir)) <= 40)) {aiplayercollision = 1;}
        return aiplayercollision;
        }
        else {return 0;}
    }
    
//    public void setAICol (){
//        aiplayercollision = 0;
//    }
    
    public void setColMod (int a){
        collisionmodifier = a;
    }
    
    public void setAIColX(int a){
        xcollide = a;
    }
    
    public void setAIColY(int a){
        ycollide = a;
    }
    
    //public void setlineXflag(int a){
       // linexflag = a;
  //  }
  //  public void setlineYflag(int a){
   //     lineyflag = a;
  //  }
}


