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

public class ScaleRowDialog extends Dialog< ScaleRow >{

      public ScaleRowDialog(){
          super();
          setTitle( "Scale Row" );
          setHeaderText( "Please enter the row to scale and a nonzero value." );
          setResizable( false );
          addComponents();
       }

       private void addComponents(){
          GridPane grid = new GridPane();
          grid.setHgap( 10 );
          grid.setVgap( 10 );
          grid.add( rowIndexLabel, 1, 1 );
          grid.add( rowIndexComboBox, 2, 1 );
          grid.add( nonzeroLabel, 1, 2 );
          grid.add( nonzeroTextField, 2, 2 );
          getDialogPane().setContent( grid );
          getDialogPane().getButtonTypes().add( okButton );
          setResultConverter( new Callback< ButtonType, ScaleRow >(){
              public ScaleRow call( ButtonType b ){
                    if( b ==  okButton ){
                        int rows = rowIndexComboBox.getValue();
                        double nonzero = Double.parseDouble( nonzeroTextField.getText() );
                        return new ScaleRow( rows, nonzero );
                    }
                  return null;
              }
          });
       }

       public void setRowCount( int rowCount ){
          rowIndexComboBox.getItems().clear();
          for( int row = 0; row < rowCount; row++ )
              rowIndexComboBox.getItems().add( row );
          rowIndexComboBox.setValue( 0 );
       }

       private ButtonType okButton = new ButtonType( "OK", ButtonData.OK_DONE );
       private Label rowIndexLabel = new Label( "Row:" );
       private Label nonzeroLabel = new Label( "Non-zero value:" );
       private ComboBox< Integer > rowIndexComboBox = new ComboBox< Integer >();
       private NumericTextField.DoubleTextField nonzeroTextField = new NumericTextField.DoubleTextField();
}
