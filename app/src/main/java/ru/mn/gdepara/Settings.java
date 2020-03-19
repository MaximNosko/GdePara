package ru.mn.gdepara;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Settings extends AppCompatActivity
{
    SharedPreferences sPref;
    final String group_text="group_text";
    final String group_id="group_id";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button saveButton=(Button)findViewById(R.id.saveSettings);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SZapros z = new SZapros();
                z.execute();
            }
        });
        //sPref = getPreferences(MODE_PRIVATE);
        //String savedText = sPref.getString(group_text, "");
        //((EditText)findViewById(R.id.groupInput)).setText(savedText);
        //String savedId = sPref.getString(group_id, "");
        //((TextView)findViewById(R.id.showGroupId)).setText(savedId);
        final SharedPreferences sPref2= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //SharedPreferences.Editor editor = sPref.edit();
        String group_text_string=(sPref2.getString(group_text, ""));
        ((EditText)findViewById(R.id.groupInput)).setText(group_text_string);
        String group_id_string=(sPref2.getString(group_id, ""));
        ((TextView)findViewById(R.id.showGroupId)).setText(group_id_string);


    }
    class SZapros extends AsyncTask<Void, Void,String>
    {
        public String txt;
        public String txt_rus;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //String htmlText = "<html><body>... </body></html>";
            //WebView webView = (WebView) findViewById(R.id.wv);
            //webView.loadData(htmlText, "text/html", "en_US");
            //TextView textView = (TextView)findViewById(R.id.test_text);
            //textView.setText("Подготовка запроса...");
            EditText et=(EditText)findViewById(R.id.groupInput);
            txt=et.getText().toString();
            txt_rus=et.getText().toString();
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
                //String pattern = "yyyy.MM.dd";
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                //String date = simpleDateFormat.format(new Date());
                //URL obj = new URL("https://ruz.fa.ru/api/schedule/group/8891?start="+date+"&finish="+date+"&lng=1");
                txt= URLEncoder.encode(txt, StandardCharsets.UTF_8.toString());
                URL obj=new URL("https://ruz.fa.ru/api/search?term="+txt+"&type=group");
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
                if(jsonArray.length()>0)
                {
                    JSONObject t = jsonArray.getJSONObject(0);
                    rez=t.getString("id");
                }

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
            /*String htmlText = "<html>"+s+"</html>";
            WebView webView = (WebView) findViewById(R.id.wv);
            webView.loadData(htmlText, "text/html", "en_US");*/
            //TextView textView = (TextView)findViewById(R.id.test_text);
            //textView.setText(s);
            if(s.equals(""))
            {

            }
            else
            {
                //sPref = getPreferences(MODE_PRIVATE);
                //SharedPreferences.Editor ed = sPref.edit();
                //ed.putString(group_id, s);
                //ed.putString(group_text, txt_rus);
                //ed.apply();
                //Settings.this.finish();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(group_id, s);
                editor.putString(group_text, txt_rus);
                editor.apply();
                Settings.this.finish();
            }
            //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
