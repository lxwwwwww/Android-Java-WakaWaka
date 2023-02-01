package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.Iterator;
import java.io.File; 
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;



public class GameManager{
	String filename;
	long lives;
	long speed;
	long originallives;
	long[] modelengths;
	long frightenedLength;
	long invisibileLength;
	int count;
	int modeindex=0;
	int Wakapositionx;
	int Wakapositiony;
	int emptytop=0;
	int emptybottom=0;
	int sodabuffer;
	PImage downLeft;
	PImage downRight;
	PImage horizontal;
	PImage vertical;
	PImage upRight;
	PImage upLeft;
	PImage fruit;
	PImage superfruit;
	PImage soda;
	PFont font;
	ArrayList<Wall> Allwall=new ArrayList<Wall>();
	ArrayList<Collection> Allcollection=new ArrayList<Collection>();
	ArrayList<Ghost> Allghost=new ArrayList<Ghost>();
	Boolean win=false;
	Boolean lose=false;
	Boolean Frightened=false;
	Boolean setrandom=false;
    Boolean scatter=false;
	Boolean chase=false;
	Boolean Soda=false;
	Boolean scattercount=false;
	Boolean invisiablecount=false;
	Boolean framecount=false;
	Boolean gamerestart=false;
	Boolean Modeindex=false;


	public GameManager(String filename,long lives,long speed,long[] modelengths){
	this.filename=filename;
	this.lives=lives;
	this.speed=speed;
	this.modelengths=modelengths;
	
	}

	public void readconfig(){
		JSONParser parser = new JSONParser();
		try{
		Object obj = parser.parse(new FileReader("config.json"));
		JSONObject config = (JSONObject) obj;
		JSONArray modelengths = (JSONArray) config.get("modeLengths");
		int arraySize = modelengths.size();
    	long[] Modelengths = new long[arraySize];
    	for(int i=0; i<arraySize; i++) {
			Modelengths[i] = (long) modelengths.get(i);
		}
		this.filename=String.valueOf(config.get("map"));
		this.lives=(long)(config.get("lives"));
		this.speed=(long)(config.get("speed"));
		this.modelengths=Modelengths;
		this.frightenedLength=(long) (config.get("frightenedLength"));
		this.invisibileLength=(long) (config.get("invisibileLength"));
		this.originallives=this.lives;
		}
		catch(Exception e){
		System.out.println("Config.json doesn't exist.");
		}
	}
	
	public void draw(PApplet app){
		int positiony=0;
		try{
		File mapfile=new File(this.filename);
        Scanner Reader = new Scanner(mapfile);
        while (Reader.hasNextLine()) {
			int positionx=0;
			String data = Reader.nextLine();
			int countempty=0;
			for (int i = 0; i < data.length(); i++){
    			char element = data.charAt(i); 
    			if(element=='1'){
					app.image(this.horizontal,positionx,positiony);
					
				}
    			else if(element=='2'){
					app.image(this.vertical,positionx,positiony);
					
				}
    			else if(element=='3'){
					app.image(this.upLeft,positionx,positiony);
					
				}
    			else if(element=='4'){
					app.image(this.upRight,positionx,positiony);
					
				}
    			else if(element=='5'){
					app.image(this.downLeft,positionx,positiony);
					
				}
    			else if(element=='6'){
					app.image(this.downRight,positionx,positiony);
				}
    			else if(element=='p'){
					this.Wakapositionx=positionx;
					this.Wakapositiony=positiony;
				}
 
				
				positionx+=16;
			}
			positiony+=16;
        }
        Reader.close();
		}
		catch(Exception e){
		System.out.println("The file of map doesn't exist.");
		}
	}

	public void live(PApplet app,PImage Wakaright){
		ArrayList<String> lives=new ArrayList<String>();
		int i=0;
		int startx=5;
		while (i<this.lives){
			lives.add("1");
			i+=1;
		}
		for(String one:lives){
			app.image(Wakaright,startx,545);
			startx+=30;
		}
		if(this.lives==0){
			this.lose=true;
		}
	}

	public void collection(PApplet app,ArrayList<Collection> Allcollection){
		int count=0;
		for(Collection collection:Allcollection){
			if(collection.contacted==false){
				if(collection.type.equals("Normal")){
					app.image(this.fruit,collection.positionx,collection.positiony);
				}
				else if(collection.type.equals("Super")){
					app.image(this.superfruit,collection.positionx,collection.positiony);
				}
				else if(collection.type.equals("Soda")){
					app.image(this.soda,collection.positionx,collection.positiony);
				}
			}
		}
		for(Collection collection:Allcollection){
			if(collection.contacted==true || collection.type.equals("Soda")){
				count++;
			}
		}
		if(count==Allcollection.size()){
			this.win=true;
		}
	}

	public void addWallandCollectionandCountemptyline(){
		int positiony=0;
		int collectionindex=0;
		try{
		File mapfile=new File(this.filename);
		Scanner Reader = new Scanner(mapfile);
        while (Reader.hasNextLine()) {
			int positionx=0;
			String data = Reader.nextLine();
			int countempty=0;
			for (String element: data.split("")){
				if(element.equals("0")){
					countempty+=1;
				}
			}
			if(countempty==data.length() && positiony<150){
				this.emptytop+=1;
			}
			else if(countempty==data.length() && positiony>150){
				this.emptybottom+=1;
			}
			for (int i = 0; i < data.length(); i++){
    			char element = data.charAt(i); 
    			if(element=='1'){
					this.Allwall.add(new Wall(positionx,positiony));
					
				}
    			else if(element=='2'){
					this.Allwall.add(new Wall(positionx,positiony));
					
				}
    			else if(element=='3'){
					this.Allwall.add(new Wall(positionx,positiony));
				}
    			else if(element=='4'){
					this.Allwall.add(new Wall(positionx,positiony));
					
				}
    			else if(element=='5'){
					this.Allwall.add(new Wall(positionx,positiony));
					
				}
    			else if(element=='6'){
					this.Allwall.add(new Wall(positionx,positiony));
				}
    			else if(element=='7'){
					this.Allcollection.add(new Collection(positionx,positiony,collectionindex,"Normal",false));
					collectionindex++;
				}
    			else if(element=='8'){
					this.Allcollection.add(new Collection(positionx,positiony,collectionindex,"Super",false));
					collectionindex++;
				}
				else if(element=='9'){
					this.Allcollection.add(new Collection(positionx,positiony,collectionindex,"Soda",false));
					collectionindex++;
				}
				positionx+=16;
			}
			positiony+=16;
        }
        Reader.close();
		}
		catch(Exception e){
		System.out.println("The file of map doesn't exist.");
		}
	}

	public void getWakaAndGhostposition(){
		int positiony=0;
		try{
		File mapfile=new File(this.filename);
        Scanner Reader = new Scanner(mapfile);
        while (Reader.hasNextLine()) {
			int positionx=0;
        	String data = Reader.nextLine();
			for (int i = 0; i < data.length(); i++){
    			char element = data.charAt(i); 
    			if(element=='p'){
					this.Wakapositionx=positionx;
					this.Wakapositiony=positiony;
				}
				if(element=='a'){
					this.Allghost.add(new Ghost(positionx,positiony,"ambusher"));
				}
				if(element=='c'){
					this.Allghost.add(new Ghost(positionx,positiony,"chaser"));
				}
				if(element=='i'){
					this.Allghost.add(new Ghost(positionx,positiony,"ignorant"));
				}
				if(element=='w'){
					this.Allghost.add(new Ghost(positionx,positiony,"whim"));
				}
				positionx+=16;
			}
			positiony+=16;
        }
        Reader.close();
		}
		catch(Exception e){
		System.out.println("The file of map doesn't exist.");
		}
	
	}

	public void scatter(PApplet app,Ghost ghost){
		if(app.frameCount<60*this.modelengths[this.modeindex] && (this.modeindex+1)%2!=0 && this.chase==false && this.Frightened==false){
			ghost.scatter(this.speed,this.emptytop,this.emptybottom);
			this.scatter=true;
			}else{
			if(this.scatter==true){
				this.scatter=false;
				if(this.Frightened==false){
				app.frameCount=0;
				this.Modeindex=true;
				}
			}
			}
	}

	public void chase(PApplet app,Waka waka,Ghost ghost,ArrayList<Ghost> Ghosts){
		if(app.frameCount<60*this.modelengths[this.modeindex] && (this.modeindex+1)%2==0 && this.scatter==false && this.Frightened==false){
			ghost.chase(waka,this.speed,this.emptytop,this.emptybottom,Ghosts);
			ghost.move=true;
			this.chase=true;
			}else{
				if(this.chase==true){
					this.chase=false;
					if(this.Frightened==false){
						app.frameCount=0;
						this.Modeindex=true;
					}
				}
			}
	}

	public void frightened(PApplet app,ArrayList<Ghost> Ghosts){
		if(this.Frightened==true){
			if(this.setrandom==false){
			for(Ghost ghost:Ghosts){
				ghost.targetx=ThreadLocalRandom.current().nextInt(12, 408);
				ghost.targety=ThreadLocalRandom.current().nextInt(this.emptytop*16,578-16*(this.emptybottom+2)-8);
				}
			}
			this.setrandom=true;
			if(this.scattercount==false){
			this.count=app.frameCount;
			}
			this.scattercount=true;
			if(this.framecount==false){
			app.frameCount=0;
			}
			this.framecount=true;
			if(app.frameCount>=this.frightenedLength*60){
				this.Frightened=false;
				this.scattercount=true;
				app.frameCount=this.count;
				for(Ghost ghost:Ghosts){
				ghost.Frightened=false;
				}
				this.framecount=false;
				this.setrandom=false;
			}
		}
	}

	public void invisiable(PApplet app){
		if(this.Soda==true){
			if(this.invisiablecount==false){
			this.sodabuffer=app.frameCount;
		}
		this.invisiablecount=true;
		if(app.frameCount-this.sodabuffer>this.invisibileLength*60 || this.Frightened==true){
			this.invisiablecount=false;
			this.Soda=false;
		}
		}
	}

	public void modechange(PApplet app){
		if(app.frameCount==0 && this.Modeindex==true){
			this.modeindex++;
			this.Modeindex=false;
		}
		if(this.modeindex>this.modelengths.length){
			this.modeindex=0;
		}
	}

	public void drawline(PApplet app,ArrayList<Ghost> Ghosts){
			for(Ghost ghost:Ghosts){
            	if(ghost.die==false){
            		app.stroke(255);
            		app.line(ghost.positionx,ghost.positiony,ghost.targetx,ghost.targety);
			   	}
			}
	}

	public void WinandLose(PApplet app,Waka waka,ArrayList<Ghost> Ghosts){
		if((this.win==true || this.lose==true)){
			if(this.gamerestart==false){
			app.frameCount=0;
			this.gamerestart=true;
			}
			if(this.win==true){
				this.font = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 32);
				app.textFont(font);
				app.text("YOU WIN", 110, 240);
				}
				if(this.lose==true){
				this.font = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 32);
				app.textFont(font);
				app.text("GAME OVER", 80, 240);
				}
			if(app.frameCount>=600){
				for(Collection collection:this.Allcollection){
					collection.contacted=false;
				}
				this.win=false;
				this.lose=false;
				this.lives=this.originallives;
				this.gamerestart=false;
				waka.positionx=this.Wakapositionx;
				waka.positiony=this.Wakapositiony;
				waka.direction="Left";
				this.modeindex=0;
				for(Ghost ghost:Ghosts){
					ghost.die=false;
				}
				app.frameCount=0;
			}
			}
	}

}

