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
          gridPane.setHgap( DEFAULT_GAP );
          gridPane.setVgap( DEFAULT_GAP );
          matrixDimensionDialog = createtMatrixDimensionDialog();
          actionChoices = createActionChoices();

          hbox.getChildren().add( gridPane );
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
          removeElements();
          addElements();
      }

      public Matrix getMatrix(){
          return matrix;
      }

      private void removeElements(){
          gridPane.getChildren().clear();
      }

      private void addElements(){
        for( int i = 0; i < matrix.getRowCount(); i++ ){
          for( int j = 0; j < matrix.getColumnCount(); j++ ){
             gridPane.add( new MatrixElement( i, j, matrix ), j + 1, i + 1 );
          }
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
          actions = actionChoices.getItems();
          actions.addAll( CHOOSE, NEW_MATRIX, AUGMENTED_MATRIX, GENERATE_ENTRIES, NEGATIVE_MATRIX, TRANSPOSE_MATRIX, REDUCED_ROW_ECHELON );
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

          if( action.equals( NEW_MATRIX ) ){
              Optional< MatrixDimension > result = matrixDimensionDialog.showAndWait();
              if( result.isPresent() ){
                 MatrixDimension dimension = result.get();
                 matrix = new Matrix( dimension.getRowCount(), dimension.getColumnCount() );
                 setMatrix( matrix );
              }
          } else if( action.equals( AUGMENTED_MATRIX ) ){

          } else if( action.equals( GENERATE_ENTRIES ) ){
              matrix.generateRandomEntries();
          } else if( action.equals( NEGATIVE_MATRIX ) ){
              matrix.copyEntries( matrix.negative() );
          } else if( action.equals( TRANSPOSE_MATRIX ) ){
              if( matrix.isSquare () ){
                  matrix.copyEntries( matrix.transpose() );
              } else {
                  Matrix transposed = matrix.transpose();
                  setMatrix( transposed );
              }
          } else if( action.equals( REDUCED_ROW_ECHELON ) ){
             matrix.copyEntries( matrix.reducedRowEchelon( constantMatrix ) );
          }
      }


      private Matrix matrix;
      private Matrix constantMatrix;
      public static final int DEFAULT_ELEMENT_SIZE = 55;
      private static final int DEFAULT_GAP = 2;
      private Dialog< MatrixDimension > matrixDimensionDialog;
      private ChoiceBox actionChoices;
      private ObservableList<String> actions;
      private GridPane gridPane = new GridPane();
      private HBox hbox = new HBox( 8 );
      private final static String CHOOSE = "Choose";
      private final static String NEW_MATRIX = "New Matrix";
      private final static String GENERATE_ENTRIES = "Generate Entries";
      private final static String NEGATIVE_MATRIX = "Negative Matrix";
      private final static String TRANSPOSE_MATRIX = "Transpose Matrix";
      private final static String REDUCED_ROW_ECHELON = "Reduced Row-Echelon";
      private final static String AUGMENTED_MATRIX = "Create Augmented Matrix";
}
