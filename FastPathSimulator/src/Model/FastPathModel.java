package Model;

import java.util.ArrayList;

import Model.ArenaTemplate.CellState;

public class FastPathModel {
	
	public enum Cell {
		OBSTACLE,
		EMPTY,
		ROBOT,
		ROBOT_DIRECTION,
		UNEXMPLORED,
		PATH,
		START,
		GOAL
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
	
	private int southWestGoalRowID;
	private int southWestGoalColID;
	
	private int southWestStartRowID;
	private int southWestStartColID;
	private Orientation startOrientation;
			
	
	public FastPathModel(CustomizedArena arenaMap, 
						int southWestStartRowID,
						int southWestStartColID,
						Orientation startOrientation,
			
						int southWestGoalRowID,
						int southWestGoalColID, 
						int robotDiameterInCellCount,
						FastestPathComputer pathComputer) throws SimulatorException {
		super();
		
		
		this.arenaMap = arenaMap;
	
		if(obstacleInArea(southWestStartRowID,southWestStartColID,robotDiameterInCellCount)){
			throw new SimulatorException(1, "There exists obstacle in the START position");
		}
		if(obstacleInArea(southWestGoalRowID,southWestGoalColID,robotDiameterInCellCount)){
			throw new SimulatorException(1, "There exists obstacle in the GOAL position");
		}
		
		this.southWestGoalRowID = southWestGoalRowID;
		this.southWestGoalColID = southWestGoalColID;
		this.southWestStartRowID = southWestStartRowID;
		this.southWestStartColID = southWestStartColID;
		this.startOrientation = startOrientation;
		
		this.robot = new Robot(southWestStartRowID, southWestStartColID, robotDiameterInCellCount,startOrientation);
		this.currentStatus = new Cell[arenaMap.getRowCount()][arenaMap.getColumnCount()];
		
		this.fastestPath = pathComputer.computeForFastestPath(arenaMap, robot, southWestGoalRowID, southWestGoalColID);
		if(this.fastestPath == null){
			throw new SimulatorException(2, "No Path can be found");
		}
		updateStatus();
	}

	private boolean obstacleInArea(int southWestRowID,
			int southWestColID, int span) {

		for(int rowID = 0;rowID < span;rowID++){
			for(int colID = 0;colID < span;colID++){
				if(this.arenaMap.getCells()
						[southWestRowID - rowID][southWestColID + colID] 
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
	
	//Return the description of the action
	public String forward(){
		String description = null;
		if(actionIndex >= 0 && actionIndex < fastestPath.size()){
			Action act = fastestPath.get(actionIndex);
			description = act.toString();
			this.robot.move(act);
			actionIndex++;
			updateStatus();
			
			}
		return description;
		}
	
	
	//Return the description of the action
	public  String backward(){
		String description = null;
		if(actionIndex >= 1 && actionIndex <= fastestPath.size()){
			actionIndex--;
			Action act = fastestPath.get(actionIndex);
			description = act.toString();

			this.robot.move(Action.revert(act));
			updateStatus();
		}
		return description;
	}
	
	private void updateStatus(){
		
		updateForArenaMap();
		updateForPath();
		updateForStart();
		updateForGoal();
		updateForRobot();

	}

	private void updateForGoal() {
		int robotDiameterInCellNum = this.robot.getDiameterInCellNum();
		for(int rowID = 0; rowID < robotDiameterInCellNum; rowID++){
			for(int colID = 0;colID < robotDiameterInCellNum; colID++){
				this.currentStatus[southWestGoalRowID - rowID][ southWestGoalColID + colID]
						= Cell.GOAL;
			}
		}
	}

	private void updateForStart() {
		int robotDiameterInCellNum = this.robot.getDiameterInCellNum();
		for(int rowID = 0; rowID < robotDiameterInCellNum; rowID++){
			for(int colID = 0;colID < robotDiameterInCellNum; colID++){
				this.currentStatus[southWestStartRowID - rowID][southWestStartColID + colID]
						= Cell.START;
			}
		}
	}

	private void updateForPath() {
		Orientation orientation = this.startOrientation;
		int southWestRowID = this.southWestStartRowID;
		int southWestColID = this.southWestStartColID;
		int robotDiameterInCellNum = this.robot.getDiameterInCellNum();
		
		for(int actID = 0;actID < this.actionIndex;actID++){
			Action act = this.fastestPath.get(actID);
			
			if((orientation.equals(Orientation.NORTH) && act.equals(Action.MOVE_FORWARD))
					||(orientation.equals(Orientation.SOUTH) && act.equals(Action.DRAW_BACK))){
				
				//MOVE UPWARD
				int rowID = southWestRowID;
				int colID = southWestColID;
				for(int offset = 0;offset < robotDiameterInCellNum; offset++){
					this.currentStatus[rowID][colID + offset] =  Cell.PATH;
				}
				southWestRowID--;
			}else if((orientation.equals(Orientation.NORTH) && act.equals(Action.DRAW_BACK))
					||(orientation.equals(Orientation.SOUTH) && act.equals(Action.MOVE_FORWARD))){
				
				//MOVE DOWNWARD
				//Draw the DIRECTION CELL at the top of robot
				int rowID = southWestRowID - robotDiameterInCellNum + 1;
				int colID = southWestColID;

				for(int offset = 0;offset < robotDiameterInCellNum; offset++){
					this.currentStatus[rowID][colID + offset] =  Cell.PATH;
				}
				southWestRowID++;
			}else if((orientation.equals(Orientation.WEST) && act.equals(Action.MOVE_FORWARD))
					||(orientation.equals(Orientation.EAST) && act.equals(Action.DRAW_BACK))){
				
				//MOVE TO LEFT	
				//Draw the path on EAST side of the robot
				int rowID = southWestRowID;
				int colID = southWestColID + robotDiameterInCellNum - 1;

				for(int offset = 0;offset < robotDiameterInCellNum; offset++){
					this.currentStatus[rowID - offset][colID] =  Cell.PATH;
				}
				southWestColID--;
			}else if((orientation.equals(Orientation.WEST) && act.equals(Action.DRAW_BACK))
					||(orientation.equals(Orientation.EAST) && act.equals(Action.MOVE_FORWARD))){
				
				//MOVE TO RIGHT	
				//Draw the path on WEST side of the robot
				int rowID = southWestRowID;
				int colID = southWestColID;

				for(int offset = 0;offset < robotDiameterInCellNum; offset++){
					this.currentStatus[rowID - offset][colID] =  Cell.PATH;
				}
				southWestColID++;
			}
			
			orientation = act.orientationAfterAction(orientation);
		}
	}

	private void updateForRobot() {
		int robotDiameterInCellNum = this.robot.getDiameterInCellNum();
		int cellRowIndex, cellColIndex;
		for(int rowOffset = 0;rowOffset < robotDiameterInCellNum;rowOffset++){
			cellRowIndex = this.robot.getLowerLeftRowIndex() - rowOffset;
			for(int colOffset = 0;colOffset < robotDiameterInCellNum;colOffset++){
				cellColIndex = this.robot.getLowerLeftColIndex() + colOffset;
				
				assert(this.currentStatus[cellRowIndex][cellColIndex] != Cell.OBSTACLE);
				this.currentStatus[cellRowIndex][cellColIndex] = Cell.ROBOT;		
			}
		}
		
		//Draw the Orientation Cell
		
		if(this.robot.getCurrentOrientation().equals(Orientation.WEST)){
		
			cellRowIndex = this.robot.getLowerLeftRowIndex();
			cellColIndex = this.robot.getLowerLeftColIndex();

			for(int offset = 0;offset < robotDiameterInCellNum;offset++){
				this.currentStatus[cellRowIndex][cellColIndex] = Cell.ROBOT_DIRECTION;
				cellRowIndex --;
			}
		}else if(this.robot.getCurrentOrientation().equals(Orientation.EAST)){
		
			cellRowIndex = this.robot.getLowerLeftRowIndex();
			cellColIndex = this.robot.getLowerLeftColIndex() + robotDiameterInCellNum - 1;
			
			for(int offset = 0;offset < robotDiameterInCellNum;offset++){
				this.currentStatus[cellRowIndex][cellColIndex] = Cell.ROBOT_DIRECTION;
				cellRowIndex --;
			}
		}else if(this.robot.getCurrentOrientation().equals(Orientation.NORTH)){
			cellRowIndex = this.robot.getLowerLeftRowIndex() - robotDiameterInCellNum + 1;
			cellColIndex = this.robot.getLowerLeftColIndex();

			for(int offset = 0;offset < robotDiameterInCellNum;offset++){
				this.currentStatus[cellRowIndex][cellColIndex] = Cell.ROBOT_DIRECTION;
				cellColIndex ++;
			}
			
		}else if(this.robot.getCurrentOrientation().equals(Orientation.SOUTH)){
			cellRowIndex = this.robot.getLowerLeftRowIndex();
			cellColIndex = this.robot.getLowerLeftColIndex();

			for(int offset = 0;offset < robotDiameterInCellNum;offset++){
				this.currentStatus[cellRowIndex][cellColIndex] = Cell.ROBOT_DIRECTION;
				cellColIndex ++;
			}
		}
		
	}

	private void updateForArenaMap() {
		for(int rowID = 0;rowID < this.arenaMap.getRowCount();rowID++){
			for(int colID = 0;colID < this.arenaMap.getColumnCount();colID++){
				if(this.arenaMap.getCells()[rowID][colID] == CellState.OBSTACLE){
					this.currentStatus[rowID][colID] = Cell.OBSTACLE;
				}else if(this.arenaMap.getCells()[rowID][colID] == CellState.EMPTY){
					this.currentStatus[rowID][colID] = Cell.EMPTY;
				}else{
					assert(false):"This arena should not contain any UNEXPLORED CELL";
				}
			}
		}
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
		this.robot.setCurrentOrientation(startOrientation);
		this.robot.setLowerLeftRowIndex(southWestStartRowID);
		this.robot.setLowerLeftColIndex(southWestStartColID);
		this.actionIndex = 0;
		this.updateStatus();
	}
	
	public boolean setPathComputer(FastestPathComputer pathComputer){
		this.reset();
		ArrayList<Action> actions = pathComputer.computeForFastestPath(arenaMap, robot, southWestGoalRowID, southWestGoalColID);
		if(actions != null){
			this.fastestPath = actions;
			return true;
		}else{
			return false;
		}

	}
	
}
