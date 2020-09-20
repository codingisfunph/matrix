package edu.codingisfunph.math.matrix.algorithms;

import edu.codingisfunph.math.matrix.*;

public class MatrixDeterminant{


        public static double laplaceExpansion( Matrix matrix ) throws NonSquareMatrixException {
               if( !matrix.isSquare() ) throw new NonSquareMatrixException();
               double determinant = 0.0;

               if( matrix.getRowCount() == 1 ) determinant = matrix.getEntry( 0, 0 );
               else if( matrix.getRowCount() == 2){
                  determinant = ( matrix.getEntry( 0, 0 ) * matrix.getEntry( 1, 1 ) ) -
                                ( matrix.getEntry( 0, 1 ) * matrix.getEntry( 1, 0 ) );
               } else {

                  for( int k = 0; k < matrix.getColumnCount(); k++ ){
                     determinant += matrix.getEntry( 0, k ) * cofactor( matrix, 0, k );
                  }

               }

               return determinant;
        }


        public static double minor( Matrix matrix, int i, int j ) throws NonSquareMatrixException {
              if( !matrix.isSquare() ) throw new NonSquareMatrixException();
              double determinant = 0.0;

              Matrix submatrix = matrix.removeRow( i ).removeColumn( j );

              if( submatrix.getRowCount() == 1 ) determinant = submatrix.getEntry( 0, 0 );
              else if( submatrix.getRowCount() == 2){
                 determinant = ( submatrix.getEntry( 0, 0 ) * submatrix.getEntry( 1, 1 ) ) -
                               ( submatrix.getEntry( 0, 1 ) * submatrix.getEntry( 1, 0 ) );
              } else {

                 for( int k = 0; k < submatrix.getColumnCount(); k++ ){
                    determinant += submatrix.getEntry( 0, k ) * cofactor( submatrix, 0, k );
                 }

              }

              return determinant;
        }

        public static double cofactor( Matrix matrix, int i, int j ) throws  NonSquareMatrixException{
              if( !matrix.isSquare() ) throw new NonSquareMatrixException();
              double cofactor = 0.0;

              double sign = Math.pow( -1, i + j );
              cofactor = sign * minor( matrix, i, j );
              return cofactor;
        }
}
