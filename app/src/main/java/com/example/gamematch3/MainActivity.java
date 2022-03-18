package com.example.gamematch3;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;

import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.gamematch3.uiTools.OnSwipeListener;
import com.example.gamematch3.util.Board;
import android.animation.*;

import java.util.concurrent.TimeUnit;

import static com.example.gamematch3.util.tools.getImage;

public class MainActivity extends AppCompatActivity {


    public ImageView[][] imageViews;
    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        int num_rows = 5;
        int num_col = 15;
        imageViews = new ImageView[num_rows][num_col];
        TableLayout tableLayout = findViewById(R.id.board);

        Board board = new Board(num_rows,num_col, MainActivity.this, findViewById(R.id.Match3));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setWeightSum(num_rows);
        tableLayout.setGravity(4);
        tableLayout.setClipChildren(false);
        for (int i = 0; i < num_rows; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            tableRow.setBackgroundColor(Color.TRANSPARENT);
            tableRow.setWeightSum(2);
            tableRow.setClipChildren(false);
            for (int j = 0; j < num_col; j++) {
                int finalI = i;
                int finalJ = j;

                board.getImages()[i][j].setOnTouchListener(new OnSwipeListener(MainActivity.this) {
                    public void onSwipeTop() {
                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI - 1][finalJ], "translationY", 176f), ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationY", -180f)};
                        animation[1].setDuration(100);
                        animation[0].setDuration(100);
                        animation[1].start();
                        animation[0].start();
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            if(board.isValidSwap(finalI, finalJ, finalI - 1, finalJ)) {
                                animation[0].setDuration(0);
                                animation[0].reverse();
                                animation[1].setDuration(0);
                                animation[1].reverse();
                                board.makeSwap(finalI, finalJ, finalI - 1, finalJ, MainActivity.this);

                                //refreshTable(num_rows, num_col, board);
                            } else {
                                animation[1] = ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationY", 0f);
                                animation[1].setDuration(200);
                                animation[1].start();
                                animation[0] = ObjectAnimator.ofFloat(board.getImages()[finalI-1][finalJ], "translationY", 0f);
                                animation[0].setDuration(200);
                                animation[0].start();
                            }
                        }, 500);


                    }
                    public void onSwipeRight() {
                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ+1], "translationX", -150f),ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationX", 150f)};
                        animation[1].setDuration(100);
                        animation[0].setDuration(100);
                        animation[1].start();
                        animation[0].start();
                        final Handler handler = new Handler();
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        handler.postDelayed(() -> {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if(board.isValidSwap(finalI, finalJ, finalI, finalJ+1)) {
                                animation[0].setDuration(0);
                                animation[0].reverse();
                                animation[1].setDuration(0);
                                animation[1].reverse();
                                board.makeSwap(finalI,finalJ,finalI,finalJ+1,MainActivity.this);


                                //refreshTable(num_rows, num_col, board);
                            } else {
                                animation[1] = ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationX", 0f);
                                animation[1].setDuration(200);
                                animation[1].start();
                                animation[0] = ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ+1], "translationX", 0f);
                                animation[0].setDuration(200);
                                animation[0].start();
                            }
                        }, 500);

                    }
                    public void onSwipeLeft() {
                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ-1], "translationX", 150f), ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationX", -150f)};
                        animation[1].setDuration(100);
                        animation[0].setDuration(100);
                        animation[1].start();
                        animation[0].start();
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if(board.isValidSwap(finalI, finalJ, finalI, finalJ-1)) {
                                animation[0].setDuration(0);
                                animation[0].reverse();
                                animation[1].setDuration(0);
                                animation[1].reverse();
                                board.makeSwap(finalI, finalJ, finalI, finalJ-1, MainActivity.this);

                                //refreshTable(num_rows, num_col, board);
                            } else {
                                animation[1] = ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationX", 0f);
                                animation[1].setDuration(200);
                                animation[1].start();
                                animation[0] = ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ-1], "translationX", 0f);
                                animation[0].setDuration(200);
                                animation[0].start();
                            }
                        }, 500);

                    }
                    public void onSwipeBottom() {
                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI + 1][finalJ], "translationY", -176f),ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationY", 180f)};
                        animation[1].setDuration(100);
                        animation[0].setDuration(100);
                        animation[1].start();
                        animation[0].start();
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if(board.isValidSwap(finalI, finalJ, finalI + 1, finalJ)) {
                                animation[0].setDuration(0);
                                animation[0].reverse();
                                animation[1].setDuration(0);
                                animation[1].reverse();
                                board.makeSwap(finalI, finalJ, finalI + 1, finalJ, MainActivity.this);

                                //refreshTable(num_rows, num_col, board);
                            } else {
                                animation[1] = ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationY", 0f);
                                animation[1].setDuration(200);
                                animation[1].start();
                                animation[0] = ObjectAnimator.ofFloat(board.getImages()[finalI+1][finalJ], "translationY", 0f);
                                animation[0].setDuration(200);
                                animation[0].start();
                            }
                        }, 500);

                    }

                });
                tableRow.addView(board.getImages()[finalI][finalJ], j);
            }

            tableLayout.addView(tableRow, i);
        }
    }

}