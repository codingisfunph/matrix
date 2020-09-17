package edu.codingisfunph.controls.operation;


import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;

public class TransposeMatrixOperation implements MatrixControlOperation{

      public TransposeMatrixOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.transposeMatrix();
      }

      private MatrixControl matrixControl;
}
