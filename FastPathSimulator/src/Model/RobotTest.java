package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RobotTest {

	@Test
	public void test() {
		
		assertTrue(Action.TURN_LEFT.directionAfterAction(Direction.LEFT).equals(Direction.DOWN));
		assertTrue(Action.TURN_LEFT.directionAfterAction(Direction.RIGHT).equals(Direction.UP));
		assertTrue(Action.TURN_LEFT.directionAfterAction(Direction.UP).equals(Direction.LEFT));
		assertTrue(Action.TURN_LEFT.directionAfterAction(Direction.DOWN).equals(Direction.RIGHT));

		assertTrue(Action.TURN_RIGHT.directionAfterAction(Direction.LEFT).equals(Direction.UP));
		assertTrue(Action.TURN_RIGHT.directionAfterAction(Direction.RIGHT).equals(Direction.DOWN));
		assertTrue(Action.TURN_RIGHT.directionAfterAction(Direction.UP).equals(Direction.RIGHT));
		assertTrue(Action.TURN_RIGHT.directionAfterAction(Direction.DOWN).equals(Direction.LEFT));

		assertTrue(Action.MOVE_FORWARD.directionAfterAction(Direction.LEFT).equals(Direction.LEFT));
		assertTrue(Action.MOVE_FORWARD.directionAfterAction(Direction.RIGHT).equals(Direction.RIGHT));
		assertTrue(Action.MOVE_FORWARD.directionAfterAction(Direction.UP).equals(Direction.UP));
		assertTrue(Action.MOVE_FORWARD.directionAfterAction(Direction.DOWN).equals(Direction.DOWN));

		assertTrue(Action.DRAW_BACK.directionAfterAction(Direction.LEFT).equals(Direction.LEFT));
		assertTrue(Action.DRAW_BACK.directionAfterAction(Direction.RIGHT).equals(Direction.RIGHT));
		assertTrue(Action.DRAW_BACK.directionAfterAction(Direction.UP).equals(Direction.UP));
		assertTrue(Action.DRAW_BACK.directionAfterAction(Direction.DOWN).equals(Direction.DOWN));

		
		
		
		//////////////////
		//Testing MOVE_BACKWARD for all directions
		Robot robot = null;
		robot = new Robot(10, 10, 3, Direction.LEFT);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentDirection().equals(Direction.LEFT));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 9);
	
		robot = new Robot(10, 10, 3, Direction.RIGHT);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentDirection().equals(Direction.RIGHT));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 11);
		
		robot = new Robot(10, 10, 3, Direction.DOWN);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentDirection().equals(Direction.DOWN));
		assertTrue(robot.getLowerLeftRowIndex() == 11);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.UP);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentDirection().equals(Direction.UP));
		assertTrue(robot.getLowerLeftRowIndex() == 9);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		//////////////
		
		
		
		
		//////////////////
		//Testing DRAW_BACK for all directions
		robot = new Robot(10, 10, 3, Direction.LEFT);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentDirection().equals(Direction.LEFT));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 11);
	
		robot = new Robot(10, 10, 3, Direction.RIGHT);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentDirection().equals(Direction.RIGHT));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 9);
		
		robot = new Robot(10, 10, 3, Direction.DOWN);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentDirection().equals(Direction.DOWN));
		assertTrue(robot.getLowerLeftRowIndex() == 9);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.UP);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentDirection().equals(Direction.UP));
		assertTrue(robot.getLowerLeftRowIndex() ==11);
		assertTrue(robot.getLowerLeftColIndex() == 10);
				
		//////////////////
		//Testing TURN_LEFT for all directions
		robot = new Robot(10, 10, 3, Direction.LEFT);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentDirection().equals(Direction.DOWN));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.RIGHT);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentDirection().equals(Direction.UP));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.DOWN);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentDirection().equals(Direction.RIGHT));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.UP);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentDirection().equals(Direction.LEFT));
		assertTrue(robot.getLowerLeftRowIndex() ==10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		
		
		//////////////////
		//Testing TURN_RIGHT for all directions
		robot = new Robot(10, 10, 3, Direction.LEFT);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentDirection().equals(Direction.UP));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.RIGHT);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentDirection().equals(Direction.DOWN));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.DOWN);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentDirection().equals(Direction.LEFT));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Direction.UP);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentDirection().equals(Direction.RIGHT));
		assertTrue(robot.getLowerLeftRowIndex() ==10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
	}

}
