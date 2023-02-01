package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import processing.event.KeyEvent;

public class Waka{
	int positionx;
	int positiony;
	PImage playerClosed;
	PImage playerUp;
	PImage playerDown;
	PImage playerLeft;
	PImage playerRight;
	String direction="Left";
	int checkmode=0;
	Boolean move=true;
	Boolean collisionup=true;
	Boolean collisiondown=true;
	Boolean collisionleft=true;
	Boolean collisionright=true;
	public Waka(int positionx,int positiony){
		this.positionx=positionx;
		this.positiony=positiony;
	}

	public void draw(PApplet app,long speed){
		if(direction.equals("Left")){
			if(this.move==true){
				app.image(this.playerLeft,this.positionx,this.positiony-5);
				this.positionx-=speed;
			}else{
				app.image(this.playerLeft,this.positionx,this.positiony-5);
			}
		}
		else if(direction.equals("Right")){
			if(this.move==true){
				app.image(this.playerRight,this.positionx,this.positiony-5);
				this.positionx+=speed;
			}else{
				app.image(this.playerRight,this.positionx,this.positiony-5);
			}
		}
		else if(direction.equals("Up")){
			if(this.move==true){
				app.image(this.playerUp,this.positionx-5,this.positiony-5);
				this.positiony-=speed;
			}else{
				app.image(this.playerUp,this.positionx-5,this.positiony-5);
			}
		}
		else if(direction.equals("Down")){
			if(this.move==true){
				app.image(this.playerDown,this.positionx-5,this.positiony-5);
				this.positiony+=speed;
			}else{
				app.image(this.playerDown,this.positionx-5,this.positiony-5);
			}
		}
	}

	public void drawclose(PApplet app){
		if(direction.equals("Up")||direction.equals("Down")){
			app.image(this.playerClosed,this.positionx-5,this.positiony-5);
		}
		else{
			app.image(this.playerClosed,this.positionx,this.positiony-5);
	
		}
	} 

	public void keyPressed(PApplet app) {
		if (app.keyCode==38){
		this.checkmode=1;
		}
		if (app.keyCode==40){
		this.checkmode=2;
		}
		if (app.keyCode==37){
		this.checkmode=3;
		}
		if (app.keyCode==39){
		this.checkmode=4;
		}
	}

	public void checkCollision(ArrayList<Wall> Allwall,int direction){
		int z1=1;
		int z2=1;
		int z3=1;
		int z4=1;
		for(Wall wall:Allwall){
			int gapx=Math.abs(this.positionx-wall.positionx);
			int gapy=Math.abs(this.positiony-wall.positiony);
			this.collisionup=false;
			this.collisiondown=false;
			this.collisionleft=false;
			this.collisionright=false;
			if(gapx<18 && gapy<14){
				if(this.positionx>wall.positionx){
				this.positionx=wall.positionx+16;
				z1=0;
				this.collisionleft=true;
				}
				if(this.positionx<wall.positionx){
				this.positionx=wall.positionx-16;
				z2=0;
				this.collisionright=true;
				}
			}
			if(gapy<17 && gapx<10 ){
				if(this.positiony>wall.positiony){
					this.positiony=wall.positiony+16;
					this.collisionup=true;
					z3=0;
					}
				if(this.positiony<wall.positiony){
					this.positiony=wall.positiony-16;
					this.collisiondown=true;
					z4=0;
					}
			}
		}
		if(this.checkmode==0){
			return;
		}
		if(this.collisionup==false && direction==1 && z3==1){
		this.direction="Up";
		this.checkmode=0;
		}
		if(this.collisiondown==false && direction==2 && z4==1){
			this.direction="Down";
			this.checkmode=0;
		}
		if(this.collisionleft==false && direction==3 && z1==1){
			this.direction="Left";
			this.checkmode=0;
		}
		if(this.collisionright==false && direction==4 && z2==1){
			this.direction="Right";
			this.checkmode=0;
		}
	}
	
	public void contact(ArrayList<Collection> Allcollection,ArrayList<Ghost> Ghosts,GameManager manager){
		for(Collection collection:Allcollection){
			if(collection.contacted==false){
			int gapx=Math.abs(this.positionx-collection.positionx);
			int gapy=Math.abs(this.positiony-collection.positiony);
			if((gapx<1 && gapy<1) || (gapy<1 && gapx<10)){
				if(collection.type.equals("Normal")){
					collection.contacted=true;
				}
			}
			if((gapx<1 && gapy<10) || (gapy<1 && gapx<10)){
				if(collection.type.equals("Super")){
					for(Ghost ghost:Ghosts){
						ghost.Frightened=true;
					}
					manager.Frightened=true;
					collection.contacted=true;
				}
			}
			if((gapx<1 && gapy<10) || (gapy<1 && gapx<10)){
				if(collection.type.equals("Soda")){
					manager.Soda=true;
					collection.contacted=true;
			}
			}
		}
	}
}
}