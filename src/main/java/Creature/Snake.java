package Creature;

import javafx.scene.image.Image;
import battlefield.Battlefield;

public class Snake extends Creature {
    public Snake(Battlefield temp){
        bf = temp;
        isalive = true;
        side = -1;
        type = Type.SNAKE;
        alive = new Image(this.getClass().getResourceAsStream("/Snake.png"));
        dead = new Image(this.getClass().getResourceAsStream("/badDead.png"));
    }
}
