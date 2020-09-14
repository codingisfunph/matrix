package edu.codingisfunph.math.matrix;


public class EntryChangeEvent{

        public EntryChangeEvent( int row, int column, double oldValue, double newValue, Matrix matrix ){
            this.row = row;
            this.column = column;
            this.oldValue = oldValue;
            this.newValue = newValue;
            this.matrix = matrix;
        }

        public int getRow(){ return row; }
        public int getColumn(){ return column; }
        public double getOldValue(){ return oldValue; }
        public double getNewValue(){ return newValue; }

        private int row;
        private int column;
        private double oldValue;
        private double newValue;
        private Matrix matrix;
}
