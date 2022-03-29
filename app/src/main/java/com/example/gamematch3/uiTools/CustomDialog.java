package com.example.gamematch3.uiTools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gamematch3.R;

import org.w3c.dom.Text;

public class CustomDialog extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String count;
    public String mission_number;
    public boolean win;
    public CustomDialog(Activity c, String count, String mission_number, boolean win) {
        super(c);
        this.c = c;
        this.count = count;
        this.mission_number = mission_number;
        this.win = win;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView MissionView = findViewById(R.id.MissionDialogView);
        String mission = (String) MissionView.getText();
        mission += " " + mission_number;
        MissionView.setText(mission);
        ((TextView)findViewById(R.id.CountingDialogView)).setText(String.valueOf(count));
        if(win) {
            ((TextView)findViewById(R.id.DialogTextView)).setText("Победа!");
        } else {
            ((TextView)findViewById(R.id.DialogTextView)).setText("ПРОВАЛ!");
            ((TextView)findViewById(R.id.DialogTextView)).setTextColor(Color.parseColor("#974851"));
        }
        yes = (Button) findViewById(R.id.btn_exit);
        yes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                c.finish();
                dismiss();
                break;
            default:
                break;
        }

    }
}
