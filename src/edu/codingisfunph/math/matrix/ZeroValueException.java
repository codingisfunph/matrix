package edu.codingisfunph.math.matrix;

public class ZeroValueException extends RuntimeException{

      public ZeroValueException(){ this( "Zero value not permitted." ); }

      public ZeroValueException( String message ){
          super( message );
      }

}
