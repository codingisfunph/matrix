package edu.codingisfunph.controls;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import edu.codingisfunph.math.matrix.Matrix;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import java.util.Optional;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ChoiceBox;
import javafx.geometry.Pos;
import java.util.Map;
import java.util.HashMap;

public class MatrixControl extends VBox{

      public MatrixControl(){
          super( 8 );
          matrixGridPane.setHgap( DEFAULT_GAP );
          matrixGridPane.setVgap( DEFAULT_GAP );
          scaleRowDialog = new ScaleRowDialog();
          replaceRowDialog = new ReplaceRowDialog();
          switchRowsDialog = new SwitchRowsDialog();
          actionChoices = createActionChoices();
          buildMatrixControlOperations();

          hbox.getChildren().addAll( matrixGridPane );
          hbox.setAlignment( Pos.CENTER );
          getChildren().add( hbox );
          getChildren().add( actionChoices );
          setAlignment( Pos.CENTER );
      }

      public MatrixControl( Matrix matrix ){
         this();
         setMatrix( matrix );
      }

      public void setMatrix( Matrix matrix ){
          this.matrix = matrix;
          removeMatrixElements();
          addMatrixElements();
      }

      public Matrix getMatrix(){
          return matrix;
      }

      private void removeMatrixElements(){
          matrixGridPane.getChildren().clear();
      }

      private void addMatrixElements(){
        for( int i = 0; i < matrix.getRowCount(); i++ ){
          for( int j = 0; j < matrix.getColumnCount(); j++ ){
             matrixGridPane.add( new MatrixElement( i, j, matrix ), j + 1, i + 1 );
          }
        }
      }

      private ChoiceBox< String > createActionChoices(){
          ChoiceBox< String > actionChoices = new ChoiceBox< String >();
          actionChoices.getItems().add(  CHOOSE );
          buildActionChoices( actionChoices );
          actionChoices.setValue( CHOOSE );
          actionChoices.valueProperty().addListener( e -> {
            ObservableValue<String> selectedAction = actionChoices.valueProperty();
            String selectedValue = selectedAction.getValue();
            doAction( selectedValue );
            actionChoices.setValue(CHOOSE);
          });

          return actionChoices;
      }

      public void generateMatrixEntries(){
          matrix.generateRandomEntries();
      }

      public void transposeMatrix(){
          matrix.copyEntries( matrix.transpose() );
      }

      public void negativeMatrix(){
          matrix.copyEntries( matrix.negative() );
      }

      public void scaleMatrixRow( int row, double nonzero ){
          matrix.scale( row, nonzero );
      }

      private void doAction( String action ){
          MatrixControlOperation controlOperation = controlOperations.get( action );
          if( controlOperation != null ) controlOperation.performOperation();
      }

      private void buildMatrixControlOperations(){
          controlOperations.put( MatrixOperations.NEW_MATRIX.toString(), new CreateNewMatrix( this ) );
          controlOperations.put( MatrixOperations.GENERATE_ENTRIES.toString(), new GenerateMatrixEntries( this ) );
          controlOperations.put( MatrixOperations.TRANSPOSE_MATRIX.toString(), new TransposeMatrix( this ) );
          controlOperations.put( MatrixOperations.NEGATIVE_MATRIX.toString(), new NegativeMatrix( this ) );
      }

      private void buildActionChoices( ChoiceBox< String > actionChoices ){
          actions = actionChoices.getItems();

          for( MatrixOperations operation : MatrixOperations.values() )
            actions.add( operation.toString() );
      }

      private Matrix matrix;
      public static final int DEFAULT_ELEMENT_SIZE = 60;
      private static final int DEFAULT_GAP = 2;
      private ChoiceBox< String > actionChoices;
      private ObservableList< String > actions;
      private GridPane matrixGridPane = new GridPane();
      private HBox hbox = new HBox( 8 );
      private final static String CHOOSE = "Choose";
      // Dialogs
      private MatrixDimensionDialog matrixDimensionDialog;
      private ScaleRowDialog scaleRowDialog;
      private ReplaceRowDialog replaceRowDialog;
      private SwitchRowsDialog switchRowsDialog;
      private Map< String, MatrixControlOperation > controlOperations = new HashMap< String, MatrixControlOperation >();
}
