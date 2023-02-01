package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import org.json.simple.*;
import java.util.ArrayList;



public class App extends PApplet {

    public static final int WIDTH = 448;
	public static final int HEIGHT = 576;
	public int checkmode=0;
	//public int modeindex=0;
	public int invisiablecount=0;
	private GameManager manager;
	private Waka waka;
	private PImage ambusher;
	private PImage chaser;
	private PImage ignorant;
	private PImage whim;
	private ArrayList<Ghost> Ghosts;
	Boolean debugline=false;

    public App() {
        //Set up your objects
		// manager
		this.manager=new GameManager(null,1,1,null);
		this.manager.readconfig();
		this.manager.getWakaAndGhostposition();
		this.manager.addWallandCollectionandCountemptyline();
		//waka
		this.waka=new Waka(this.manager.Wakapositionx,this.manager.Wakapositiony);
		//Ghost
		this.Ghosts=this.manager.Allghost;
		for(Ghost ghost:this.Ghosts){
			ghost.originalpositionx=ghost.positionx;
			ghost.originalpositiony=ghost.positiony;
		}
	}
    public void setup() {
        frameRate(60);
        // Load images
		// manager
		this.manager.downLeft=this.loadImage("src/main/resources/downLeft.png");
		this.manager.downRight=this.loadImage("src/main/resources/downRight.png");
		this.manager.horizontal=this.loadImage("src/main/resources/horizontal.png");
		this.manager.vertical=this.loadImage("src/main/resources/vertical.png");
		this.manager.upRight=this.loadImage("src/main/resources/upRight.png");
		this.manager.upLeft=this.loadImage("src/main/resources/upLeft.png");
		this.manager.fruit=this.loadImage("src/main/resources/fruit.png");
		this.manager.superfruit=this.loadImage("src/main/resources/superfruit.png");
		this.manager.soda=this.loadImage("src/main/resources/soda.png");
		// Waka
		this.waka.playerClosed=this.loadImage("src/main/resources/playerClosed.png");
		this.waka.playerUp=this.loadImage("src/main/resources/playerUp.png");
		this.waka.playerDown=this.loadImage("src/main/resources/playerDown.png");
		this.waka.playerLeft=this.loadImage("src/main/resources/playerLeft.png");
		this.waka.playerRight=this.loadImage("src/main/resources/playerRight.png");
		// Ghost
		for (Ghost ghost:Ghosts){
		ghost.ambusher=this.loadImage("src/main/resources/ambusher.png");
		ghost.chaser=this.loadImage("src/main/resources/chaser.png");
		ghost.ignorant=this.loadImage("src/main/resources/ignorant.png");
		ghost.whim=this.loadImage("src/main/resources/whim.png");
		ghost.frightened=this.loadImage("src/main/resources/frightened.png");
		ghost.invisiable=this.loadImage("src/main/resources/invisiable.png");

		}
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void draw() { 
		background(0, 0, 0);
		// Main loop
		if(this.manager.win==false && this.manager.lose==false){
			this.manager.draw(this);
			this.manager.collection(this,this.manager.Allcollection);
			this.manager.live(this,this.waka.playerRight);
			this.waka.contact(this.manager.Allcollection,this.Ghosts,this.manager);
			if(frameCount%16<8){
				this.waka.drawclose(this);
			}else{
			this.waka.draw(this,this.manager.speed);
			}
			this.waka.checkCollision(this.manager.Allwall,this.waka.checkmode);
			this.waka.keyPressed(this);
			for(Ghost ghost:this.Ghosts){
				ghost.draw(this,this.manager,this.manager.speed);
				ghost.frightened(this,this.manager);
				ghost.checkWallcollision(this.manager.Allwall);
				ghost.checkWakaCollision(this.waka,this.manager,this.Ghosts);
				// scatter mode
				this.manager.scatter(this,ghost);
				this.manager.modechange(this);
				// chase mode
				this.manager.chase(this,this.waka,ghost,this.Ghosts);
				this.manager.modechange(this);
				// invisible mode
				this.manager.invisiable(this);
			}
			// frightened mode
			this.manager.frightened(this,this.Ghosts);
		}
		//debug line
		this.manager.WinandLose(this,this.waka,this.Ghosts);
		if(this.debugline==true){
			this.manager.drawline(this,this.Ghosts);
		}
    }
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode==32){
			if(this.debugline==false){
			this.debugline=true;
			}else{
				this.debugline=false;
			}
		}
	}

    public static void main(String[] args) {
		PApplet.main("ghost.App");
    }

}
