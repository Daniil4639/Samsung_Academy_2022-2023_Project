package com.example.mathjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class GraphViewActivity extends AppCompatActivity {
    // активность по отрисовке графика

    String code;
    ImageView imageView;
    XYPlot plot;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        plot = findViewById(R.id.plot);
        imageView = findViewById(R.id.imageViewEscape);
        textView = findViewById(R.id.textViewHedder);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingGadgets();
            }
        });

        // создание цепи, что рисует границы
        Number[] arraysOy1 = {0,0};
        Number[] arraysOy2 = {60,-60};
        Number[] arraysOx1 = {-60,60};
        Number[] arraysOx2 = {0,0};

        XYSeries seriesOy = new SimpleXYSeries(Arrays.asList(arraysOy1), Arrays.asList(arraysOy2),"Oy");
        XYSeries seriesOx = new SimpleXYSeries(Arrays.asList(arraysOx1), Arrays.asList(arraysOx2),"Ox");

        LineAndPointFormatter seriesFormatO = new LineAndPointFormatter(Color.BLACK, Color.TRANSPARENT,null,null);

        plot.getOuterLimits().set(-50,50,-50,50);
        plot.getLayoutManager().remove(plot.getLegend());
        plot.addSeries(seriesOy,seriesFormatO);
        plot.addSeries(seriesOx,seriesFormatO);

        Double[] domainSeries = new Double[1300];
        Double[] series = new Double[1300];

        LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.RED, Color.TRANSPARENT,null,null);
        XYSeries series1;

        boolean posOk = false;
        boolean negOk = false;
        boolean minusOk = false, plusOk=false;

        // процесс высчитывания значение функции в каждой точке интервала (-60; 60)
        // написан алгоритм, что позволяет не идеально, но с высокой долей вероятности определить вертикальную ассимптоту без польской записи
        int index = 0;
        for (double i=-60;i<=60;){
            double y = Solution.eval(code,i);
            if (Double.isNaN(y)){
                i+=0.1;
                continue;
            }
            else if (y>50){
                if (index == 0){
                    i+=0.1;
                    plusOk=true;
                    continue;
                }
                domainSeries[index]=i;
                series[index]=50.0;
                series1 = new SimpleXYSeries(Arrays.asList(domainSeries), Arrays.asList(series),"");
                index = 0;
                domainSeries = new Double[1300];
                series = new Double[1300];
                plot.addSeries(series1,seriesFormat);

                posOk=true;
                for (double j = i;j<i+0.1;j+=0.001){
                    if (Solution.eval(code,j)<-50){
                        posOk=false;
                        negOk=true;
                        break;
                    }
                }
                if (Double.isInfinite(Solution.eval(code,BigDecimal.valueOf(i+0.1).setScale(4,RoundingMode.HALF_UP).doubleValue()))){
                    for (double j = i+0.101;j<i+0.2;j+=0.001){
                        if (Solution.eval(code,j)<-50){
                            posOk=false;
                            negOk=true;
                            break;
                        }
                    }
                }
            }
            else if (y<-50){
                if (index == 0){
                    i+=0.1;
                    minusOk=true;
                    continue;
                }
                domainSeries[index]=i;
                series[index]=-50.0;
                series1 = new SimpleXYSeries(Arrays.asList(domainSeries), Arrays.asList(series),"");
                index = 0;
                domainSeries = new Double[1300];
                series = new Double[1300];
                plot.addSeries(series1,seriesFormat);

                negOk=true;
                for (double j = i;j<i+0.1;j+=0.001){
                    if (Solution.eval(code,j)>50){
                        negOk=false;
                        posOk=true;
                        break;
                    }
                }
                if (Double.isInfinite(Solution.eval(code,BigDecimal.valueOf(i+0.1).setScale(4,RoundingMode.HALF_UP).doubleValue()))){
                    for (double j = i+0.101;j<i+0.2;j+=0.001){
                        if (Solution.eval(code,j)>50){
                            negOk=false;
                            posOk=true;
                            break;
                        }
                    }
                }
            }
            else{
                if (plusOk){
                    plusOk=false;
                    domainSeries[index]=i-0.1;
                    series[index]=51.;
                    index++;
                }
                else if (minusOk){
                    minusOk=false;
                    domainSeries[index]=i-0.1;
                    series[index]=-51.;
                    index++;
                }
                if (i>=-59 && i<59){
                    double y1 = Solution.eval(code,i+0.1);
                    double y2 = Solution.eval(code, i+0.05);
                    if (y>y1){
                        if (y2>y1 && y2<y){
                            if (posOk){
                                domainSeries[index] = i;
                                series[index] = 50.0;
                                index++;
                                posOk = false;
                            }
                            else if (negOk){
                                domainSeries[index] = i;
                                series[index] = -50.0;
                                index++;
                                negOk = false;
                            }
                            else {
                                domainSeries[index] = i;
                                series[index] = y;
                                index++;
                            }
                        }
                        else{
                            int upOk = 0, downOk=0;
                            for (double j = i;j<i+0.1;j+=0.001){
                                if (Solution.eval(code,j)>50 && upOk==0){
                                    upOk=1;
                                }
                                else if (Solution.eval(code,j)<-50 && upOk==0){
                                    upOk=2;
                                }
                                else if (Solution.eval(code,j)>50 && upOk==2){
                                    downOk=1;
                                }
                                else if (Solution.eval(code,j)<-50 && upOk==1){
                                    downOk=2;
                                }
                                if (upOk!=0 && downOk!=0){
                                    break;
                                }
                            }
                            if (upOk==1 && downOk==0){
                                downOk = 1;
                            }
                            else if (upOk==2 && downOk==0){
                                downOk=2;
                            }
                            if (upOk == 0){
                                domainSeries[index] = i;
                                series[index] = y;
                                index++;
                            }
                            else if (upOk == 1){
                                domainSeries[index] = i;
                                series[index] = 50.0;
                                series1 = new SimpleXYSeries(Arrays.asList(domainSeries), Arrays.asList(series),"");
                                index = 0;
                                domainSeries = new Double[1300];
                                series = new Double[1300];
                                plot.addSeries(series1,seriesFormat);
                                if (downOk==1){
                                    posOk=true;
                                }
                                else{
                                    negOk=true;
                                }
                            }
                            else {
                                domainSeries[index] = i;
                                series[index] = -50.0;
                                series1 = new SimpleXYSeries(Arrays.asList(domainSeries), Arrays.asList(series),"");
                                index = 0;
                                domainSeries = new Double[1300];
                                series = new Double[1300];
                                plot.addSeries(series1,seriesFormat);
                                if (downOk==1){
                                    posOk=true;
                                }
                                else{
                                    negOk=true;
                                }
                            }

                            if (Double.isInfinite(Solution.eval(code,BigDecimal.valueOf(i+0.1).setScale(4,RoundingMode.HALF_UP).doubleValue()))){
                                for (double j = i+0.101;j<i+0.2;j+=0.001){
                                    if (Solution.eval(code,j)>50){
                                        posOk=true;
                                        negOk=false;
                                        break;
                                    }
                                    else if (Solution.eval(code,j)<-50){
                                        negOk=true;
                                        posOk=false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else if (y<y1){
                        if (y2>y && y2<y1){
                            if (posOk){
                                domainSeries[index] = i;
                                series[index] = 50.0;
                                index++;
                                posOk = false;
                            }
                            else if (negOk){
                                domainSeries[index] = i;
                                series[index] = -50.0;
                                index++;
                                negOk = false;
                            }
                            else {
                                domainSeries[index] = i;
                                series[index] = y;
                                index++;
                            }
                        }
                        else{
                            int upOk = 0, downOk=0;
                            for (double j = i;j<i+0.1;j+=0.0001){
                                if (Solution.eval(code,j)>50 && upOk==0){
                                    upOk=1;
                                }
                                else if (Solution.eval(code,j)<-50 && upOk==0){
                                    upOk=2;
                                }
                                else if (Solution.eval(code,j)>50 && upOk==2){
                                    downOk=1;
                                }
                                else if (Solution.eval(code,j)<-50 && upOk==1){
                                    downOk=2;
                                }
                                if (upOk!=0 && downOk!=0){
                                    break;
                                }
                            }
                            if (upOk==1 && downOk==0){
                                downOk = 1;
                            }
                            else if (upOk==2 && downOk==0){
                                downOk=2;
                            }
                            if (upOk == 0){
                                domainSeries[index] = i;
                                series[index] = y;
                                index++;
                            }
                            else if (upOk == 1){
                                domainSeries[index] = i;
                                series[index] = 50.0;
                                series1 = new SimpleXYSeries(Arrays.asList(domainSeries), Arrays.asList(series),"");
                                index = 0;
                                domainSeries = new Double[1300];
                                series = new Double[1300];
                                plot.addSeries(series1,seriesFormat);
                                if (downOk==1){
                                    posOk=true;
                                }
                                else{
                                    negOk=true;
                                }
                            }
                            else {
                                domainSeries[index] = i;
                                series[index] = -50.0;
                                series1 = new SimpleXYSeries(Arrays.asList(domainSeries), Arrays.asList(series),"");
                                index = 0;
                                domainSeries = new Double[1300];
                                series = new Double[1300];
                                plot.addSeries(series1,seriesFormat);
                                if (downOk==1){
                                    posOk=true;
                                }
                                else{
                                    negOk=true;
                                }
                            }

                            if (Double.isInfinite(Solution.eval(code,BigDecimal.valueOf(i+0.1).setScale(4,RoundingMode.HALF_UP).doubleValue()))){
                                for (double j = i+0.101;j<i+0.2;j+=0.001){
                                    if (Solution.eval(code,j)>50){
                                        posOk=true;
                                        negOk=false;
                                        break;
                                    }
                                    else if (Solution.eval(code,j)<-50){
                                        negOk=true;
                                        posOk=false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else{
                        domainSeries[index] = i;
                        series[index] = y;
                        index++;
                    }
                }
                else {
                    if (posOk){
                        domainSeries[index] = i;
                        series[index] = 50.0;
                        index++;
                        posOk = false;
                    }
                    else if (negOk){
                        domainSeries[index] = i;
                        series[index] = -50.0;
                        index++;
                        negOk = false;
                    }
                    else {
                        domainSeries[index] = i;
                        series[index] = y;
                        index++;
                    }
                }
            }
            i+=0.1;
        }

        // отрисовка массива значений
        series1 = new SimpleXYSeries(Arrays.asList(domainSeries), Arrays.asList(series),"");

        plot.addSeries(series1,seriesFormat);

        PanZoom.attach(plot);
    }

    protected void loadingGadgets(){
        Intent i;
        i = new Intent(this,FunctionCreatActivity.class);
        startActivity(i);
    }
}