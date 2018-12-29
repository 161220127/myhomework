package Creature;

import javafx.scene.image.Image;
import battlefield.Battlefield;

public class Nonexistent extends Creature{
    public Nonexistent(Battlefield temp){
        bf = temp;
        isalive = false;
        side = 0;
        type = Type.NONEXISTENET;

        dead = new Image(this.getClass().getResourceAsStream("/None.png"));
    }
}