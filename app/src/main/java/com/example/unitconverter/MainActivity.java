package com.example.unitconverter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Text;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView fah_inp = findViewById(R.id.f_inp);
        TextView cel_inp = findViewById(R.id.c_inp);
        cel_inp.setHintTextColor(Color.parseColor("#ff9500"));
        cel_inp.setTextColor(Color.parseColor("#ff9500"));
        cel_inp.setOnClickListener(v -> {
            if (cel_inp.getText().toString() == "0.00") cel_inp.setText("");
            cel_inp.setHintTextColor(Color.parseColor("#ff9500"));
            cel_inp.setTextColor(Color.parseColor("#ff9500"));
            fah_inp.setHintTextColor(Color.BLACK);
            fah_inp.setTextColor(Color.BLACK);
        });
        fah_inp.setOnClickListener(v -> {
            if (fah_inp.getText().toString() == "0.00") fah_inp.setText("");
            fah_inp.setHintTextColor(Color.parseColor("#ff9500"));
            fah_inp.setTextColor(Color.parseColor("#ff9500"));
            cel_inp.setHintTextColor(Color.BLACK);
            cel_inp.setTextColor(Color.BLACK);
        });
        GridLayout gridLayout = findViewById(R.id.numpad);
        for (int i = -1; i <= 12; i++) {
            Button btn = new Button(this);
            if (i == -1) btn.setText("-");
            else if (i == 0) btn.setText("C");
            else if (i <= 9) btn.setText(String.valueOf(i));
            else if (i == 10) btn.setText(".");
            else if (i == 11) btn.setText("0");
            else btn.setText("⌫");


            btn.setTextSize(25);
            btn.setGravity(Gravity.CENTER);
            btn.setId(i);
            btn.setPadding(0, 50, 0, 50);
            btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#555555")));
            btn.setBackgroundResource(R.drawable.circular_btn);
            btn.setTextColor(Color.WHITE);

            btn.setOnClickListener(v -> {
                TextView selectedInputBox = (cel_inp.getHintTextColors().getDefaultColor() == Color.parseColor("#ff9500") ? cel_inp : fah_inp);
                if (((Button) v).getText() == ".") {
                    if (selectedInputBox.getText().toString().isEmpty())
                        selectedInputBox.append("0.");
                    else if (!selectedInputBox.getText().toString().contains("."))
                        selectedInputBox.append(".");
                } else if (((Button) v).getText() == "⌫") {
                    if (!selectedInputBox.getText().toString().isEmpty()) {
                        selectedInputBox.setText(selectedInputBox.getText().toString().substring(0, selectedInputBox.getText().toString().length() - 1));
                        if (!selectedInputBox.getText().toString().isEmpty()) {
                            if (selectedInputBox == cel_inp) {
                                fah_inp.setText(String.valueOf((Double.parseDouble(cel_inp.getText().toString()) * 9 / 5) + 32));
                            } else
                                cel_inp.setText(String.valueOf((Double.parseDouble(fah_inp.getText().toString()) - 32) * 5 / 9));
                        } else {
                            if (selectedInputBox == cel_inp) {
                                fah_inp.setText("0.00");
                            } else
                                cel_inp.setText("0.00");
                        }
                    }
                } else {
                    if (selectedInputBox.getText().toString().length() < 8) {
                        selectedInputBox.append(((Button) v).getText());
                        if (selectedInputBox == cel_inp) {
                            fah_inp.setText(String.valueOf((Double.parseDouble(cel_inp.getText().toString()) * 9 / 5) + 32));
                        } else
                            cel_inp.setText(String.valueOf((Double.parseDouble(fah_inp.getText().toString()) - 32) * 5 / 9));
                    }
                }
            });
            gridLayout.addView(btn);
        }
        for (int i = 0; i <= 13; i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) btn.getLayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1F);
            params.setGravity(Gravity.FILL_HORIZONTAL);
            btn.setLayoutParams(params);
        }
    }
}