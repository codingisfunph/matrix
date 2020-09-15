package edu.codingisfunph.controls;

import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
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
       }

}
