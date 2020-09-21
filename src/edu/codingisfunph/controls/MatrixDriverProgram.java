package edu.codingisfunph.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import edu.codingisfunph.math.matrix.Matrix;

public class MatrixDriverProgram extends Application{

      public MatrixDriverProgram(){}

      @Override
      public void start( Stage primaryStage ){
          this.primaryStage = primaryStage;
          primaryStage.setTitle( "Matrix Driver Program" );

          hbox.getChildren().add( matrixControl );
          hbox.setAlignment( Pos.CENTER );

          primaryScene = new Scene( hbox, WIDTH, HEIGHT );
          primaryStage.setScene( primaryScene );
          primaryStage.show();
      }


      public static void main( String args[] ){
          launch( args );
      }

      private HBox hbox = new HBox( 8 );
      private VBox vbox = new VBox( 8 );
      private Matrix matrix = Matrix.createSquareMatrix( 4 );
      private MatrixControl matrixControl = new MatrixControl( matrix );
      private Stage primaryStage;
      private Scene primaryScene;
      private int ROWS = 4;
      private int COLUMNS = 4;
      private int GAP = 2;
      private int ENTRY_SIZE = 60;
      private static final int WIDTH = 500;
      private static final int HEIGHT = 500;
}
