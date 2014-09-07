package Model;

public class Robot {
	
	private Block lowerLeftBlock;
	private int diameterInCellNum;
	private Orientation currentOrientation;
	
	
	
	
	public Robot(int lowerLeftRowIndex, int lowerLeftColIndex,
			int diameterInCellNum, Orientation currentOrientation) {
		super();
		this.lowerLeftBlock = new Block(lowerLeftRowIndex, lowerLeftColIndex);
		this.diameterInCellNum = diameterInCellNum;
		this.currentOrientation = currentOrientation;
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
				+"Lower Left Block: " + this.lowerLeftBlock.toString() + "\n"
				+"Diameter: " + this.diameterInCellNum + " Orientation: " + this.currentOrientation;
	}
	
	public void move(Action act){
		if(act.equals(Action.MOVE_FORWARD)){
			this.lowerLeftBlock = this.lowerLeftBlock.aheadOf(currentOrientation);
		}else if(act.equals(Action.DRAW_BACK)){
			this.lowerLeftBlock = this.lowerLeftBlock.rearOf(currentOrientation);

		}
		this.currentOrientation = act.orientationAfterAction(this.currentOrientation);

		//		if(this.currentOrientation.equals(Orientation.LEFT)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftColIndex--;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftColIndex++;
//			}
//		}else if(this.currentOrientation.equals(Orientation.RIGHT)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftColIndex++;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftColIndex--;
//			}
//		}else if(this.currentOrientation.equals(Orientation.UP)){
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftRowIndex--;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftRowIndex++;
//			}
//		}else{
//			//Current Orientation is DOWN
//			if(act.equals(Action.MOVE_FORWARD)){
//				this.lowerLeftRowIndex++;
//			}else if(act.equals(Action.DRAW_BACK)){
//				this.lowerLeftRowIndex--;
//			}
//		}
	}
	 
	
}
