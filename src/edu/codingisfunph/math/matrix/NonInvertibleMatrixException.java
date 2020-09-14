package edu.codingisfunph.math.matrix;

public class NonInvertibleMatrixException extends RuntimeException{

      public NonInvertibleMatrixException(){ this( "Non invertible matrix." ); }

      public NonInvertibleMatrixException( String message ){
          super( message );
      }

}
