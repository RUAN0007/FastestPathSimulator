package Model;

import java.util.ArrayList;

public class CloseWallPathComputer extends FastestPathComputer {
	
	private Direction preferedDirection;
	private Integer[][] map;
	private int rowCount;
	private int colCount;
	
	public CloseWallPathComputer(Direction preferedDirection) {
		super();
		this.preferedDirection = preferedDirection;
	}

	@Override
	public ArrayList<Action> compute(Integer[][] map, int rowCount,
			int colCount, int startRowID, int startColID,
			Orientation startOrientation, int goalRowID, int goalColID) {
		
		
		this.map = map;
		this.rowCount = rowCount;
		this.colCount = colCount;
		
		Block startBlock = new Block(startRowID, startColID);
		Block goalBlock = new Block(goalRowID,goalColID);

		
		if(this.preferedDirection == Direction.LEFT){
			return moveAlongLeftWall(startBlock, goalBlock,
					startOrientation);
		}else if(this.preferedDirection == Direction.RIGHT){
			return moveAlongRightWall(startBlock, goalBlock,
					startOrientation);
		}else{
			assert(false):"Should never reach here";
		}
		return null;
		
		
	}

	private ArrayList<Action> moveAlongRightWall(Block startBlock,
			Block goalBlock, Orientation startOrientation) {
		ArrayList<Action> actions = new ArrayList<Action>();
		Block currentBlock = new Block(startBlock.getRowID(), startBlock.getColID());
		Orientation currentOrientation = startOrientation.clone();
		
		while(!currentBlock.equals(goalBlock)){
			boolean moved = false;

			Block rightBlock = currentBlock.toRightOf(currentOrientation);
			Integer rightCell = cellAtBlock(rightBlock);
			if(rightCell != null && rightCell.equals(new Integer(0))){
				actions.add(Action.TURN_RIGHT);
				actions.add(Action.MOVE_FORWARD);
				currentBlock = rightBlock;
				currentOrientation = currentOrientation.relativeToRight();
				moved = true;
			}
			
			
			if(!moved){
				Block aheadBlock = currentBlock.aheadOf(currentOrientation);
				Integer aheadCell = cellAtBlock(aheadBlock);
				if(aheadCell != null && aheadCell.equals(new Integer(0))){
					actions.add(Action.MOVE_FORWARD);
					currentBlock = aheadBlock;
					moved = true;
				}
			}
			
			if(!moved){

				Block leftBlock = currentBlock.toLeftOf(currentOrientation);
				Integer leftCell = cellAtBlock(leftBlock);
				if(leftCell != null && leftCell.equals(new Integer(0))){
					actions.add(Action.TURN_LEFT);
					actions.add(Action.MOVE_FORWARD);
					currentBlock = leftBlock;
					currentOrientation = currentOrientation.relativeToLeft();
					moved = true;
				}
			}
			
			if(!moved){
				actions.add(Action.TURN_LEFT);
				actions.add(Action.TURN_LEFT);
				currentOrientation = currentOrientation.relativeToRight();
				currentOrientation = currentOrientation.relativeToRight();
			}
			
			
			if(currentBlock.equals(startBlock)) return null;
		}
		return actions;
	}

	private ArrayList<Action> moveAlongLeftWall(Block startBlock, Block goalBlock,
			Orientation startOrientation) {
		ArrayList<Action> actions = new ArrayList<Action>();
		Block currentBlock = new Block(startBlock.getRowID(), startBlock.getColID());
		Orientation currentOrientation = startOrientation.clone();
		
		while(!currentBlock.equals(goalBlock)){
			boolean moved = false;

			Block leftBlock = currentBlock.toLeftOf(currentOrientation);
			Integer leftCell = cellAtBlock(leftBlock);
			if(leftCell != null && leftCell.equals(new Integer(0))){
				actions.add(Action.TURN_LEFT);
				actions.add(Action.MOVE_FORWARD);
				currentBlock = leftBlock;
				currentOrientation = currentOrientation.relativeToLeft();
				moved = true;
			}
			
			if(!moved){
				Block aheadBlock = currentBlock.aheadOf(currentOrientation);
				Integer aheadCell = cellAtBlock(aheadBlock);
				if(aheadCell != null && aheadCell.equals(new Integer(0))){
					actions.add(Action.MOVE_FORWARD);
					currentBlock = aheadBlock;
					moved = true;
				}
			}
			
			if(!moved){
				Block rightBlock = currentBlock.toRightOf(currentOrientation);
				Integer rightCell = cellAtBlock(rightBlock);
				if(rightCell != null && rightCell.equals(new Integer(0))){
					actions.add(Action.TURN_RIGHT);
					actions.add(Action.MOVE_FORWARD);
					currentBlock = rightBlock;
					currentOrientation = currentOrientation.relativeToRight();
					moved = true;
				}
			}
			
			if(!moved){
				actions.add(Action.TURN_RIGHT);
				actions.add(Action.TURN_RIGHT);
				currentOrientation = currentOrientation.relativeToRight();
				currentOrientation = currentOrientation.relativeToRight();
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
	