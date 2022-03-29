package com.example.gamematch3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.gamematch3.R;
import com.example.gamematch3.uiTools.OnSwipeListener;
import com.example.gamematch3.util.Board;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.gamematch3.util.Board.CELL_LABELS;

public class MainActivity extends AppCompatActivity {
    public static int steps;

    public ImageView[][] imageViews;
    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) { }
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        TextView stepView = findViewById(R.id.StepsTextView);
        stepView.setText(String.valueOf(steps));
        int num_rows = 8;
        int num_col = 15;
        imageViews = new ImageView[num_rows][num_col];
        LinearLayout tableLayout = findViewById(R.id.subLinear);
        Board board = new Board(num_rows,num_col, MainActivity.this, findViewById(R.id.Match3), stepView, findViewById(R.id.CountView), new TextView[]{findViewById(R.id.QuestionTextView), findViewById(R.id.QuestionTextView2)}, new ImageView[]{findViewById(R.id.QuestionImage), findViewById(R.id.QuestionImage2)}, Arrays.asList(CELL_LABELS[1]), Arrays.asList(24), "1");
        //tableLayout.setShrinkAllColumns(true);
        //tableLayout.setWeightSum(num_rows);
        tableLayout.setClipChildren(false);
        for (int i = 0; i < num_rows; i++) {

            TableRow tableRow = new TableRow(this);

            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    0, (float)100/num_rows));

            tableRow.setBackgroundColor(Color.TRANSPARENT);
            tableRow.setClipChildren(false);
            for (int j = 0; j < num_col; j++) {
                int finalI = i;
                int finalJ = j;

                board.getImages()[i][j].setOnTouchListener(new OnSwipeListener(MainActivity.this) {
                    public void onSwipeTop() {
                        stepView.setText(String.valueOf(Integer.parseInt((String) stepView.getText()) - 1));
                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI - 1][finalJ], "translationY", board.getImages()[finalI][finalJ].getMeasuredHeight()), ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationY", - board.getImages()[finalI][finalJ].getMeasuredHeight())};
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
                        stepView.setText(String.valueOf(Integer.parseInt((String) stepView.getText()) - 1));
                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ+1], "translationX", -board.getImages()[finalI][finalJ].getMeasuredWidth()),ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationX", board.getImages()[finalI][finalJ].getMeasuredWidth())};
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

                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ-1], "translationX", board.getImages()[finalI][finalJ].getMeasuredWidth()), ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationX", -board.getImages()[finalI][finalJ].getMeasuredWidth())};
                        stepView.setText(String.valueOf(Integer.parseInt((String) stepView.getText()) - 1));
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

                        final ObjectAnimator[] animation = {ObjectAnimator.ofFloat(board.getImages()[finalI + 1][finalJ], "translationY", -board.getImages()[finalI][finalJ].getMeasuredHeight()),ObjectAnimator.ofFloat(board.getImages()[finalI][finalJ], "translationY", board.getImages()[finalI][finalJ].getMeasuredHeight())};
                        stepView.setText(String.valueOf(Integer.parseInt((String) stepView.getText()) - 1));
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
                tableRow.addView(board.getImages()[i][j], j);
            }

            tableLayout.addView(tableRow, i);
        }
    }

}