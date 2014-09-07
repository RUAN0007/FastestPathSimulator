package Model;

import java.util.ArrayList;

public class DFSPathComputer extends FastestPathComputer {
	
	private Integer[][] map;
	private ArrayList<Action> actions;
	private boolean explored[][];
	private int rowCount;
	private int colCount;
	
	private Direction preferedDirection;
	
	public DFSPathComputer(Direction preDirection){
		this.preferedDirection = preDirection;
	}
	
	@Override
	public ArrayList<Action> compute(Integer[][] map, int rowCount,
			int colCount, int startRowID, int startColID,
			Direction startDirection, int goalRowID, int goalColID) {
		this.map = map;
		this.actions = new ArrayList<Action>();
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.initDataStructure(rowCount,colCount);
		
		Block startBlock = new Block(startRowID, startColID);
		Block goalBlock = new Block(goalRowID,goalColID);
		
		if(foundPath(startBlock,startDirection,goalBlock)){
			return this.actions;
		}else{
			return null;
		}
		
	}
	
	private boolean foundPath(Block currentBlock, Direction currentDirection,Block goalBlock) {
		if(currentBlock.equals(goalBlock)){
			return true;
		}
	
		
		if(cellAtBlock(currentBlock) == null ||
				cellAtBlock(currentBlock).equals(new Integer(1))){
			return false;
		}
		
		if(isExplored(currentBlock)){
			return false;
		}
		
		this.setExplored(currentBlock);
		
		if(this.preferedDirection.equals(Direction.LEFT)){
			return exploreFromLeftToRight(currentBlock, currentDirection,
					goalBlock);
		}else if(this.preferedDirection.equals(Direction.RIGHT)){
			return exploreFromRightToLeft(currentBlock, currentDirection,
					goalBlock);	
		}else if(this.preferedDirection.equals(Direction.UP)){
			//Explore From From Top, then left and finally right
			return exploreFromAheadLeftRight(currentBlock, currentDirection,
					goalBlock);	
		}else{
			assert(false):"Should not reach here";
		}
		
		
		return false;
		
		
	}

	private boolean exploreFromLeftToRight(Block currentBlock,
			Direction currentDirection, Block goalBlock) {
		
		
		
		if(exploreLeft(currentBlock, currentDirection, goalBlock)) return true;
		
		if( exploreAhead(currentBlock, currentDirection, goalBlock)) return true;
		if(exploreRight(currentBlock, currentDirection, goalBlock)) return true;

		return false;
	}
	
	private boolean exploreFromAheadLeftRight(Block currentBlock,
			Direction currentDirection, Block goalBlock) {
		
		
		if(exploreAhead(currentBlock, currentDirection, goalBlock)) return true;

		if(exploreLeft(currentBlock, currentDirection, goalBlock)) return true;
		
		if(exploreRight(currentBlock, currentDirection, goalBlock)) return true;

		return false;
	}
	
	private boolean exploreFromRightToLeft(Block currentBlock,
			Direction currentDirection, Block goalBlock) {
		
		
		
		
		if(exploreRight(currentBlock, currentDirection, goalBlock)) return true;
		
		if( exploreAhead(currentBlock, currentDirection, goalBlock)) return true;
		if(exploreLeft(currentBlock, currentDirection, goalBlock)) return true;

		return false;
	}
	
	
	

	private boolean exploreAhead(Block currentBlock,
			Direction currentDirection, Block goalBlock) {
		Block nextBlock = currentBlock.toRightOf(currentDirection);
		Direction nextDirection = currentDirection.relativeToRight();
		this.actions.add(Action.TURN_RIGHT);
		this.actions.add(Action.MOVE_FORWARD);
		
		if(!foundPath(nextBlock, nextDirection,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
			this.actions.remove(this.actions.size() - 1);
			return false;
		}else{
			return true;
		}
	}

	private boolean exploreRight(Block currentBlock,
			Direction currentDirection, Block goalBlock) {
		Block nextBlock = currentBlock.aheadOf(currentDirection);
		Direction nextDirection = currentDirection.clone();
		this.actions.add(Action.MOVE_FORWARD);

		if(!foundPath(nextBlock, nextDirection,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
			return false;
		}else{
			return true;
		}
	}

	private boolean exploreLeft(Block currentBlock,
			Direction currentDirection, Block goalBlock) {
		Block nextBlock = null;
		Direction nextDirection = null;
		
		nextBlock = currentBlock.toLeftOf(currentDirection);
		nextDirection = currentDirection.relativeToLeft();
		this.actions.add(Action.TURN_LEFT);
		this.actions.add(Action.MOVE_FORWARD);
		
		if(!foundPath(nextBlock, nextDirection,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
			this.actions.remove(this.actions.size() - 1);
			return false;
		}else{
			return true;
		}
	}
	private void initDataStructure(int rowCount, int colCount) {
		this.explored = new boolean[rowCount][colCount];
		for(int rowID = 0;rowID < rowCount;rowID++){
			for(int colID = 0;colID < colCount;colID++){
				this.explored[rowID][colID] = false;
			}
		}
		
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
	
	private boolean isExplored(Block blk){
		return this.explored[blk.getRowID()][blk.getColID()];
	}
	
	private void setExplored(Block blk){
		this.explored[blk.getRowID()][blk.getColID()] = true;
	}
}
