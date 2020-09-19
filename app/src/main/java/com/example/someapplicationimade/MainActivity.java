package com.example.someapplicationimade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected EditText editA, editB, editC;
    protected Button btnCalculate;
    protected TextView viewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the elements
        editA = findViewById(R.id.editA);
        editB = findViewById(R.id.editB);
        editC = findViewById(R.id.editC);
        btnCalculate = findViewById(R.id.btnCalculate);
        viewResults = findViewById(R.id.viewResults);

        // set button onclick

        btnCalculate.setOnClickListener((view)->{
            try {
                double a = Double.parseDouble(editA.getText().toString());
                double b = Double.parseDouble(editB.getText().toString());
                double c = Double.parseDouble(editC.getText().toString());

                if(!(a>0 && b>0 && c>0))
                {
                    throw new Exception("Input is invalid. Please enter positive numbers only.");
                }

                if(a+b<c || a+c<b || b+c<a)
                {
                    throw new Exception("Triangle does not exist");
                }

                double p = (a+b+c)/2;
                double S = Math.sqrt(p*(p-a)*(p-b)*(p-c));
                viewResults.setText("S = " + S);
            }
            catch(Exception e) {
                viewResults.setText(e.getLocalizedMessage());
            }
        });
    }
}