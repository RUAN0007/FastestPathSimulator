package Model;

import java.util.ArrayList;

public class DFSPathComputer extends FastestPathComputer {
	
	private Integer[][] map;
	private ArrayList<Action> actions;
	private boolean explored[][];
	private int rowCount;
	private int colCount;
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
		
		
		Block nextBlock = null;
		Direction nextDirection = null;
		
		
		nextBlock = currentBlock.toLeftOf(currentDirection);
		nextDirection = currentDirection.relativeToLeft();
		this.actions.add(Action.TURN_LEFT);
		this.actions.add(Action.MOVE_FORWARD);
		
		if(!foundPath(nextBlock, nextDirection,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
			this.actions.remove(this.actions.size() - 1);
		}else{
			return true;
		}
		
		nextBlock = currentBlock.aheadOf(currentDirection);
		nextDirection = currentDirection.clone();
		this.actions.add(Action.MOVE_FORWARD);

		if(!foundPath(nextBlock, nextDirection,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
		}else{
			return true;
		}
		
		
		nextBlock = currentBlock.toRightOf(currentDirection);
		nextDirection = currentDirection.relativeToRight();
		this.actions.add(Action.TURN_RIGHT);
		this.actions.add(Action.MOVE_FORWARD);
		
		if(!foundPath(nextBlock, nextDirection,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
			this.actions.remove(this.actions.size() - 1);
		}else{
			return true;
		}

		
		return false;
		
		
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
