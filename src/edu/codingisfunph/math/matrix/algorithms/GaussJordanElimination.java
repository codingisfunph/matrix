package edu.codingisfunph.math.matrix.algorithms;

import edu.codingisfunph.math.matrix.Matrix;

public class GaussJordanElimination{

       public static Matrix reducedRowEchelon( Matrix matrix ){
            Matrix result = matrix.duplicate();

            result.copyEntries( GaussianElimination.rowEchelon( result ) );

            return result;
       }

       private static void reducedRowEchelon( int pivotRow, int pivotColumn, Matrix matrix ){
          if( ( pivotRow < 0 || pivotRow >= matrix.getRowCount() ) ||
            ( pivotColumn < 0 || pivotColumn >= matrix.getColumnCount() ) ) return;

       }
}
