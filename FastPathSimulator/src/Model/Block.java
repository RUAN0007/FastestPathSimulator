package Model;

public class Block {
	private int rowID;
	private int colID;
	
	public int getRowID() {
		return rowID;
	}
	public int getColID() {
		return colID;
	}
	public Block(int rowID, int colID) {
		super();
		this.rowID = rowID;
		this.colID = colID;
	}
	
	public Block toLeftOf(Direction face){
		int leftRowID = this.rowID;
		int leftColID = this.colID;
		
		if(face.equals(Direction.UP)){
			leftColID--;
		}else if(face.equals(Direction.RIGHT)){
			leftRowID--;
		}else if(face.equals(Direction.DOWN)){
			leftColID++;
		}else{
			//face to left
			leftRowID++;
		}
		return new Block(leftRowID, leftColID);
	}
	
	public Block toRightOf(Direction face){
		int rightRowID = this.rowID;
		int rightColID = this.colID;
		
		if(face.equals(Direction.UP)){
			rightColID++;
		}else if(face.equals(Direction.RIGHT)){
			rightRowID++;
		}else if(face.equals(Direction.DOWN)){
			rightColID--;
		}else{
			//face to left
			rightRowID--;
		}
		return new Block(rightRowID, rightColID);
	}
	
	public Block aheadOf(Direction face){
		int aheadRowID = this.rowID;
		int aheadColID = this.colID;
		
		if(face.equals(Direction.UP)){
			aheadRowID--;

		}else if(face.equals(Direction.RIGHT)){
			aheadColID++;

		}else if(face.equals(Direction.DOWN)){
			aheadRowID++;

		}else{
			//face to left
			aheadColID--;		
		}
		return new Block(aheadRowID, aheadColID);
	}
	
	public Block rearOf(Direction face){
		int rearRowID = this.rowID;
		int rearColID = this.colID;
		
		if(face.equals(Direction.UP)){
			rearRowID++;

		}else if(face.equals(Direction.RIGHT)){
			rearColID--;

		}else if(face.equals(Direction.DOWN)){
			rearRowID--;

		}else{
			//face to left
			rearColID++;		
		}
		return new Block(rearRowID, rearColID);
	}
	
	public boolean equals(Block b){
		return this.rowID == b.rowID && this.colID == b.colID;
	}
	
}
