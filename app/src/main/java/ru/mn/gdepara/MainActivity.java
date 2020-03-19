package ru.mn.gdepara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.*;
import java.nio.charset.*;
import java.net.*;
import org.json.*;
import org.w3c.dom.Text;

import java.util.*;


public class MainActivity extends AppCompatActivity
{
    SharedPreferences sPref;
    final String group_id="group_id";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, R.string.reloading, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Zapros z = new Zapros();
                z.execute();

            }
        });
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String loadedId=(mSharedPreference.getString(group_id, ""));
        if(loadedId.equals(""))
        {
            Intent myIntent = new Intent(MainActivity.this, Settings.class);
            MainActivity.this.startActivity(myIntent);
        }
        else
        {
            Zapros z = new Zapros();
            z.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(MainActivity.this, Settings.class);
            MainActivity.this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class Zapros extends AsyncTask<Void, Void,String>
    {
        SharedPreferences sPref;
        final String group_id="group_id";
        String loadedId;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView textView = (TextView)findViewById(R.id.test_text);
            textView.setText("Подготовка запроса...");
            final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            loadedId=(mSharedPreference.getString(group_id, ""));

        }

        @Override
        protected String doInBackground(Void... strings)
        {
            OutputStream out = null;
            String rez=":(";
            try
            {
                String pattern = "yyyy.MM.dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());
                String adress="https://ruz.fa.ru/api/schedule/group/"+loadedId+"?start="+date+"&finish="+date+"&lng=1";

                URL obj = new URL(adress);

                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    response.append(inputLine);
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(response.toString());
                rez="";
                ParaSet ps = new ParaSet();
                for(int i=0;i<jsonArray.length();i++)
                {
                    Para tp=new Para(jsonArray.getJSONObject(i));
                    ps.list.add(tp);
                    //rez+=tp.toString();
                }
                rez=ps.toString();

            }
            catch (Exception e)
            {
                rez=e.getMessage();
            }
            return rez;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            TextView textView = (TextView)findViewById(R.id.test_text);
            textView.setText(s);
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
    }
}
