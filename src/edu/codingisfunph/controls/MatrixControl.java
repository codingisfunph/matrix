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

      private ChoiceBox< MatrixOperations > createActionChoices(){
          ChoiceBox< MatrixOperations > actionChoices = new ChoiceBox< MatrixOperations >();
          buildActionChoices( actionChoices );
          actionChoices.valueProperty().addListener( e -> {
            ObservableValue< MatrixOperations > selectedAction = actionChoices.valueProperty();
            MatrixOperations selectedValue = selectedAction.getValue();
            doAction( selectedValue );
            actionChoices.setValue( MatrixOperations.CHOOSE_OPERATION );
          });

          return actionChoices;
      }

      public void generateMatrixEntries(){
          matrix.generateRandomEntries();
      }

      public void transposeMatrix(){
          if( matrix.isSquare() )
            matrix.copyEntries( matrix.transpose() );
          else
            setMatrix( matrix.transpose() );
      }

      public void negativeMatrix(){
          matrix.copyEntries( matrix.negative() );
      }

      public void scaleMatrixRow( int row, double nonzero ){
          matrix.scale( row, nonzero );
      }

      public void reducedRowEchelonForm(){
          matrix.copyEntries( matrix.reducedRowEchelonForm() );
      }

      public void rowEchelonForm(){
          matrix.copyEntries( matrix.rowEchelonForm() );
      }

      public void matrixPower( int value ){
          matrix.copyEntries( matrix.power( value ) );
      }


      private void doAction( MatrixOperations action ){
          MatrixControlOperation controlOperation = controlOperations.get( action );
          if( controlOperation != null ) controlOperation.performOperation();
      }

      private void buildMatrixControlOperations(){
          controlOperations.put( MatrixOperations.NEW_MATRIX, new CreateNewMatrix( this ) );
          controlOperations.put( MatrixOperations.GENERATE_ENTRIES, new GenerateMatrixEntries( this ) );
          controlOperations.put( MatrixOperations.TRANSPOSE_MATRIX, new TransposeMatrix( this ) );
          controlOperations.put( MatrixOperations.NEGATIVE_MATRIX, new NegativeMatrix( this ) );
          controlOperations.put( MatrixOperations.ROW_ECHELON, new RowEchelon( this ) );
          controlOperations.put( MatrixOperations.REDUCED_ROW_ECHELON, new ReducedRowEchelon( this ) );
      }

      private void buildActionChoices( ChoiceBox< MatrixOperations > actionChoices ){
          actions = actionChoices.getItems();

          for( MatrixOperations operation : MatrixOperations.values() )
            actions.add( operation );

          actionChoices.setValue( MatrixOperations.CHOOSE_OPERATION );
      }

      private Matrix matrix;
      public static final int DEFAULT_ELEMENT_SIZE = 60;
      private static final int DEFAULT_GAP = 2;
      private ChoiceBox< MatrixOperations > actionChoices;
      private ObservableList< MatrixOperations > actions;
      private GridPane matrixGridPane = new GridPane();
      private HBox hbox = new HBox( 8 );
      private final static String CHOOSE = "Choose";
      // Dialogs
      private MatrixDimensionDialog matrixDimensionDialog;
      private ScaleRowDialog scaleRowDialog;
      private ReplaceRowDialog replaceRowDialog;
      private SwitchRowsDialog switchRowsDialog;
      private Map< MatrixOperations, MatrixControlOperation > controlOperations = new HashMap< MatrixOperations, MatrixControlOperation >();
}
