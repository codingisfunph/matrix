package edu.codingisfunph.controls;

public class MatrixDimension{
      public MatrixDimension( int rows, int columns ){
         this.rows = rows;
         this.columns = columns;
      }

      public int getRowCount(){ return rows; }
      public int getColumnCount(){ return columns; }

      private int rows;
      private int columns;
}
