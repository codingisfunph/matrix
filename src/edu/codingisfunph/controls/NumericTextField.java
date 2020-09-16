package edu.codingisfunph.controls;

import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextField;
import java.util.function.UnaryOperator;
import javafx.util.StringConverter;

public class NumericTextField{


      public static class DoubleTextField extends TextField {

            public DoubleTextField(){
                setTextFormatter( textFormatter );
            }

            UnaryOperator< TextFormatter.Change > filter = createFilter( doubleStringPattern );
            DoubleStringConverter converter = new DoubleStringConverter();
            TextFormatter< Double > textFormatter = new TextFormatter< Double >( converter, 0.0, filter );
      }

      public static class IntegerTextField extends TextField {

            public IntegerTextField(){
                setTextFormatter( textFormatter );
            }

            UnaryOperator< TextFormatter.Change > filter = createFilter( integerStringPattern );
            IntegerStringConverter converter = new IntegerStringConverter();
            TextFormatter< Integer > textFormatter = new TextFormatter< Integer >( converter, 0, filter );
      }


      private static UnaryOperator< TextFormatter.Change > createFilter( String stringPattern ){
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

      private static final String doubleStringPattern = "-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?";
      private static final String integerStringPattern = "[^\\d]";
}


class IntegerStringConverter extends StringConverter< Integer >{

    @Override
    public Integer fromString( String s ) {
        if (s.isEmpty() || "-".equals( s ) || ".".equals( s ) || "-.".equals( s ) ) {
            return 0 ;
        } else {
            return Integer.valueOf( s );
        }
    }

    @Override
    public String toString( Integer i ) {
        return i.toString();
    }
}


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
