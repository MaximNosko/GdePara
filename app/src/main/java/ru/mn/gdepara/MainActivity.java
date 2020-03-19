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
        //sPref = getPreferences(MODE_PRIVATE);
        //String loadedId = sPref.getString(group_id, "");
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String loadedId=(mSharedPreference.getString(group_id, ""));
        if(loadedId.equals(""))
        {
            Intent myIntent = new Intent(MainActivity.this, Settings.class);
            MainActivity.this.startActivity(myIntent);
        }
        else
        {
            //((TextView)findViewById(R.id.test_text)).setText(loadedId);
            Zapros z = new Zapros();
            z.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            //String htmlText = "<html><body>... </body></html>";
            //WebView webView = (WebView) findViewById(R.id.wv);
            //webView.loadData(htmlText, "text/html", "en_US");
            TextView textView = (TextView)findViewById(R.id.test_text);
            textView.setText("Подготовка запроса...");
            //sPref = getPreferences(MODE_PRIVATE);
            //String loadedId = sPref.getString(group_id, "");
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
                //TextView textView = (TextView)findViewById(R.id.test_text);
                //textView.setText("Запрос...");
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
                //rez=response.toString();
                JSONArray jsonArray = new JSONArray(response.toString());
                //JSONObject jsonObject = jsonArray.getJSONObject(0);
                //String a=jsonObject.getString("auditorium");
                //rez=a;
                rez="";
                for(int i=0;i<jsonArray.length();i++)
                {
                    rez+=new Para(jsonArray.getJSONObject(i)).toString();
                }

            }
            catch (Exception e)
            {
                rez=e.getMessage();
                //rez=e.getMessage();
            }
            return rez;
            /*OutputStream out = null;
            String t="-";
            try {
                //Получение ид
                URL obj = new URL("https://portal.fa.ru/CoreAccount/LogOn");
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }
                bufferedReader.close();
                //split("=")[1].
                t=connection.getHeaderField("Set-Cookie").split(";")[0];
                connection.disconnect();

                //авторизация
                URL obj2 = new URL("https://portal.fa.ru/CoreAccount/LogOn");
                HttpURLConnection connection2 = (HttpURLConnection) obj2.openConnection();
                connection2.setDoOutput(true);
                connection2.setRequestMethod("POST");
                connection2.setRequestProperty("Accept-Charset", "UTF-8");
                connection2.setRequestProperty("Cookie", t);
                /*OutputStream outputStream = connection2.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write("Login=174549&Pwd=пароль");*/ /*
                DataOutputStream wr = new DataOutputStream(connection2.getOutputStream());
                wr.writeBytes("Login=174549&Pwd=пароль");
                wr.flush();
                wr.close();

                BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String inputLine3;
                StringBuffer response3 = new StringBuffer();
                String txt="";

                while ((inputLine3 = bufferedReader3.readLine()) != null) {
                    response3.append(inputLine3);
                    //txt=txt+inputLine2;
                }
                bufferedReader3.close();
                //t=response3.toString();

                connection2.disconnect();
                //connection2.set



                //запрос расписания
                URL obj3 = new URL("https://portal.fa.ru/Job/SearchAjax");
                HttpURLConnection connection3 = (HttpURLConnection) obj3.openConnection();
                connection3.setDoOutput(true);
                connection3.setRequestMethod("POST");
                connection3.setRequestProperty("Accept-Charset", "UTF-8");
                connection3.setRequestProperty("Cookie", t);
                //OutputStream outputStream2 = connection3.getOutputStream();
                //OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(outputStream2);
                //BufferedWriter bufferedWriter2 = new BufferedWriter(outputStreamWriter2);
                //19","JobType": "INDIVIDUAL","GroupId":"21259","FacultyId":"0","FacultyValue":"","TutorId":"0","Tutor":"","DepartmentId":"0","DepartmentValue":"","AreaId":"0","AreaValue":""
                //bufferedWriter2.write("Date=08/08/2019&DateBegin=01/03/2019&DateEnd=01/03/2019&JobType=INDIVIDUAL&GroupId=21259&FacultyId=0&FacultyValue=&TutorId=0&Tutor=&DepartmentId=0&DepartmentValue=&AreaId=0&AreaValue=");

                DataOutputStream wr2 = new DataOutputStream(connection3.getOutputStream());
                GregorianCalendar nachalo=new GregorianCalendar();
                GregorianCalendar konec=new GregorianCalendar();
                switch (tday)
                {
                    case 1:
                        nachalo = new GregorianCalendar();
                        nachalo.add(Calendar.DATE, 1);
                        konec=nachalo;
                        break;
                    case 2:
                        nachalo = new GregorianCalendar();
                        konec = new GregorianCalendar();
                        konec.add(Calendar.DATE, 7);
                        break;
                }
                //String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                String nachalo_date = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(nachalo.getTime());
                String segodnya_date = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(new Date());
                String konec_date = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(konec.getTime());
                wr2.writeBytes("Date="+segodnya_date+"&DateBegin="+nachalo_date+"&DateEnd="+konec_date+"&JobType=INDIVIDUAL&GroupId=21259&FacultyId=0&FacultyValue=&TutorId=0&Tutor=&DepartmentId=0&DepartmentValue=&AreaId=0&AreaValue=");
                wr2.flush();
                wr2.close();



                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(connection3.getInputStream()));
                String inputLine2;
                StringBuffer response2 = new StringBuffer();
                //String txt="";

                while ((inputLine2 = bufferedReader2.readLine()) != null) {
                    response2.append(inputLine2);
                    //txt=txt+inputLine2;
                }
                bufferedReader2.close();
                connection3.disconnect();
                t=response2.toString();
            } catch (Exception e)
            {
                t="!!!"+e.getMessage();
                System.out.println(e.getMessage());
            }

            return t;*/
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            /*String htmlText = "<html>"+s+"</html>";
            WebView webView = (WebView) findViewById(R.id.wv);
            webView.loadData(htmlText, "text/html", "en_US");*/
            TextView textView = (TextView)findViewById(R.id.test_text);
            textView.setText(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
