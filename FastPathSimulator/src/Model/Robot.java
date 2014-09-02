package Model;

public class Robot {
	
	private int lowerLeftRowIndex;
	private int lowerLeftColIndex;
	private int diameterInCellNum;
	private Direction currentDirection;
	
	
	
	
	public Robot(int lowerLeftRowIndex, int lowerLeftColIndex,
			int diameterInCellNum, Direction currentDirection) {
		super();
		this.lowerLeftRowIndex = lowerLeftRowIndex;
		this.lowerLeftColIndex = lowerLeftColIndex;
		this.diameterInCellNum = diameterInCellNum;
		this.currentDirection = currentDirection;
	}
	
	
	public int getLowerLeftRowIndex() {
		return lowerLeftRowIndex;
	}
	public void setLowerLeftRowIndex(int lowerLeftRowIndex) {
		this.lowerLeftRowIndex = lowerLeftRowIndex;
	}
	public int getLowerLeftColIndex() {
		return lowerLeftColIndex;
	}
	public void setLowerLeftColIndex(int lowerLeftColIndex) {
		this.lowerLeftColIndex = lowerLeftColIndex;
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
		return "Robot--Row: " + this.lowerLeftRowIndex + " Col: " + this.lowerLeftColIndex 
					+"\nDiameter: " + this.diameterInCellNum + " Direction: " + this.currentDirection;
	}
	
	public void move(Action act){
		this.currentDirection = act.directionAfterAction(this.currentDirection);
		if(this.currentDirection.equals(Direction.LEFT)){
			if(act.equals(Action.MOVE_FORWARD)){
				this.lowerLeftColIndex--;
			}else if(act.equals(Action.DRAW_BACK)){
				this.lowerLeftColIndex++;
			}
		}else if(this.currentDirection.equals(Direction.RIGHT)){
			if(act.equals(Action.MOVE_FORWARD)){
				this.lowerLeftColIndex++;
			}else if(act.equals(Action.DRAW_BACK)){
				this.lowerLeftColIndex--;
			}
		}else if(this.currentDirection.equals(Direction.UP)){
			if(act.equals(Action.MOVE_FORWARD)){
				this.lowerLeftRowIndex--;
			}else if(act.equals(Action.DRAW_BACK)){
				this.lowerLeftRowIndex++;
			}
		}else{
			//Current Direction is DOWN
			if(act.equals(Action.MOVE_FORWARD)){
				this.lowerLeftRowIndex++;
			}else if(act.equals(Action.DRAW_BACK)){
				this.lowerLeftRowIndex--;
			}
		}
	}
	 
	
}
