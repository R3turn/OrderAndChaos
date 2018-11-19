package com.christophertuncap.game.tuncapassign5;

import android.view.View;
import android.widget.ImageView;

public class GamePiece implements View.OnClickListener {

    private ImageView gamePiece;
    private int pieceType;

    public GamePiece(ImageView gamePiece){
        this.gamePiece = gamePiece;
        this.gamePiece.setImageResource(R.drawable.empty_piece);
    }

    public void setPieceType(Boolean type){
        if(true){
            pieceType = R.drawable.x_piece;
        }
        else
            pieceType = R.drawable.o_piece;
    }

    public int getPieceType(){
        return pieceType;
    }

    public int getId(){
        return gamePiece.getId();
    }

    @Override
    public void onClick(View view) {
        gamePiece.setImageResource(pieceType);
        gamePiece.setClickable(false);
    }
}
