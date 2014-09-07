package Model;

public class Robot {
	
	private Block southWestBlock;
	private int diameterInCellNum;
	private Orientation currentOrientation;
	
	
	
	
	public Robot(int southWestRowIndex, int southWestColIndex,
			int diameterInCellNum, Orientation currentOrientation) {
		super();
		this.southWestBlock = new Block(southWestRowIndex, southWestColIndex);
		this.diameterInCellNum = diameterInCellNum;
		this.currentOrientation = currentOrientation;
	}
	
	
	public int getLowerLeftRowIndex() {
		return this.southWestBlock.getRowID();
	}
	public void setLowerLeftRowIndex(int southWestRowIndex) {
		int southWestcolID = this.southWestBlock.getColID();
		this.southWestBlock = new Block(southWestRowIndex, southWestcolID);
	}
	public int getLowerLeftColIndex() {
		return this.southWestBlock.getColID();
	}
	
	public void setLowerLeftColIndex(int southWestColIndex) {
		int southWestRowID = this.southWestBlock.getRowID();
		this.southWestBlock = new Block(southWestRowID, southWestColIndex);
	}
	
	public Orientation getCurrentOrientation() {
		return currentOrientation;
	}
	public void setCurrentOrientation(Orientation currentOrientation) {
		this.currentOrientation = currentOrientation;
	}
	public int getDiameterInCellNum() {
		return diameterInCellNum;
	}
	
	@Override
	public String toString(){
		return "Robot:\n"
				+"South West Block: " + this.southWestBlock.toString() + "\n"
				+"Diameter: " + this.diameterInCellNum + " Orientation: " + this.currentOrientation;
	}
	
	public void move(Action act){
		if(act.equals(Action.MOVE_FORWARD)){
			this.southWestBlock = this.southWestBlock.aheadOf(currentOrientation);
		}else if(act.equals(Action.DRAW_BACK)){
			this.southWestBlock = this.southWestBlock.rearOf(currentOrientation);

		}
		this.currentOrientation = act.orientationAfterAction(this.currentOrientation);

		//		if(this.currentOrientation.equals(Orientation.LEFT)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.southWestColIndex--;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.southWestColIndex++;
//			}
//		}else if(this.currentOrientation.equals(Orientation.RIGHT)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.southWestColIndex++;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.southWestColIndex--;
//			}
//		}else if(this.currentOrientation.equals(Orientation.UP)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.southWestRowIndex--;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.southWestRowIndex++;
//			}
//		}else{
//			//Current Orientation is DOWN
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.southWestRowIndex++;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.southWestRowIndex--;
//			}
//		}
	}
	 
	
}
