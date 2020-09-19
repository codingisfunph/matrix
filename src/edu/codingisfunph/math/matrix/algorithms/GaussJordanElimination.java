package edu.codingisfunph.math.matrix.algorithms;

import edu.codingisfunph.math.matrix.Matrix;

public class GaussJordanElimination{

       public static Matrix reducedRowEchelon( Matrix matrix ){
            Matrix result = matrix.duplicate();

            result.copyEntries( GaussianElimination.rowEchelon( matrix ) );

            reducedRowEchelon( 0, 0, result );

            return result;
       }

       private static void reducedRowEchelon( int pivotRow, int pivotColumn, Matrix matrix ){
          if( ( pivotRow < 0 || pivotRow >= matrix.getRowCount() ) ||
            ( pivotColumn < 0 || pivotColumn >= matrix.getColumnCount() ) ) return;

          if( matrix.getEntry( pivotRow, pivotColumn ) == 0.0 ){
              reducedRowEchelon( pivotRow, ++pivotColumn, matrix );
          } else {

              if( Math.abs( matrix.getEntry( pivotRow, pivotColumn ) ) != 1.0 ){
                double pivot = matrix.getEntry( pivotRow, pivotColumn );
                matrix.scale( pivotRow, ( 1.0 / pivot ) );
              }

              for( int i = pivotRow - 1; i >= 0; i-- ){
                if( matrix.getEntry( i, pivotColumn ) != 0.0 )
                  matrix.replace( i, pivotRow, matrix.getEntry( i, pivotColumn ) * -1.0 );
              }

              reducedRowEchelon( ++pivotRow, ++pivotColumn, matrix );
          }
       }
}
