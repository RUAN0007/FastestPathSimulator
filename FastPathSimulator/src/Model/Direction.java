package Model;


public class Direction {
	
	public static Direction UP = new Direction(0);
	public static Direction RIGHT = new Direction(1);

	public static Direction DOWN = new Direction(2);
	public static Direction LEFT = new Direction(3);
	public static int DirectionCount = 4;
	
	private int dirSymbol;
	
	private Direction(int dir){
		this.dirSymbol = dir;
	}
	
	
	
	@Override
	public String toString(){
		switch(this.dirSymbol){
			case 0:
				return "UP";
			case 1:
				return "RIGHT";
			case 2:
				return "DOWN";
			case 3:
				return "RIGHT";
			default:
				return "NULL";
		}
	}
	
	public boolean equals(Direction dir){
		return this.dirSymbol == dir.dirSymbol;
	}
	
	public  Direction relativeToLeft(){

		return new Direction((this.dirSymbol + DirectionCount - 1) % DirectionCount);
	}
	
	public Direction relativeToRight(){
		return new Direction((this.dirSymbol + 1) % DirectionCount);
	}
	
	public Direction toOppsite(){
		return new Direction((this.dirSymbol - DirectionCount / 2) % DirectionCount);		
	}
	
	public Direction clone(){
		return new Direction(this.dirSymbol);
	}
}
