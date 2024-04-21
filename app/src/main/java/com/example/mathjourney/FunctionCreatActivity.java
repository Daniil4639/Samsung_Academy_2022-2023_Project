package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.janino.ScriptEvaluator;
import org.w3c.dom.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class FunctionCreatActivity extends AppCompatActivity {
    // класс для активности, в которой пользователь набирает уравнение, которое нужно перевести в график
    // нажатие на конкретные кнопки добавляет в финальную строку те или иные элементы
    // в итоге, строка переводится в польскую запись и обрабатывается

    LinearLayout ll, ll2;
    LinearLayout.LayoutParams lp2;

    int xOk;

    String basicStr = "y =";
    Vector<String> newStr1, codeStr1;

    ImageView button1;
    TextView textView1, textView2, textWait, testView;
    String[] symbols = {"|x|","π", "e", "x", "<-",
                        "x^n", "7", "8", "9", "/",
                        "n!", "4", "5", "6", "*",
                        "log","1", "2", "3", "-",
                        "ln", "%", "0", ".", "+",
                        "sin","cos", "(","tg", "ctg",
                        "asin","acos", ")", "atg", "actg"
    };

    String[] outputSymbols = {" abs(  "," π ", " e ", " x ", "<-",
    "^","7","8", "9"," / ",
            " fact( ", "4", "5", "6", " * ",
            " log( ","1", "2", "3", " - ",
            " ln( ", " % ", "0", ".", " + ",
            " sin( "," cos( ", "("," tg( ", " ctg( ",
            " asin( "," acos( ", ")", " atg( ", " actg( "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_creat);
        button1 = findViewById(R.id.imageViewEscape);
        textView1 = findViewById(R.id.textViewGraph);
        textWait = findViewById(R.id.textView27);
        testView = findViewById(R.id.textView34);
        textView2 = findViewById(R.id.GraphText);
        ll = findViewById(R.id.buttonsSpace);
        textView2.setText(basicStr);
        newStr1 = new Vector<String>();

        String[] numbers = new String[101];
        int j = -50;
        for (int i=0;i<101;i++){
            numbers[i]=Integer.toString(j++);
        }

        xOk = 0;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        for (int i=0;i<35;i++){
            // здесь идет создание кнопок и настройка результатов их нажатия

            if (i%5==0){
                ll2 = new LinearLayout(this);
                lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
                ll2.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(ll2);
            }

            int index = i;
            TextView symb = new TextView(this);
            symb.setText(symbols[index]);
            symb.setTextSize(testView.getTextSize());
            symb.setTextColor(Color.BLACK);
            symb.setGravity(Gravity.CENTER);
            symb.setHeight(130);
            symb.setWidth((displayMetrics.widthPixels-20)/5);
            symb.setBackgroundResource(R.drawable.shape_button_3);

            symb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // если нажали кнопку "<-"
                    if(index == 4){
                        if (newStr1.size()!=0){
                            if (newStr1.elementAt(newStr1.size()-1).equals(" x ")){
                                xOk--;
                            }
                            newStr1.remove(newStr1.size()-1);
                        }
                    }
                    else{
                        if (index == 3){
                            xOk++;
                        }
                        newStr1.add(outputSymbols[index]);
                    }
                    String newStr="";
                    for (int j=0; j<newStr1.size();j++){
                        newStr+=newStr1.elementAt(j);
                    }
                    textView2.setText(basicStr+newStr);
                }
            });

            ll2.addView(symb);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingGadgets();
            }
        });

        // алгоритм нажатия кнопки "построить график"
        textView1. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textWait.setVisibility(View.VISIBLE);
                String code="";
                for (int i=0;i<newStr1.size();i++){
                    code+=newStr1.elementAt(i);
                }
                try {
                    // попытка построить график
                    loadingGraph(code, xOk);
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Функция задана некорректно!",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    protected void loadingGadgets(){
        Intent i;
        i = new Intent(this,GadgetsActivity.class);
        startActivity(i);
    }

    protected void loadingGraph(String codeStr, int xOk) throws Exception{

        // сначала идет проверка, можно ли данную строку перевести в польскую запись и обработать
        // если нет, то выдается исключение
        int x = 1;
        double res = Solution.eval(codeStr, x); // проверка работы функции
        if (xOk == 0){
            Toast toast = Toast.makeText(getApplicationContext(), Double.toString(res),Toast.LENGTH_LONG);
            toast.show();
            textWait.setVisibility(View.INVISIBLE);
        }
        else {
            Intent i;
            i = new Intent(this, GraphViewActivity.class);
            i.putExtra("code",codeStr);
            startActivity(i);
        }
    }


}