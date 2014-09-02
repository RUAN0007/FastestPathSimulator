package Model;

import java.util.ArrayList;

public class FastPathModel {
	
	public enum Cell{OBSTACLE,EMPTY,PATH,ROBOT,DIRECTION};
	
	private CustomizedArena arenaMap;
	private Robot robot;
	private ArrayList<Action> fastestPath = new ArrayList<>();
	private int actionIndex;
	
	private int lowerLeftGoalRowID;
	private int lowerLeftGoalColID;
	private int lowerLeftStartRowID;
	private int lowerLeftStartColID;
	private Direction startDirection;
	private int stepCount = 0;
	private int turnCount = 0;
	
	
	
	
	public void computeFastestPath(){
		
	}
	
	public Cell[][] getCurrentStatus(){
		return null;
	}
	
	//return whether there exists movement after this
	public boolean forward(){
		return true;
	}
	
	//return whether there exists movement before this
	public boolean backward(){
		return true;
	}
	
	
}
