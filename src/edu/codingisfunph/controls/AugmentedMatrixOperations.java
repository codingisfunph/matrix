package edu.codingisfunph.controls;

public enum AugmentedMatrixOperations{

        SCALE_ROW( "Scale Row" ),
        REPLACE_ROW( "Replace Row" ),
        SWITCH_ROWS( "Switch Rows "),
        ROW_ECHELON( "Row Echelon" ),
        REDUCED_ROW_ECHELON( "Reduced Row Echelon" );

        AugmentedMatrixOperations( String operation ){
          this.operation = operation;
        }

        public String getOperation(){ return operation; }

        private final String operation;
}
