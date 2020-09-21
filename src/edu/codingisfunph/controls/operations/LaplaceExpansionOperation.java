package edu.codingisfunph.controls.operation;


import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.controls.*;

public class LaplaceExpansionOperation implements MatrixControlOperation{

      public LaplaceExpansionOperation( MatrixControl matrixControl ){
          this.matrixControl = matrixControl;
      }

      public void performOperation(){
        if( !matrixControl.isMatrixSquare() ){
          Alert alert = new Alert( AlertType.WARNING );
          alert.setTitle( "Laplace Expansion" );
          alert.setHeaderText( "Determinant is not defined for non-square matrix." );
          alert.setContentText( "Please create a square matrix." );
          alert.showAndWait();
        } else {
          Alert alert = new Alert( AlertType.INFORMATION );
          alert.setTitle( "Laplace Expansion" );
          alert.setHeaderText( "Determinant using the Laplace expansion algorithm." );
          alert.setContentText( String.format( "Determinant: %.4f", matrixControl.laplaceExpansion() ) );
          alert.showAndWait();
        }
      }

      private MatrixControl matrixControl;
}
