package com.example.someapplicationimade;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected EditText editA, editB, editC;
    protected Button btnCalculate;
    protected TextView viewResults;

    interface TLog{
        void Log(String text);
    }
    TLog myLogger = (text) -> {
        SQLiteDatabase db = null;
        try{
            db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/LOG.db",
                null
            );
            String createQ =
                "CREATE TABLE if not exists Log(" +
                "Lice text not null, " +
                "Time DATE DEFAULT (datetime('now', 'localtime')) " +
                ")";
            db.execSQL(createQ);
            String insertQ = "INSERT INTO Log(Lice) VALUES(?)";
            db.execSQL(insertQ, new Object[]{text});
            Toast.makeText(getApplicationContext(), "Log Successful", Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            viewResults.setText(e.getLocalizedMessage());
        }
        finally {
            if(db!=null)
            {
                db.close();
                db=null;
            }
        }
    };

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
                    myLogger.Log("Invalid fields (negative)");
                    throw new Exception("Input is invalid. Please enter positive numbers only.");
                }

                if(a+b<c || a+c<b || b+c<a)
                {
                    myLogger.Log("Invalid triangle");
                    throw new Exception("Triangle does not exist");
                }

                double p = (a+b+c)/2;
                double S = Math.sqrt(p*(p-a)*(p-b)*(p-c));
                myLogger.Log("S = " + S);
                viewResults.setText("S = " + S);
            }
            catch(Exception e) {
                myLogger.Log(e.getLocalizedMessage());
                viewResults.setText(e.getLocalizedMessage());
            }
        });
    }
}