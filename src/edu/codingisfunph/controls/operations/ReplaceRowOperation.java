package edu.codingisfunph.controls.operation;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;

public class ReplaceRowOperation implements MatrixControlOperation{

      public ReplaceRowOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          replaceRowDialog.setRowCount( matrixControl.getMatrixRowCount() );
          Optional< ReplaceRow > result = replaceRowDialog.showAndWait();
          if( result.isPresent() ){
            if( result.get().getNonzero() != 0.0 ){
              ReplaceRow replaceRow = result.get();
              int row1 = replaceRow.getRow1();
              int row2 = replaceRow.getRow2();
              double nonzero = replaceRow.getNonzero();
              matrixControl.replaceMatrixRow( row1, row2, nonzero );
            } else {
              Alert alert = new Alert( AlertType.WARNING );
              alert.setTitle( "Replace Row" );
              alert.setHeaderText( "You must provide a nonzero value." );
              alert.setContentText( "Please enter a nonzero value." );
              alert.showAndWait();
            }
          }
      }

      private ReplaceRowDialog replaceRowDialog = new ReplaceRowDialog();
      private MatrixControl matrixControl;
}
