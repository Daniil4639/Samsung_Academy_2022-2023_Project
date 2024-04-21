package com.example.mathjourney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidplot.ui.SeriesRenderer;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.LineAndPointRenderer;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransformActivity extends AppCompatActivity{
    // активность, в которой происходит визуализация массива коориднат и вывод таблицы координат для удобства

    LinearLayout ll, ll2;
    LinearLayout.LayoutParams lp2;
    ImageView escape_im, dialog_image;
    ArrayList<Double> valX, valY, valXold, valYold;
    TextView textView;
    Boolean ok;
    XYPlot plot;

    double maxX,minX,maxY,minY;

    protected void loadingBack(){
        Intent i;
        i = new Intent(this,Transform_Creat_Activity.class);
        startActivity(i);
    }

    protected void loadingDialog(){
        Intent i;
        i = new Intent(this,Change_Dialog_Activity.class);
        i.putExtra("valuesX", valX);
        i.putExtra("valuesY", valY);
        i.putExtra("ok",ok);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        escape_im = findViewById(R.id.imageViewEscape);
        dialog_image = findViewById(R.id.imageView15);
        ll = findViewById(R.id.tableView);
        textView = findViewById(R.id.textViewHedder);
        plot = findViewById(R.id.plot);

        valX = (ArrayList<Double>) getIntent().getSerializableExtra("valuesX");
        valY = (ArrayList<Double>) getIntent().getSerializableExtra("valuesY");
        valXold = (ArrayList<Double>) getIntent().getSerializableExtra("oldX");
        valYold = (ArrayList<Double>) getIntent().getSerializableExtra("oldY");

        ok = (Boolean) getIntent().getBooleanExtra("ok",true);
        maxX = minX = valX.get(0);
        maxY = minY = valY.get(0);

        int xok = 0;
        while (xok!=3){
            if (xok==0) {
                ll2 = new LinearLayout(this);
                lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
                ll2.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(ll2);
                if (ok) {
                    for (int i = -1; i < valX.size(); i++) {
                        TextView tx = new TextView(this);
                        tx.setTextSize(30);
                        tx.setTextColor(Color.BLACK);
                        tx.setGravity(Gravity.CENTER);
                        tx.setBackgroundResource(R.drawable.text_view_board);
                        tx.setWidth(300);
                        tx.setHeight(120);
                        if (i==-1){
                            tx.setText("");
                        }
                        else{
                            tx.setText("A" + Integer.toString(i + 1));
                        }

                        ll2.addView(tx);
                    }
                }
                else{
                    for (int i = -1; i < valX.size() - 1; i++) {
                        TextView tx = new TextView(this);
                        tx.setText("A" + Integer.toString(i + 1));
                        tx.setTextSize(30);
                        tx.setTextColor(Color.BLACK);
                        tx.setGravity(Gravity.CENTER);
                        tx.setBackgroundResource(R.drawable.text_view_board);
                        tx.setWidth(300);
                        tx.setHeight(120);
                        if (i==-1){
                            tx.setText("");
                        }

                        ll2.addView(tx);
                    }
                }
                xok++;
            }
            else if (xok==1){
                ll2 = new LinearLayout(this);
                lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
                ll2.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(ll2);
                if (ok) {
                    for (int i = -1; i < valX.size(); i++) {
                        TextView tx = new TextView(this);
                        tx.setTextSize(30);
                        tx.setTextColor(Color.BLACK);
                        tx.setGravity(Gravity.CENTER);
                        tx.setBackgroundResource(R.drawable.text_view_board);
                        tx.setWidth(300);
                        tx.setHeight(120);
                        if (i!=-1){
                            tx.setText(String.format("%.4f", valX.get(i)));
                            if (valX.get(i)>maxX){
                                maxX = valX.get(i);
                            }
                            if (valX.get(i) < minX){
                                minX = valX.get(i);
                            }
                        }
                        else{
                            tx.setText("X");
                        }

                        ll2.addView(tx);
                    }
                }
                else{
                    for (int i = -1; i < valX.size() - 1; i++) {
                        TextView tx = new TextView(this);
                        tx.setTextSize(30);
                        tx.setTextColor(Color.BLACK);
                        tx.setGravity(Gravity.CENTER);
                        tx.setBackgroundResource(R.drawable.text_view_board);
                        tx.setWidth(300);
                        tx.setHeight(120);
                        if (i!=-1) {
                            tx.setText(String.format("%.4f", valX.get(i)));
                            if (valX.get(i) > maxX) {
                                maxX = valX.get(i);
                            }
                            if (valX.get(i) < minX) {
                                minX = valX.get(i);
                            }
                        }
                        else{
                            tx.setText("X");
                        }

                        ll2.addView(tx);
                    }
                }
                xok++;
            }
            else if (xok==2){
                ll2 = new LinearLayout(this);
                lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
                ll2.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(ll2);
                if (ok) {
                    for (int i = -1; i < valX.size(); i++) {
                        TextView tx = new TextView(this);
                        tx.setTextSize(30);
                        tx.setTextColor(Color.BLACK);
                        tx.setGravity(Gravity.CENTER);
                        tx.setBackgroundResource(R.drawable.text_view_board);
                        tx.setWidth(300);
                        tx.setHeight(120);
                        if (i!=-1) {
                            tx.setText(String.format("%.4f", valY.get(i)));
                            if (valY.get(i) > maxY) {
                                maxY = valY.get(i);
                            }
                            if (valY.get(i) < minY) {
                                minY = valY.get(i);
                            }
                        }
                        else{
                            tx.setText("Y");
                        }

                        ll2.addView(tx);
                    }
                }
                else{
                    for (int i = -1; i < valX.size() - 1; i++) {
                        TextView tx = new TextView(this);
                        tx.setTextSize(30);
                        tx.setTextColor(Color.BLACK);
                        tx.setGravity(Gravity.CENTER);
                        tx.setBackgroundResource(R.drawable.text_view_board);
                        tx.setWidth(300);
                        tx.setHeight(120);
                        if (i!=-1) {
                            tx.setText(String.format("%.4f", valY.get(i)));
                            if (valY.get(i) > maxY) {
                                maxY = valY.get(i);
                            }
                            if (valY.get(i) < minY) {
                                minY = valY.get(i);
                            }
                        }
                        else{
                            tx.setText("Y");
                        }

                        ll2.addView(tx);
                    }
                }
                xok++;
            }
        }

        for (int i=0;i<valXold.size();i++){
            if (valXold.get(i) > maxX){
                maxX = valXold.get(i);
            }
            else if (valXold.get(i) < minX){
                minX = valXold.get(i);
            }
        }

        for (int i=0;i<valYold.size();i++){
            if (valYold.get(i) > maxY){
                maxY = valYold.get(i);
            }
            else if (valYold.get(i) < minY){
                minY = valYold.get(i);
            }
        }

        // здесь происходит отрисовка предыдущей конфигурации координат (если была) и текущей
        Number[] arraysOy1 = {0,0};
        Number[] arraysOy2 = {maxY + 2,minY - 2};
        Number[] arraysOx1 = {minX - 2,maxX + 2};
        Number[] arraysOx2 = {0,0};

        XYSeries seriesOy = new SimpleXYSeries(Arrays.asList(arraysOy1), Arrays.asList(arraysOy2),"Oy");
        XYSeries seriesOx = new SimpleXYSeries(Arrays.asList(arraysOx1), Arrays.asList(arraysOx2),"Ox");

        LineAndPointFormatter seriesFormatO = new LineAndPointFormatter(Color.BLACK, Color.TRANSPARENT,null,null);
        LineAndPointFormatter seriesFormat1 = new LineAndPointFormatter(Color.BLUE, Color.BLUE,null,null);

        plot.getOuterLimits().set(minX - 2,maxX + 2,minY - 2,maxY + 2);
        //plot.getLayoutManager().remove(plot.getLegend());
        plot.addSeries(seriesOy,seriesFormatO);
        plot.addSeries(seriesOx,seriesFormatO);

        LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.RED, Color.RED,null,null);
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.TRANSPARENT, Color.WHITE,null,null);

        PointLabelFormatter plf = new PointLabelFormatter();
        plf.getTextPaint().setTextSize(PixelUtils.spToPix(15));
        plf.getTextPaint().setColor(Color.BLACK);

        series1Format.setPointLabelFormatter(plf);

        series1Format.setPointLabeler(new PointLabeler() {
            @Override
            public String getLabel(XYSeries series, int index) {
                if (!ok && index == valX.size() - 1){
                    return null;
                }
                return "A" + Integer.toString(index + 1);
            }
        });



        seriesFormat.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(12));
        series1Format.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(6));
        XYSeries series1, series2;

        series2 = new SimpleXYSeries(valXold, valYold,"old");
        series1 = new SimpleXYSeries(valX, valY,"new");

        if (valXold.size()!=0) {
            plot.addSeries(series2,seriesFormat1);
        }
        plot.addSeries(series1,seriesFormat);
        plot.addSeries(series1,series1Format);

        escape_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBack();
            }
        });

        dialog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog();
            }
        });
    }
}