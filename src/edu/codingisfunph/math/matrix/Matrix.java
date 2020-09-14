package edu.codingisfunph.math.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Matrix{

      public Matrix( int rows, int columns ){
          entries = new double[ rows ][ columns ];
          entriesChangeListeners = new ArrayList< EntryChangeListener >();
          entryChangeListeners = new HashMap< String, ArrayList< EntryChangeListener > >();
      }

      public int getRowCount(){ return entries.length; }

      public int getColumnCount(){ return entries[0].length; }

      public double getEntry( int row, int column ) throws IndexOutOfBoundsException { return entries[ row ][ column ]; }

      public void setEntry( int row, int column, double value ){
        double oldValue = entries[ row ][ column ];
        entries[ row ][ column ] = value;
        EntryChangedEvent event = new EntryChangedEvent( row, column, oldValue, value, this );
        fireEntryChangeEvent( event );
      }

      public void addEntryChangeListener( EntryChangeListener listener ){ entriesChangeListeners.add( listener ); }

      public void addEntryChangeListener( int row, int column, EntryChangeListener listener ){
        String key = String.format( "%d%d", row, column );
        if( entryChangeListeners.containsKey( key ) ){
            entryChangeListeners.get( key ).add( listener );
        } else {
            ArrayList< EntryChangeListener > listeners = new ArrayList< EntryChangeListener >();
            listeners.add( listener );
            entryChangeListeners.put( key, listeners );
        }
      }

      private void fireEntryChangeEvent( EntryChangedEvent event ){
          String key = String.format( "%d%d", event.getRow(), event.getColumn() );

          for( EntryChangeListener listener : entriesChangeListeners ) listener.entryChanged( event );
          for( EntryChangeListener listener : entryChangeListeners.get( key ) ) listener.entryChanged( event );
      }

      public void setRowEntries( int row, double... values ){
          for( int j = 0; j < values.length && j < getColumnCount(); j++ )
              setEntry( row, j, values[ j ] );
      }

      public void setRowEntries( int row, String entries ){
          Scanner scanner = new Scanner( entries );

          int j = 0;
          while( scanner.hasNext() && j < getColumnCount() ){
            setEntry( row, j++, scanner.nextDouble() );
          }
      }

      public void setColumnEntries( int column, double... values ){
          for( int i = 0; i < values.length && i < getRowCount(); i++ )
              setEntry( i, column, values[ i ] );
      }

      public void setColumnEntries( int column, String entries ){
        Scanner scanner = new Scanner( entries );

        int i = 0;
        while( scanner.hasNext() && i < getRowCount() ){
          setEntry( i++, column, scanner.nextDouble() );
        }
      }

      public void scale( int row, double nonzero ) throws ZeroValueException {
          if( nonzero != 0.0 )
            for( int j = 0; j < getColumnCount(); j++ )
              entries[ row ][ j ] = entries[ row ][ j ] * nonzero;
          else
            throw new ZeroValueException();
      }

      public void replace( int row2, int row1, double nonzero ) throws ZeroValueException {
        if( nonzero != 0.0 )
          for( int j = 0; j < getColumnCount(); j++ )
            entries[ row2 ][ j ] += entries[ row1 ][ j ] * nonzero;
        else
          throw new ZeroValueException();
      }

      /**
      * Switches the values of row1 and row2
      * @param row1 index of the first row to switch with row2
      * @param row2 index of the second row to switch with row1
      */
      public void switchRows( int row1, int row2 ) throws IndexOutOfBoundsException{
        double[] temp;
        temp = entries[ row1 ];
        entries[ row1 ] = entries[ row2 ];
        entries[ row2 ] = temp;
      }

      public void generateRandomEntries(){
          LocalTime now = LocalTime.now(); // the value of now.toNanoDay() will be use as a seed for the random generator
          Random random = new Random( now.toNanoOfDay() );

          for( int i = 0; i < getRowCount(); i++ )
            for( int j = 0; j < getColumnCount(); j++ )
                setEntry( i,  j, ( double ) ( -10 + random.nextInt( 20 ) ) );
      }

      // Variables
      private double entries[][];
      private ArrayList< EntryChangeListener > entriesChangeListeners;
      private Map< String, ArrayList< EntryChangeListener > > entryChangeListeners;
}
