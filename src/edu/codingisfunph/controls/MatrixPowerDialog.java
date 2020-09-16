package edu.codingisfunph.controls;

import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.util.Optional;

public class MatrixPowerDialog extends Dialog< Integer >{

      public MatrixPowerDialog(){
          super();
          setTitle( "Matrix Power" );
          setHeaderText( "Please enter a non-negative integer." );
          setResizable( false );
          addComponents();
       }

       private void addComponents(){
          GridPane grid = new GridPane();
          grid.setHgap( 10 );
          grid.setVgap( 10 );
          grid.add( nonNegativeIntegerLabel, 1, 1 );
          grid.add( integerTextField, 2, 1 );
          getDialogPane().setContent( grid );
          getDialogPane().getButtonTypes().add( okButton );
          setResultConverter( new Callback< ButtonType, Integer >(){
              public Integer call( ButtonType b ){
                    if( b ==  okButton ){
                        return integerTextField.getText();
                    }
                  return null;
              }
          });
       }

       private ButtonType okButton = new ButtonType( "OK", ButtonData.OK_DONE );
       private Label nonNegativeIntegerLabel = new Label( "Non-negative integer:" );
       private NumericTextField.IntegerTextField integerTextField = new NumericTextField.IntegerTextField();
}
