package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class TextNoteActivity extends AppCompatActivity {
    // активность, в которой идет забор данных о заметке из соответствующей ячейки базы

    private ImageView imageView1, imageView2;
    private EditText editText, editText1;

    private DBNotesHelper mDBHelper;
    private SQLiteDatabase mDb;
    private String dbName = "notes";

    protected void loadingNotes(){
        Intent i;
        i = new Intent(this,NotesActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        imageView1 = findViewById(R.id.imageViewEscape);
        imageView2 = findViewById(R.id.imageView9);
        editText = findViewById(R.id.textViewHedder);
        editText1 = findViewById(R.id.editTextNote);

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

        Cursor c = mDb.rawQuery("SELECT * FROM "+ dbName,null);

        Intent intent = getIntent();
        String index = intent.getStringExtra("index");

        while (c.moveToNext()){
            if (index.equals(c.getString(0))){
                editText.setText(c.getString(1));
                editText1.setText(c.getString(2));
            }
        }

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("_name",editText.getText().toString());
                cv.put("_text",editText1.getText().toString());
                mDb.update(dbName,cv,"_id" + "=" + index, null);
                c.close();
                loadingNotes();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.delete(dbName,"_id =" + index, null);

                long i = 1;
                Cursor c1 = mDb.rawQuery("SELECT * FROM "+ dbName,null);
                while (c1.moveToNext()){
                    ContentValues cv1 = new ContentValues();
                    cv1.put("_id", Long.toString(i));
                    cv1.put("_name", c1.getString(1));
                    cv1.put("_text", c1.getString(2));
                    mDb.update(dbName,cv1,"_id" + "=" + c1.getString(0), null);
                    i++;
                }

                c.close();

                loadingNotes();
            }
        });
    }
}