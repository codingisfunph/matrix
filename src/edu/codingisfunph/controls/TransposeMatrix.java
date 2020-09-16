package edu.codingisfunph.controls;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;

public class TransposeMatrix implements MatrixControlOperation{

      public TransposeMatrix( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.transposeMatrix();
      }

      private MatrixControl matrixControl;
}
