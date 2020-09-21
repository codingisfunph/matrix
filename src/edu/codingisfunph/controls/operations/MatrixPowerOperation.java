package edu.codingisfunph.controls.operation;


import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;
import edu.codingisfunph.controls.dialogs.MatrixPowerDialog;

public class MatrixPowerOperation implements MatrixControlOperation{

      public MatrixPowerOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
          this.matrixPowerDialog = new MatrixPowerDialog();
      }

      public void performOperation(){
        if( !matrixControl.isMatrixSquare() ){
          Alert alert = new Alert( AlertType.WARNING );
          alert.setTitle( "Matrix Power" );
          alert.setHeaderText( "Matrix power requires a square matrix." );
          alert.setContentText( "Please create a square matrix." );
          alert.showAndWait();
        } else {
          Optional< Integer > result = matrixPowerDialog.showAndWait();
          if( result.isPresent() ){
            int power = result.get();
            matrixControl.matrixPower( power );
          }
        }
      }

      private MatrixControl matrixControl;
      private MatrixPowerDialog matrixPowerDialog;
}
