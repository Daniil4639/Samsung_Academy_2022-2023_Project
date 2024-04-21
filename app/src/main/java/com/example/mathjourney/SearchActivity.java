package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    // активность, в которой происходит поиск темы по вхождению набранной фразы в название
    // учитывается, что активность может быть запущена из конкретного раздела
    // так что поиск в этом случае осуществляется именно в этом разделе

    protected void loadingList(String name){
        Intent i;
        if (name.equals("1")) {
            i = new Intent(this, ListActivity.class);
        }
        else{
            i = new Intent(this, TheoryListActivity.class);
            i.putExtra("name",name);
        }
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

    protected void search(String str){
        textSearch = findViewById(R.id.editText3);
        text = textSearch.getText().toString().toLowerCase();

            ll = findViewById(R.id.button_layout);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            array = getResources().getStringArray(R.array.themes);

            for (int i = 0;i < 7 ; i++){
                if (str.charAt(i)=='1'){
                    String name = array[i];

                    if (name.equals("Введение в анализ")){
                        array1 = getResources().getStringArray(R.array.analiz);
                    }
                    else if (name.equals("Дискретная математика")) {
                        array1 = getResources().getStringArray(R.array.discret);
                    }
                    else if (name.equals("Алгебра и теория чисел")) {
                        array1 = getResources().getStringArray(R.array.algebra_1);
                    }
                    else if (name.equals("Линейная алгебра")) {
                        array1 = getResources().getStringArray(R.array.algebra_2);
                    }
                    else if (name.equals("Математическая логика")) {
                        array1 = getResources().getStringArray(R.array.logic);
                    }
                    else if (name.equals("Геометрия и топология")) {
                        array1 = getResources().getStringArray(R.array.geometry);
                    }
                    else if (name.equals("Дифференциальное и интегральное исчисление")) {
                        array1 = getResources().getStringArray(R.array.diff);
                    }

                    for (int j =0;j<array1.length;j+=2){
                        if (array1[j].toLowerCase().contains(text)){
                            String nextName = array1[j], index = array1[j+1];
                            TextView textView = new TextView(SearchActivity.this);
                            textView.setText(array1[j]);
                            textView.setPadding(10,0,10,0);
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
                    }
                }
            }
    }

    private ImageView imageView, imageView1;
    private String array[], array1[], str, text, name;
    private TextView textSearch, testView;

    LinearLayout ll;
    LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        testView = findViewById(R.id.textView35);
        imageView = findViewById(R.id.imageViewEscape);
        imageView1 = findViewById(R.id.imageView9);
        textSearch = findViewById(R.id.editText3);

        Intent intent = getIntent();
        str=intent.getStringExtra("text");
        name = intent.getStringExtra("ok");

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(str);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingList(name);
            }
        });
    }
}