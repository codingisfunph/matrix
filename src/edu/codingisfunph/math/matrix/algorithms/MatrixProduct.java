package edu.codingisfunph.math.matrix.algorithms;

import edu.codingisfunph.math.matrix.*;

public class MatrixProduct{

    public static Matrix power( Matrix matrix, int r ) throws NonSquareMatrixException {
        if( matrix.isSquare() ){
          Matrix matrixPower = Matrix.createIdentityMatrix( matrix.getRowCount() );

          for( int s = 0; s < r; s++)
            matrixPower = matrixPower.multiply( matrix );

            return matrixPower;
          } else {
            throw new NonSquareMatrixException();
          }
    }

    public static Matrix multiply( Matrix a, Matrix b ) throws MatrixSizeMismatchException{
        // Matrix-Vector Product: Ax = b
        // A is an m x n matrix
        // x is an n-vector
        // b is an m-vector
        if( a.getColumnCount() == b.getRowCount() ){
            Matrix product = new Matrix( a.getRowCount(), b.getColumnCount() );
            Matrix columnVector;
            for( int j = 0; j < b.getColumnCount(); j++ ){
                columnVector = vectorProduct( a, b.getColumnVector( j ) );
                product.setColumnVector( j, columnVector );
            }
              return product;
            } else {
              throw new MatrixSizeMismatchException();
        }
    }

    public static Matrix vectorProduct( Matrix matrix, Matrix vector ) throws MatrixSizeMismatchException {
      if( matrix.getColumnCount() == vector.getRowCount() && vector.isVector() ){
        Matrix product = new Matrix( matrix.getRowCount(), vector.getColumnCount() );
        Matrix columnVector;
        for( int i = 0; i < matrix.getColumnCount(); i++ ){
          columnVector = matrix.getColumnVector( i );
          product = product.add( columnVector.scalarProduct( vector.getEntry( i, 0 ) ) );
        }
        return product;
      } else {
        throw new MatrixSizeMismatchException();
      }
    }

    public static Matrix scalarProduct( Matrix matrix, double scalarValue ){
        Matrix product = matrix.duplicate();

        for( int i = 0; i < matrix.getRowCount(); i++ )
          for( int j = 0; j < matrix.getColumnCount(); j++ )
            product.setEntry( i, j, matrix.getEntry( i, j ) * scalarValue );

        return product;
    }

}
