package edu.codingisfunph.controls;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;

public class RowEchelon implements MatrixControlOperation{

      public RowEchelon( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.rowEchelonForm();
      }

      private MatrixControl matrixControl;
}
