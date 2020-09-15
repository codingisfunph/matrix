package edu.codingisfunph.controls;


public class ScaleRow{

      public ScaleRow( int row, double nonzero ){
          this.row = row;
          this.nonzero = nonzero;
      }

      public int getRow(){ return row; }
      public double getNonzero(){ return nonzero; }

      private int row;
      private double nonzero;
}
