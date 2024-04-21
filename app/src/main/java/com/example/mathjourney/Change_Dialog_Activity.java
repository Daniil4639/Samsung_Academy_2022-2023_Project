package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Change_Dialog_Activity extends AppCompatActivity {
    //активность, в которой высчитываются операции на плоскости

    ImageView escape_im;
    ArrayList<Double> valX, valY, valXold, valYold;
    Boolean ok;
    TextView butMove, butRotate, butSimm, butScale;
    EditText editMove1, editMove2, editRotate1, editRotate2, editRotate3, editSimmK, editSimmB, editScale1, editScale2;

    protected void loadingBack(){
        Intent i;
        i = new Intent(this,TransformActivity.class);
        i.putExtra("oldX",valXold);
        i.putExtra("oldY",valYold);
        i.putExtra("valuesX", valX);
        i.putExtra("valuesY", valY);
        i.putExtra("ok",ok);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_dialog);

        // инициализация кнопок и полей ввода
        butMove = findViewById(R.id.butMove);
        butRotate = findViewById(R.id.butRotate);
        butSimm = findViewById(R.id.butSimm);
        butScale = findViewById(R.id.butScale);
        editMove1 = findViewById(R.id.editMove1);
        editMove2 = findViewById(R.id.editMove2);
        editRotate1 = findViewById(R.id.editRotate1);
        editRotate2 = findViewById(R.id.editRotate2);
        editRotate3 = findViewById(R.id.editRotate3);
        editSimmK = findViewById(R.id.editSimmK);
        editSimmB = findViewById(R.id.editSimmB);
        editScale1 = findViewById(R.id.editScale1);
        editScale2 = findViewById(R.id.editScale2);
        escape_im = findViewById(R.id.imageViewEscape);
        valX = (ArrayList<Double>) getIntent().getSerializableExtra("valuesX");
        valY = (ArrayList<Double>) getIntent().getSerializableExtra("valuesY");
        ok = (Boolean) getIntent().getBooleanExtra("ok",true);
        valXold = new ArrayList<>();
        valYold = new ArrayList<>();

        for (int i=0;i<valX.size();i++){
            valXold.add(valX.get(i));
            valYold.add(valY.get(i));
        }

        // описание действий при нажатии на конкретную кнопку (значение кнопки в её названии)
        escape_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBack();
            }
        });

        butMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean ok1 = true;
                double kx = 0,ky = 0;
                try {
                    kx = Double.parseDouble(editMove1.getText().toString());
                    ky = Double.parseDouble(editMove2.getText().toString());
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Данные заданы некорректно!",Toast.LENGTH_LONG);
                    ok1 = false;
                }

                if (ok1){
                    for (int i=0;i<valX.size();i++){
                        valX.set(i,valX.get(i)+kx);
                        valY.set(i,valY.get(i)+ky);
                    }
                    loadingBack();
                }
            }
        });

        butScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok1 = true;
                double kx = 0,ky = 0;
                try {
                    kx = Double.parseDouble(editScale1.getText().toString());
                    ky = Double.parseDouble(editScale2.getText().toString());
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Данные заданы некорректно!",Toast.LENGTH_LONG);
                    ok1 = false;
                }

                if (ok1){
                    for (int i=0;i<valX.size();i++){
                        valX.set(i,valX.get(i)*kx);
                        valY.set(i,valY.get(i)*ky);
                    }
                    loadingBack();
                }
            }
        });

        butRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok1 = true;
                double kx = 0,ky = 0, deg = 0;
                try {
                    kx = Double.parseDouble(editRotate1.getText().toString());
                    ky = Double.parseDouble(editRotate2.getText().toString());
                    deg = Double.parseDouble(editRotate3.getText().toString());
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Данные заданы некорректно!",Toast.LENGTH_LONG);
                    ok1 = false;
                }

                if (ok1){
                    for (int i=0;i<valX.size();i++){
                        double num1 = valX.get(i)*Math.cos(deg*Math.PI/180) - valY.get(i)*Math.sin(deg*Math.PI/180) + Math.sin(deg*Math.PI/180)*ky - Math.cos(deg*Math.PI/180)*kx + kx;
                        double num2 = valX.get(i)*Math.sin(deg*Math.PI/180) + valY.get(i)*Math.cos(deg*Math.PI/180) - Math.sin(deg*Math.PI/180)*kx - Math.cos(deg*Math.PI/180)*ky + ky;
                        valX.set(i,num1);
                        valY.set(i,num2);
                    }
                    loadingBack();
                }
            }
        });

        butSimm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok1 = true;
                double k = 0,b = 0;
                try {
                    k = Math.atan(Double.parseDouble(editSimmK.getText().toString()));
                    b = Double.parseDouble(editSimmB.getText().toString());
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Данные заданы некорректно!",Toast.LENGTH_LONG);
                    ok1 = false;
                }

                if (ok1){
                    for (int i=0;i<valX.size();i++){
                        double num1 = valX.get(i)*(2*Math.cos(k)*Math.cos(k) - 1) + valY.get(i)*Math.sin(2*k) - Math.sin(2*k)*b;
                        double num2 = valX.get(i)*Math.sin(2*k) + valY.get(i)*(2*Math.sin(k)*Math.sin(k) - 1) + 2*Math.cos(k)*Math.cos(k) + b - 1;
                        valX.set(i,num1);
                        valY.set(i,num2);
                    }
                    loadingBack();
                }
            }
        });
    }
}