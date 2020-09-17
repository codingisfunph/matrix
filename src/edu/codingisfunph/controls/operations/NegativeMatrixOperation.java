package edu.codingisfunph.controls.operation;


import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;

public class NegativeMatrixOperation implements MatrixControlOperation{

      public NegativeMatrixOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.negativeMatrix();
      }

      private MatrixControl matrixControl;
}
