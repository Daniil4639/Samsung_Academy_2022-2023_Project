package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class NotesActivity extends AppCompatActivity {
    // активность по отрисовке списка заметок
    // присутствует кнопка создания заметка, что добавляет заметку в SQLite базу

    private ImageView imageView1, imageView2;
    private TextView textView1;

    LinearLayout ll;
    LinearLayout.LayoutParams lp;

    private DBNotesHelper mDBHelper;
    private SQLiteDatabase mDb;
    private String dbName = "notes";

    private int rowCnt = 0;

    protected void loadingGadgets(){
        Intent i;
        i = new Intent(this,GadgetsActivity.class);
        startActivity(i);
    }

    protected void loadingNoteText(String index){
        Intent i;
        i = new Intent(this,TextNoteActivity.class);
        i.putExtra("index",index);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        imageView1 = findViewById(R.id.imageViewEscape);
        imageView2 = findViewById(R.id.imageView9);
        textView1 = findViewById(R.id.textViewHedder);

        ll = findViewById(R.id.button_layout);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);

        mDBHelper = new DBNotesHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        // отрисовка списка заметок
        Cursor c = mDb.rawQuery("SELECT * FROM "+ dbName,null);

        while (c.moveToNext()){
            rowCnt += 1;
            String ind = c.getString(0);
            String name = c.getString(1);
            TextView textView = new TextView(this);
            textView.setText(name);
            textView.setTextSize(textView1.getTextSize()/3);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.list_botton);
            ll.addView(textView, lp);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingNoteText(ind);
                }
            });
        }

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingGadgets();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rowCnt<=30) {
                    ContentValues cv = new ContentValues();
                    cv.put("_name", "Заметка " + Integer.toString(rowCnt + 1));
                    cv.put("_text", "");
                    mDb.insert(dbName, null, cv);
                    c.close();

                    loadingNoteText(Integer.toString(rowCnt + 1));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Достигнуто максимальное количество заметок!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }


}