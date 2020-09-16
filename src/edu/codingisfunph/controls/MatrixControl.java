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

public class MatrixControl extends VBox{

      public MatrixControl(){
          super( 8 );
          matrixGridPane.setHgap( DEFAULT_GAP );
          matrixGridPane.setVgap( DEFAULT_GAP );
          constantMatrixGridPane.setHgap( DEFAULT_GAP );
          constantMatrixGridPane.setVgap( DEFAULT_GAP );
          matrixDimensionDialog = new MatrixDimensionDialog();
          scaleRowDialog = new ScaleRowDialog();
          replaceRowDialog = new ReplaceRowDialog();
          switchRowsDialog = new SwitchRowsDialog();
          actionChoices = createActionChoices();

          hbox.getChildren().addAll( matrixGridPane, constantMatrixGridPane );
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
          removeConstantMatrixElements();
          addMatrixElements();
          addConstantMatrixElements();
      }

      public Matrix getMatrix(){
          return matrix;
      }

      private void removeMatrixElements(){
          matrixGridPane.getChildren().clear();
      }

      private void removeConstantMatrixElements(){
          constantMatrixGridPane.getChildren().clear();
      }

      private void addMatrixElements(){
        for( int i = 0; i < matrix.getRowCount(); i++ ){
          for( int j = 0; j < matrix.getColumnCount(); j++ ){
             matrixGridPane.add( new MatrixElement( i, j, matrix ), j + 1, i + 1 );
          }
        }
      }

      private void addConstantMatrixElements(){
        for( int i = 0; i < constantMatrix.getRowCount(); i++ ){
          constantMatrixGridPane.add( new MatrixElement( i, 0, constantMatrix ), 1, i + 1 );
        }
      }

      private ChoiceBox< String > createActionChoices(){
          ChoiceBox< String > actionChoices = new ChoiceBox< String >();
          actionChoices.getItems().add(  CHOOSE );
          showMatrixDefinitionOperations( actionChoices );
          showMatrixUnaryOperations( actionChoices );
          actionChoices.setValue( CHOOSE );
          actionChoices.valueProperty().addListener( e -> {
            ObservableValue<String> selectedAction = actionChoices.valueProperty();
            String selectedValue = selectedAction.getValue();
            doAction( selectedValue );
            actionChoices.setValue(CHOOSE);
          });

          return actionChoices;
      }

      private void doAction( String action ){
          if( action == null ) return;
          if( action.equals( MatrixDefinitionOperations.NEW_MATRIX.getOperation() ) ){
              matrixDimensionDialog.setTitle( "New Matrix" );
              matrixDimensionDialog.setHeaderText( "Please enter the size of the new matrix." );
              Optional< MatrixDimension > result = matrixDimensionDialog.showAndWait();
              if( result.isPresent() ){
                 MatrixDimension dimension = result.get();
                 matrix = new Matrix( dimension.getRowCount(), dimension.getColumnCount() );
                 constantMatrix = new Matrix( 0, 0 );
                 setMatrix( matrix );
                 constantMatrixGridPane.setVisible( false );
                 actionChoices.getItems().clear();
                 actionChoices.getItems().add( CHOOSE );
                 actionChoices.setValue( CHOOSE );
                 showMatrixDefinitionOperations( actionChoices );
                 showMatrixUnaryOperations( actionChoices );
              }
          } else if( action.equals( MatrixDefinitionOperations.NEW_AUGMENTED_MATRIX.getOperation() ) ){
              matrixDimensionDialog.setTitle( "Create Augmented Matrix" );
              matrixDimensionDialog.setHeaderText( "Please enter the size of the coefficient matrix." );
              Optional< MatrixDimension > result = matrixDimensionDialog.showAndWait();
              if( result.isPresent() ){
                 MatrixDimension dimension = result.get();
                 matrix = new Matrix( dimension.getRowCount(), dimension.getColumnCount() );
                 constantMatrix = Matrix.createColumnMatrix( dimension.getRowCount() );
                 setMatrix( matrix );
                 constantMatrixGridPane.setVisible( true );
                 actionChoices.getItems().clear();
                 actionChoices.getItems().add( CHOOSE );
                 actionChoices.setValue( CHOOSE );
                 showMatrixDefinitionOperations( actionChoices );
                 showAugmentedMatrixOperations( actionChoices );
              }
          } else if( action.equals( MatrixDefinitionOperations.GENERATE_ENTRIES.getOperation() ) ){
              matrix.generateRandomEntries();
              constantMatrix.generateRandomEntries();
          } else if( action.equals( MatrixUnaryOperations.NEGATIVE_MATRIX.getOperation() ) ){
              matrix.copyEntries( matrix.negative() );
          } else if( action.equals( MatrixUnaryOperations.TRANSPOSE_MATRIX.getOperation() ) ){
              if( matrix.isSquare () ){
                  matrix.copyEntries( matrix.transpose() );
              } else {
                  Matrix transposed = matrix.transpose();
                  setMatrix( transposed );
              }
          } else if( action.equals( AugmentedMatrixOperations.REDUCED_ROW_ECHELON.getOperation() ) ){
              matrix.copyEntries( matrix.reducedRowEchelon( constantMatrix ) );
          } else if( action.equals( AugmentedMatrixOperations.SCALE_ROW.getOperation() ) ){
              scaleRowDialog.setRowCount( matrix.getRowCount() );
              Optional< ScaleRow > result = scaleRowDialog.showAndWait();
              if( result.isPresent() ){
                  ScaleRow scaleRow = result.get();
                  matrix.scale( scaleRow.getRow(), scaleRow.getNonzero() );
                  constantMatrix.scale( scaleRow.getRow(), scaleRow.getNonzero() );
              }
          } else if( action.equals( AugmentedMatrixOperations.REPLACE_ROW.getOperation() ) ){
              replaceRowDialog.setRowCount( matrix.getRowCount() );
              Optional< ReplaceRow > result = replaceRowDialog.showAndWait();
              if( result.isPresent() ){
                  ReplaceRow replaceRow = result.get();
                  matrix.replace( replaceRow.getRow1(), replaceRow.getRow2(), replaceRow.getNonzero() );
                  constantMatrix.replace( replaceRow.getRow1(), replaceRow.getRow2(), replaceRow.getNonzero() );
              }
          }  else if( action.equals( AugmentedMatrixOperations.SWITCH_ROWS.getOperation() ) ){
              switchRowsDialog.setRowCount( matrix.getRowCount() );
              Optional< SwitchRows > result = switchRowsDialog.showAndWait();
              if( result.isPresent() ){
                  SwitchRows switchRows = result.get();                  
                  matrix.switchRows( switchRows.getRow1(), switchRows.getRow2() );
                  constantMatrix.switchRows( switchRows.getRow1(), switchRows.getRow2() );
              }
          }
      }


      private void showAugmentedMatrixOperations( ChoiceBox< String > actionChoices ){
          actions = actionChoices.getItems();

          for( AugmentedMatrixOperations operations : AugmentedMatrixOperations.values() )
            actions.add( operations.getOperation() );
      }

      private void showMatrixUnaryOperations( ChoiceBox< String > actionChoices ){
          actions = actionChoices.getItems();

          for( MatrixUnaryOperations operations : MatrixUnaryOperations.values() )
            actions.add( operations.getOperation() );
      }

      private void showMatrixDefinitionOperations( ChoiceBox< String > actionChoices ){
          actions = actionChoices.getItems();

          for( MatrixDefinitionOperations operations : MatrixDefinitionOperations.values() )
            actions.add( operations.getOperation() );
      }

      private Matrix matrix;
      private Matrix constantMatrix = new Matrix( 0, 0 );
      public static final int DEFAULT_ELEMENT_SIZE = 60;
      private static final int DEFAULT_GAP = 2;
      private ChoiceBox< String > actionChoices;
      private ObservableList< String > actions;
      private GridPane matrixGridPane = new GridPane();
      private GridPane constantMatrixGridPane = new GridPane();
      private HBox hbox = new HBox( 8 );
      private final static String CHOOSE = "Choose";
      // Dialogs
      private MatrixDimensionDialog matrixDimensionDialog;
      private ScaleRowDialog scaleRowDialog;
      private ReplaceRowDialog replaceRowDialog;
      private SwitchRowsDialog switchRowsDialog;
}
