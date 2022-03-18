package com.example.gamematch3.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gamematch3.R;

import java.util.*;

import static android.os.Build.VERSION_CODES.R;
import static com.example.gamematch3.util.tools.getImage;

// class Board
// underlying game engine for Match3
public class Board {
    static final int CELL_EMPTY = 0;   // empty cell
    static final int[] CELL_LABELS = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED, Color.BLACK};
    private int numrows, numcols;
    private int[][] board;
    private ImageView[][] images;
    private Context context;
    private ConstraintLayout layout;

    // create an empty board
    public Board(int numrows, int numcols, Context context, ConstraintLayout layout) {
        this.numrows = numrows;
        this.numcols = numcols;
        this.context = context;
        this.layout = layout;
        board = new int[numrows][numcols];
        images = new ImageView[numrows][numcols];
        Random ran = new Random();
        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                int randColor = CELL_LABELS[ran.nextInt(5)];
                if (j > 1) {
                    while (board[i][j - 1] == randColor && board[i][j - 2] == randColor) {
                        randColor = CELL_LABELS[ran.nextInt(5)];
                        if (i > 1) {
                            while (board[i - 1][j] == randColor && board[i - 2][j] == randColor) {
                                randColor = CELL_LABELS[ran.nextInt(5)];

                            }
                        }

                    }
                }
                if (i > 1) {
                    while (board[i - 1][j] == randColor && board[i - 2][j] == randColor) {
                        randColor = CELL_LABELS[ran.nextInt(5)];
                        if (j > 1) {
                            while (board[i][j - 1] == randColor && board[i][j - 2] == randColor) {
                                randColor = CELL_LABELS[ran.nextInt(5)];

                            }
                        }
                    }
                }
                board[i][j] = randColor;
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageResource(getImage(randColor));
                imageView.setBackgroundColor(Color.TRANSPARENT);
                images[i][j] = imageView;
            }
        }
    }

    // construct a copy of an existing board
    public Board(Board oldboard) {
        numrows = oldboard.getNumRows();
        numcols = oldboard.getNumCols();
        board = new int[numrows][numcols];
        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                board[i][j] = oldboard.board[i][j];
            }
        }


    }

    public int getNumRows() {
        return numrows;
    }

    public int getNumCols() {
        return numcols;
    }

    // set the entire board to empty cells
    public void resetBoard() {
        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                board[i][j] = CELL_EMPTY;
            }
        }
    }

    public int getValueAt(int row, int col) {
        return board[row][col];
    }

    // let all pieces fall to the bottom under gravity, then let in
    // new ones from the top drawn randomly
    public void dropPieces() {

        // work column by column
        for (int j = 0; j < numcols; j++) {
//            int[] thiscol = new int[numrows];
//            for (int i = 0; i < numrows; i++) {
//                thiscol[i] = CELL_EMPTY;
//            }
//
//            int target_index = numrows - 1;
//
//            for (int i = numrows - 1; i >= 0; i--) {
//                if (board[i][j] != CELL_EMPTY) {
//                    thiscol[target_index] = board[i][j];
//                    target_index--;
//                }
//            }
//            Random random = new Random();
//            while (target_index >= 0) {
//                thiscol[target_index] = CELL_LABELS[random.nextInt(5)];
//                target_index--;
//            }
            for (int i = 0; i < numrows; i++) {
                images[i][j].setImageResource(getImage(board[i][j]));
                images[i][j].setVisibility(View.VISIBLE);
            }

        }

    }

    ;

    // check if there exists an empty cell
    public boolean existsEmptyCell() {
        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                if (board[i][j] == CELL_EMPTY) {
                    return true;
                }
            }
        }

        return false;
    }

    public void AnimatedNewHeightImageDown(int n, int j, double k) {
        Random random = new Random();
        int color = CELL_LABELS[random.nextInt(5)];
        ImageView image = new ImageView(context);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setImageResource(getImage(color));
        image.setBackgroundColor(Color.TRANSPARENT);
        image.setX(images[0][j].getX());
        image.setY((float) (images[0][j].getY() - images[0][0].getMeasuredHeight() * n));
        layout.addView(image);
        image.getLayoutParams().height = images[0][0].getMeasuredHeight();
        image.getLayoutParams().width = images[0][0].getMeasuredWidth();
        image.requestLayout();
        ObjectAnimator animation = ObjectAnimator.ofFloat(image, "translationY", (float) (images[0][0].getMeasuredHeight()* k));
        animation.setDuration(200);
        animation.start();
        Handler handler = new Handler();
       // board[(int) k][j] = color;
        handler.postDelayed(() -> {
            board[(int) k][j] = color;
            images[(int) k][j].setImageResource(getImage(color));
            images[(int) k][j].setVisibility(View.VISIBLE);
            layout.removeView(image);
        }, 1000);
    }

    public void AnimatedNewWidthImageDown(int j) {
        Random random = new Random();
        int color = CELL_LABELS[random.nextInt(5)];
        ImageView image = new ImageView(context);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setImageResource(getImage(color));
        image.setBackgroundColor(Color.TRANSPARENT);
        image.setX(images[0][j].getX());
        image.setY((float) (images[0][0].getY() - images[0][0].getMeasuredHeight()));
        layout.addView(image);
        image.getLayoutParams().height = images[0][0].getMeasuredHeight();
        image.getLayoutParams().width = images[0][0].getMeasuredWidth();
        image.requestLayout();
        ObjectAnimator animation = ObjectAnimator.ofFloat(image, "translationY", (float) (images[0][0].getMeasuredHeight()* 0.15));
        animation.setDuration(200);

        animation.start();
        Handler handler = new Handler();

        handler.postDelayed(() -> {
            board[0][j] = color;
            images[0][j].setImageResource(getImage(color));
            images[0][j].setVisibility(View.VISIBLE);
            layout.removeView(image);

        }, 1000);
    }

    public void AnimatedDown(int i, int j, double k) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(images[i][j], "translationY", (float) (images[0][0].getMeasuredHeight() * k));
        animation.setDuration(200);
        animation.start();
        Handler handler = new Handler();
        //board[(int) k][j] = board[i][j];
        handler.postDelayed(() -> {
            board[(int) (i + k)][j] = board[i][j];
            images[(int)(i + k)][j].setImageResource(getImage(board[i][j]));
            images[(int)(i + k)][j].setVisibility(View.VISIBLE);
            animation.setDuration(0);
            animation.reverse();
        }, 1000);
    }
    public void AnimatedWidthDown(int i, int j) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(images[i][j], "translationY", (float) (images[0][0].getMeasuredHeight()));
        animation.setDuration(200);
        animation.start();
        Handler handler = new Handler();
        //board[i+1][j] = board[i][j];
        handler.postDelayed(() -> {
            board[i+1][j] = board[i][j];
            images[i+1][j].setImageResource(getImage(board[i][j]));
            images[i+1][j].setVisibility(View.VISIBLE);

            animation.setDuration(0);
            animation.reverse();
        }, 1000);
    }

    // eliminate any 3 consecutive matching pieces
    public void eliminateMatches() {
        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                // 3 in a row
                if (0 < j && j < numcols - 1) {
                    if (board[i][j - 1] == board[i][j] &&
                            board[i][j + 1] == board[i][j]) {
                        board[i][j] = CELL_EMPTY;
                        board[i][j - 1] = CELL_EMPTY;
                        board[i][j + 1] = CELL_EMPTY;
                        if (images != null) {
                            images[i][j].setVisibility(View.INVISIBLE);
                            images[i][j - 1].setVisibility(View.INVISIBLE);
                            images[i][j + 1].setVisibility(View.INVISIBLE);
                            Handler handler = new Handler();
                            for (int k = i; k > 0; k--) {
                                AnimatedWidthDown(k - 1, j);
                                AnimatedWidthDown(k - 1, j + 1);
                                AnimatedWidthDown(k - 1, j - 1);
                            }
                            AnimatedNewWidthImageDown(j);
                            AnimatedNewWidthImageDown(j + 1);
                            AnimatedNewWidthImageDown(j - 1);


                            handler.postDelayed(() -> {
                                //dropPieces();
                                handler.postDelayed(this::eliminateMatches,50);

                            }, 1010);
                            return;
                        }
                    }
                }


                // 3 in a col
                if (0 < i && i < numrows - 1) {
                    if (board[i - 1][j] == board[i][j] &&
                            board[i + 1][j] == board[i][j]) {
                        board[i][j] = CELL_EMPTY;
                        board[i - 1][j] = CELL_EMPTY;
                        board[i + 1][j] = CELL_EMPTY;
                        if (images != null) {
                            images[i - 1][j].setVisibility(View.INVISIBLE);
                            images[i][j].setVisibility(View.INVISIBLE);
                            images[i + 1][j].setVisibility(View.INVISIBLE);
                            Handler handler = new Handler();
                            for (int k = i - 2; k >= 0; k--) {
                                AnimatedDown(k, j, 3);
                            }
                            AnimatedNewHeightImageDown(1, j, 2.15);
                            AnimatedNewHeightImageDown(2, j, 1.15);
                            AnimatedNewHeightImageDown(3, j, 0.15);

                            handler.postDelayed(() -> {
                                //dropPieces();
                                handler.postDelayed(this::eliminateMatches,50);

                            }, 1010);
                            return;
                        }
                    }
                }
            }
        }

    }


    // check whether the given swap is valid.
    // returns False if there is already some match on the board (this
    // should never happen in normal gameplay though).
    public boolean isValidSwap(int row1, int col1, int row2, int col2) {
        if (!(row1 == row2 && Math.abs(col1 - col2) == 1) && !(col1 == col2 && Math.abs(row1 - row2) == 1)) {
            return false;
        }

        Board toyboard = new Board(this);

        toyboard.eliminateMatches();
        if (toyboard.existsEmptyCell()) {
            return false;
        }

        int tmp = toyboard.board[row1][col1];
        toyboard.board[row1][col1] = toyboard.board[row2][col2];
        toyboard.board[row2][col2] = tmp;
        toyboard.eliminateMatches();
        if (toyboard.existsEmptyCell()) {
            return true;
        } else {
            return false;
        }
    }


    // swap the given pieces, then call eliminateMatches() and then dropPieces()
    // until no matches exist
    public void makeSwap(int row1, int col1, int row2, int col2, Context context) {
        int tmp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = tmp;
        images[row1][col1].setImageResource(getImage(board[row1][col1]));
        images[row2][col2].setImageResource(getImage(board[row2][col2]));
        eliminateMatches();

//        do {
//           eliminateMatches();
//        } while (existsEmptyCell());
    }

    public ImageView[][] getImages() {
        return images;
    }

    public void setImages(ImageView[][] images) {
        this.images = images;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getNumrows() {
        return numrows;
    }

    public void setNumrows(int numrows) {
        this.numrows = numrows;
    }

    public int getNumcols() {
        return numcols;
    }

    public void setNumcols(int numcols) {
        this.numcols = numcols;
    }
}