package battlefield;

import Creature.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Battlefield {
    public final int lenx = 8;
    public final int leny = 12;

    private Block[][]blocks;
    private Creature[][]creatures;
    public int[][]timecount;

    private Boolean display;
    private Boolean runthreads;


    private String Savedhistory;

    private int Cchoose;
    private int Mchoose;
    public Battlefield(){
        blocks = new Block[lenx][leny];
        creatures = new Creature[lenx][leny];
        timecount = new int[lenx][leny];
        display = false;
        runthreads = true;
        Savedhistory = "";
        for(int i = 0; i < lenx; i++){
            for(int j = 0; j < leny; j ++) {
                blocks[i][j] = new Block(i, j);
                creatures[i][j] = new Nonexistent(this);
                creatures[i][j].block = blocks[i][j];
                timecount[i][j] = 0;

            }
        }
        Cchoose = 7;
        Mchoose = 0;
    }

    public <Template extends Creature> void Put(int x, int y, Template c){
        blocks[x][y].creature = c;
        creatures[x][y] = c;
        creatures[x][y].block = blocks[x][y];
    }

    public <Template extends Creature> void Remove(int x, int y){
        Creature temp = new Nonexistent(this);
        temp.block = blocks[x][y];
        creatures[x][y] = temp;
        blocks[x][y].creature = temp;
    }

    public void setDisplay(Boolean temp){
        this.display = temp;
    }
    public Boolean getDisplay(){
        return this.display;

    }
    public Boolean getRunthreads(){
        return this.runthreads;
    }
    public void setRunthreads(Boolean temp){
        this.runthreads = temp;
    }

    public Creature getcreature(int i, int j){
        return creatures[i][j];
    }

    public void setMchoose(int temp){
        Mchoose = temp;
    }

    public void setCchoose(int temp){
        Cchoose = temp;
    }
    public void init(){
        for(int i = 0; i < lenx; i++){
            for(int j = 0; j < leny; j ++) {
                blocks[i][j] = new Block(i, j);
                creatures[i][j] = new Nonexistent(this);
                creatures[i][j].block = blocks[i][j];
                timecount[i][j] = 0;

            }
        }
        Trooparrays  trooparray = new Trooparrays();
        Trooparrays.Troop temp = trooparray.choose(Cchoose);
        int startrow = 0;
        int startcol = 0;
        if(temp.length == 5) {
            startrow = 1;
            startcol = 3;
        }
        Calabashbrothers.Color[] values = Calabashbrothers.Color.values();
        Calabashbrothers.Rank[] ranks = Calabashbrothers.Rank.values();
        for(int i = 0; i < 7; i ++){
            Calabashbrothers brother = new Calabashbrothers(values[i],ranks[i], this);
            Put(startrow + temp.x[i], startcol + temp.y[i], brother);
        }

        Grandpa grandpa = new Grandpa(this);
        Put(startrow + temp.x[7], startcol + temp.y[7], grandpa);

        temp = trooparray.choose(Mchoose);
        startcol = 10;
        startrow = 0;
        if(temp.length == 5) {
            startrow = 1;
            startcol = 8;
        }
        int count = 0;
        for(; count < 3; count ++){
            Minions m = new Minions(this);
            Put(startrow+temp.x[count], startcol + temp.y[count], m);
        }
        Scorpion scorpion = new Scorpion(this);
        Put(startrow+temp.x[count], startcol + temp.y[count], scorpion);
        count ++;
        Snake snake = new Snake(this);
        Put(startrow+temp.x[count], startcol + temp.y[count], snake);
        count ++;
        for(; count < 8; count ++){
            Minions m = new Minions(this);
            Put(startrow+temp.x[count], startcol + temp.y[count], m);
        }

    }

    public int judgefinished(){
        Boolean good = false;
        Boolean bad = false;
        for(int i = 0; i < lenx; i++){
            for(int j = 0; j < leny; j ++){
                if(getcreature(i,j).isalive && getcreature(i,j).side == 1)
                    good = true;
                if(getcreature(i,j).isalive && getcreature(i,j).side == -1)
                    bad = true;
            }
        }

        if(good && bad){
            return 0;
        }
        else if(good)
            return 1;
        else if(bad)
            return -1;
        else
            return 2;
    }

    public void start() {
        for(int i = 0; i < lenx; i++) {
            for (int j = 0; j < leny; j++) {
                if (creatures[i][j].type !=  Creature.Type.NONEXISTENET && creatures[i][j].isalive) {
                    Thread t = new Thread(creatures[i][j]);
                    creatures[i][j].thread = t;
                    t.start();
                }
            }
        }
    }

    public void SaveField(){
        Savedhistory += '#';
        for(int i = 0; i <  lenx; i++) {
            for (int j = 0; j < leny; j++) {
                switch (creatures[i][j].type) {
                    case NONEXISTENET: {
                        Savedhistory += "*";
                        continue;
                    }
                    case CALABASH: {
                        if (creatures[i][j].isalive) {
                            switch(creatures[i][j].rank) {
                                case 老大:Savedhistory += "a";break;
                                case 老二:Savedhistory += "b";break;
                                case 老三:Savedhistory += "c";break;
                                case 老四:Savedhistory += "d";break;
                                case 老五:Savedhistory += "e";break;
                                case 老六:Savedhistory += "f";break;
                                case 老七:Savedhistory += "g";break;
                            }

                        } else {

                            switch(creatures[i][j].rank) {
                                case 老大:Savedhistory += "A";break;
                                case 老二:Savedhistory += "B";break;
                                case 老三:Savedhistory += "C";break;
                                case 老四:Savedhistory += "D";break;
                                case 老五:Savedhistory += "E";break;
                                case 老六:Savedhistory += "F";break;
                                case 老七:Savedhistory += "G";break;
                            }
                        }
                        continue;
                    }
                    case GRANNDPA: {
                        if (creatures[i][j].isalive)
                            Savedhistory += "r";
                        else
                            Savedhistory += "R";
                        continue;
                    }
                    case MINIONS: {
                        if (creatures[i][j].isalive)
                            Savedhistory += "m";
                        else
                            Savedhistory += "M";
                        continue;
                    }
                    case SCORPION: {
                        if (creatures[i][j].isalive)
                            Savedhistory += "s";
                        else
                            Savedhistory += "S";
                        continue;
                    }
                    case SNAKE: {
                        if (creatures[i][j].isalive)
                            Savedhistory += "n";
                        else
                            Savedhistory += "N";
                        continue;
                    }
                    default: {
                        Savedhistory += "*";
                        continue;
                    }
                }
            }
        }
        Savedhistory += "#";

    }

    public Boolean readHistory(String filename, int scene){
        Reader reader;
        try{
            reader = new FileReader(filename);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        long len = (lenx * leny + 2) * scene;

        try {
            reader.skip(len);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Boolean flag = false;
        int count = -1;

        for(int i = 0; i < lenx; ++i) {
            for (int j = 0; j < leny; ++j) {
                blocks[i][j] = new Block(i, j);
                creatures[i][j] = new Nonexistent(this);
                creatures[i][j].block = blocks[i][j];
            }
        }

        while(true){
            try {
                char ch = (char)(reader.read());
                if(!flag && ch != '#')
                    return false;
                if(ch == '#' && !flag) {
                    flag = true;
                }
                else if(ch == '#' && flag)
                    break;
                else{
                    int x = count / leny;
                    int y = count % leny;

                    Calabashbrothers.Color[] values = Calabashbrothers.Color.values();
                    Calabashbrothers.Rank[] ranks = Calabashbrothers.Rank.values();
                    switch (ch){
                        case '*':Nonexistent t = new Nonexistent(this); Put(x, y, t);break;
                        case 'a':Calabashbrothers c1 = new Calabashbrothers(values[0],ranks[0], this); Put(x,y,c1);break;
                        case 'b':Calabashbrothers c2 = new Calabashbrothers(values[1],ranks[1], this); Put(x,y,c2);break;
                        case 'c':Calabashbrothers c3 = new Calabashbrothers(values[2],ranks[2], this); Put(x,y,c3);break;
                        case 'd':Calabashbrothers c4 = new Calabashbrothers(values[3],ranks[3], this); Put(x,y,c4);break;
                        case 'e':Calabashbrothers c5 = new Calabashbrothers(values[4],ranks[4], this); Put(x,y,c5);break;
                        case 'f':Calabashbrothers c6 = new Calabashbrothers(values[5],ranks[5], this); Put(x,y,c6);break;
                        case 'g':Calabashbrothers c7 = new Calabashbrothers(values[6],ranks[6], this); Put(x,y,c7);break;
                        case 'A':Calabashbrothers C1 = new Calabashbrothers(values[0],ranks[0], this); Put(x,y,C1);creatures[x][y].isalive = false;break;
                        case 'B':Calabashbrothers C2 = new Calabashbrothers(values[1],ranks[1], this); Put(x,y,C2);creatures[x][y].isalive = false;break;
                        case 'C':Calabashbrothers C3 = new Calabashbrothers(values[2],ranks[2], this); Put(x,y,C3);creatures[x][y].isalive = false;break;
                        case 'D':Calabashbrothers C4 = new Calabashbrothers(values[3],ranks[3], this); Put(x,y,C4);creatures[x][y].isalive = false;break;
                        case 'E':Calabashbrothers C5 = new Calabashbrothers(values[4],ranks[4], this); Put(x,y,C5);creatures[x][y].isalive = false;break;
                        case 'F':Calabashbrothers C6 = new Calabashbrothers(values[5],ranks[5], this); Put(x,y,C6);creatures[x][y].isalive = false;break;
                        case 'G':Calabashbrothers C7 = new Calabashbrothers(values[6],ranks[6], this); Put(x,y,C7);creatures[x][y].isalive = false;break;
                        case 'r':Grandpa g = new Grandpa(this); Put(x,y,g);break;
                        case 'R':Grandpa G = new Grandpa(this); Put(x,y,G);creatures[x][y].isalive = false;break;
                        case 'm':Minions m = new Minions(this); Put(x,y,m);break;
                        case 'M':Minions M = new Minions(this); Put(x,y,M);creatures[x][y].isalive = false;break;
                        case 's':Scorpion s= new Scorpion(this); Put(x,y,s);break;
                        case 'S':Scorpion S= new Scorpion(this); Put(x,y,S);creatures[x][y].isalive = false;break;
                        case 'n':Snake n = new Snake(this); Put(x,y,n);break;
                        case 'N':Snake N = new Snake(this); Put(x,y,N);creatures[x][y].isalive = false;break;
                        default:break;
                    }
                }
                count++;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getSavedhistory(){
        return Savedhistory;
    }


    public Boolean repeatHistory(int scene){

        int len = (lenx * leny + 2) * scene;

        Boolean flag = false;
        int count = -1;

        for(int i = 0; i < lenx; ++i) {
            for (int j = 0; j < leny; ++j) {
                blocks[i][j] = new Block(i, j);
                creatures[i][j] = new Nonexistent(this);
                creatures[i][j].block = blocks[i][j];
            }
        }

        while(true){

            char ch = Savedhistory.charAt(len+count+1);
            if(!flag && ch != '#')
                return false;
            if(ch == '#' && !flag) {
                flag = true;
            }
            else if(ch == '#' && flag)
                break;
            else{
                int x = count / leny;
                int y = count % leny;

                Calabashbrothers.Color[] values = Calabashbrothers.Color.values();
                Calabashbrothers.Rank[] ranks = Calabashbrothers.Rank.values();
                switch (ch){
                    case '*':Nonexistent t = new Nonexistent(this); Put(x, y, t);break;
                    case 'a':Calabashbrothers c1 = new Calabashbrothers(values[0],ranks[0], this); Put(x,y,c1);break;
                    case 'b':Calabashbrothers c2 = new Calabashbrothers(values[1],ranks[1], this); Put(x,y,c2);break;
                    case 'c':Calabashbrothers c3 = new Calabashbrothers(values[2],ranks[2], this); Put(x,y,c3);break;
                    case 'd':Calabashbrothers c4 = new Calabashbrothers(values[3],ranks[3], this); Put(x,y,c4);break;
                    case 'e':Calabashbrothers c5 = new Calabashbrothers(values[4],ranks[4], this); Put(x,y,c5);break;
                    case 'f':Calabashbrothers c6 = new Calabashbrothers(values[5],ranks[5], this); Put(x,y,c6);break;
                    case 'g':Calabashbrothers c7 = new Calabashbrothers(values[6],ranks[6], this); Put(x,y,c7);break;
                    case 'A':Calabashbrothers C1 = new Calabashbrothers(values[0],ranks[0], this); Put(x,y,C1);creatures[x][y].isalive = false;break;
                    case 'B':Calabashbrothers C2 = new Calabashbrothers(values[1],ranks[1], this); Put(x,y,C2);creatures[x][y].isalive = false;break;
                    case 'C':Calabashbrothers C3 = new Calabashbrothers(values[2],ranks[2], this); Put(x,y,C3);creatures[x][y].isalive = false;break;
                    case 'D':Calabashbrothers C4 = new Calabashbrothers(values[3],ranks[3], this); Put(x,y,C4);creatures[x][y].isalive = false;break;
                    case 'E':Calabashbrothers C5 = new Calabashbrothers(values[4],ranks[4], this); Put(x,y,C5);creatures[x][y].isalive = false;break;
                    case 'F':Calabashbrothers C6 = new Calabashbrothers(values[5],ranks[5], this); Put(x,y,C6);creatures[x][y].isalive = false;break;
                    case 'G':Calabashbrothers C7 = new Calabashbrothers(values[6],ranks[6], this); Put(x,y,C7);creatures[x][y].isalive = false;break;
                    case 'r':Grandpa g = new Grandpa(this); Put(x,y,g);break;
                    case 'R':Grandpa G = new Grandpa(this); Put(x,y,G);creatures[x][y].isalive = false;break;
                    case 'm':Minions m = new Minions(this); Put(x,y,m);break;
                    case 'M':Minions M = new Minions(this); Put(x,y,M);creatures[x][y].isalive = false;break;
                    case 's':Scorpion s= new Scorpion(this); Put(x,y,s);break;
                    case 'S':Scorpion S= new Scorpion(this); Put(x,y,S);creatures[x][y].isalive = false;break;
                    case 'n':Snake n = new Snake(this); Put(x,y,n);break;
                    case 'N':Snake N = new Snake(this); Put(x,y,N);creatures[x][y].isalive = false;break;
                    default:break;
                }
            }
            count++;

        }
        return true;
    }

    public void setSavedhistory(String temp){
        this.Savedhistory = temp;
    }
}


