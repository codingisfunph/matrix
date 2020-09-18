package edu.codingisfunph.math.matrix.algorithms;

import edu.codingisfunph.math.matrix.Matrix;

public class GaussianElimination{

        public static Matrix rowEchelon( Matrix matrix ){
            Matrix result = matrix.duplicate();

            rowEchelon( 0, 0, result );

            return result;
        }

        private static void rowEchelon( int pivotRow, int pivotColumn, Matrix matrix ){
            if( ( pivotRow < 0 || pivotRow >= matrix.getRowCount() ) ||
              ( pivotColumn < 0 || pivotColumn >= matrix.getColumnCount() ) ) return;

            selectNonzeroPivot( pivotRow, pivotColumn, matrix );

            if( matrix.getEntry( pivotRow, pivotColumn ) == 0.0  )
                rowEchelon( pivotRow, ++pivotColumn, matrix );
            else{
                createZeroEntries( pivotRow, pivotColumn, matrix );
                rowEchelon( ++pivotRow, ++pivotColumn, matrix );
            }
        }

        private static void createZeroEntries( int pivotRow, int pivotColumn, Matrix matrix ){
            double pivot = matrix.getEntry( pivotRow, pivotColumn );
            for( int i = pivotRow + 1; i < matrix.getRowCount(); i++ ){
               double entry = matrix.getEntry( i, pivotColumn );
               if( entry != 0.0 ){
                  matrix.replace( i, pivotRow, ( entry / pivot ) * -1.0 );
               }
            }
        }

        private static void selectNonzeroPivot( int pivotRow, int pivotColumn, Matrix matrix ){
            int index = findPivotWithGcd( pivotRow, pivotColumn, matrix );
            if( index != -1 && index != pivotRow ){ matrix.switchRows( index, pivotRow ); }
            else if( index == -1 ){
               index = findMaxPivotIndex( pivotRow, pivotColumn, matrix );
               matrix.switchRows( index, pivotRow );
            }
        }

        private static int findPivotWithGcd( int pivotRow, int pivotColumn, Matrix matrix ){
            int index = -1;

            for( int i = pivotRow; i < matrix.getRowCount(); i++ ){
               if( hasGcd( i, pivotColumn, matrix ) ) index =  i;
            }

            return index;
        }

        private static int findMaxPivotIndex( int pivotRow, int pivotColumn, Matrix matrix ){
            int index = pivotRow;

            for( int i = pivotRow + 1; i < matrix.getRowCount(); i++ )
                if( Math.abs( matrix.getEntry( i, pivotColumn ) ) > Math.abs( matrix.getEntry( index, pivotColumn ) ) )
                    index = i;

            return index;
        }

        private static boolean hasGcd( int row, int pivotColumn, Matrix matrix ){
            boolean result = false;

            double gcd = matrix.getEntry( row, pivotColumn );
            for( int j = pivotColumn + 1; j < matrix.getColumnCount(); j++ )
                gcd = gcd( matrix.getEntry( row, j ), gcd );

            result = Math.abs( gcd ) != 0.0 && Math.abs( gcd ) != 1.0 && Math.abs( gcd ) % 1.0 == 0.0;

            return result;
        }

        private static double gcd( double a, double b){
           if( a == 0.0 ) return b;
           else return gcd( b % a, a );
        }
}
