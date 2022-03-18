package com.example.gamematch3.util;

import android.graphics.Color;

import com.example.gamematch3.R;

public class tools {
    public static int getImage(int color) {
        switch (color) {
            case Color.GREEN: return R.drawable.green;
            case Color.BLUE: return R.drawable.blue;
            case Color.YELLOW: return R.drawable.yellow;
            case Color.BLACK: return R.drawable.black;
            case Color.RED: return R.drawable.red;
        }
        return R.drawable.black;
    }
}
