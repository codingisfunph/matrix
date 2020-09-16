package edu.codingisfunph.controls;

public enum MatrixOperations{

        NEW_MATRIX( "New Matrix" ),
        GENERATE_ENTRIES( "Generate Entries" ),
        TRANSPOSE_MATRIX( "Tranpose Matrix" ),
        NEGATIVE_MATRIX( "Negative Matrix" ),
        SCALE_ROW( "Scale Row" ),
        REPLACE_ROW( "Replace Row" ),
        SWITCH_ROWS( "Switch Rows "),
        ROW_ECHELON( "Row Echelon" ),
        REDUCED_ROW_ECHELON( "Reduced Row Echelon" );

        MatrixOperations( String operation ){
          this.operation = operation;
        }

        public String getOperation(){ return operation; }
        public String toString(){ return operation; }
        
        private final String operation;
}
