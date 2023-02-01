package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;

class GhostTest {
    @Test 
    public void constructor() {
        Ghost ambusher=new Ghost(222,222,"ambusher");
        assertNotNull(ambusher);
    }
    @Test
    public void GotoTarget() {
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
        manager.readconfig();
		manager.getWakaAndGhostposition();
		manager.addWallandCollectionandCountemptyline();
        App app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        Ghost ambusher=new Ghost(176,240,"ambusher");
        ambusher.targetx=408;
        ambusher.targety=56;
        ambusher.draw(app,manager,manager.speed);
        ambusher.GotoTarget(manager.speed);
        assertTrue(ambusher.positionx==176);
        assertTrue(ambusher.positiony==240);
    }
    @Test
    public void scatter(){
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
        manager.readconfig();
		manager.getWakaAndGhostposition();
		manager.addWallandCollectionandCountemptyline();
        App app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        Waka waka=new Waka(manager.Wakapositionx,manager.Wakapositiony);
        Ghost ambusher=new Ghost(176,240,"ambusher");
        ambusher.targetx=408;
        ambusher.targety=56;
        ambusher.scatter(manager.speed,manager.emptytop,manager.emptybottom);
    }
    @Test
    public void chase(){
        GameManager manager=new GameManager(null,1,1,null);
        manager.readconfig();
        manager.readconfig();
		manager.getWakaAndGhostposition();
		manager.addWallandCollectionandCountemptyline();
        App app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        Waka waka=new Waka(manager.Wakapositionx,manager.Wakapositiony);
        Ghost ambusher=new Ghost(176,240,"ambusher");
        Ghost ignorant=new Ghost(192,240,"ignorant");
        Ghost whim=new Ghost(160,240,"whim");
        Ghost chaser=new Ghost(192,240,"chaser");
        ambusher.draw(app,manager,manager.speed);
        ignorant.draw(app,manager,manager.speed);
        whim.draw(app,manager,manager.speed);
        chaser.draw(app,manager,manager.speed);
        ambusher.chase(waka,manager.speed,manager.emptytop,manager.emptybottom,manager.Allghost);
        whim.chase(waka,manager.speed,manager.emptytop,manager.emptybottom,manager.Allghost);
        chaser.chase(waka,manager.speed,manager.emptytop,manager.emptybottom,manager.Allghost);


    }
}