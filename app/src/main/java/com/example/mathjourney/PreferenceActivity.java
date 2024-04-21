package com.example.mathjourney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class PreferenceActivity extends AppCompatActivity {
    // активность по выбору отображаемых тем

    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_COUNTER = "counter";
    private SharedPreferences mSettings;
    private CheckBox analiz;
    private CheckBox discret;
    private CheckBox algebra;
    private CheckBox algebra2;
    private CheckBox logic;
    private CheckBox geometry;
    private CheckBox deffur;
    private ImageView button, buttonLoad;
    private String res, resNew;
    private TextView textView;

    private String array[];
    private ArrayList<String> values;

    private FirebaseDatabase firebase;
    private DatabaseReference fireBaseRef;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    private String dbName = "materials";

    private ConnectionDetector connectionDetector;

    protected void loadingMenu(){
        Intent i;
        i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        connectionDetector = new ConnectionDetector(this);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        analiz = findViewById(R.id.checkboxAnaliz);
        discret = findViewById(R.id.checkboxDiscret);
        algebra = findViewById(R.id.checkboxAlgebra);
        algebra2 = findViewById(R.id.checkboxAlgebra2);
        logic = findViewById(R.id.checkboxLogic);
        geometry = findViewById(R.id.checkboxGeometry);
        deffur = findViewById(R.id.checkboxDeffur);
        button = findViewById(R.id.imageView);
        buttonLoad = findViewById(R.id.imageView1);
        textView = findViewById(R.id.textView26);
        firebase = FirebaseDatabase.getInstance();
        values = new ArrayList<>();

        mDBHelper = new DBHelper(this);
        res = mSettings.getString(APP_PREFERENCES_COUNTER, "0000000");

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMenu();
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean changeOk = true;

                resNew = Integer.toString(Boolean.compare(analiz.isChecked(),false)) + Integer.toString(Boolean.compare(discret.isChecked(),false)) + Integer.toString(Boolean.compare(algebra.isChecked(),false)) + Integer.toString(Boolean.compare(algebra2.isChecked(),false)) + Integer.toString(Boolean.compare(logic.isChecked(),false)) + Integer.toString(Boolean.compare(geometry.isChecked(),false)) + Integer.toString(Boolean.compare(deffur.isChecked(),false));
                for (int i=0;i<7;i++){
                    if (res.charAt(i) == resNew.charAt(i)){
                        continue;
                    }
                    // если галочка удаляется, то происходит удаление соответствующих разделу тем из SQLite
                    else if (res.charAt(i) == '1' && resNew.charAt(i) == '0'){
                        switch (i) {
                            case 0:
                                array = getResources().getStringArray(R.array.analiz);
                                break;
                            case 1:
                                array = getResources().getStringArray(R.array.discret);
                                break;
                            case 2:
                                array = getResources().getStringArray(R.array.algebra_1);
                                break;
                            case 3:
                                array = getResources().getStringArray(R.array.algebra_2);
                                break;
                            case 4:
                                array = getResources().getStringArray(R.array.logic);
                                break;
                            case 5:
                                array = getResources().getStringArray(R.array.geometry);
                                break;
                            case 6:
                                array = getResources().getStringArray(R.array.diff);
                                break;
                        }

                        for (int j = 1;j<array.length;j+=2){
                            String theoryIndex = array[j];
                            Cursor c = mDb.rawQuery("SELECT * FROM "+ dbName,null);
                            while (c.moveToNext()){
                                if (theoryIndex.equals(c.getString(0))){
                                    mDb.delete(dbName,"_id =" + theoryIndex, null);
                                }
                            }
                            c.close();
                        }
                    }
                    // если галочка добавляется, то из Firebase подгружаются в SQLite темы по индексам
                    else{
                        switch (i) {
                            case 0:
                                array = getResources().getStringArray(R.array.analiz);
                                break;
                            case 1:
                                array = getResources().getStringArray(R.array.discret);
                                break;
                            case 2:
                                array = getResources().getStringArray(R.array.algebra_1);
                                break;
                            case 3:
                                array = getResources().getStringArray(R.array.algebra_2);
                                break;
                            case 4:
                                array = getResources().getStringArray(R.array.logic);
                                break;
                            case 5:
                                array = getResources().getStringArray(R.array.geometry);
                                break;
                            case 6:
                                array = getResources().getStringArray(R.array.diff);
                                break;
                        }

                        if (connectionDetector.isConnected()){
                            for (int j = 1;j< array.length;j+=2){
                                String theoryIndex = array[j];

                                loadingData(theoryIndex);
                            }
                        }
                        else{
                            String resNew1 = "";
                            changeOk = false;
                            for (int strIndex = 0;strIndex<7;strIndex++){
                                if (strIndex!=i){
                                    resNew1+=resNew.charAt(strIndex);
                                }
                                else{
                                    resNew1+='0';
                                }
                            }
                            resNew = resNew1;

                            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует Интернет - соединение!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_COUNTER, resNew);
                editor.apply();
                setChecker(resNew);
                if (changeOk){
                    Toast toast = Toast.makeText(getApplicationContext(), "Изменения сохранены!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    // функция, в которой происходит связь с Firebase и запись данных
    private void loadingData(String theoryIndex){

        fireBaseRef = firebase.getReference(theoryIndex);
        fireBaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    values.add(ds.getValue(String.class));
                }

                ContentValues cv = new ContentValues();
                cv.put("_id", theoryIndex);
                cv.put("text_1", values.get(0));
                cv.put("text_2", values.get(1));
                cv.put("image_link", values.get(2));
                mDb.insert(dbName, null, cv);
                values.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        setChecker(res);
    }

    private void setChecker(String res){
        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            analiz.setChecked(Integer.parseInt(res.charAt(0) + "") == 1);
            discret.setChecked(Integer.parseInt(res.charAt(1) + "") == 1);
            algebra.setChecked(Integer.parseInt(res.charAt(2) + "") == 1);
            algebra2.setChecked(Integer.parseInt(res.charAt(3) + "") == 1);
            logic.setChecked(Integer.parseInt(res.charAt(4) + "") == 1);
            geometry.setChecked(Integer.parseInt(res.charAt(5) + "") == 1);
            deffur.setChecked(Integer.parseInt(res.charAt(6) + "") == 1);
        }
        else{
            analiz.setChecked(false);
            discret.setChecked(false);
            algebra.setChecked(false);
            algebra2.setChecked(false);
            logic.setChecked(false);
            geometry.setChecked(false);
            deffur.setChecked(false);
        }
    }

    /*@Override
    protected void onPause(){
        super.onPause();
        res = Integer.toString(Boolean.compare(analiz.isChecked(),false)) + Integer.toString(Boolean.compare(discret.isChecked(),false)) + Integer.toString(Boolean.compare(algebra.isChecked(),false)) + Integer.toString(Boolean.compare(algebra2.isChecked(),false)) + Integer.toString(Boolean.compare(logic.isChecked(),false)) + Integer.toString(Boolean.compare(geometry.isChecked(),false)) + Integer.toString(Boolean.compare(deffur.isChecked(),false));
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_COUNTER, res);
        editor.apply();
    }*/
}