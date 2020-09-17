package edu.codingisfunph.controls.operation;


import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;

public class GenerateMatrixEntriesOperation implements MatrixControlOperation{

      public GenerateMatrixEntriesOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          matrixControl.generateMatrixEntries();
      }

      private MatrixControl matrixControl;
}
