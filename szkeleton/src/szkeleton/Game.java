package szkeleton;

import java.awt.*;
import java.util.ArrayList;

public class Game implements Runnable {
	// telepesek
    private ArrayList<Settler> settlers;
    // robotok
    private ArrayList<Robot> robots;
	private ArrayList<Ufo> ufos;
    // térkép
    private Map map;
    // játék neve
    private String name;
    //private Prototype proto;

	private MainFrame frame;
	private boolean isTerminated = false;
	private boolean canMoveToNext = false;
	private Settler currentSettler = null;

    // konstruktor
    public  Game(MainFrame mf) {
    	frame = mf;
    	settlers = new ArrayList<Settler>();
    	robots = new ArrayList<Robot>();
    	ufos = new ArrayList<>();
    }
    //getter for settlers
    public ArrayList<Settler> getSettlers(){
    	return this.settlers;
	}
  	//getter for robots
	public ArrayList<Robot> getRobots(){
    	return this.robots;
	}
	//getter for ufos
	public ArrayList<Ufo> getUfos() {
		return ufos;
	}
	//setter for ufos
	public void setUfos(ArrayList<Ufo> ufos) {
		this.ufos = ufos;
	}
	//getter for name
	public String getName(){ return this.name; }
	//setter for settlers
	public void setSettlers(ArrayList<Settler> list){
    	this.settlers = list;
	}
	//setter for name
	public void setName(String name) {
		this.name = name;
	}
	//setter for map
	public void setMap(Map map) {
		this.map = map;
	}
	//setter for robots
	public void setRobots(ArrayList<Robot> robots) {
		this.robots = robots;
	}

	// játék megnyerése
    public void Win() {
        isTerminated = true;
    	System.out.println(name + ".Win()");
    }
    // játék elvesztése
    public void Lose() {
        isTerminated = true;
    	System.out.println(name + ".Lose()");
    }
    // új játék kezdése
    public void NewGame() {
        // pálya létrehozása
        Map map = new Map("map", this, 2);

        // pálya összekötöttségek létrehozása
    	map.Connect();

    	//Most ket jatekos letrehozasa random kezdőhelyen
    	Place p1 = map.GetRandomPlace();
    	Place p2 = map.GetRandomPlace();
    	Settler s1 = new Settler("s1",this,p1, frame.getView().getSettlerView());
    	Settler s2 = new Settler("s2",this,p2, frame.getView().getSettlerView());
    	settlers.add(s1);
    	settlers.add(s2);
        

    }
    // robot hozzáadása a játékhoz
    public void AddRobot(Robot robot) {
    	robots.add(robot);
    }

    public void addUfo(Ufo u) {
    	ufos.add(u);
	}
    // játékos meghalt
    public void SettlerDied(Settler settler) {
    	settlers.remove(settler);

    	// játék elvesztése ha az utolsó telepes is meghalt
		if (settlers.size() == 0) {
			this.Lose();
		}
    }

    // robot meghalt
    public void RobotDied(Robot robot) {
    	robots.remove(robot);
    }

    // ufo meghalt
	public void UfoDied(Ufo ufo){
    	ufos.remove(ufo);
	}

	// térkép lekérése
    public Map GetMap(){
		return map;
    }

    // egy kör végrehajtása konzolos felületen
    public void OneRound() {
    	// először a telepesek lépnek
    	for(int i =0; i < settlers.size(); i++) {
    		settlers.get(i).Step();
    	}
		// aztán a robotok lépnek
    	for(int i =0; i < robots.size(); i++) {
    		robots.get(i).Step();
    	}
    	// végül a térkép is lép
    	map.Step();
    }

    // telepes hozzáadása a játékhoz
    public void AddSettler(Settler s) {
        settlers.add(s);
    }

	@Override
	synchronized public void run() {
		while(!isTerminated){
			for (Settler s : settlers){
				currentSettler = s;
				while (!canMoveToNext) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						break;
					}
				}
				canMoveToNext = false;
			}
			for (Robot r : robots){
				r.Step();
			}
			for (Ufo u : ufos){
				u.Step();
			}
			map.Step();
		}
	}

	// ezt kell meghívnia amikor egy settler befejezte a körét!!!
	public void finishedTurn(){
		canMoveToNext = true;
	}

	public Settler getCurrentSettler(){return currentSettler;}
	public MainFrame getFrame(){return frame;}
}
