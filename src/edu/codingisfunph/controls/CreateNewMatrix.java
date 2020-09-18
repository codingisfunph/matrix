package edu.codingisfunph.controls;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;

public class CreateNewMatrix implements MatrixControlOperation{

      public CreateNewMatrix( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
          matrixDimensionDialog.setTitle( "New Matrix" );
          matrixDimensionDialog.setHeaderText( "Please enter the size of the new matrix." );
      }

      public void performOperation(){
          Optional< MatrixDimension > result = matrixDimensionDialog.showAndWait();
          if( result.isPresent() ){
            MatrixDimension dimension = result.get();
            matrixControl.setMatrix( new Matrix( dimension.getRowCount(), dimension.getColumnCount() ) );
          }
      }

      private MatrixDimensionDialog matrixDimensionDialog = new MatrixDimensionDialog();;
      private MatrixControl matrixControl;
}
