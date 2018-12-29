package Creature;

import javafx.scene.image.Image;
import battlefield.Battlefield;

public class Minions extends Creature{
    public Minions(Battlefield temp){
        bf = temp;
        isalive = true;
        side = -1;
        type = Type.MINIONS;
        alive = new Image(this.getClass().getResourceAsStream("/Minions.png"));
        dead = new Image(this.getClass().getResourceAsStream("/badDead.png"));
    }
}