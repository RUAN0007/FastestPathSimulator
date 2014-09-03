package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Model.CustomizedArena;
import Model.CustomizedArena.ArenaException;
import Model.Direction;
import Model.FastPathModel;
import Model.FastPathModel.Cell;
import Model.FastPathModel.SimulatorException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FastestPathSimulatorController implements Initializable{

	private static Color OBSTACLE_COLOR = Color.BLACK;
	private static Color EMPTY_COLOR = Color.WHITE;
	private static Color ROBOT_COLOR = Color.BLUE;
	private static Color DIRECTION_COLOR = Color.RED;
	private static Color PATH_COLOR = Color.VIOLET;
	private static Color UNEXPLORED_COLOR = Color.GRAY;
	
	@FXML
	GridPane arena;
	
	@FXML
	Label msgLabel;
	
	@FXML
	Label rowIndexLabel;
	
	@FXML
	Label colIndexLabel;
	
	@FXML
	Label cellTypeLabel;
	
	@FXML
	Label stepCountLabel;
	
	@FXML
	Label turnCountLabel;
	
	
	@FXML
	Rectangle demoCell;
	
	@FXML
	ToggleButton startpausedButton;
	
	@FXML
	Button resetButton;
	
	
	@FXML
	Button backwardButton;
	
	@FXML
	Button forwardButton;
	
	@FXML
	ChoiceBox<String> secondsPerStepChoiceBox;
	
	FastPathModel model = null;
	
	private Stage stage;
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startpausedButton.setDisable(true);
		forwardButton.setDisable(true);
		backwardButton.setDisable(true);
		resetButton.setDisable(true);
		
		secondsPerStepChoiceBox.getItems().add("1");
		secondsPerStepChoiceBox.getItems().add("2");
		secondsPerStepChoiceBox.getItems().add("3");
		secondsPerStepChoiceBox.getItems().add("5");
		secondsPerStepChoiceBox.setValue("1");
		addBlocks();
	}
	
	
	private void addBlocks() {
		
		double blockWidth = arena.getPrefWidth() / (GlobalUtil.columnCount + 1) * 0.9;
		double blockHeight = arena.getPrefHeight() / (GlobalUtil.rowCount + 1) * 0.9;
		
		for(int rowLabelIndex = 0;rowLabelIndex <= GlobalUtil.rowCount;rowLabelIndex++){
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100.0 / (GlobalUtil.rowCount + 1));
			arena.getRowConstraints().add(row);
		}
		
		for(int colLabelIndex = 0;colLabelIndex <= GlobalUtil.columnCount;colLabelIndex++){
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(100.0 / (GlobalUtil.columnCount + 1));
			arena.getColumnConstraints().add(col);
		}
		
		
		for(int colLabelIndex = 1;colLabelIndex <= GlobalUtil.columnCount;colLabelIndex++){
			Label colLabel = new Label("" + colLabelIndex);
			colLabel.setFont(new Font(GlobalUtil.indexFont));
			colLabel.setMinSize(blockWidth, blockHeight);
			colLabel.setAlignment(Pos.CENTER);
			arena.add(colLabel, colLabelIndex, 0);
			
		}

		for(int rowLabelIndex = 1;rowLabelIndex <= GlobalUtil.rowCount;rowLabelIndex++){
			
			Label colLabel = new Label("" + rowLabelIndex);
			colLabel.setFont(new Font(GlobalUtil.indexFont));

			colLabel.setMinSize(blockWidth, blockHeight);
			colLabel.setAlignment(Pos.CENTER);
			arena.add(colLabel, 0, rowLabelIndex);
			
		}
	
		
		
		for(int rowIndex = 1; rowIndex <= GlobalUtil.rowCount;rowIndex++){
			for(int colIndex = 1; colIndex <= GlobalUtil.columnCount;colIndex++){
				Rectangle rec = new Rectangle(blockWidth, blockHeight);
				rec.setId("" + (rowIndex - 1) + "Block" + (colIndex - 1));
				rec.setArcHeight(20);
				rec.setArcWidth(20);
				arena.add(rec,colIndex, rowIndex);
			}
		}
		
	}



	@FXML
	public void onDescriptorLoaded(){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onDescriptorLoaded");
		}
		
		String descriptor = getDescriptorFromFile();
		
		CustomizedArena arena = null;
		try {
			arena = new CustomizedArena(GlobalUtil.rowCount, 
									   GlobalUtil.columnCount);
			arena.setDescriptor(descriptor);
		} catch (ArenaException e) {
			this.msgLabel.setText(e.getMessage());
			return;
		}
		
			FastPathModel newModel;
		try {
			newModel = new FastPathModel(arena,
										GlobalUtil.lowerLeftStartRowIndex,
										GlobalUtil.lowerLeftStartColIndex,
										Direction.UP,
										GlobalUtil.lowerLeftGoalRowIndex,
										GlobalUtil.lowerLeftGoalColIndex,
										GlobalUtil.robotDiameterInCellNumber
					
										);
			this.model = newModel;

		} catch (SimulatorException e) {
			this.msgLabel.setText(e.getMessage());
			return;
		}
		
		this.startpausedButton.setDisable(false);
		this.forwardButton.setDisable(false);
		this.resetButton.setDisable(false);
		refleshView();
		
	}
	
	private void refleshView() {
		int rowCount = this.model.getArenaRowCount();
		int colCount = this.model.getArenaColCount();
		
		for(int rowID = 0;rowID < rowCount; rowID++){
			for(int colID = 0;colID < colCount;colID++){
				Rectangle rectToPaint = getRectangle(rowID,colID);
				Cell cellModel = this.model.getCellStatus(rowID, colID);
				paintRectBasedOnStatus(rectToPaint,cellModel);
			}
		}
		
		this.stepCountLabel.setText("" + this.model.getCurrentStepCount());
		this.turnCountLabel.setText("" + this.model.getCurrentTurnCount());

	}
	
	private void paintRectBasedOnStatus(Rectangle rectToPaint, Cell cellModel) {
		if(cellModel == Cell.UNEXMPLORED){
			rectToPaint.setFill(UNEXPLORED_COLOR);
			
		}else if(cellModel == Cell.EMPTY){
			rectToPaint.setFill(EMPTY_COLOR);
			
		}else if(cellModel == Cell.OBSTACLE){
			rectToPaint.setFill(OBSTACLE_COLOR);
			
		}else if(cellModel == Cell.ROBOT){
			rectToPaint.setFill(ROBOT_COLOR);
			
		}else if(cellModel == Cell.ROBOT_DIRECTION){
			rectToPaint.setFill(DIRECTION_COLOR);
			
		}else if(cellModel == Cell.PATH){
			rectToPaint.setFill(PATH_COLOR);
			
		}else if(cellModel == Cell.EMPTY){
			rectToPaint.setFill(EMPTY_COLOR);
		}
	}
	private Rectangle getRectangle(int rowID, int colID) {
		return (Rectangle) this.arena.getScene().lookup("" + rowID + "Block" + colID);
	}
	
	 
	
	private FileChooser getMapDescriptorFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(
				new ExtensionFilter("Map Descripter", "*.txt"));
		return fileChooser;
	}
	
	private String getDescriptorFromFile() {
		FileChooser fileChooser = getMapDescriptorFileChooser();
		File descriptorFile = fileChooser.showOpenDialog(this.stage);
		StringBuilder mapDescriptors = new StringBuilder();
		if(descriptorFile != null){
			
			try(BufferedReader br = new BufferedReader(
					new FileReader(descriptorFile))) {
				mapDescriptors.append(br.readLine());
				mapDescriptors.append("\n");
				mapDescriptors.append(br.readLine());
				
			} catch (IOException e) {
				//e.printStackTrace();
				this.msgLabel.setText("Open up " + descriptorFile.getName() + " failed...");
				return null;
			}
			return mapDescriptors.toString();
			
		}
		return null;
	}



	@FXML
	public void onArenaHovered(MouseEvent me){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onArenaHovered");
		}
		double xCdn = me.getSceneX();
		double yCdn = me.getSceneY();
		
		int rowIndex = computeArenaRowIndex(yCdn);
		int columnIndex = computeArenaColumnIndex(xCdn);

		updateCellIndexDisplay(rowIndex, columnIndex);
		if(this.model != null){
			updateCellStateDisplay(rowIndex, columnIndex);
		}
	}
	
  private int computeArenaRowIndex(double yCdn){ //xCdn = Coordinate X on the scene
  		double arenaYCdn = this.arena.getLayoutY();
  		double cellHeight = this.arena.getPrefHeight() / (GlobalUtil.rowCount + 1);
  		int rowIndex = (int)((yCdn - arenaYCdn) / cellHeight);
  		rowIndex--;
  		return rowIndex;
  		
  }
  
  private void updateCellStateDisplay(int rowIndex, int columnIndex) {
	  	Cell cell = this.model.getCellStatus(rowIndex, columnIndex);
	  	paintRectBasedOnStatus(this.demoCell, cell);
	  	updateDemoLabel(cell);
	}
  
  
  
  private void updateDemoLabel(Cell cell) {
	  if(cell == Cell.UNEXMPLORED){
		  	this.cellTypeLabel.setText("UNEXPLORED");
		  	
		}else if(cell == Cell.EMPTY){
		  	this.cellTypeLabel.setText("EMPTY");
			
		}else if(cell == Cell.OBSTACLE){
		  	this.cellTypeLabel.setText("OBSTACLE");
			
		}else if(cell == Cell.ROBOT){
		  	this.cellTypeLabel.setText("ROBOT");
			
		}else if(cell == Cell.ROBOT_DIRECTION){
		  	this.cellTypeLabel.setText("ROBOT_DIRECTION");
			
		}else if(cell == Cell.PATH){
		  	this.cellTypeLabel.setText("PATH");
			
		}
  }
//return a value between [0,GlobalUtil.columnCount - 1]
  private int computeArenaColumnIndex(double xCdn){ //xCdn = Coordinate X on the scene
		double arenaXCdn = this.arena.getLayoutX();
		double cellWidth = this.arena.getWidth() / (GlobalUtil.columnCount + 1);
		int columnIndex = (int)((xCdn - arenaXCdn) / cellWidth);
		columnIndex--;
		return columnIndex;
		
  }
  
  private void updateCellIndexDisplay(int rowIndex, int columnIndex) {
		if((0 <= rowIndex && rowIndex <= GlobalUtil.rowCount - 1 ) &&
		     (0 <= columnIndex && columnIndex <= GlobalUtil.columnCount - 1)){
			this.rowIndexLabel.setText("" + (rowIndex + 1));
			this.colIndexLabel.setText("" + (columnIndex + 1));

		}else{
			this.rowIndexLabel.setText("-");
			this.colIndexLabel.setText("-");

		}
	}
	
	@FXML
	public void onBackwardPressed(){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onBackwardPressed");
		}
		this.model.backward();
		refleshView();
		if(!this.model.hasPreStep()){
			this.backwardButton.setDisable(true);
		}
		this.forwardButton.setDisable(false);
	}
	
	@FXML
	public void onForwardPressed(ActionEvent event){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onForwardPressed");
		}
		this.model.forward();
		refleshView();
		if(!this.model.hasNextStep()){
			this.forwardButton.setDisable(true);
		}
		this.backwardButton.setDisable(false);
	}
	
	@FXML
	Timer timer = null;
	public void onStartPausedPressed(ActionEvent e ){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onStartPausedPressed");
		}
		if (startpausedButton.isSelected()){
			//Start button is pressed
			
			this.resetButton.setDisable(true);
			this.forwardButton.setDisable(true);
			this.backwardButton.setDisable(true);
			startpausedButton.setText("Pause");
			
			timer = new Timer();
			int secondPerStep = Integer.parseInt(this.secondsPerStepChoiceBox.getValue());
			timer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					model.forward();
					boolean hasNextStep = model.hasNextStep();
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							//Update the view in JavaFX thread
							refleshView();
							if(!hasNextStep){
								startpausedButton.setSelected(false);
								onPausedPressed();

							}
						} //End of run() in Runnuble
					}); //End of runLater() method
					
				}//End of run() in TimerTask
			}, 1000, 1000 * secondPerStep);
			
		}else{
			//Pause button is pressed
			onPausedPressed();
		}
	}
	
	protected void onPausedPressed() {
		timer.cancel();
		startpausedButton.setText("Start");
		this.resetButton.setDisable(false);
		if(model.hasNextStep()){
			this.forwardButton.setDisable(true);
		}
		if(model.hasPreStep()){
			this.backwardButton.setDisable(true);
		}
		
	}
	@FXML
	public void onResetPressed(ActionEvent e){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onResetPressed");
		}
		this.model.reset();
		this.refleshView();
	}

    
    
	
}
