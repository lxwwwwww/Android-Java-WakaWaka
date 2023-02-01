package ghost;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;


public class Ghost{
    int positionx;
    int positiony;
    int originalpositionx;
    int originalpositiony;
    int targetx;
    int targety;
    int turnup=0;
    int turndown=0;
    int turnleft=0;
    int turnright=0;
    int stuckcorner=0;
    String type;
    String direction="A";
    PImage ambusher;
    PImage chaser;
    PImage ignorant;
    PImage whim;
    PImage frightened;
    PImage invisiable;
    Boolean collisionup=true;
	Boolean collisiondown=true;
	Boolean collisionleft=true;
    Boolean collisionright=true;
    Boolean collisiontop=false;
    Boolean collisionbottom=false;
    Boolean move=true;
    Boolean Frightened=false;
    Boolean die=false;

    public Ghost(int positionx,int positiony,String type){
        this.positionx=positionx;
        this.positiony=positiony;
        this.type=type;
    }

    public void draw(PApplet app,GameManager manager,long speed){
        if(this.die==false){
            if(this.type.equals("ambusher")){
                if(this.ambusher==null){
                    return;
                }
                if(manager.Soda==true){
                    app.image(this.invisiable,this.positionx,this.positiony);
                }else{
                if(this.Frightened==false){
                app.image(this.ambusher,this.positionx,this.positiony);
                }
                }
                this.move(speed);
            }
            if(this.type.equals("chaser")){
                if(this.ambusher==null){
                    return;
                }
                if(manager.Soda==true){
                    app.image(this.invisiable,this.positionx,this.positiony);
                }else{
                app.image(this.chaser,this.positionx,this.positiony);
                }
                this.move(speed);
            }
            if(this.type.equals("ignorant")){
                if(this.ambusher==null){
                    return;
                }
                if(manager.Soda==true){
                    app.image(this.invisiable,this.positionx,this.positiony);
                }else{
                app.image(this.ignorant,this.positionx,this.positiony);
                }
                this.move(speed);
            }
            if(this.type.equals("whim")){
                if(this.ambusher==null){
                    return;
                }
                if(manager.Soda==true){
                    app.image(this.invisiable,this.positionx,this.positiony);
                }else{
                app.image(this.whim,this.positionx,this.positiony);
                }
                this.move(speed);
            }
        }
    }
    public void scatter(long speed,int emptytop,int emptybottom){
        if(this.type.equals("ambusher") && this.move==true){
            this.targetx=408;
            this.targety=(emptytop+1)*16-8;
            this.GotoTarget(speed);
        }
        if(this.type.equals("chaser") && this.move==true){
            this.targetx=12;
            this.targety=(emptytop+1)*16-8;
            this.GotoTarget(speed);
        }
        if(this.type.equals("ignorant") && this.move==true){
            this.targetx=12;
            this.targety=578-16*(emptybottom+2)-8;
            this.GotoTarget(speed);
        }
        if(this.type.equals("whim") && this.move==true){
            this.targetx=408;
            this.targety=578-16*(emptybottom+2)-8;
            this.GotoTarget(speed);
        }
    }

    public void chase(Waka waka,long speed,int emptytop,int emptybottom,ArrayList<Ghost> Ghosts){
        this.targetx=0;
        this.targety=0;
        if(this.type.equals("ambusher")){
            if(waka.direction.equals("Up")){
                this.targetx=waka.positionx;
                this.targety=waka.positiony-64;
            }
            if(waka.direction.equals("Down")){
                this.targetx=waka.positionx;
                this.targety=waka.positiony+64;
            }
            if(waka.direction.equals("Left")){
                this.targetx=waka.positionx-64;
                this.targety=waka.positiony;
            }
            if(waka.direction.equals("Right")){
                this.targetx=waka.positionx+64;
                this.targety=waka.positiony;
            }
            this.GotoTarget(speed);
        }
        if(this.type.equals("chaser")){
            this.targetx=waka.positionx;
            this.targety=waka.positiony;
            this.GotoTarget(speed);
        }
        if(this.type.equals("ignorant")){
            int distancex=Math.abs(waka.positionx-this.positionx);
            int distancey=Math.abs(waka.positiony-this.positiony);
            double distance=Math.sqrt(distancex*distancex+distancey*distancey);
            if(distance>128){
                this.targetx=waka.positionx;
                this.targety=waka.positiony;
                this.GotoTarget(speed);
            }else{
                if(this.positionx>12){
                    this.positionx-=speed;
                }
                if(this.positiony<578-16*(emptybottom+2)-8){
                    this.positiony+=speed;
                }
            }
        }
        if(this.type.equals("whim")){
            for(Ghost chaser:Ghosts){
                if(chaser.type.equals("chaser")){                        
                    int gapx=Math.abs(waka.positionx-chaser.positionx);
                    int gapy=Math.abs(waka.positiony-chaser.positiony);
                    if(waka.direction.equals("Up")){           
                        if(waka.positionx>=chaser.positionx){
                            this.targetx=chaser.positionx+2*gapx;
                        }else{
                            this.targetx=chaser.positionx-2*gapx;
                        } 
                        if(waka.positiony>=chaser.positiony){
                            this.targety=chaser.positiony+2*(gapy+32);
                        }else{
                            this.targety=chaser.positiony-2*(gapy-32);
                        }
                        this.GotoTarget(speed);
                    }
                    if(waka.direction.equals("Down")){           
                        if(waka.positionx>=chaser.positionx){
                            this.targetx=chaser.positionx+2*gapx;
                        }else{
                            this.targetx=chaser.positionx-2*gapx;
                        } 
                        if(waka.positiony>=chaser.positiony){
                            this.targety=chaser.positiony+2*(gapy-32);
                        }else{
                            this.targety=chaser.positiony-2*(gapy+32);
                        }
                        this.GotoTarget(speed);
                    }
                    if(waka.direction.equals("Right")){           
                        if(waka.positionx>=chaser.positionx){
                            this.targetx=chaser.positionx+2*(gapx+32);
                        }else{
                            this.targetx=chaser.positionx-2*(gapx-32);
                        } 
                        if(waka.positiony>=chaser.positiony){
                            this.targety=chaser.positiony+2*gapy;
                        }else{
                            this.targety=chaser.positiony-2*gapy;
                        }
                        this.GotoTarget(speed);
                    }
                    if(waka.direction.equals("Left")){           
                        if(waka.positionx>=chaser.positionx){
                            this.targetx=chaser.positionx+2*(gapx-32);
                        }else{
                            this.targetx=chaser.positionx-2*(gapx+32);
                        } 
                        if(waka.positiony>=chaser.positiony){
                            this.targety=chaser.positiony+2*gapy;
                        }else{
                            this.targety=chaser.positiony-2*gapy;
                        }
                        this.GotoTarget(speed);
                    }

                }
            }
        }

    }
    public void GotoTarget(long speed){
        if(this.targetx>=this.positionx){
            if(this.collisionright==false && this.turnleft==0){
            this.direction="Right";
            }
            if(this.collisionup==true && this.collisionright==true){
                this.direction="Left";
                this.turnleft=1;
                if(Math.abs(this.positiony-this.targety)<12){
                    this.collisiontop=true;
                    this.direction="Down";
                    this.turnleft=0;
                }
            }
        }
        else if(this.targetx<this.positionx){
            if(this.collisionleft==false && this.turnright==0 && this.turnleft==0){
                this.direction="Left";
            }
            if(this.collisionup==true && this.collisionleft==true){
                this.direction="Right";
                this.turnright=1;
                if(Math.abs(this.positiony-this.targety)<12){
                    this.collisiontop=true;
                    this.direction="Down";
                    this.turnleft=0;
                    this.turnright=0;
                }
            }
            if(this.collisionup==false && this.collisionright==false && this.collisionleft==true && this.collisiondown==true){
                this.direction="Right";
                this.turnleft=1;
                this.turnup=1;
            }
        }
        if(this.targety<=this.positiony){
            if(this.collisionup==false && this.collisiontop==false){
                this.direction="Up";
                this.turnleft=0;
                this.turnright=0;
                }
            if(this.collisiontop==true && Math.abs(this.targetx-this.positionx)<10){
                this.direction="Up";
                this.collisiontop=false;
                }
        }
        else if(this.targety>this.positiony){
            if(this.collisiondown==false && this.collisiontop==false){
                this.direction="Down";
                this.turnleft=0;
                this.turnright=0;
                }
            if(this.collisiondown==true && Math.abs(this.targetx-this.positionx)<10){
                this.direction="Down";
                this.collisiontop=false;
                }
            if(this.positionx==400){
                this.positiony+=15;
            }
            
        }
        if(Math.abs(this.targetx-this.positionx)<8 && Math.abs(this.positiony-this.targety)<8){
            this.move=false;
        }
    }

    public void move(long speed){
        if(this.direction.equals("Up")){
            this.positiony-=speed;
        }
        if(this.direction.equals("Down")){
            this.positiony+=speed;
        }
        if(this.direction.equals("Left")){
            this.positionx-=speed;
        }
        if(this.direction.equals("Right")){
            this.positionx+=speed;
        }
    }

    public void checkWallcollision(ArrayList<Wall> Allwall){
        this.collisionup=false;
        this.collisiondown=false;
        this.collisionleft=false;
        this.collisionright=false;
        for(Wall wall:Allwall){
            int gapx=Math.abs(this.positionx-wall.positionx);
            int gapy=Math.abs(this.positiony-wall.positiony);
            if(gapx<18 && gapy<14){
				if(this.positionx>wall.positionx){
				this.positionx=wall.positionx+16;
                this.collisionleft=true;
				}
				if(this.positionx<wall.positionx){
				this.positionx=wall.positionx-16;
                this.collisionright=true;
				}
			}
			if(gapy<17 && gapx<10 ){
				if(this.positiony>wall.positiony){
					this.positiony=wall.positiony+16;
                    this.collisionup=true;
					}
				if(this.positiony<wall.positiony){
					this.positiony=wall.positiony-16;
                    this.collisiondown=true;
					}
			}
        }
    }

    public void checkWakaCollision(Waka waka,GameManager manager,ArrayList<Ghost> Ghosts){
        int gapx=Math.abs(this.positionx-waka.positionx);
        int gapy=Math.abs(this.positiony-waka.positiony);
        if(gapx<8 && gapy<8){
            if(this.Frightened==false && this.die==false){
                manager.lives-=1;
                waka.positionx=manager.Wakapositionx;
                waka.positiony=manager.Wakapositiony;
            for(Ghost ghost:Ghosts){
                ghost.positionx=ghost.originalpositionx;
                ghost.positiony=ghost.originalpositiony;
            }
            }else{
                this.die=true;;
            }

        }
    }

	public void frightened(PApplet app,GameManager manager){
        if(manager.Frightened==true && this.die==false){
            app.image(this.frightened,this.positionx,this.positiony);
            this.GotoTarget(manager.speed);
		}
    }
}