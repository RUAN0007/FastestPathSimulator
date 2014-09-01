package application;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class FastestPathSimulatorController implements Initializable{

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
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
		//TODO DEBUG
//		System.out.println("row Index: " + rowIndex);
		updateCellIndexDisplay(rowIndex, columnIndex);
		updateCellStateDisplay(rowIndex, columnIndex);
	}
	
  private int computeArenaRowIndex(double yCdn){ //xCdn = Coordinate X on the scene
  		double arenaYCdn = this.arena.getLayoutY();
  		double cellHeight = this.arena.getPrefHeight() / (GlobalUtil.rowCount + 1);
  		int rowIndex = (int)((yCdn - arenaYCdn) / cellHeight);
  		rowIndex--;
  		return rowIndex;
  		
  }
  
  private void updateCellStateDisplay(int rowIndex, int columnIndex) {
	  	Rectangle rec =(Rectangle) this.arena.getScene().lookup("" + rowIndex + "Block" + columnIndex);
	  	//TODO
	  	//Retrieve the state from model
	  	//Set the rec based on state
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
	}
	
	@FXML
	public void onForwardPressed(ActionEvent event){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onForwardPressed");
		}
	}
	
	@FXML
	public void onStartPausedPressed(ActionEvent e ){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onStartPausedPressed");
		}
		if (startpausedButton.isSelected()){
			startpausedButton.setText("Pause");
		}else{
			startpausedButton.setText("Start");

		}
	}
	
	@FXML
	public void onResetPressed(ActionEvent e){
		if(GlobalUtil.ViewDEBUG){
			System.out.println("onResetPressed");
		}
	}

    
    
	
}
