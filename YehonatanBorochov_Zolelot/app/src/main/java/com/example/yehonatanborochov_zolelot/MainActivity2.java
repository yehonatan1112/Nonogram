package com.example.yehonatanborochov_zolelot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    private GameBoard boardGame;
    private FrameLayout frm;
    Button btnSolve;
    Button btnRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        boardGame = new GameBoard(this);
        frm =findViewById(R.id.frm);
        frm.addView(boardGame);
        btnSolve = (Button)findViewById(R.id.btn);
        btnRefresh = (Button)findViewById(R.id.btn2);
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            boardGame.solve();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardGame.Refresh();
            }
        });
    }
}