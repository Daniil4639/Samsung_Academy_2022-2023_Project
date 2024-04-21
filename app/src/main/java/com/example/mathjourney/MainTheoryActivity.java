package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class MainTheoryActivity extends AppCompatActivity {
    // активность, в которой данные забираются из SQLite базы и отрисовываются в виде двух текстовых полей и картинки

    private ImageView imageView;
    private TextView textView, testView;
    private String array[];

    private TextView text1, text2;
    private ImageView image;

    LinearLayout ll;
    LinearLayout.LayoutParams lp;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    private String dbName = "materials";

    protected void loadingBack(String name){
        Intent i;
        i = new Intent(this, TheoryListActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_theory);
        imageView = findViewById(R.id.imageViewEscape);
        textView = findViewById(R.id.textViewHedder);
        testView = findViewById(R.id.textView35);

        ll = findViewById(R.id.button_layout);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String index = intent.getStringExtra("index");
        String pastname = intent.getStringExtra("pastname");

        textView.setPadding(40,5,40,5);


        textView.setText(name);

        mDBHelper = new DBHelper(this);

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

        // доступ к ячейке базы по индексу соответствующей темы
        Cursor c = mDb.rawQuery("SELECT * FROM "+ dbName,null);
        while (c.moveToNext()){
            String str0 = c.getString(0);
            if (str0.equals(index)){
                String str1 = c.getString(1);
                String str2 = c.getString(2);
                String str3 = c.getString(3);

                text1 = new TextView(this);
                text1.setText(str1);
                text1.setTextSize(testView.getTextSize());
                text1.setTextColor(Color.BLACK);
                text2 = new TextView(this);
                text2.setText(str2);
                text2.setTextSize(testView.getTextSize());
                text2.setTextColor(Color.BLACK);
                image = new ImageView(this);
                int image_id = getResources().getIdentifier(str3, "drawable", getPackageName());
                image.setImageResource(image_id);

                ll.addView(text1, lp);
                ll.addView(image, lp);
                ll.addView(text2, lp);

                break;
            }
        }
        c.close();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBack(pastname);
            }
        });
    }
}