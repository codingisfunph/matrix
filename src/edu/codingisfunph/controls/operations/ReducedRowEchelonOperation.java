package edu.codingisfunph.controls.operation;


import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;

public class ReducedRowEchelonOperation implements MatrixControlOperation{

      public ReducedRowEchelonOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.reducedRowEchelonForm();
      }

      private MatrixControl matrixControl;
}
