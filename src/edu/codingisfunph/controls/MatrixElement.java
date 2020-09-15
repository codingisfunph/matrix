package edu.codingisfunph.controls;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.math.matrix.EntryChangeListener;
import edu.codingisfunph.math.matrix.EntryChangeEvent;
import static edu.codingisfunph.controls.MatrixControl.DEFAULT_ELEMENT_SIZE;

public class MatrixElement extends TextField implements ChangeListener< String >,
                                                        EntryChangeListener {

       public MatrixElement( int row, int column, Matrix matrix ){
           super( String.format( "%2.2f", matrix.getEntry( row, column ) ) );
           setPrefSize( DEFAULT_ELEMENT_SIZE, DEFAULT_ELEMENT_SIZE );
           setAlignment( Pos.CENTER );
           textProperty().addListener( this );
           this.row = row;
           this.column = column;
           this.matrix = matrix;
           this.matrix.addEntryChangeListener( row, column, this );
       }

       public void changed( ObservableValue observable, String oldValue, String newValue ){
            try{
                matrix.setEntry( row, column, Double.parseDouble( newValue) );
            }catch( NumberFormatException ex ){
                textProperty().set( String.format( "%4.2f", matrix.getEntry( row, column ) ) );
            }
       }

       public void entryChanged( EntryChangeEvent e ){
            textProperty().set( String.format( "%4.2f", matrix.getEntry( row, column ) ) );
       }

       public int getRow(){ return row; }
       public int getColumn(){ return column; }
       public Matrix getMatrix(){ return matrix; }

       private int row;
       private int column;
       private Matrix matrix;
}
