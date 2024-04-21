package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GadgetsActivity extends AppCompatActivity {
    // активность с выбором предпочтительного гаджета

    private TextView textView1, textView2, textView3, textView4;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadgets);

        textView1 = findViewById(R.id.textViewMatrix);
        textView2 = findViewById(R.id.textViewNotes);
        textView3 = findViewById(R.id.textViewGraph);
        imageView = findViewById(R.id.imageViewEscape);
        textView4 = findViewById(R.id.textViewTransform);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMatrix();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingNotes();
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingGraph();
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingTransform();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMenu();
            }
        });
    }

    protected void loadingMatrix(){
        Intent i;
        i = new Intent(this,MainActivity2.class);
        startActivity(i);
    }

    protected void loadingNotes(){
        Intent i;
        i = new Intent(this,NotesActivity.class);
        startActivity(i);
    }

    protected void loadingMenu(){
        Intent i;
        i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    protected void loadingGraph(){
        Intent i;
        i = new Intent(this,FunctionCreatActivity.class);
        startActivity(i);
    }

    protected void loadingTransform(){
        Intent i;
        i = new Intent(this,Transform_Creat_Activity.class);
        startActivity(i);
    }
}