package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class ListActivity extends AppCompatActivity {
    // активность для отрисовки списка доступных областей математики
    // при сохранении данных в активности настроек создается строка из 1 или 0, на основе которой строится список и дозволяется вход пользователя в соответствующий раздел

    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_COUNTER = "counter";
    private SharedPreferences mSettings;
    private String str;
    private String array[];

    private ImageView imageView;
    private ImageView imageView1;
    private TextView testView;

    LinearLayout ll;
    LinearLayout.LayoutParams lp;

    protected void loadingMenu(){
        Intent i;
        i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    protected void loadingSearch(String str){
        Intent i;
        i = new Intent(this,SearchActivity.class);
        i.putExtra("text",str);
        i.putExtra("ok","1");
        startActivity(i);
    }

    protected void loadingTheoryTo( String name ){
        Intent i;
        i = new Intent(this,TheoryListActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        imageView = findViewById(R.id.imageViewEscape);
        imageView1 = findViewById(R.id.imageView9);
        testView = findViewById(R.id.textView35);
        array = getResources().getStringArray(R.array.themes);

        if (mSettings.contains(APP_PREFERENCES_COUNTER)){
            str = mSettings.getString(APP_PREFERENCES_COUNTER, "0000000");
            ll = findViewById(R.id.button_layout);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);

            for (int i = 0;i < 7 ; i++){
                if (str.charAt(i)=='1'){
                    String name = array[i];
                    TextView textView = new TextView(this);
                    textView.setId(i);
                    textView.setPadding(10,0,10,0);
                    textView.setText(array[i]);
                    textView.setTextSize(testView.getTextSize());
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundResource(R.drawable.list_botton);
                    ll.addView(textView, lp);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (name.equals("Введение в анализ") || name.equals("Алгебра и теория чисел") || name.equals("Линейная алгебра")){
                                Toast toast = Toast.makeText(getApplicationContext(),"Конспект еще не завершен!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else {
                                loadingTheoryTo(name);
                            }
                        }
                    });
                }
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMenu();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingSearch(str);
            }
        });
    }
}