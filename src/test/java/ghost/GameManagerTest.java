package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;

class GameManagerTest {
    @Test 
    public void constructorAndreadconfig() {
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
        assertNotNull(manager);
        assertTrue(manager.speed==2);
        assertTrue(manager.lives==3);
        assertTrue(manager.filename.equals("map.txt"));
        assertNotNull(manager.modelengths);
        assertTrue(manager.frightenedLength==7);
        assertTrue(manager.invisibileLength==7);
        assertTrue(manager.originallives==3);
    }
    @Test
    public void win(){
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
        manager.lives=0;
        manager.live(null,null);
        assertTrue(manager.lose==true);
    }
    @Test void lose(){
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
        for(Collection collection:manager.Allcollection){
            collection.contacted=true;
        }
        manager.collection(null,manager.Allcollection);
        assertTrue(manager.win==true);
    }
}