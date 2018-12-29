package Creature;

import javafx.scene.image.Image;
import battlefield.Battlefield;

public class Scorpion extends Creature{
    public Scorpion(Battlefield temp){
        bf = temp;
        isalive = true;
        side = -1;
        type = Type.SCORPION;
        alive = new Image(this.getClass().getResourceAsStream("/Scorpion.png"));
        dead = new Image(this.getClass().getResourceAsStream("/badDead.png"));
    }
}