package ru.evoytenkoapps.yandexapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import android.view.*;
import android.widget.*;
import android.view.View.*;

public class MainActivity extends AppCompatActivity implements AsyncResponse
{
    // Формат XML
    
    final String BaseUrl = "https://translate.yandex.net/api/v1.5/tr/translate?";
    // API
    final String APIKEY = "key=trnsl.1.1.20170417T163213Z.7a423cf28b93e25b.40a68a67d0dada4e73dbfdda1c29447168a6e706";
    String TextForRequest = "&text=";
    // Язык
    final String Lang = "&lang=en-ru";
    String Result;

    EditText edttxtEnterText;
    TextView tvShowResult;
    Button btnAddToBest;
    Button btnFind;

    HTTPDownloadTask httpDT;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edttxtEnterText = (EditText) findViewById(R.id.edttxtEnterText);
        btnFind = (Button) findViewById(R.id.btnFind);
        tvShowResult = (TextView) findViewById(R.id.tv1);
        btnAddToBest = (Button) findViewById(R.id.btnAddToBest);
        
        httpDT = new HTTPDownloadTask();

        btnFind.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    // TODO: Implement this method
                    //Toast.makeText( MainActivity.this, "hh", Toast.LENGTH_SHORT).show();
                    String txt =  edttxtEnterText.getText().toString();
                    TextForRequest += txt;

                  
                    httpDT.delegate = MainActivity.this;

                    // Запускаем загрузку URL
                    httpDT.execute(BaseUrl + APIKEY + TextForRequest + Lang);
                   
                }
            });
    }

    // Вызывается когда данные загрузились в asynctask
    @Override
    public void processFinish(List<String> input)
    {
        if (input != null || input.size() != 0)
        {
            Result = input.get(0);
            tvShowResult.setText(Result);
        }
    }
}
