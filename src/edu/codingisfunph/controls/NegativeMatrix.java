package edu.codingisfunph.controls;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;

public class NegativeMatrix implements MatrixControlOperation{

      public NegativeMatrix( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.negativeMatrix();
      }

      private MatrixControl matrixControl;
}
