package Model;

public class Direction {
	
	public static Direction UP = new Direction("UP");
	public static Direction DOWN = new Direction("DOWN");
	public static Direction LEFT = new Direction("LEFT");
	public static Direction RIGHT = new Direction("RIGHT");
	
	private String direction;
	
	private Direction(String dir){
		this.direction = dir;
	}
	
	
	
	@Override
	public String toString(){
		return this.direction;
	}
	
	public boolean equals(Direction dir){
		return this.direction.equals(dir.direction);
	}
}
