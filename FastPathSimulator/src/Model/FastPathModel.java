package Model;

import java.util.ArrayList;

public class FastPathModel {
	
	public enum Cell {
		OBSTACLE,
		EMPTY,
		ROBOT,
		ROBOT_DIRECTION,
		UNEXMPLORED,
		PATH
	}
	
	public class SimulatorException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int ID;
		private String msg;
		public String toString(){
			return "" + ID + ": "+ msg;
		}
		
		public  SimulatorException(int ID,String msg){
			this.ID = ID;
			this.msg = msg;
		}
	}
	
	private CustomizedArena arenaMap;
	private Robot robot;
	private Cell[][] currentStatus;
	
	private ArrayList<Action> fastestPath = new ArrayList<>();
	//All the action from 0 to actionIndex - 1 has been executed.
	//FastestPath[action] has not yet be executed.
	private int actionIndex = 0;
	
	private int lowerLeftGoalRowID;
	private int lowerLeftGoalColID;
	
	private int lowerLeftStartRowID;
	private int lowerLeftStartColID;
	private Direction startDirection;
		
	
	
	public FastPathModel(CustomizedArena arenaMap, 
						int lowerLeftStartRowID,
						int lowerLeftStartColID,
						Direction startDirection,
			
						int lowerLeftGoalRowID,
						int lowerLeftGoalColID, 
						int robotDiameterInCellCount) throws SimulatorException {
		super();
		
		
		this.arenaMap = arenaMap;
	
		if(obstacleInArea(lowerLeftStartRowID,lowerLeftStartColID,robotDiameterInCellCount)){
			throw new SimulatorException(1, "There exists obstacle in the START position");
		}
		if(obstacleInArea(lowerLeftGoalRowID,lowerLeftGoalColID,robotDiameterInCellCount)){
			throw new SimulatorException(1, "There exists obstacle in the GOAL position");
		}
		
		this.lowerLeftGoalRowID = lowerLeftGoalRowID;
		this.lowerLeftGoalColID = lowerLeftGoalColID;
		this.lowerLeftStartRowID = lowerLeftStartRowID;
		this.lowerLeftStartColID = lowerLeftStartColID;
		this.startDirection = startDirection;
		
		this.robot = new Robot(lowerLeftStartRowID, lowerLeftStartColID, robotDiameterInCellCount,startDirection);
		
		if(computeFastestPath()){
			throw new SimulatorException(2, "No Path can be found");

		}
		
	}

	private boolean obstacleInArea(int lowerLeftRowID,
			int lowerLeftColID, int span) {

		for(int rowID = 0;rowID < span;rowID++){
			for(int colID = 0;colID < span;colID++){
				if(this.arenaMap.getCells()
						[rowID - lowerLeftRowID][colID + lowerLeftColID] 
						== ArenaTemplate.CellState.OBSTACLE){
					return true;
				}
			}
		}
		return false;
	}
	
	public int getArenaRowCount(){
		return this.arenaMap.getRowCount();
	}
	
	public int getArenaColCount(){
		return this.arenaMap.getColumnCount();
	}

	public boolean hasNextStep(){
		return this.actionIndex < this.fastestPath.size();
	}
	
	public boolean hasPreStep(){
		return this.actionIndex > 0;
	}
	
	
	private boolean computeFastestPath(){
		//TODO 
		//The below actions are only for testing
		
		int robotDiameterInCellNum = this.robot.getDiameterInCellNum();
		
		for(int i = 0;
				i < this.getArenaRowCount() - robotDiameterInCellNum;
				i++){
			this.fastestPath.add(Action.MOVE_FORWARD);
		}
		this.fastestPath.add(Action.TURN_RIGHT);
		for(int i = 0;
				i < this.getArenaColCount() - robotDiameterInCellNum;
				i++){
			this.fastestPath.add(Action.MOVE_FORWARD);
		}
		return true;
	}
	
	public Cell getCellStatus(int rowID, int colID){
		return this.currentStatus[rowID][colID];
	}
	
	//return whether there exists movement after this
	public void forward(){
		if(actionIndex >= 0 && actionIndex < fastestPath.size()){
			this.robot.move(fastestPath.get(actionIndex));
			actionIndex++;
			updateStatus();
			
			}
		}
	
	
	//return whether there exists movement before this
	public  void backward(){
		if(actionIndex >= 1 && actionIndex <= fastestPath.size()){
			actionIndex--;
			this.robot.move(Action.opposite(fastestPath.get(actionIndex)));
			updateStatus();
		}
		
	}
	
	private void updateStatus(){
		//TODO
		//Update the current map status based on the executed action from [0 actionIndex-1]
		
	}
	
	public int getCurrentTurnCount(){
		int count = 0;
		for (int actionID = 0;actionID < actionIndex;actionID++){
			if(fastestPath.get(actionID) == Action.TURN_LEFT 
				|| fastestPath.get(actionID) == Action.TURN_RIGHT){
				count++;
			}
		}
		return count;
	}
	
	public int getCurrentStepCount(){
		int count = 0;
		for (int actionID = 0;actionID < actionIndex;actionID++){
			if(fastestPath.get(actionID) == Action.MOVE_FORWARD 
				|| fastestPath.get(actionID) == Action.DRAW_BACK){
				count++;
			}
		}
		return count;
	}
	
	public void reset(){
		this.robot.setCurrentDirection(startDirection);
		this.robot.setLowerLeftRowIndex(lowerLeftStartRowID);
		this.robot.setLowerLeftColIndex(lowerLeftStartColID);
		this.actionIndex = 0;
	}
	
}
