package com.example.someapplicationimade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowLog extends AppCompatActivity {

    protected ListView simpleList;
    protected Button btnClearLog, btnBack;

    protected void ClearTheLog()
    {
        SQLiteDatabase db = null;
        try{
            db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/LOG.db",
                    null
            );
            String deleteQ = "DELETE FROM Log";
            db.execSQL(deleteQ);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            if(db!=null)
            {
                db.close();
                db=null;
            }
        }
    }

    protected void selectDb()
    {
        SQLiteDatabase db = null;
        try{
            db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/LOG.db",
                    null
            );
            String selectQ = "SELECT Lice FROM Log ORDER BY Time DESC";
            Cursor c = db.rawQuery(selectQ, null);
            ArrayList<String> listResults = new ArrayList<String>();
            while(c.moveToNext())
            {
                String Lice = c.getString(c.getColumnIndex("Lice"));
                listResults.add(Lice);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.activity_list_view,
                    R.id.textView,
                    listResults
            );
            simpleList.setAdapter(arrayAdapter);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            if(db!=null)
            {
                db.close();
                db=null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);

        simpleList = findViewById(R.id.simpleList);
        btnBack = findViewById(R.id.btnBack);
        btnClearLog = findViewById(R.id.btnClearLog);

        selectDb();

        btnClearLog.setOnClickListener((view)->{
            ClearTheLog();
            selectDb();
        });

        btnBack.setOnClickListener((view)->{
            Intent i= new Intent(ShowLog.this, MainActivity.class);
            startActivity(i);
        });
    }
}