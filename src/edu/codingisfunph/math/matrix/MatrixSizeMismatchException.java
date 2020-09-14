package edu.codingisfunph.math.matrix;

public class MatrixSizeMismatchException extends RuntimeException{

      public MatrixSizeMismatchException(){ this( "Matrix size does not match" ); }

      public MatrixSizeMismatchException( String message ){
          super( message );
      }

}
