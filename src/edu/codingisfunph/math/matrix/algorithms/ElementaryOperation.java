package edu.codingisfunph.math.matrix.algorithms;

import edu.codingisfunph.math.matrix.*;

public class ElementaryOperation{

    public static void scale( Matrix matrix, int row, double nonzero ) throws ZeroValueException {
        if( nonzero != 0.0 )
          for( int j = 0; j < matrix.getColumnCount(); j++ )
            matrix.setEntry( row, j, matrix.getEntry( row, j ) * nonzero );
          else
            throw new ZeroValueException();
    }

    public static void replace( Matrix matrix, int row2, int row1, double nonzero ) throws ZeroValueException {
      if( nonzero != 0.0 )
        for( int j = 0; j < matrix.getColumnCount(); j++ )
          matrix.setEntry( row2, j, matrix.getEntry( row2, j ) + matrix.getEntry( row1, j ) * nonzero );
        else
          throw new ZeroValueException();
    }

    /**
    * Switches the values of row1 and row2
    * @param row1 index of the first row to switch with row2
    * @param row2 index of the second row to switch with row1
    */
    public static void switchRows( Matrix matrix, int row1, int row2 ) throws IndexOutOfBoundsException{
      double temp;
      for( int j = 0; j < matrix.getColumnCount(); j++ ){
        temp = matrix.getEntry( row1, j );
        matrix.setEntry( row1, j, matrix.getEntry( row2, j ) );
        matrix.setEntry( row2, j, temp );
      }
    }

}
