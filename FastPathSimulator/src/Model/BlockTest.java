package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlockTest {

	@Test
	public void test() {
		Block test = null;
		test = new Block(2, 2);
		
		assert(test.toLeftOf(Orientation.WEST).equals(new Block(3, 2)));
		assert(test.toLeftOf(Orientation.EAST).equals(new Block(1, 2)));
		assert(test.toLeftOf(Orientation.NORTH).equals(new Block(2, 1)));
		assert(test.toLeftOf(Orientation.SOUTH).equals(new Block(2, 3)));

		assert(test.toRightOf(Orientation.WEST).equals(new Block(1, 2)));
		assert(test.toRightOf(Orientation.EAST).equals(new Block(3, 2)));
		assert(test.toRightOf(Orientation.NORTH).equals(new Block(2, 3)));
		assert(test.toRightOf(Orientation.SOUTH).equals(new Block(2, 1)));
		
		assert(test.aheadOf(Orientation.WEST).equals(new Block(2, 1)));
		assert(test.aheadOf(Orientation.EAST).equals(new Block(2, 3)));
		assert(test.aheadOf(Orientation.NORTH).equals(new Block(1, 2)));
		assert(test.aheadOf(Orientation.SOUTH).equals(new Block(3, 2)));
		
		assert(test.rearOf(Orientation.WEST).equals(new Block(2, 3)));
		assert(test.rearOf(Orientation.EAST).equals(new Block(2, 1)));
		assert(test.rearOf(Orientation.NORTH).equals(new Block(3, 2)));
		assert(test.rearOf(Orientation.SOUTH).equals(new Block(1, 2)));
	
	}

}
