package edu.codingisfunph.controls;

import javafx.scene.layout.GridPane;
import edu.codingisfunph.math.matrix.Matrix;

public class MatrixField extends GridPane{

      public MatrixField(){
          setHgap( DEFAULT_GAP );
          setVgap( DEFAULT_GAP );
      }

      public void setMatrix( Matrix matrix ){
          this.matrix = matrix;
          addElements();
      }

      public Matrix getMatrix(){
          return matrix;
      }

      private void addElements(){
        for( int i = 0; i < matrix.getRowCount(); i++ ){
          for( int j = 0; j < matrix.getColumnCount(); j++ ){
             add( new MatrixElement( i, j, matrix ), j, i );
          }
        }
      }

      private Matrix matrix;
      public static final int DEFAULT_ELEMENT_SIZE = 55;
      private static final int DEFAULT_GAP = 2;
}
