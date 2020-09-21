package edu.codingisfunph.controls.operation;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;
import edu.codingisfunph.controls.dialogs.SwitchRowsDialog;

public class SwitchRowsOperation implements MatrixControlOperation{

      public SwitchRowsOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          switchRowsDialog.setRowCount( matrixControl.getMatrixRowCount() );
          Optional< SwitchRows > result = switchRowsDialog.showAndWait();
          if( result.isPresent() ){
            SwitchRows switchRows = result.get();
            int row1 = switchRows.getRow1();
            int row2 = switchRows.getRow2();
            matrixControl.switchMatrixRows( row1, row2 );
          }
      }

      private SwitchRowsDialog switchRowsDialog = new SwitchRowsDialog();
      private MatrixControl matrixControl;
}
