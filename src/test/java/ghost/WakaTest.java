package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;

class WakaTest {
    @Test 
    public void constructor() {
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
        Waka waka=new Waka(manager.Wakapositionx,manager.Wakapositiony);
        assertNotNull(waka);
        assertTrue(waka.positionx<209);
        assertTrue(waka.positiony<330);
    }
    @Test
    public void intergration(){
        App app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.noLoop(); 
        app.setup();
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
		manager.getWakaAndGhostposition();
		manager.addWallandCollectionandCountemptyline();
        Waka waka=new Waka(manager.Wakapositionx,manager.Wakapositiony);
        waka.direction="Up";
        waka.checkCollision(manager.Allwall,waka.checkmode);
        waka.direction="Right";
        waka.checkCollision(manager.Allwall,waka.checkmode);
        waka.direction="Down";
        waka.checkCollision(manager.Allwall,waka.checkmode);
    }
}