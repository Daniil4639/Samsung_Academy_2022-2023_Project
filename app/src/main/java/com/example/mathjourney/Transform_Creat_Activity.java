package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class Transform_Creat_Activity extends AppCompatActivity {
    // активность, в которой пользователь создает массив координат точек фигуры, что будет изменена на плоскости

    protected void loadingMenu(){
        Intent i;
        i = new Intent(this,GadgetsActivity.class);
        startActivity(i);
    }

    ImageView escape_im;
    String str;
    TextView textView1, buttonAdd, buttonDelete, buttonCurve, buttonCircle;
    Vector<Pair<Double,Double>> vec;
    EditText editText1, editText2;

    // загрузка кривой
    protected void loadingCurve(){
        if (vec.size()!=0) {
            Intent intent;
            intent = new Intent(this, TransformActivity.class);

            ArrayList<Double> valX1 = new ArrayList<>();
            ArrayList<Double> valY1 = new ArrayList<>();
            ArrayList<Double> valXold = new ArrayList<>();
            ArrayList<Double> valYold = new ArrayList<>();

            for (int i = 0; i < vec.size(); i++) {
                valX1.add(vec.elementAt(i).first);
                valY1.add(vec.elementAt(i).second);
            }


            intent.putExtra("oldX",valXold);
            intent.putExtra("oldY",valYold);
            intent.putExtra("valuesX", valX1);
            intent.putExtra("valuesY", valY1);
            intent.putExtra("ok",true);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Массив не заполнен!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // загрузка цикла (если нужна замкнутая фигуры: треугольник, квадрат...)
    protected void loadingCircle(){
        if (vec.size()!=0) {
            Intent intent;
            intent = new Intent(this, TransformActivity.class);

            if (vec.size()>2) {
                vec.add(vec.elementAt(0));
            }

            ArrayList<Double> valX1 = new ArrayList<>();
            ArrayList<Double> valY1 = new ArrayList<>();
            ArrayList<Double> valXold = new ArrayList<>();
            ArrayList<Double> valYold = new ArrayList<>();

            for (int i = 0; i < vec.size(); i++) {
                valX1.add(vec.elementAt(i).first);
                valY1.add(vec.elementAt(i).second);
            }

            intent.putExtra("oldX",valXold);
            intent.putExtra("oldY",valYold);
            intent.putExtra("valuesX", valX1);
            intent.putExtra("valuesY", valY1);
            intent.putExtra("ok",vec.size()<=2);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Массив не заполнен!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform_creat);
        escape_im = findViewById(R.id.imageViewEscape);
        textView1 = findViewById(R.id.vectorSide);
        buttonAdd = findViewById(R.id.textView31);
        buttonDelete = findViewById(R.id.textView38);
        editText1 = findViewById(R.id.editTextNumber3);
        editText2 = findViewById(R.id.editTextNumber4);
        buttonCurve = findViewById(R.id.textView32);
        buttonCircle = findViewById(R.id.textView33);

        vec = new Vector<Pair<Double,Double>>();

        str = "A = {";
        textView1.setText(str+'}');

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = editText1.getText().toString(), str2 = editText2.getText().toString();

                if (str1.equals("") || str2.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Координаты заданные некорректно!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    try {
                        double num1 = Double.parseDouble(str1), num2 = Double.parseDouble(str2);
                        Pair<Double,Double> pr = new Pair<>(num1,num2);
                        vec.add(pr);

                        if (vec.size()==1){
                            str+="( " + Double.toString(num1)+"; "+Double.toString(num2)+")";
                        }
                        else {
                            str += ", ( " + Double.toString(num1) + "; " + Double.toString(num2) + ")";
                        }
                        textView1.setText(str+'}');

                        editText1.setText("");
                        editText2.setText("");
                    }
                    catch (Exception e){
                        Toast toast = Toast.makeText(getApplicationContext(), "Координаты заданные некорректно!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vec.size()!=0){
                    vec.remove(vec.size()-1);

                    str = "A = {";
                    for (int i=0;i<vec.size();i++){
                        if (i==0){
                            str+="( " + Double.toString(vec.elementAt(i).first)+"; "+Double.toString(vec.elementAt(i).second)+")";
                        }
                        else{
                            str+=", ( " + Double.toString(vec.elementAt(i).first)+"; "+Double.toString(vec.elementAt(i).second)+")";
                        }
                    }

                    textView1.setText(str+'}');
                }
            }
        });

        buttonCurve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingCurve();
            }
        });

        buttonCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingCircle();
            }
        });

        escape_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMenu();
            }
        });
    }
}