package Creature;

import battlefield.Battlefield;
import javafx.scene.image.Image;

public class Calabashbrothers extends Creature {
    public enum Color{红色, 橙色, 黄色, 绿色, 青色, 蓝色, 紫色}

    private Color color;


    public Calabashbrothers(Color color, Rank rank, Battlefield bf){
        this.color = color;
        this.rank = rank;
        this.bf = bf;
        this.isalive = true;
        this.side = 1;
        this.type = Type.CALABASH;
        this.alive = new Image(this.getClass().getResourceAsStream("/Calabashboy.png"));
        this.dead = new Image(this.getClass().getResourceAsStream("/goodDead.png"));
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
                    if (Math.random() > 0.6) {
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

