package edu.codingisfunph.controls;

public class ReplaceRow{

      public ReplaceRow( int row1, int row2, double nonzero ){
          this.row1 = row1;
          this.row2 = row2;
          this.nonzero = nonzero;
      }

      public int getRow1(){ return row1; }
      public int getRow2(){ return row2; }
      public double getNonzero(){ return nonzero; }

      private int row1;
      private int row2;
      private double nonzero;
}
