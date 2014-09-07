package Model;

public class Robot {
	
	private Block lowerLeftBlock;
	private int diameterInCellNum;
	private Direction currentDirection;
	
	
	
	
	public Robot(int lowerLeftRowIndex, int lowerLeftColIndex,
			int diameterInCellNum, Direction currentDirection) {
		super();
		this.lowerLeftBlock = new Block(lowerLeftRowIndex, lowerLeftColIndex);
		this.diameterInCellNum = diameterInCellNum;
		this.currentDirection = currentDirection;
	}
	
	
	public int getLowerLeftRowIndex() {
		return this.lowerLeftBlock.getRowID();
	}
	public void setLowerLeftRowIndex(int lowerLeftRowIndex) {
		int lowerLeftcolID = this.lowerLeftBlock.getColID();
		this.lowerLeftBlock = new Block(lowerLeftRowIndex, lowerLeftcolID);
	}
	public int getLowerLeftColIndex() {
		return this.lowerLeftBlock.getColID();
	}
	
	public void setLowerLeftColIndex(int lowerLeftColIndex) {
		int lowerLeftRowID = this.lowerLeftBlock.getRowID();
		this.lowerLeftBlock = new Block(lowerLeftRowID, lowerLeftColIndex);
	}
	
	public Direction getCurrentDirection() {
		return currentDirection;
	}
	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}
	public int getDiameterInCellNum() {
		return diameterInCellNum;
	}
	
	@Override
	public String toString(){
		return "Robot:\n"
				+"Lower Left Block: " + this.lowerLeftBlock.toString() + "\n"
				+"Diameter: " + this.diameterInCellNum + " Direction: " + this.currentDirection;
	}
	
	public void move(Action act){
		if(act.equals(Action.MOVE_FORWARD)){
			this.lowerLeftBlock = this.lowerLeftBlock.aheadOf(currentDirection);
		}else if(act.equals(Action.DRAW_BACK)){
			this.lowerLeftBlock = this.lowerLeftBlock.rearOf(currentDirection);

		}
		this.currentDirection = act.directionAfterAction(this.currentDirection);

		//		if(this.currentDirection.equals(Direction.LEFT)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftColIndex--;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftColIndex++;
//			}
//		}else if(this.currentDirection.equals(Direction.RIGHT)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftColIndex++;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftColIndex--;
//			}
//		}else if(this.currentDirection.equals(Direction.UP)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftRowIndex--;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftRowIndex++;
//			}
//		}else{
//			//Current Direction is DOWN
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftRowIndex++;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftRowIndex--;
//			}
//		}
	}
	 
	
}
