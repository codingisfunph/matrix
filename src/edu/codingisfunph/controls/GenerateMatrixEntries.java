package edu.codingisfunph.controls;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;

public class GenerateMatrixEntries implements MatrixControlOperation{

      public GenerateMatrixEntries( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.generateMatrixEntries();         
      }

      private MatrixControl matrixControl;
}
