package edu.codingisfunph.controls;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;

public class MatrixPower implements MatrixControlOperation{

      public MatrixPower( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
          this.matrixPowerDialog = new MatrixPowerDialog();
      }

      public void performOperation(){
        Optional< Integer > result = matrixPowerDialog.showAndWait();
        if( result.isPresent() ){
          int power = result.get();
          matrixControl.matrixPower( power );
        }
      }

      private MatrixControl matrixControl;
      private MatrixPowerDialog matrixPowerDialog;
}
