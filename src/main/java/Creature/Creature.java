package Creature;

import battlefield.*;
import javafx.scene.image.Image;

public class Creature  implements Runnable{
    public enum Type {CALABASH, GRANNDPA, SCORPION, SNAKE, MINIONS, NONEXISTENET}
    public Block block;
    public Battlefield bf;
    public Image alive;
    public Image dead;
    public Boolean isalive;
    public Thread thread = null;
    public int side;
    public Type type;
    public enum Rank{老大, 老二, 老三, 老四, 老五, 老六, 老七}
    public Rank rank;

    public Image getNowimage(){
        if(this.isalive)
            return this.alive;
        else
            return this.dead;
    }

    public void run(){
        while(bf.getRunthreads()&&this.isalive) {
            int min = 100;
            int targetx = -1;
            int targety = -1;
            for (int i = 0; i < bf.lenx; i++) {
                for (int j = 0; j < bf.leny; j++) {
                    if (this.side == -(bf.getcreature(i, j).side) && bf.getcreature(i, j).isalive) {
                        int len = Math.abs(block.x - i) + Math.abs(block.y - j);
                        if (min > len) {
                            min = len;
                            targetx = i;
                            targety = j;
                        }
                    }
                }
            }
            while (bf.getDisplay()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread.interrupt();
                    break;
                }
            }
            synchronized (this) {
                if (min == 1) {
                    if (Math.random() > 0.5) {
                        this.isalive = false;
                        //System.out.print(this.type +"died\n");
                        thread.interrupt();
                        break;
                    } else {
                        bf.getcreature(targetx, targety).isalive = false;
                        //System.out.print(bf.getcreature(targetx, targety).type +"died\n");
                        bf.getcreature(targetx, targety).thread.interrupt();
                    }

                } else if (targetx > this.block.x) {
                    if ((this.block.x + 1 >= bf.lenx) && bf.getcreature(this.block.x + 1, this.block.y).type == Type.NONEXISTENET) {
                        bf.Remove(this.block.x, this.block.y);
                        bf.Put(this.block.x + 1, this.block.y, this);
                        //System.out.print(this.type+"x+1\n");
                    }
                } else if (targetx < this.block.x) {
                    if (this.block.x - 1 >= 0 && bf.getcreature(this.block.x - 1, this.block.y).type == Type.NONEXISTENET) {
                        bf.Remove(this.block.x, this.block.y);
                        bf.Put(this.block.x - 1, this.block.y, this);
                        //System.out.print(this.type+"x-1\n");
                    }
                } else if (targety > this.block.y) {
                    if ((this.block.y + 1 <= bf.leny) && bf.getcreature(this.block.x, this.block.y + 1).type == Type.NONEXISTENET) {
                        bf.Remove(this.block.x, this.block.y);
                        bf.Put(this.block.x, this.block.y + 1, this);
                        //System.out.print(this.type+"y+1\n");
                    }
                } else if (targety < this.block.y) {
                    if (this.block.y - 1 >= 0 && bf.getcreature(this.block.x, this.block.y - 1).type == Type.NONEXISTENET) {
                        bf.Remove(this.block.x, this.block.y);
                        bf.Put(this.block.x, this.block.y - 1, this);
                        //System.out.print(this.type+"y-1\n");
                    }
                }
                if (!this.isalive) {
                    //System.out.print(this.type +"died\n");
                    thread.interrupt();
                    break;
                }
                try {
                    Thread.sleep((int) (Math.random() * 500) + 500);
                } catch (Exception e) {
                    thread.interrupt();
                    break;
                }
            }
        }
    }
}
