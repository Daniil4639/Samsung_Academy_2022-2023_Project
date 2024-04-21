package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TheoryListActivity extends AppCompatActivity {
    // активность, в котрой происходит отрисовка списка доступных тем раздела

    private ImageView imageView, imageView2;
    private TextView textView, testView;
    private String array[];

    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_COUNTER = "counter";
    private SharedPreferences mSettings;
    private String text;

    LinearLayout ll;
    LinearLayout.LayoutParams lp;

    protected void loadingBack(){
        Intent i;
        i = new Intent(this, ListActivity.class);
        startActivity(i);
    }

    protected void loadingTheoryTo( String name , String index, String pastname){
        Intent i;
        i = new Intent(this,MainTheoryActivity.class);
        i.putExtra("name", name);
        i.putExtra("index", index);
        i.putExtra("pastname",pastname);
        startActivity(i);
    }

    protected void loadingSearch(String text, String name){
        Intent i;
        i = new Intent(this,SearchActivity.class);
        i.putExtra("text",text);
        i.putExtra("ok",name);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_list);
        imageView = findViewById(R.id.imageViewEscape);
        imageView2=findViewById(R.id.imageView9);
        textView = findViewById(R.id.textViewHedder);
        testView = findViewById(R.id.textView35);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        textView.setText(name);
        if (name.equals("Введение в анализ")){
            array = getResources().getStringArray(R.array.analiz);
            text="1000000";
        }
        else if (name.equals("Дискретная математика")) {
            array = getResources().getStringArray(R.array.discret);
            textView.setText("Дискретная матем...");
            text="0100000";
        }
        else if (name.equals("Алгебра и теория чисел")) {
            array = getResources().getStringArray(R.array.algebra_1);
            textView.setText("Алгебра и теория...");
            text="0010000";
        }
        else if (name.equals("Линейная алгебра")) {
            array = getResources().getStringArray(R.array.algebra_2);
            text="0001000";
        }
        else if (name.equals("Математическая логика")) {
            array = getResources().getStringArray(R.array.logic);
            textView.setText("Математическая л...");
            text="0000100";
        }
        else if (name.equals("Геометрия и топология")) {
            array = getResources().getStringArray(R.array.geometry);
            textView.setText("Геометрия и топо...");
            text="0000010";
        }
        else if (name.equals("Дифференциальное и интегральное исчисление")) {
            array = getResources().getStringArray(R.array.diff);
            textView.setText("Дифференциальное...");
            text="0000001";
        }

        ll = findViewById(R.id.button_layout);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);

        for (int i = 0;i<array.length;i+=2){
            String nextName = array[i], index = array[i+1];
            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setPadding(60,0,60,0);
            textView.setText(array[i]);
            textView.setTextSize(testView.getTextSize());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.list_botton);
            ll.addView(textView, lp);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingTheoryTo(nextName, index, name);
                }
            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBack();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingSearch(text, name);
            }
        });
    }
}