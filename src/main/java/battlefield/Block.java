package battlefield;

import Creature.Creature;
public class Block {
    public int x;
    public int y;
    public Creature creature;
    public Block(int x, int y){
        this.x = x;
        this.y = y;
    }
}