package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlockTest {

	@Test
	public void test() {
		Block test = null;
		test = new Block(2, 2);
		
		assert(test.toLeftOf(Direction.LEFT).equals(new Block(3, 2)));
		assert(test.toLeftOf(Direction.RIGHT).equals(new Block(1, 2)));
		assert(test.toLeftOf(Direction.UP).equals(new Block(2, 1)));
		assert(test.toLeftOf(Direction.DOWN).equals(new Block(2, 3)));

		assert(test.toRightOf(Direction.LEFT).equals(new Block(1, 2)));
		assert(test.toRightOf(Direction.RIGHT).equals(new Block(3, 2)));
		assert(test.toRightOf(Direction.UP).equals(new Block(2, 3)));
		assert(test.toRightOf(Direction.DOWN).equals(new Block(2, 1)));
		
		assert(test.aheadOf(Direction.LEFT).equals(new Block(2, 1)));
		assert(test.aheadOf(Direction.RIGHT).equals(new Block(2, 3)));
		assert(test.aheadOf(Direction.UP).equals(new Block(1, 2)));
		assert(test.aheadOf(Direction.DOWN).equals(new Block(3, 2)));
		
		assert(test.rearOf(Direction.LEFT).equals(new Block(2, 3)));
		assert(test.rearOf(Direction.RIGHT).equals(new Block(2, 1)));
		assert(test.rearOf(Direction.UP).equals(new Block(3, 2)));
		assert(test.rearOf(Direction.DOWN).equals(new Block(1, 2)));
	
	}

}
