package edu.codingisfunph.math.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import edu.codingisfunph.math.matrix.algorithms.GaussianElimination;
import edu.codingisfunph.math.matrix.algorithms.GaussJordanElimination;
import edu.codingisfunph.math.matrix.algorithms.MatrixProduct;
import edu.codingisfunph.math.matrix.algorithms.ElementaryOperation;


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
        if( oldValue != value ) fireEntryChangeEvent( event );
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
          ElementaryOperation.scale( this, row, nonzero );
      }

      public void replace( int row2, int row1, double nonzero ) throws ZeroValueException {
          ElementaryOperation.replace( this, row2, row1, nonzero );
      }

      /**
      * Switches the values of row1 and row2
      * @param row1 index of the first row to switch with row2
      * @param row2 index of the second row to switch with row1
      */
      public void switchRows( int row1, int row2 ) throws IndexOutOfBoundsException{
          ElementaryOperation.switchRows( this, row1, row2 );
      }

      public Matrix power( int r ) throws NonSquareMatrixException {
          return MatrixProduct.power( this, r );
      }

      public Matrix multiply( Matrix matrix ) throws MatrixSizeMismatchException{
          return MatrixProduct.multiply( this, matrix );
      }

      private Matrix vectorProduct( Matrix vector ) throws MatrixSizeMismatchException {
          return MatrixProduct.vectorProduct( this, vector );
      }

      public Matrix scalarProduct( double scalarValue ){
          return MatrixProduct.scalarProduct( this, scalarValue );
      }

      /**
      * Determines whether the matrix is either a row or column vector.
      * @return true if the matrix is a row or column vector, and false otherwise.
      */
      public boolean isVector(){
          return isRowVector() || isColumnVector();
      }

      /**
      * Determines if the matrix is a row vector.
      * @return true if the matrix is a row vector, and false otherwise.
      */
      public boolean isRowVector(){
          return getRowCount() == 1;
      }

      /**
      * Determines if the matrix is a column vector.
      * @return true if the matrix is a column vector, and false otherwise.
      */
      public boolean isColumnVector(){
          return getColumnCount() ==  1;
      }

      public Matrix add( Matrix matrix ) throws MatrixSizeMismatchException {
          if( this.sizeEquals( matrix) ){
            Matrix sum = new Matrix( getRowCount(), getColumnCount() );

            for( int i = 0; i < getRowCount(); i++ )
              for( int j = 0; j < getColumnCount(); j++ )
                sum.setEntry( i, j, entries[ i ][ j ] + matrix.getEntry( i, j ) );

            return sum;
          } else {
            throw new MatrixSizeMismatchException();
          }
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

      public Matrix reducedRowEchelonForm() {
          return GaussJordanElimination.reducedRowEchelon( this );
      }

      public Matrix rowEchelonForm() {
          return GaussianElimination.rowEchelon( this );
      }

      public boolean isSquare(){ return getRowCount() == getColumnCount(); }


      public static Matrix createColumnMatrix( int rows ){ return new Matrix( rows, 1 ); }
      public static Matrix createSquareMatrix( int n ){ return new Matrix( n, n ); }

      /**
      * Creates a identity matrix object of size n x n. An identity matrix is a square matrix with 1's on the main
      * diagonal (upper left to lower right), and zeros elsewhere.
      * @param n is the number of rows and columns of the identity matrix.
      * @return a identity matrix object of size n x n.
      */
      public static Matrix createIdentityMatrix( int n ){
          Matrix identityMatrix = new Matrix( n, n );

          for( int i = 0; i < identityMatrix.getRowCount(); i++ )
            identityMatrix.setEntry( i, i, 1.0 );

          return identityMatrix;
      }

      public Matrix getColumnVector( int column ){
          Matrix vector = createColumnMatrix( getRowCount() );

          for( int i = 0; i < getRowCount(); i++ )
              vector.setEntry( i, 0, entries[ i ][ column ] );

          return vector;
      }

      public void setColumnVector( int column, Matrix vector ) throws MatrixSizeMismatchException {
          if( vector.isColumnVector() && getRowCount() == vector.getRowCount() ){
            for( int i = 0; i < getRowCount(); i++ ){
              setEntry( i, column, vector.getEntry( i, 0 ) );
            }
          } else {
            throw new MatrixSizeMismatchException();
          }
      }


      // Variables
      private double entries[][];
      private ArrayList< EntryChangeListener > entriesChangeListeners;
      private Map< String, ArrayList< EntryChangeListener > > entryChangeListeners;
}
