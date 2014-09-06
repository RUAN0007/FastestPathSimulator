package Model;

import java.util.ArrayList;

public class MinStepTurnPathComputer extends FastestPathComputer {
	
	private static int DIR_NULL = -1;
	private static int DIR_MIN = 0;
	private static int UP_INDEX = 0;
	private static int RIGHT_INDEX = 1;
	private static int DOWN_INDEX = 2;
	private static int LEFT_INDEX = 3;
	private static int DIR_MAX = 3;
	
	
	
	private int turnWeight;
	private int stepWeight;
	
	
	public MinStepTurnPathComputer(int turnWeight, int stepWeight) {
		super();
		this.turnWeight = turnWeight;
		this.stepWeight = stepWeight;
	}



	@Override
	public ArrayList<Action> compute(Integer[][] map, int rowCount, int colCount,
			int startRowID, int startColID, Direction startDirection,
			int goalRowID, int goalColID) {
//		Integer mapWithDrc[][][] = new Integer[rowCount][colCount][Direction.DirectionCount];
		int distance[][][] = new int[rowCount][colCount][Direction.DirectionCount];
		Action preAction[][][] = new Action[rowCount][colCount][Direction.DirectionCount];
		boolean explored[][][] = new boolean[rowCount][colCount][Direction.DirectionCount];
		
		for(int rowID = 0;rowID < rowCount ; rowID++){
			for(int colID = 0;colID < colCount;colID++){
				boolean isObstacle = false;
				if(map[rowID][colID].equals(new Integer(1))){
					isObstacle = true;
				}
				for(int drcID = DIR_MIN;drcID <= DIR_MAX;drcID ++){
					distance[rowID][colID][drcID] = Integer.MAX_VALUE;
					preAction[rowID][colID][drcID] = null;
					explored[rowID][colID][drcID] = isObstacle;
					
				}//END of loop on direction
			}// END of loop on columns
		}//End of loop on rows
		
		int startDrcID = IndexOfDirection(startDirection);
		distance[startRowID][startColID][startDrcID] = 0;
		int goalDrcID = DIR_NULL;
		while(true){
			int minRowID = -1;
			int minColID = -1;
			int minDrcID = DIR_NULL;
			int minDist = Integer.MAX_VALUE;
			boolean foundMin = false;
			
			for(int rowID = 0;rowID < rowCount ; rowID++){
				for(int colID = 0;colID < colCount;colID++){
					for(int drcID = DIR_MIN;drcID <= DIR_MAX;drcID ++){
						if(!explored[rowID][colID][drcID] && 
								distance[rowID][colID][drcID] < minDist){
							minRowID = rowID;
							minColID = colID;
							minDrcID = drcID;
							minDist = distance[rowID][colID][drcID];
							foundMin = true;
						}
					}//END of loop on direction
				}// END of loop on columns
			}//End of loop on rows
			

			if(!foundMin) return null;
			
			assert(!explored[minRowID][minColID][minDrcID]);
			explored[minRowID][minColID][minDrcID] = true;
			//All the directions at goal state has been explored
			int minDistForDrcOnGoal = Integer.MAX_VALUE;
			for(int drcID = DIR_MIN;drcID <= DIR_MAX;drcID ++){
				if(!explored[goalRowID][goalColID][drcID]){
					goalDrcID = DIR_NULL;
					break;
				}else{
					if(distance[goalRowID][goalColID][drcID] < minDistForDrcOnGoal){
						minDistForDrcOnGoal = distance[goalRowID][goalColID][drcID];
						goalDrcID = drcID;
					}
				}
				
			}//END of loop on direction
			if(goalDrcID != DIR_NULL) break;
			
			
			//Update its three adjacent node
			
			//Update the first adjacent node
			int leftDirectionID = (minDrcID + DIR_MAX) % (DIR_MAX + 1);
			if(!explored[minRowID][minColID][leftDirectionID] &&
					distance[minRowID][minColID][leftDirectionID]
							> distance[minRowID][minColID][minDrcID] + turnWeight){
				
				distance[minRowID][minColID][leftDirectionID] 
						= distance[minRowID][minColID][minDrcID] + turnWeight;
				preAction[minRowID][minColID][leftDirectionID] = Action.TURN_LEFT;				
			}
			
			//Update the second adjacent node
			int rightDirectionID = (minDrcID + 1) % (DIR_MAX + 1);
			if(!explored[minRowID][minColID][rightDirectionID] &&
					distance[minRowID][minColID][rightDirectionID]
							> distance[minRowID][minColID][minDrcID] + turnWeight){
				
				distance[minRowID][minColID][rightDirectionID] 
						= distance[minRowID][minColID][minDrcID] + turnWeight;
				preAction[minRowID][minColID][rightDirectionID] = Action.TURN_RIGHT;				
			}
			
			//Update the third adjacent node
			int adjacentRowID = minRowID;
			int adjacentColID = minColID;
			
			if(minDrcID == UP_INDEX){
				adjacentRowID--;
			}else if(minDrcID == DOWN_INDEX){
				adjacentRowID++;
			}else if(minDrcID == LEFT_INDEX){
				adjacentColID--;
			}else{
				//RIGHT Direction
				adjacentColID++;
			}
			if(0 <= adjacentRowID && adjacentRowID < rowCount &&
					0 <= adjacentColID && adjacentColID < colCount){
				if(!explored[adjacentRowID][adjacentColID][minDrcID] &&
						distance[adjacentRowID][adjacentColID][minDrcID] >
							distance[minRowID][minColID][minDrcID] + stepWeight){
					distance[adjacentRowID][adjacentColID][minDrcID] =
							distance[minRowID][minColID][minDrcID] + stepWeight;
					preAction[adjacentRowID][adjacentColID][minDrcID] = Action.MOVE_FORWARD;
				}
			}
			

		}// END of infinite WHILE
		
		
		for(int drcID = DIR_MIN;drcID <= DIR_MAX;drcID ++){
			assert(explored[goalRowID][goalColID][drcID]);
			assert(preAction[goalRowID][goalColID][drcID] != null);
		}//END of loop on direction		
	
		
		
		
		return findPathsFromActionMap(preAction,
									goalRowID,
									goalColID,
									goalDrcID
//									startRowID,
//									startColID,
//									startDrcID
									);
		
	}
	
	


	//TODO
	//Remove the last three parameters for testing
	private ArrayList<Action> findPathsFromActionMap(Action[][][] preAction,
			int goalRowID, int goalColID, int goalDrcID) {
			//int startRowID,int startColID,int startDrcID) {
		
		ArrayList<Action> actions = new ArrayList<>();
		//Find the action min distance
		int rowID = goalRowID;
		int colID = goalColID;
		int drcID = goalDrcID;
		
		while(preAction[rowID][colID][drcID] != null){
			Action currentAction = preAction[rowID][colID][drcID];
			actions.add(0, currentAction);
			if(currentAction.equals(Action.TURN_LEFT)){
				 drcID = (drcID + 1) % (DIR_MAX + 1);

			}else if(currentAction.equals(Action.TURN_RIGHT)){
				drcID = (drcID + DIR_MAX) % (DIR_MAX + 1);

			}else{
				//currentAction == MOVE_FORWARD
				if(drcID == UP_INDEX){
					rowID++;
				}else if(drcID == DOWN_INDEX){
					rowID--;
				}else if(drcID == LEFT_INDEX){
					colID++;
				}else{
					//RIGHT Direction
					colID--;
				}
				
			}
		}
	//	assert(rowID == startRowID && colID == startColID && drcID == startDrcID);

		return actions;
	}



	private static int IndexOfDirection(Direction direction){
		if(direction.equals(Direction.UP)){
			return UP_INDEX;
		}else if(direction.equals(Direction.RIGHT)){
			return RIGHT_INDEX;
		}else if(direction.equals(Direction.DOWN)){
			return DOWN_INDEX;
		}else{
			//LEFT DIRECTION
			return LEFT_INDEX;
		}
	}

}
