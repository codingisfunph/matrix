package edu.codingisfunph.controls.operation;

import java.util.Optional;
import edu.codingisfunph.math.matrix.Matrix;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.codingisfunph.controls.*;
import edu.codingisfunph.controls.dialogs.ScaleRowDialog;

public class ScaleRowOperation implements MatrixControlOperation{

      public ScaleRowOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
          scaleRowDialog.setRowCount( matrixControl.getMatrixRowCount() );
          Optional< ScaleRow > result = scaleRowDialog.showAndWait();

          if( result.isPresent() ){
            if( result.get().getNonzero() != 0.0 ){
              ScaleRow scaleRow = result.get();
              matrixControl.scaleMatrixRow( scaleRow.getRow(), scaleRow.getNonzero() );
            } else {
              Alert alert = new Alert( AlertType.WARNING );
              alert.setTitle( "Scale Row" );
              alert.setHeaderText( "You must provide a nonzero value." );
              alert.setContentText( "Please enter a nonzero value." );
              alert.showAndWait();
            }
          }
      }

      private ScaleRowDialog scaleRowDialog = new ScaleRowDialog();
      private MatrixControl matrixControl;
}
