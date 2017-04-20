package ru.evoytenkoapps.yandexapi;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by evv on 19.04.2017.
 */

public class HTTPDownloadTask extends AsyncTask<String, Void, List>
{
    // Нужно для возвоащения результата
    public AsyncResponse delegate = null;

    List lst = new ArrayList<String>();
    //String path = "/storage/emulated/0/AppProjects/RSS/app/src/main/java/ru/evoytenkoapps/rss/MyDB";
    String DB_NAME = "MyDB.db";

    private String LOG_TAG ="RSS_BD";

    @Override
    protected List doInBackground(String... params)
    {
        // Ссылку на перевод получаем на входе
        String urlStr = params[0];
        InputStream is = null;

        lst.clear();
        try
        {
            URL url = new URL(urlStr);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d("debug", "The response is: " + response);
            is = connection.getInputStream();

            // Отработка XML
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            // включаем поддержку namespace (по умолчанию выключена)
            xmlFactoryObject.setNamespaceAware(true);
            // создаем парсер
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(is, null);

            int event;
            String text="";
            event = myParser.getEventType();

            boolean ITEM_START = false;
            boolean ITEM_END = false;

            while (event != XmlPullParser.END_DOCUMENT)
            {
                String name=myParser.getName();

                switch (event)
                {
                    // Фиксируем начало тега "text"
                    case XmlPullParser.START_TAG:
                        if (name.equals("text"))
                        {
                            // Фиксируем начало
                            ITEM_START = true;
                            ITEM_END = false;
                        }
                        break;

                    // Берем текст в теге "text"
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    // Фиксируем конец тега "text"
                    case XmlPullParser.END_TAG:
                        if (name.equals("text"))
                        {
                            if (ITEM_START == true && ITEM_END != true)
                            {
                                lst.add(text);
                            }
                        }

                        else if (name.equals("text"))
                        {
                            ITEM_START = false;
                            ITEM_END = true;
                        }
                        break;
                }

                event = myParser.next();
            }

            //return result;
            return lst;
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List data)
    {
        super.onPostExecute(data);
        if (data  != null)
        {
            List<String> tmplst = new ArrayList<String>();
            tmplst.addAll(data);
            delegate.processFinish(tmplst);
        }
    }
}

