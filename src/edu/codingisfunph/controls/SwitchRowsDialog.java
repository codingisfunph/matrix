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

public class SwitchRowsDialog extends Dialog< SwitchRows >{

      public SwitchRowsDialog(){
          super();
          setTitle( "Switch Rows" );
          setHeaderText( "Please enter the rows switch." );
          setResizable( false );
          addComponents();
       }

       private void addComponents(){
          GridPane grid = new GridPane();
          grid.setHgap( 10 );
          grid.setVgap( 10 );
          grid.add( row1IndexLabel, 1, 1 );
          grid.add( row1IndexComboBox, 2, 1 );
          grid.add( row2IndexLabel, 1, 2 );
          grid.add( row2IndexComboBox, 2, 2 );
          getDialogPane().setContent( grid );
          getDialogPane().getButtonTypes().add( okButton );
          setResultConverter( new Callback< ButtonType, SwitchRows >(){
              public SwitchRows call( ButtonType b ){
                    if( b ==  okButton ){
                        int row1 = row1IndexComboBox.getValue();
                        int row2 = row2IndexComboBox.getValue();
                        return new SwitchRows( row1, row2 );
                    }
                  return null;
              }
          });
       }

       public void setRowCount( int rowCount ){
          row1IndexComboBox.getItems().clear();
          row2IndexComboBox.getItems().clear();
          for( int row = 0; row < rowCount; row++ ){
              row1IndexComboBox.getItems().add( row );
              row2IndexComboBox.getItems().add( row );
          }
          row1IndexComboBox.setValue( 0 );
          row2IndexComboBox.setValue( 0 );
       }

       private ButtonType okButton = new ButtonType( "OK", ButtonData.OK_DONE );
       private Label row1IndexLabel = new Label( "Replace Row:" );
       private Label row2IndexLabel = new Label( "Replace By:" );
       private ComboBox< Integer > row1IndexComboBox = new ComboBox< Integer >();
       private ComboBox< Integer > row2IndexComboBox = new ComboBox< Integer >();
}
