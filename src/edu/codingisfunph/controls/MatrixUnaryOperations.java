package edu.codingisfunph.controls;

public enum MatrixUnaryOperations{

        TRANSPOSE_MATRIX( "Tranpose Matrix" ),
        NEGATIVE_MATRIX( "Negative Matrix" );

        MatrixUnaryOperations( String operation ){
          this.operation = operation;
        }

        public String getOperation(){ return operation; }

        private final String operation;
}
