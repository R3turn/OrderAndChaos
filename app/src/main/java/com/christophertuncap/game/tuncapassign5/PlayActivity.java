//Programmer: Christopher Tuncap
//Date created: 11/4/2018
//Class: CSCI 4010

package com.christophertuncap.game.tuncapassign5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;

public class PlayActivity extends Activity
    implements View.OnClickListener{

    //Array of all ImagesViews on our board

    int [] pieceLocations = new int[]{
            R.id.imageView_11, R.id.imageView_12, R.id.imageView_13, R.id.imageView_14, R.id.imageView_15, R.id.imageView_16,
            R.id.imageView_21, R.id.imageView_22, R.id.imageView_23, R.id.imageView_24, R.id.imageView_25, R.id.imageView_26,
            R.id.imageView_31, R.id.imageView_32, R.id.imageView_33, R.id.imageView_34, R.id.imageView_35, R.id.imageView_36,
            R.id.imageView_41, R.id.imageView_42, R.id.imageView_43, R.id.imageView_44, R.id.imageView_45, R.id.imageView_46,
            R.id.imageView_51, R.id.imageView_52, R.id.imageView_53, R.id.imageView_54, R.id.imageView_55, R.id.imageView_56,
            R.id.imageView_61, R.id.imageView_62, R.id.imageView_63, R.id.imageView_64, R.id.imageView_65, R.id.imageView_66
    };

    //HashMap for each ImageView and their gamepiece
    HashMap<Integer, Integer> gamePieces = new HashMap<>();

    //variable to keep track of pieces placed
    int piecesPlaced = 0;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_acitivity);

        ImageView gamePiece;

        /*
            This foreach loop will:
                1. setOnClickListener for each ImageView on the board.
                2. Set each imageView to an empty piece.
                3. Put that specific ImageView's image resource in a hashmap.

         */
        for(int piece : pieceLocations){
            gamePiece = findViewById(piece);
            gamePiece.setImageResource(R.drawable.empty_piece);
            gamePiece.setOnClickListener(this);
            gamePieces.put(piece, R.drawable.empty_piece);
        }

        RadioGroup radioGroup = findViewById(R.id.pieceType_radioGroup);
        radioGroup.check(R.id.x_radioButton);

    }

    @Override
    public void onClick(View view) {

        ImageView imageView;

        //HashMap to retrieve image resources for a selected radio button
        HashMap<Integer, Integer> gamePieceType = new HashMap<>();
        gamePieceType.put(R.id.x_radioButton, R.drawable.x_piece);
        gamePieceType.put(R.id.o_radioButton, R.drawable.o_piece);

        //RadioGroup for that contains user selected piece
        RadioGroup selectedPiece = findViewById(R.id.pieceType_radioGroup);

        /*
            This loop will:
                1. Go through all ImageViews to find the one that was clicked.
                    a. Set the replace the image resource with the new user selected item.
                    b. Disable the ImageView from being clicked again.
                    c. Overwrite the image resource in our gamePieces HashMap.
         */
        for(int pieceLocation : pieceLocations){
            if(view.getId() == pieceLocation){
                imageView = findViewById(pieceLocation);
                imageView.setImageResource(gamePieceType.get(selectedPiece.getCheckedRadioButtonId()));
                imageView.setClickable(false);
                gamePieces.put(view.getId(), gamePieceType.get(selectedPiece.getCheckedRadioButtonId()));
                ++piecesPlaced;
            }
        }

        /*
            This will check for a winner by:
                1. Seeing if there are any pieces in a row (vertically or horizontally or diagonally).
                2. Check if there are no more pieces to be placed.

                Result TextView will be updated if there is.
         */

        TextView textView = findViewById(R.id.result_textView);
        if(isOrder() && piecesPlaced > 4){
            disableBoard();
            endGame("ORDER");
        }else if(piecesPlaced == pieceLocations.length){
            endGame("CHAOS");
        }
        changePlayer();

    }

    public boolean isOrder(){

        //  2D array for our gameGrid.
        int [][] gameGrid  = new int[][]{
                {R.id.imageView_11, R.id.imageView_12, R.id.imageView_13, R.id.imageView_14, R.id.imageView_15, R.id.imageView_16},
                {R.id.imageView_21, R.id.imageView_22, R.id.imageView_23, R.id.imageView_24, R.id.imageView_25, R.id.imageView_26},
                {R.id.imageView_31, R.id.imageView_32, R.id.imageView_33, R.id.imageView_34, R.id.imageView_35, R.id.imageView_36},
                {R.id.imageView_41, R.id.imageView_42, R.id.imageView_43, R.id.imageView_44, R.id.imageView_45, R.id.imageView_46},
                {R.id.imageView_51, R.id.imageView_52, R.id.imageView_53, R.id.imageView_54, R.id.imageView_55, R.id.imageView_56},
                {R.id.imageView_61, R.id.imageView_62, R.id.imageView_63, R.id.imageView_64, R.id.imageView_65, R.id.imageView_66}
        };


        if(isWinnerHorizontally(gameGrid) || isWinnerVertically(gameGrid) || isWinnerDiagonally(gameGrid)){
            return true;
        }
        return false;

    }

    private void endGame(String winner){
        TextView textView = findViewById(R.id.result_textView);
        textView.setVisibility(View.VISIBLE);

        textView = findViewById(R.id.winner_textView);
        textView.setVisibility(View.VISIBLE);
        textView.setText(winner);

        textView = findViewById(R.id.piece_textView);
        textView.setVisibility(View.GONE);

        RadioGroup radioGroup = findViewById(R.id.pieceType_radioGroup);
        radioGroup.setVisibility(View.GONE);

        Button button = findViewById(R.id.retry_button);
        button.setVisibility(View.VISIBLE);

        textView = findViewById(R.id.gameover_textView);
        textView.setVisibility(View.VISIBLE);

        textView = findViewById(R.id.player_textView);
        textView.setVisibility(View.GONE);

        textView = findViewById(R.id.title_textView);
        textView.setVisibility(View.GONE);

        textView = findViewById(R.id.turn_TextView);
        textView.setVisibility(View.GONE);

        ImageView imageView = findViewById(R.id.gameover_imageView);
        imageView.setVisibility(View.VISIBLE);

    }

    public void restartGame(View view){
        TextView textView = findViewById(R.id.result_textView);
        textView.setVisibility(View.GONE);

        textView = findViewById(R.id.winner_textView);
        textView.setVisibility(View.GONE);

        textView = findViewById(R.id.piece_textView);
        textView.setVisibility(View.VISIBLE);

        RadioGroup radioGroup = findViewById(R.id.pieceType_radioGroup);
        radioGroup.setVisibility(View.VISIBLE);

        Button button = findViewById(R.id.retry_button);
        button.setVisibility(View.GONE);

        textView = findViewById(R.id.gameover_textView);
        textView.setVisibility(View.GONE);

        ImageView imageView = findViewById(R.id.gameover_imageView);
        imageView.setVisibility(View.GONE);

        textView = findViewById(R.id.player_textView);
        textView.setVisibility(View.VISIBLE);
        textView.setText("ORDER");

        textView = findViewById(R.id.turn_TextView);
        textView.setVisibility(View.VISIBLE);

        textView = findViewById(R.id.title_textView);
        textView.setVisibility(View.VISIBLE);

        ImageView gamePiece;

        for(int piece : pieceLocations){
            gamePiece = findViewById(piece);
            gamePiece.setImageResource(R.drawable.empty_piece);
            gamePiece.setOnClickListener(this);
            gamePieces.put(piece, R.drawable.empty_piece);
        }


        piecesPlaced = 0;
    }

    private boolean isWinnerHorizontally(int[][] gameGrid) {
        int consecutivePieces = 1;
        int previousPiece = -1;
        int emptyPiece = R.drawable.empty_piece;
        ImageView currentPiece;

        for(int row = 0; row < 6; row++){
            for(int col = 0; col < 6; col++ ){

                currentPiece = findViewById(gameGrid[row][col]);

                if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
                    ++consecutivePieces;
                }else{
                    consecutivePieces = 1;
                    previousPiece = gamePieces.get(currentPiece.getId());
                }

                if(consecutivePieces == 5){
                    return true;
                }

            }
            previousPiece = -1;

        }

        return false;
    }

    // BETTER THAN N^2, HOWEVER THIS WOULD BE MORE EFFICIENT IF THE GAME COULD KEEP TRACK OF THE X AND Y POSITIONS TO DO ISOLATED SCANS
    private boolean isWinnerDiagonally(int[][] gameGrid){
        int emptyPiece = R.drawable.empty_piece;
        int consecutivePieces = 1;
        int previousPiece = -1;

        ImageView currentPiece;

        //checks the opposite diagonal
      for(int i = 0; i < 6; i++){
          currentPiece = findViewById(gameGrid[i][5 - i]);

          if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
              ++consecutivePieces;
          }else{
              consecutivePieces = 1;
              previousPiece = gamePieces.get(currentPiece.getId());
          }

          if(consecutivePieces == 5){
              return true;
          }
      }


        consecutivePieces = 1;
        previousPiece = -1;

        //checks the main diagonal
        for(int i = 0; i < 6; i++){
            currentPiece = findViewById(gameGrid[i][i]);

            if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
                ++consecutivePieces;
            }else{
                consecutivePieces = 1;
                previousPiece = gamePieces.get(currentPiece.getId());
            }

            if(consecutivePieces == 5){
                return true;
            }
        }

        consecutivePieces = 1;
        previousPiece = -1;

        //checks the upper diagonal
        for(int i = 0; i < 5; i++){
            currentPiece = findViewById(gameGrid[i][i + 1]);

            if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
                ++consecutivePieces;
            }else{
                consecutivePieces = 1;
                previousPiece = gamePieces.get(currentPiece.getId());
            }

            if(consecutivePieces == 5){
                return true;
            }
        }


        consecutivePieces = 1;
        previousPiece = -1;


        //checks the lower diagonal
        for(int i = 0; i < 5; i++){

            currentPiece = findViewById(gameGrid[i+ 1][i]);

            if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
                ++consecutivePieces;
            }else{
                consecutivePieces = 1;
                previousPiece = gamePieces.get(currentPiece.getId());
            }

            if(consecutivePieces == 5){
                return true;
            }
        }

        consecutivePieces = 1;
        previousPiece = -1;
        //Checks the opposite upper diagonal
        for(int i = 0; i < 5; i++){
            currentPiece = findViewById(gameGrid[i][4 - i]);

            if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
                ++consecutivePieces;
            }else{
                consecutivePieces = 1;
                previousPiece = gamePieces.get(currentPiece.getId());
            }

            if(consecutivePieces == 5){
                return true;
            }
        }

        consecutivePieces = 1;
        previousPiece = -1;

        //Checks the opposite lower diagonal
        for(int i = 0; i < 5; i++){
            currentPiece = findViewById(gameGrid[i + 1][5 - i]);

            if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
                ++consecutivePieces;
            }else{
                consecutivePieces = 1;
                previousPiece = gamePieces.get(currentPiece.getId());
            }

            if(consecutivePieces == 5){
                return true;
            }
        }


        return false;
    }

    private boolean isWinnerVertically(int[][] gameGrid) {

        int emptyPiece = R.drawable.empty_piece;
        int consecutivePieces = 1;
        int previousPiece = -1;

        ImageView currentPiece;
        for(int col = 0; col < 6; col++){
            for(int row = 0; row < 6; row++ ){

                currentPiece = findViewById(gameGrid[row][col]);

                if(previousPiece == gamePieces.get(currentPiece.getId()) && gamePieces.get(currentPiece.getId()) != emptyPiece){
                    ++consecutivePieces;
                }else{
                    consecutivePieces = 1;
                    previousPiece = gamePieces.get(currentPiece.getId());
                }

                if(consecutivePieces == 5){
                    return true;
                }

            }
            previousPiece = -1;
        }

        return false;
    }

    public void changePlayer(){
        TextView tv = findViewById(R.id.player_textView);
        if(piecesPlaced % 2 == 0){
            tv.setText("ORDER");
        }else
        {
            tv.setText("CHAOS");
        }
    }

    public void disableBoard(){
        ImageView imageView;
        for(int piece : pieceLocations){
            imageView = findViewById(piece);
            imageView.setClickable(false);
        }
    }


}
