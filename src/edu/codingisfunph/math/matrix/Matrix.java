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
        EntryChangeEvent event = new EntryChangeEvent( row, column, oldValue, value, this );
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

      private void fireEntryChangeEvent( EntryChangeEvent event ){
          String key = String.format( "%d%d", event.getRow(), event.getColumn() );

          for( EntryChangeListener listener : entriesChangeListeners ) listener.entryChanged( event );

          if( entryChangeListeners.containsKey( key ) )
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

      public Matrix negative(){
        Matrix negativeMatrix = new Matrix( getRowCount(), getColumnCount() );

        for( int i = 0; i < getRowCount(); i++ )
          for( int j = 0; j < getColumnCount(); j++ )
            negativeMatrix.setEntry( i, j, entries[ i ][ j ] * -1 );

        return negativeMatrix;
      }

      public void copyEntries( Matrix matrix ) throws MatrixSizeMismatchException{
          if( sizeEquals( matrix ) ){
            for( int i = 0; i < getRowCount(); i++ )
              for( int j = 0; j < getColumnCount(); j++ )
                setEntry( i, j, matrix.getEntry( i, j ) );
          } else {
            throw new MatrixSizeMismatchException();
          }
      }

      public boolean sizeEquals( Matrix matrix ){
          if( getRowCount() == matrix.getRowCount() &&
              getColumnCount() == matrix.getColumnCount() )
              return true;
          else
              return false;
      }

      public Matrix transpose(){
          Matrix transposedMatrix = new Matrix( getColumnCount(), getRowCount() );

          for( int i = 0; i < getRowCount(); i++ )
            for( int j = 0; j < getColumnCount(); j++ )
              transposedMatrix.setEntry( j, i, getEntry( i, j ) );

          return transposedMatrix;
      }

      public Matrix duplicate(){
          Matrix duplicate = new Matrix( getRowCount(), getColumnCount() );

          for( int i = 0; i < getRowCount(); i++ )
            for( int j = 0; j < getColumnCount(); j++ )
              duplicate.setEntry( i, j, entries[ i ][ j ] );

          return duplicate;
      }


      public Matrix reducedRowEchelon(){
          Matrix echelonForm = duplicate();
          reducedRowEchelon( 0, 0, echelonForm );
          return echelonForm;
      }

      private void reducedRowEchelon( int pivotRow, int pivotColumn, Matrix echelonForm ){
          if( ( pivotRow < 0 || pivotRow >= getRowCount() ) ||
              ( pivotColumn < 0 || pivotColumn >= getColumnCount() ) ) return;

          // Find index of max value in column vector starting from pivot row
          int max = pivotRow;
          for( int i = pivotRow + 1; i < getRowCount(); i++ )
              if( Math.abs( echelonForm.getEntry( i, pivotColumn ) ) > Math.abs( echelonForm.getEntry( max, pivotColumn ) ) ) max = i;

          if( max != pivotRow ){
            echelonForm.switchRows( pivotRow, max );
          }

          if( echelonForm.getEntry( pivotRow, pivotColumn ) == 0.0 ){
            reducedRowEchelon( pivotRow, pivotColumn + 1, echelonForm );
          } else {
            double nonzero = 0.0;

            nonzero = ( 1.0 / echelonForm.getEntry( pivotRow, pivotColumn ) );
            echelonForm.scale( pivotRow,  nonzero );

            for( int i = pivotRow + 1; i < getRowCount(); i++ ){
              if( echelonForm.getEntry( i, pivotColumn ) != 0.0 ){
                nonzero = echelonForm.getEntry( i, pivotColumn ) * -1.0;
                echelonForm.replace( i, pivotRow, nonzero );
              }
            }

            for( int j = pivotRow - 1; j >= 0; j--){
              if( echelonForm.getEntry( j, pivotColumn ) != 0.0 ){
                nonzero = echelonForm.getEntry( j, pivotColumn ) * -1.0;
                echelonForm.replace( j, pivotRow, nonzero );
              }
            }

            reducedRowEchelon( pivotRow + 1, pivotColumn + 1, echelonForm );
          }
      }


      public boolean isSquare(){ return getRowCount() == getColumnCount(); }


      public static Matrix createColumnMatrix( int rows ){ return new Matrix( rows, 1 ); }
      public static Matrix createSquareMatrix( int n ){ return new Matrix( n, n ); }

      // Variables
      private double entries[][];
      private ArrayList< EntryChangeListener > entriesChangeListeners;
      private Map< String, ArrayList< EntryChangeListener > > entryChangeListeners;
}
