package Model;

import java.util.ArrayList;

import org.omg.CORBA.Current;

public class CloseWallPathComputer extends FastestPathComputer {
	
	private Direction robotSideOnObstacle;
	private Integer[][] map;
	private int rowCount;
	private int colCount;
	
	public CloseWallPathComputer(Direction robotSideOnObstacle) {
		super();
		this.robotSideOnObstacle = robotSideOnObstacle;
	}

	@Override
	public ArrayList<Action> compute(Integer[][] map, int rowCount,
			int colCount, int startRowID, int startColID,
			Direction startDirection, int goalRowID, int goalColID) {
		
		
		this.map = map;
		this.rowCount = rowCount;
		this.colCount = colCount;
		
		Block startBlock = new Block(startRowID, startColID);
		Block goalBlock = new Block(goalRowID,goalColID);

		Block currentBlock = new Block(startRowID, startColID);
		Direction currentDirection = startDirection;
		
		if(this.robotSideOnObstacle.equals(Direction.LEFT)){
			return moveAlongLeftWall(startBlock, goalBlock,
					startDirection);
		}else{
			return moveAlongRightWall(startBlock, goalBlock,
					startDirection);
		}
	
		
		
	}

	private ArrayList<Action> moveAlongRightWall(Block startBlock,
			Block goalBlock, Direction startDirection) {
		ArrayList<Action> actions = new ArrayList<Action>();
		Block currentBlock = new Block(startBlock.getRowID(), startBlock.getColID());
		Direction currentDirection = startDirection.clone();
		
		while(!currentBlock.equals(goalBlock)){
			boolean moved = false;

			Block rightBlock = currentBlock.toRightOf(currentDirection);
			Integer rightCell = cellAtBlock(rightBlock);
			if(rightCell != null && rightCell.equals(new Integer(0))){
				actions.add(Action.TURN_RIGHT);
				actions.add(Action.MOVE_FORWARD);
				currentBlock = rightBlock;
				currentDirection = currentDirection.relativeToRight();
				moved = true;
			}
			
			
			if(!moved){
				Block aheadBlock = currentBlock.aheadOf(currentDirection);
				Integer aheadCell = cellAtBlock(aheadBlock);
				if(aheadCell != null && aheadCell.equals(new Integer(0))){
					actions.add(Action.MOVE_FORWARD);
					currentBlock = aheadBlock;
					moved = true;
				}
			}
			
			if(!moved){

				Block leftBlock = currentBlock.toLeftOf(currentDirection);
				Integer leftCell = cellAtBlock(leftBlock);
				if(leftCell != null && leftCell.equals(new Integer(0))){
					actions.add(Action.TURN_LEFT);
					actions.add(Action.MOVE_FORWARD);
					currentBlock = leftBlock;
					currentDirection = currentDirection.relativeToLeft();
					moved = true;
				}
			}
			
			if(!moved){
				actions.add(Action.TURN_LEFT);
				actions.add(Action.TURN_LEFT);
				currentDirection = currentDirection.relativeToRight();
				currentDirection = currentDirection.relativeToRight();
			}
			
			
			if(currentBlock.equals(startBlock)) return null;
		}
		return actions;
	}

	private ArrayList<Action> moveAlongLeftWall(Block startBlock, Block goalBlock,
			Direction startDirection) {
		ArrayList<Action> actions = new ArrayList<Action>();
		Block currentBlock = new Block(startBlock.getRowID(), startBlock.getColID());
		Direction currentDirection = startDirection.clone();
		
		while(!currentBlock.equals(goalBlock)){
			boolean moved = false;

			Block leftBlock = currentBlock.toLeftOf(currentDirection);
			Integer leftCell = cellAtBlock(leftBlock);
			if(leftCell != null && leftCell.equals(new Integer(0))){
				actions.add(Action.TURN_LEFT);
				actions.add(Action.MOVE_FORWARD);
				currentBlock = leftBlock;
				currentDirection = currentDirection.relativeToLeft();
				moved = true;
			}
			
			if(!moved){
				Block aheadBlock = currentBlock.aheadOf(currentDirection);
				Integer aheadCell = cellAtBlock(aheadBlock);
				if(aheadCell != null && aheadCell.equals(new Integer(0))){
					actions.add(Action.MOVE_FORWARD);
					currentBlock = aheadBlock;
					moved = true;
				}
			}
			
			if(!moved){
				Block rightBlock = currentBlock.toRightOf(currentDirection);
				Integer rightCell = cellAtBlock(rightBlock);
				if(rightCell != null && rightCell.equals(new Integer(0))){
					actions.add(Action.TURN_RIGHT);
					actions.add(Action.MOVE_FORWARD);
					currentBlock = rightBlock;
					currentDirection = currentDirection.relativeToRight();
					moved = true;
				}
			}
			
			if(!moved){
				actions.add(Action.TURN_RIGHT);
				actions.add(Action.TURN_RIGHT);
				currentDirection = currentDirection.relativeToRight();
				currentDirection = currentDirection.relativeToRight();
			}
			
			
			if(currentBlock.equals(startBlock)) return null;
		}
		return actions;
	}
	
	private Integer cellAtBlock(Block b){
		int rowID = b.getRowID();
		int colID = b.getColID();
		
		if(rowID < 0 || rowID >= rowCount ){
			return null;
		}
		
		if(colID < 0 || colID >= colCount){
			return null;
		}
		return map[rowID][colID];
	}
}
	