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
          matrixDimensionDialog = createtMatrixDimensionDialog();
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

      private class MatrixDimension{
            public MatrixDimension( int rows, int columns ){
               this.rows = rows;
               this.columns = columns;
            }

            public int getRowCount(){ return rows; }
            public int getColumnCount(){ return columns; }

            private int rows;
            private int columns;
      }

      private ChoiceBox createActionChoices(){
          ChoiceBox actionChoices = new ChoiceBox();
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

      private Dialog< MatrixDimension > createtMatrixDimensionDialog(){
          Dialog< MatrixDimension > dialog = new Dialog< MatrixDimension >();
          dialog.setTitle( "New Matrix" );
          dialog.setHeaderText( "Please enter the size of the new matrix." );
          dialog.setResizable( false );

          Label rowSizeLabel = new Label( "Row Size:" );
          Label columnSizeLabel = new Label( "Column Size:" );
          Spinner< Integer > rowSizeSpinner = new Spinner< Integer >();
          Spinner< Integer > columnSizeSpinner = new Spinner< Integer >();

          SpinnerValueFactory<Integer> rowSizeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory( 1, 6, 1 );
          SpinnerValueFactory<Integer> columnSizeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory( 1, 6, 1 );

          rowSizeSpinner.setValueFactory( rowSizeValueFactory );
          columnSizeSpinner.setValueFactory( columnSizeValueFactory );

          GridPane grid = new GridPane();
          grid.setHgap( 10 );
          grid.setVgap( 10 );
          grid.add( rowSizeLabel, 1, 1 );
          grid.add( rowSizeSpinner, 2, 1 );
          grid.add( columnSizeLabel, 1, 2 );
          grid.add( columnSizeSpinner, 2, 2 );

          dialog.getDialogPane().setContent( grid );

          ButtonType createButton = new ButtonType( "Create", ButtonData.OK_DONE );
          dialog.getDialogPane().getButtonTypes().add( createButton );

          dialog.setResultConverter( new Callback< ButtonType, MatrixDimension>(){
              public MatrixDimension call( ButtonType b ){
                  if( b ==  createButton ){
                        int rows = rowSizeSpinner.getValue();
                        int columns = columnSizeSpinner.getValue();
                        return new MatrixDimension( rows, columns );
                  }
                  return null;
              }
          });

          return dialog;
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
          }
      }


      private void showAugmentedMatrixOperations( ChoiceBox actionChoices ){
          actions = actionChoices.getItems();

          for( AugmentedMatrixOperations operations : AugmentedMatrixOperations.values() )
            actions.add( operations.getOperation() );
      }

      private void showMatrixUnaryOperations( ChoiceBox actionChoices ){
          actions = actionChoices.getItems();

          for( MatrixUnaryOperations operations : MatrixUnaryOperations.values() )
            actions.add( operations.getOperation() );
      }

      private void showMatrixDefinitionOperations( ChoiceBox actionChoices ){
          actions = actionChoices.getItems();

          for( MatrixDefinitionOperations operations : MatrixDefinitionOperations.values() )
            actions.add( operations.getOperation() );
      }

      private Matrix matrix;
      private Matrix constantMatrix = new Matrix( 0, 0 );
      public static final int DEFAULT_ELEMENT_SIZE = 60;
      private static final int DEFAULT_GAP = 2;
      private Dialog< MatrixDimension > matrixDimensionDialog;
      private ChoiceBox actionChoices;
      private ObservableList<String> actions;
      private GridPane matrixGridPane = new GridPane();
      private GridPane constantMatrixGridPane = new GridPane();
      private HBox hbox = new HBox( 8 );
      private final static String CHOOSE = "Choose";
}
