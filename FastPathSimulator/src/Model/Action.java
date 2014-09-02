package Model;

public class Action {
	private String action;
	
	public static Action TURN_LEFT= new Action("TURN_LEFT");
	public static Action TURN_RIGHT = new Action("TURN_RIGHT");
	public static Action MOVE_FORWARD = new Action("MOVE_FORWARD");
	public static Action DRAW_BACK = new Action("DRAW_BACK");
	
	
	private Action(String action){
		this.action = action;	
	}
	
	@Override
	public String toString(){
		return this.action;
	}
	
	public boolean equals(Action act){
		return this.action.equals(act.action);
	}
	
	public Direction directionAfterAction(Direction dir){
		if(this.equals(TURN_LEFT)){
			if(dir.equals(Direction.LEFT)){
				return Direction.DOWN;
			}else if(dir.equals(Direction.RIGHT)){
				return Direction.UP;
			}else if(dir.equals(Direction.UP)){
				return Direction.LEFT;
			}else{
				//Current Direction is DOWN
				return Direction.RIGHT;
			}
		}else if(this.equals(TURN_RIGHT)){
			if(dir.equals(Direction.LEFT)){
				return Direction.UP;
			}else if(dir.equals(Direction.RIGHT)){
				return Direction.DOWN;
			}else if(dir.equals(Direction.UP)){
				return Direction.RIGHT;
			}else{
				//Current Direction is DOWN
				return Direction.LEFT;
			}
		}else{
			return dir;
		}
	}
}
