package Model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PathComputerTest {

	@Test
	public void test() {
		int rowCount = 5;
		int colCount = 5;
		Integer[][] map = new Integer[rowCount][colCount];
		for(int rowID = 0;rowID < rowCount;rowID++){
			for(int colID = 0;colID < colCount;colID++){
				map[rowID][colID] = new Integer(0);
			}
		}
		map[1][1] = new Integer(1);
		map[1][2] = new Integer(1);
		map[1][3] = new Integer(1);
		map[1][4] = new Integer(1);

		map[3][0] = new Integer(1);
		map[3][1] = new Integer(1);
		map[3][2] = new Integer(1);
		map[3][3] = new Integer(1);

		//MinStepTurnPathComputer pathComputer = new MinStepTurnPathComputer(1, 1);
		//CloseWallPathComputer pathComputer = new CloseWallPathComputer(Direction.RIGHT);
		DFSPathComputer pathComputer = new DFSPathComputer();
		ArrayList<Action> actions = pathComputer.compute(map, rowCount, colCount,
														rowCount - 1, 0, Direction.UP, 
														0, colCount - 1);
		if(actions == null){
			System.out.println("NO WAY!");
		}else{
			for(Action act:actions){
				System.out.println(act.toString());
			}
		}
	
	}

}
