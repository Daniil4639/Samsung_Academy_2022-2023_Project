package com.example.mathjourney;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.resources.TextAppearance;

import org.w3c.dom.Text;

public class MainActivity2 extends AppCompatActivity {
    // активность, в которой происходит просчет действий с матрицами

    static int[][] matrix = new int [2][2];
    static int[][] matrix1 = new int [2][2];

    // нахождение определителя матрицы
    public static int determ (int[][] matrix, int N) {
        if (N == 1){
            return matrix[0][0];
        }
        else if (N == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        }
        else {
            int result = 0;
            for (int i = 0; i < N; i++) {
                int[][] matrix1 = new int[N - 1][N-1];
                int p = 0;
                int l = 0;
                for (int j = 1; j < N; j++) {
                    matrix1[p] = new int[N - 1];
                    for (int k = 0; k < N; k++) {
                        if (k != i) {
                            matrix1[p][l++] = matrix[j][k];
                        }
                    }
                    if (l == N - 1) {
                        p++;
                        l = 0;
                    }
                }
                if (i % 2 == 0) {
                    result += matrix[0][i] * determ(matrix1, N - 1);
                }
                else {
                    result += (-1) * matrix[0][i] * determ(matrix1, N - 1);
                }
            }
            return result;
        }
    }

    // нахождение суммы двух матриц
    public static int[][] sum(int[][] matrix1, int[][] matrix2, int N, int M) {
        int[][] result = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    // нахождение проиведения двух квадратных матриц
    public static int[][] mul(int[][] matrix1, int[][] matrix2, int N) {
        int[][] result = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int sum1 = 0;
                for (int k = 0; k < N; k++) {
                    sum1 += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum1;
            }
        }
        return result;
    }

    // нахождение произведения двух прямоугольных матриц
    public static int[][] mulNonSquare(int[][] matrix1, int[][] matrix2, int N, int M, int K, int L){
        int[][] result = new int[N][L];
        for (int i=0;i<N;i++){
            for (int j=0;j<L;j++){
                int sum1 = 0;
                for (int k=0;k<M;k++){
                    sum1+= matrix1[i][k]*matrix2[k][j];
                }
                result[i][j]=sum1;
            }
        }
        return result;
    }

    // нахождение обратной матрицы
    String[][] rev(int[][] matrix, int N) {
        String[][] result = new String[N][N];
        int det = determ(matrix, N);
        for (int i = 0; i < N; i++) {
            result[i] = new String[N];
            for (int j = 0; j < N; j++) {
                int[][] matrix1 = new int[N - 1][N - 1];
                int p = 0;
                int l = 0;
                for (int h = 0; h < N; h++) {
                    if (h != i) {
                        for (int k = 0; k < N; k++) {
                            if (k != j) {
                                matrix1[p][l++] = matrix[h][k];
                            }
                        }
                        if (l == N - 1) {
                            p++;
                            l = 0;
                        }
                    }
                }
                int num;
                if ((i + j) % 2 == 0) {
                    num = determ(matrix1, N - 1);
                }
                else {
                    num = -1 * determ(matrix1, N - 1);
                }
                int del = 2;
                int det1 = det;
                while (del <= abs(num) && del <= abs(det1)) {
                    if (num % del == 0 && det1 % del == 0) {
                        num /= del;
                        det1 /= del;
                    }
                    else {
                        del++;
                    }
                }
                result[i][j] = num + "/" + det1;
            }
        }
        String[][] result2 = new String[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result2[i][j] = result[j][i];
            }
        }
        return result2;
    }

    protected void loadingMenu(){
        Intent i;
        i = new Intent(this,GadgetsActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView imageView = findViewById(R.id.imageView2);
        EditText editText = findViewById(R.id.editTextNumber);
        EditText editText1 = findViewById(R.id.editTextNumber2);
        ImageView button = findViewById(R.id.imageView3);
        ImageView button1 = findViewById(R.id.imageView4);
        ImageView button2 = findViewById(R.id.imageView);
        ImageView button3 = findViewById(R.id.imageView1);
        TextView textView = findViewById(R.id.textView7);
        TextView textView1 = findViewById(R.id.textView11);
        TextView textView2 = findViewById(R.id.textView12);
        TextView textView3 = findViewById(R.id.textView13);
        TextView textView4 = findViewById(R.id.textView14);
        TextView textView5 = findViewById(R.id.textView8);
        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        Spinner spinner4 = findViewById(R.id.spinner4);
        TextView textViewDeterm1 = findViewById(R.id.textView16);
        TextView textViewDeterm2 = findViewById(R.id.textView21);
        TextView textViewAdd = findViewById(R.id.textView22);
        TextView textViewMul = findViewById(R.id.textView23);
        TextView textViewRev1 = findViewById(R.id.textView24);
        TextView textViewRev2 = findViewById(R.id.textView25);
        TextView textViewRes = findViewById(R.id.textView19);

        // назначение действий для каждой кнопки и проверка исключительных ситуаций
        textViewDeterm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(textView1.getText().toString()) > Integer.parseInt(spinner.getSelectedItem().toString()) && Integer.parseInt(spinner.getSelectedItem().toString()) == Integer.parseInt(spinner2.getSelectedItem().toString())) {
                    int res = determ(matrix, Integer.parseInt(spinner.getSelectedItem().toString()));
                    textViewRes.setText(Integer.toString(res));
                }
                else if (Integer.parseInt(textView1.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №1 не заполнена!");
                }
                else if (Integer.parseInt(spinner.getSelectedItem().toString()) != Integer.parseInt(spinner2.getSelectedItem().toString())) {
                    textViewRes.setText("Нахождение определителя неквадратной матрицы №1\n не представляется возможным!");
                }
            }
        });

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(spinner.getSelectedItem().toString()) != Integer.parseInt(spinner3.getSelectedItem().toString()) || Integer.parseInt(spinner2.getSelectedItem().toString()) != Integer.parseInt(spinner4.getSelectedItem().toString())){
                    textViewRes.setText("Нахождение суммы двух\nразных по размеру матриц\nне представляется возможным!");
                }
                else if (Integer.parseInt(textView1.getText().toString()) > Integer.parseInt(spinner.getSelectedItem().toString()) && Integer.parseInt(textView3.getText().toString()) > Integer.parseInt(spinner.getSelectedItem().toString())){
                    int[][] res;
                    res = sum(matrix, matrix1, Integer.parseInt(spinner.getSelectedItem().toString()), Integer.parseInt(spinner2.getSelectedItem().toString()));
                    String resStr = "";
                    for (int i = 0; i < Integer.parseInt(spinner.getSelectedItem().toString()); i++){
                        for (int j = 0; j < Integer.parseInt(spinner2.getSelectedItem().toString()); j++){
                            if (j == 0){
                                resStr+=Integer.toString(res[i][j]);
                            }
                            else{
                                resStr+=" " + res[i][j];
                            }
                        }
                        resStr+="\n";
                    }
                    textViewRes.setText(resStr);
                }
                else if (Integer.parseInt(textView1.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString()) && Integer.parseInt(textView3.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Обе матрицы не заполнены!");
                }
                else if (Integer.parseInt(textView1.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №1 не заполнена!");
                }
                else if (Integer.parseInt(textView3.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №2 не заполнена!");
                }
            }
        });

        textViewMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(spinner.getSelectedItem().toString()) != Integer.parseInt(spinner2.getSelectedItem().toString()) || Integer.parseInt(spinner3.getSelectedItem().toString()) != Integer.parseInt(spinner4.getSelectedItem().toString()) || Integer.parseInt(spinner.getSelectedItem().toString()) != Integer.parseInt(spinner3.getSelectedItem().toString()) || Integer.parseInt(spinner2.getSelectedItem().toString()) != Integer.parseInt(spinner4.getSelectedItem().toString())) {
                    if (Integer.parseInt(spinner2.getSelectedItem().toString()) != Integer.parseInt(spinner3.getSelectedItem().toString())){
                        textViewRes.setText("Нахождение произведения\nпредоставленных матриц\nне представляется возможным!");
                    }
                    else {
                        int[][] res = mulNonSquare(matrix, matrix1, Integer.parseInt(spinner.getSelectedItem().toString()), Integer.parseInt(spinner2.getSelectedItem().toString()), Integer.parseInt(spinner3.getSelectedItem().toString()), Integer.parseInt(spinner4.getSelectedItem().toString()));
                        String resStr = "";
                        for (int i = 0; i < Integer.parseInt(spinner.getSelectedItem().toString()); i++){
                            for (int j = 0; j < Integer.parseInt(spinner4.getSelectedItem().toString()); j++){
                                if (j == 0){
                                    resStr+=Integer.toString(res[i][j]);
                                }
                                else{
                                    resStr+=" " + res[i][j];
                                }
                            }
                            resStr+="\n";
                        }
                        textViewRes.setText(resStr);
                    }
                }
                else if (Integer.parseInt(textView1.getText().toString()) > Integer.parseInt(spinner.getSelectedItem().toString()) && Integer.parseInt(textView3.getText().toString()) > Integer.parseInt(spinner.getSelectedItem().toString())){
                    int[][] res;
                    res = mul(matrix, matrix1, Integer.parseInt(spinner.getSelectedItem().toString()));
                    String resStr = "";
                    for (int i = 0; i < Integer.parseInt(spinner.getSelectedItem().toString()); i++){
                        for (int j = 0; j < Integer.parseInt(spinner.getSelectedItem().toString()); j++){
                            if (j == 0){
                                resStr+=Integer.toString(res[i][j]);
                            }
                            else{
                                resStr+=" " + res[i][j];
                            }
                        }
                        resStr+="\n";
                    }
                    textViewRes.setText(resStr);
                }
                else if (Integer.parseInt(textView1.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString()) && Integer.parseInt(textView3.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Обе матрицы не заполнены!");
                }
                else if (Integer.parseInt(textView1.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №1 не заполнена!");
                }
                else if (Integer.parseInt(textView3.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №2 не заполнена!");
                }
            }
        });

        textViewDeterm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(textView3.getText().toString()) > Integer.parseInt(spinner3.getSelectedItem().toString()) && Integer.parseInt(spinner3.getSelectedItem().toString()) == Integer.parseInt(spinner4.getSelectedItem().toString())) {
                    int res = determ(matrix1, Integer.parseInt(spinner3.getSelectedItem().toString()));
                    textViewRes.setText(Integer.toString(res));
                }
                else if (Integer.parseInt(textView3.getText().toString()) <= Integer.parseInt(spinner3.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №2 не заполнена!");
                }
                else if (Integer.parseInt(spinner3.getSelectedItem().toString()) != Integer.parseInt(spinner4.getSelectedItem().toString())) {
                    textViewRes.setText("Нахождение определителя неквадратной матрицы №2\n не представляется возможным!");
                }
            }
        });

        textViewRev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(textView1.getText().toString()) <= Integer.parseInt(spinner.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №1 не заполнена!");
                }
                else if (Integer.parseInt(spinner.getSelectedItem().toString()) != Integer.parseInt(spinner2.getSelectedItem().toString())) {
                    textViewRes.setText("Нахождение обратной матрицы\nдля неквадратной матрицы №1\n не представляется возможным!");
                }
                else if (determ(matrix, Integer.parseInt(spinner.getSelectedItem().toString())) == 0){
                    textViewRes.setText("Нахождение обратной матрицы\nдля вырожденной матрицы №1\nне представляется возможным!");
                }
                else if (Integer.parseInt(textView1.getText().toString()) > Integer.parseInt(spinner.getSelectedItem().toString()) && Integer.parseInt(spinner.getSelectedItem().toString()) == Integer.parseInt(spinner2.getSelectedItem().toString())) {
                    String[][] res;
                    res = rev(matrix, Integer.parseInt(spinner.getSelectedItem().toString()));
                    String resStr = "";
                    for (int i = 0; i < Integer.parseInt(spinner.getSelectedItem().toString()); i++){
                        for (int j = 0; j < Integer.parseInt(spinner.getSelectedItem().toString()); j++){
                            if (j == 0){
                                resStr+=res[i][j];
                            }
                            else{
                                resStr+=" " + res[i][j];
                            }
                        }
                        resStr+="\n";
                    }
                    textViewRes.setText(resStr);
                }
            }
        });

        textViewRev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(textView3.getText().toString()) <= Integer.parseInt(spinner3.getSelectedItem().toString())){
                    textViewRes.setText("Матрица №2 не заполнена!");
                }
                else if (Integer.parseInt(spinner3.getSelectedItem().toString()) != Integer.parseInt(spinner4.getSelectedItem().toString())) {
                    textViewRes.setText("Нахождение обратной матрицы\nдля неквадратной матрицы №2\n не представляется возможным!");
                }
                else if (determ(matrix1, Integer.parseInt(spinner3.getSelectedItem().toString())) == 0){
                    textViewRes.setText("Нахождение обратной матрицы\nдля вырожденной матрицы №2\nне представляется возможным!");
                }
                else if (Integer.parseInt(textView3.getText().toString()) > Integer.parseInt(spinner3.getSelectedItem().toString()) && Integer.parseInt(spinner3.getSelectedItem().toString()) == Integer.parseInt(spinner4.getSelectedItem().toString())) {
                    String[][] res;
                    res = rev(matrix1, Integer.parseInt(spinner3.getSelectedItem().toString()));
                    String resStr = "";
                    for (int i = 0; i < Integer.parseInt(spinner3.getSelectedItem().toString()); i++){
                        for (int j = 0; j < Integer.parseInt(spinner3.getSelectedItem().toString()); j++){
                            if (j == 0){
                                resStr+=res[i][j];
                            }
                            else{
                                resStr+=" " + res[i][j];
                            }
                        }
                        resStr+="\n";
                    }
                    textViewRes.setText(resStr);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                textView1.setText("1");
                textView2.setText("1");
                textView.setText("");
                matrix = new int [Integer.parseInt(spinner.getSelectedItem().toString())][Integer.parseInt(spinner2.getSelectedItem().toString())];
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                textView1.setText("1");
                textView2.setText("1");
                textView.setText("");
                matrix = new int [Integer.parseInt(spinner.getSelectedItem().toString())][Integer.parseInt(spinner2.getSelectedItem().toString())];
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                textView3.setText("1");
                textView4.setText("1");
                textView5.setText("");
                matrix1 = new int [Integer.parseInt(spinner3.getSelectedItem().toString())][Integer.parseInt(spinner4.getSelectedItem().toString())];
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                textView3.setText("1");
                textView4.setText("1");
                textView5.setText("");
                matrix1 = new int [Integer.parseInt(spinner3.getSelectedItem().toString())][Integer.parseInt(spinner4.getSelectedItem().toString())];
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMenu();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setText("1");
                textView2.setText("1");
                textView.setText("");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView3.setText("1");
                textView4.setText("1");
                textView5.setText("");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1, num2, num, count, count1;
                String matr = textView.getText().toString();
                count = Integer.parseInt(spinner.getSelectedItem().toString());
                count1 = Integer.parseInt(spinner2.getSelectedItem().toString());
                num1 = Integer.parseInt(textView1.getText().toString());
                num2 = Integer.parseInt(textView2.getText().toString());
                if (!editText.getText().toString().equals("") && !editText.getText().toString().equals("-") && num1 <= count){
                    num = Integer.parseInt(editText.getText().toString());
                    editText.setText("");
                    if (num2 == count1){
                        matrix[num1 - 1][num2 - 1] = num;
                        textView1.setText(Integer.toString(num1 + 1));
                        textView2.setText("1");
                        matr+=" " + num + "\n";
                        textView.setText(matr);
                    }
                    else{
                        matrix[num1 - 1][num2 - 1] = num;
                        textView2.setText(Integer.toString(num2 + 1));
                        matr+=" " + num;
                        textView.setText(matr);
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1, num2, num, count, count1;
                String matr = textView5.getText().toString();
                count = Integer.parseInt(spinner3.getSelectedItem().toString());
                count1 = Integer.parseInt(spinner4.getSelectedItem().toString());
                num1 = Integer.parseInt(textView3.getText().toString());
                num2 = Integer.parseInt(textView4.getText().toString());
                if (!editText1.getText().toString().equals("") && !editText1.getText().toString().equals("-") && num1 <= count){
                    num = Integer.parseInt(editText1.getText().toString());
                    editText1.setText("");
                    if (num2 == count1){
                        matrix1[num1 - 1][num2 - 1] = num;
                        textView3.setText(Integer.toString(num1 + 1));
                        textView4.setText("1");
                        matr+=" " + num + "\n";
                        textView5.setText(matr);
                    }
                    else{
                        matrix1[num1 - 1][num2 - 1] = num;
                        textView4.setText(Integer.toString(num2 + 1));
                        matr+=" " + num;
                        textView5.setText(matr);
                    }
                }
            }
        });
    }
}