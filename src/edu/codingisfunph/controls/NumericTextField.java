package edu.codingisfunph.controls;

import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class NumericTextField extends TextField implements ChangeListener< String >{

        public NumericTextField(){
            textProperty().addListener( this );
        }

        @Override
        public void changed( ObservableValue<? extends String> observable, String oldValue, String newValue ) {
            if( !newValue.matches( "\\d*" ) ) {
                setText( newValue.replaceAll( "[^\\d]", "" ) );
            }
        }
}
