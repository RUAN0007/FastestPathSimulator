package Model;

import java.util.ArrayList;

public class DFSPathComputer extends FastestPathComputer {
	
	private Integer[][] map;
	private ArrayList<Action> actions;
	private boolean explored[][];
	private int rowCount;
	private int colCount;
	
	private Direction firstChoice;
	private Direction secondChoice;
	private Direction thirdChoice;
	
	public DFSPathComputer(Direction firstChoice, Direction secondChoice,
			Direction thirdChoice) {
		super();
		assert(!firstChoice.equals(secondChoice));
		assert(!firstChoice.equals(thirdChoice));
		assert(!secondChoice.equals(thirdChoice));

		this.firstChoice = firstChoice;
		this.secondChoice = secondChoice;
		this.thirdChoice = thirdChoice;
	}

	@Override
	public ArrayList<Action> compute(Integer[][] map, int rowCount,
			int colCount, int startRowID, int startColID,
			Orientation startOrientation, int goalRowID, int goalColID) {
		this.map = map;
		this.actions = new ArrayList<Action>();
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.initDataStructure(rowCount,colCount);
		
		Block startBlock = new Block(startRowID, startColID);
		Block goalBlock = new Block(goalRowID,goalColID);
		
		if(foundPath(startBlock,startOrientation,goalBlock)){
			return this.actions;
		}else{
			return null;
		}
		
	}
	
	private boolean foundPath(Block currentBlock, Orientation currentOrientation,Block goalBlock) {
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
		
		if(exploreOnDirection(firstChoice, currentBlock, currentOrientation, goalBlock)){
			return true;
		}
		
		if(exploreOnDirection(secondChoice, currentBlock, currentOrientation, goalBlock)){
			return true;
		}
		
		if(exploreOnDirection(thirdChoice, currentBlock, currentOrientation, goalBlock)){
			return true;
		}
		
		return false;
		
		
	}
	
	private boolean exploreOnDirection(Direction drc,Block currentBlock,
			Orientation currentOrientation, Block goalBlock){
		if(drc == Direction.LEFT){
			return exploreLeft(currentBlock, currentOrientation, goalBlock);
		}else if(drc == Direction.RIGHT){
			return exploreRight(currentBlock, currentOrientation, goalBlock);
		}else if(drc == Direction.AHEAD){
			return exploreAhead(currentBlock, currentOrientation, goalBlock);
		}else{
			assert(false):"Should not reach here";
		}
		return false;
	}


	private boolean exploreAhead(Block currentBlock,
			Orientation currentOrientation, Block goalBlock) {
		Block nextBlock = currentBlock.toRightOf(currentOrientation);
		Orientation nextOrientation = currentOrientation.relativeToRight();
		this.actions.add(Action.TURN_RIGHT);
		this.actions.add(Action.MOVE_FORWARD);
		
		if(!foundPath(nextBlock, nextOrientation,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
			this.actions.remove(this.actions.size() - 1);
			return false;
		}else{
			return true;
		}
	}

	private boolean exploreRight(Block currentBlock,
			Orientation currentOrientation, Block goalBlock) {
		Block nextBlock = currentBlock.aheadOf(currentOrientation);
		Orientation nextOrientation = currentOrientation.clone();
		this.actions.add(Action.MOVE_FORWARD);

		if(!foundPath(nextBlock, nextOrientation,goalBlock)){
			this.actions.remove(this.actions.size() - 1);
			return false;
		}else{
			return true;
		}
	}

	private boolean exploreLeft(Block currentBlock,
			Orientation currentOrientation, Block goalBlock) {
		Block nextBlock = null;
		Orientation nextOrientation = null;
		
		nextBlock = currentBlock.toLeftOf(currentOrientation);
		nextOrientation = currentOrientation.relativeToLeft();
		this.actions.add(Action.TURN_LEFT);
		this.actions.add(Action.MOVE_FORWARD);
		
		if(!foundPath(nextBlock, nextOrientation,goalBlock)){
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
