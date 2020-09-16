package edu.codingisfunph.controls;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import edu.codingisfunph.math.matrix.Matrix;
import edu.codingisfunph.math.matrix.EntryChangeListener;
import edu.codingisfunph.math.matrix.EntryChangeEvent;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextField;
import java.util.function.UnaryOperator;
import javafx.util.StringConverter;
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
           setTextFormatter( textFormatter );
       }

       public void changed( ObservableValue observable, String oldValue, String newValue ){
            matrix.setEntry( row, column, converter.fromString( newValue ) );
       }

       public void entryChanged( EntryChangeEvent e ){
            textProperty().set( String.format( "%4.2f", matrix.getEntry( row, column ) ) );
       }

       private UnaryOperator< TextFormatter.Change > createFilter( String stringPattern ){
         Pattern validEditingState = Pattern.compile( stringPattern );

         UnaryOperator<TextFormatter.Change> filter = c -> {
           String text = c.getControlNewText();
             if ( validEditingState.matcher( text ).matches() ) {
               return c ;
             } else {
               return null ;
             }
           };

           return filter;
       }


       public int getRow(){ return row; }
       public int getColumn(){ return column; }
       public Matrix getMatrix(){ return matrix; }


       private int row;
       private int column;
       private Matrix matrix;
       private static final String doubleStringPattern = "-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?";
       private UnaryOperator< TextFormatter.Change > filter = createFilter( doubleStringPattern );
       private DoubleStringConverter converter = new DoubleStringConverter();
       private TextFormatter< Double > textFormatter = new TextFormatter< Double >( converter, 0.0, filter );

       class DoubleStringConverter extends StringConverter< Double >{

           @Override
           public Double fromString( String s ) {
               if (s.isEmpty() || "-".equals( s ) || ".".equals( s ) || "-.".equals( s ) ) {
                   return 0.0 ;
               } else {
                   return Double.valueOf( s );
               }
           }

           @Override
           public String toString( Double d ) {
               return d.toString();
           }

       }
}
