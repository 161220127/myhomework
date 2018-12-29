package Calabash;

import static org.junit.Assert.*;

import org.junit.Test;
import Creature.*;
import battlefield.*;
import javafx.scene.image.Image;

public class AppTest 
{
   private Battlefield field;

   @Test
   public void testPut(){
       field = new Battlefield();
       field.Put(0, 0, new Grandpa(field));
       field.Put(0,1, new Snake(field));
       assertTrue(field.getcreature(0,0).type == Creature.Type.GRANNDPA);
       assertTrue(field.getcreature(0,1).type == Creature.Type.SNAKE);
   }

    @Test
    public void testRemove(){
        field = new Battlefield();
        field.Put(0, 0, new Grandpa(field));
        assertTrue(field.getcreature(0,0).type == Creature.Type.GRANNDPA);
        field.Remove(0,0);
        assertTrue(field.getcreature(0,0).type == Creature.Type.NONEXISTENET);
    }


    @Test
    public void testjudgefinished(){
        field = new Battlefield();
        field.Put(0,0, new Grandpa(field));
        field.Put(0,1, new Snake(field));
        field.getcreature(0,0).isalive = false;
        assertTrue(field.judgefinished() == -1);

        field = new Battlefield();
        field.Put(0,0, new Grandpa(field));
        field.Put(0,1, new Snake(field));
        field.getcreature(0,1).isalive = false;
        assertTrue(field.judgefinished() == 1);

        field = new Battlefield();
        field.Put(0,0, new Grandpa(field));
        field.Put(0,1, new Snake(field));

        assertTrue(field.judgefinished() == 0);
    }

    @Test
    public void testchoose(){
        Trooparrays  trooparray = new Trooparrays();
        Trooparrays.Troop temp;
        temp = trooparray.choose(0);
        assertTrue(temp.x[0] == 0 && temp.y[0] == 0);
        assertTrue(temp.x[1] == 1 && temp.y[1] == -1);
        assertTrue(temp.x[2] == 1 && temp.y[2] == 1);
        assertTrue(temp.x[3] == 2 && temp.y[3] == -2);
        assertTrue(temp.x[4] == 2 && temp.y[4] == 2);
        assertTrue(temp.x[5] == 3 && temp.y[5] == -1);
        assertTrue(temp.x[6] == 3 && temp.y[6] == 1);
        assertTrue(temp.x[7] == 4 && temp.y[7] == 0);

    }



    @Test
    public void testCreature(){
       field = new Battlefield();
       Grandpa p = new Grandpa(field);
       Snake s = new Snake(field);
       Scorpion c = new Scorpion(field);
       Minions m = new Minions(field);
       assertTrue(p.bf == field);
       assertTrue(s.bf == field);
       assertTrue(c.bf == field);
       assertTrue(m.bf == field);
        Calabashbrothers.Color[] values = Calabashbrothers.Color.values();
        Calabashbrothers.Rank[] ranks = Calabashbrothers.Rank.values();
        Calabashbrothers brother = new Calabashbrothers(values[0],ranks[0], field);
        assertTrue(brother.bf == field);

    }
    @Test
    public void testBlock(){
       Block temp = new Block(0,0);
       assertTrue(temp.x == 0 && temp.y == 0);
    }


    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
