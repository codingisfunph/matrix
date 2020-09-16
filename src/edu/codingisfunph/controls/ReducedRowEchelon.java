package edu.codingisfunph.controls;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;

public class ReducedRowEchelon implements MatrixControlOperation{

      public ReducedRowEchelon( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.reducedRowEchelonForm();
      }

      private MatrixControl matrixControl;
}
