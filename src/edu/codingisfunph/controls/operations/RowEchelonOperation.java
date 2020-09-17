package edu.codingisfunph.controls.operation;


import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;

public class RowEchelonOperation implements MatrixControlOperation{

      public RowEchelonOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.rowEchelonForm();
      }

      private MatrixControl matrixControl;
}
