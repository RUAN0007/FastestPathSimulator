package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RobotTest {

	@Test
	public void test() {
		
		assertTrue(Action.TURN_LEFT.orientationAfterAction(Orientation.WEST).equals(Orientation.SOUTH));
		assertTrue(Action.TURN_LEFT.orientationAfterAction(Orientation.EAST).equals(Orientation.NORTH));
		assertTrue(Action.TURN_LEFT.orientationAfterAction(Orientation.NORTH).equals(Orientation.WEST));
		assertTrue(Action.TURN_LEFT.orientationAfterAction(Orientation.SOUTH).equals(Orientation.EAST));

		assertTrue(Action.TURN_RIGHT.orientationAfterAction(Orientation.WEST).equals(Orientation.NORTH));
		assertTrue(Action.TURN_RIGHT.orientationAfterAction(Orientation.EAST).equals(Orientation.SOUTH));
		assertTrue(Action.TURN_RIGHT.orientationAfterAction(Orientation.NORTH).equals(Orientation.EAST));
		assertTrue(Action.TURN_RIGHT.orientationAfterAction(Orientation.SOUTH).equals(Orientation.WEST));

		assertTrue(Action.MOVE_FORWARD.orientationAfterAction(Orientation.WEST).equals(Orientation.WEST));
		assertTrue(Action.MOVE_FORWARD.orientationAfterAction(Orientation.EAST).equals(Orientation.EAST));
		assertTrue(Action.MOVE_FORWARD.orientationAfterAction(Orientation.NORTH).equals(Orientation.NORTH));
		assertTrue(Action.MOVE_FORWARD.orientationAfterAction(Orientation.SOUTH).equals(Orientation.SOUTH));

		assertTrue(Action.DRAW_BACK.orientationAfterAction(Orientation.WEST).equals(Orientation.WEST));
		assertTrue(Action.DRAW_BACK.orientationAfterAction(Orientation.EAST).equals(Orientation.EAST));
		assertTrue(Action.DRAW_BACK.orientationAfterAction(Orientation.NORTH).equals(Orientation.NORTH));
		assertTrue(Action.DRAW_BACK.orientationAfterAction(Orientation.SOUTH).equals(Orientation.SOUTH));

		
		
		
		//////////////////
		//Testing MOVE_BACKWARD for all orientations
		Robot robot = null;
		robot = new Robot(10, 10, 3, Orientation.WEST);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.WEST));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 9);
	
		robot = new Robot(10, 10, 3, Orientation.EAST);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.EAST));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 11);
		
		robot = new Robot(10, 10, 3, Orientation.SOUTH);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.SOUTH));
		assertTrue(robot.getLowerLeftRowIndex() == 11);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.NORTH);
		robot.move(Action.MOVE_FORWARD);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.NORTH));
		assertTrue(robot.getLowerLeftRowIndex() == 9);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		//////////////
		
		
		
		
		//////////////////
		//Testing DRAW_BACK for all orientations
		robot = new Robot(10, 10, 3, Orientation.WEST);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.WEST));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 11);
	
		robot = new Robot(10, 10, 3, Orientation.EAST);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.EAST));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 9);
		
		robot = new Robot(10, 10, 3, Orientation.SOUTH);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.SOUTH));
		assertTrue(robot.getLowerLeftRowIndex() == 9);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.NORTH);
		robot.move(Action.DRAW_BACK);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.NORTH));
		assertTrue(robot.getLowerLeftRowIndex() ==11);
		assertTrue(robot.getLowerLeftColIndex() == 10);
				
		//////////////////
		//Testing TURN_LEFT for all orientations
		robot = new Robot(10, 10, 3, Orientation.WEST);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.SOUTH));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.EAST);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.NORTH));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.SOUTH);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.EAST));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.NORTH);
		robot.move(Action.TURN_LEFT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.WEST));
		assertTrue(robot.getLowerLeftRowIndex() ==10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		
		
		//////////////////
		//Testing TURN_RIGHT for all orientations
		robot = new Robot(10, 10, 3, Orientation.WEST);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.NORTH));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.EAST);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.SOUTH));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.SOUTH);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.WEST));
		assertTrue(robot.getLowerLeftRowIndex() == 10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
		
		robot = new Robot(10, 10, 3, Orientation.NORTH);
		robot.move(Action.TURN_RIGHT);
		assertTrue(robot.getCurrentOrientation().equals(Orientation.EAST));
		assertTrue(robot.getLowerLeftRowIndex() ==10);
		assertTrue(robot.getLowerLeftColIndex() == 10);
	}

}
