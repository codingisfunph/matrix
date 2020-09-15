package edu.codingisfunph.controls;

public enum MatrixDefinitionOperations{

        NEW_MATRIX( "New Matrix" ),
        NEW_AUGMENTED_MATRIX( "New Augmented Matrix" ),
        GENERATE_ENTRIES( "Generate Entries" );
                
        MatrixDefinitionOperations( String operation ){
          this.operation = operation;
        }

        public String getOperation(){ return operation; }

        private final String operation;
}
