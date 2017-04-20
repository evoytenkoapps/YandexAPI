package ru.evoytenkoapps.yandexapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse
{
    // Формат XML
    final String BaseUrl = "https://translate.yandex.net/api/v1.5/tr/translate?";
    // API
    final String APIKEY = "key=trnsl.1.1.20170417T163213Z.7a423cf28b93e25b.40a68a67d0dada4e73dbfdda1c29447168a6e706";
    String Text = "&text=hello";
    // Язык
    final String Lang = "&lang=en-ru";
    String Result;

    EditText edttxtEnterText;
    TextView tvShowResult;
    Button btnAddToBest;
    Button btnGetResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edttxtEnterText =(EditText) findViewById(R.id.edttxtEnterText);
        btnGetResult = (Button) findViewById(R.id.btnGetResult);
        tvShowResult = (TextView) findViewById(R.id.tv1);
        btnAddToBest = (Button) findViewById(R.id.btnAddToBest);

        HTTPDownloadTask httpDT = new HTTPDownloadTask();
        httpDT.delegate = this;

        // Запускаем загрузку URL
        httpDT.execute(BaseUrl + APIKEY + Text + Lang);
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
