package edu.codingisfunph.math.matrix;

public class NonSquareMatrixException extends RuntimeException{

      public NonSquareMatrixException(){ this( "Non square matrix." ); }

      public NonSquareMatrixException( String message ){
          super( message );
      }

}
