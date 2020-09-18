package edu.codingisfunph.controls.dialogs;

import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import edu.codingisfunph.controls.NumericTextField.IntegerTextField;
import edu.codingisfunph.controls.*;


public class MatrixDimensionDialog extends Dialog< MatrixDimension > implements ChangeListener< Integer >{

      public MatrixDimensionDialog(){
          super();
          setTitle( "New Matrix" );
          setHeaderText( "Please enter the size of the new matrix." );
          setResizable( false );
          rowSizeSpinner.setValueFactory( rowSizeValueFactory );
          columnSizeSpinner.setValueFactory( columnSizeValueFactory );
          rowSizeValueFactory.valueProperty().addListener( this );
          columnSizeValueFactory.valueProperty().addListener( this );
          squareMatrixCheckBox.setIndeterminate( false );
          addComponents();
       }

       private void addComponents(){
            GridPane grid = new GridPane();
            grid.setHgap( 10 );
            grid.setVgap( 10 );
            grid.add( rowSizeLabel, 1, 1 );
            grid.add( rowSizeSpinner, 2, 1 );
            grid.add( columnSizeLabel, 1, 2 );
            grid.add( columnSizeSpinner, 2, 2 );
            grid.add( squareMatrixCheckBox, 2, 3 );
            getDialogPane().setContent( grid );
            getDialogPane().getButtonTypes().add( createButton );
            setResultConverter( new Callback< ButtonType, MatrixDimension>(){
                public MatrixDimension call( ButtonType b ){
                      if( b ==  createButton ){
                          int rows = rowSizeSpinner.getValue();
                          int columns = columnSizeSpinner.getValue();
                          return new MatrixDimension( rows, columns );
                      }
                    return null;
                }
            });
       }

       @Override
       public void changed( ObservableValue observable, Integer oldValue, Integer newValue ){
            if( squareMatrixCheckBox.isSelected() ){
                rowSizeValueFactory.setValue( newValue );
                columnSizeValueFactory.setValue( newValue );
            }
       }

       private ButtonType createButton = new ButtonType( "Create", ButtonData.OK_DONE );
       private Label rowSizeLabel = new Label( "Row Size:" );
       private Label columnSizeLabel = new Label( "Column Size:" );
       private CheckBox squareMatrixCheckBox = new CheckBox( "Square Matrix" );
       private Spinner< Integer > rowSizeSpinner = new Spinner< Integer >();
       private Spinner< Integer > columnSizeSpinner = new Spinner< Integer >();
       private SpinnerValueFactory< Integer > rowSizeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory( 1, 6, 1 );
       private SpinnerValueFactory< Integer > columnSizeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory( 1, 6, 1 );
}
