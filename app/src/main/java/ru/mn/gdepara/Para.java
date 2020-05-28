package ru.mn.gdepara;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Para
{
    public String auditorium;
    public Date beginTime;
    public Date endTime;
    public int lessonNumberStart;
    public int lessonNumberEnd;
    public String kindOfWork;
    public String discipline;
    public String lecturer;
    public int beginM;
    public int beginH;
    public int endM;
    public int endH;
    public Para(JSONObject jsonObject)
    {
        auditorium=null;
        beginTime=null;
        endTime=null;
        lessonNumberStart=-1;
        lessonNumberEnd=-1;
        kindOfWork=null;
        discipline=null;
        lecturer=null;
        beginH=0;
        beginM=0;
        endH=0;
        endM=0;
        try
        {
            auditorium=jsonObject.getString("auditorium");
            discipline=jsonObject.getString("discipline");
            lecturer=jsonObject.getString("lecturer");
            SimpleDateFormat shablon = new SimpleDateFormat("HH:mm");
            beginTime=shablon.parse(jsonObject.getString("beginLesson"));
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(beginTime);
            beginM=calendar.get(Calendar.MINUTE);
            beginH=calendar.get(Calendar.HOUR_OF_DAY);
            endTime=shablon.parse(jsonObject.getString("endLesson"));
            calendar.setTime(endTime);
            endM=calendar.get(Calendar.MINUTE);
            endH=calendar.get(Calendar.HOUR_OF_DAY);
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public String toString()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return auditorium+"\n"+simpleDateFormat.format(beginTime)+"\t"+simpleDateFormat.format(endTime)+"\n"+discipline+"\n"+lecturer+"\n\n";
    }
    public LinearLayout getLayout(Context context)
    {
        Date d = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(d);
        int v1 = calendar.get(Calendar.HOUR_OF_DAY)*60+calendar.get(Calendar.MINUTE);
        int v2 = beginH*60+beginM;
        int min_do=v2-v1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        LinearLayout osn=new LinearLayout(context);
        osn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        osn.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout time=new LinearLayout(context);
        time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        time.setOrientation(LinearLayout.VERTICAL);
        final TextView vr_do=new TextView(context);
        vr_do.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        String n="";
        if(min_do>0)
        {
            osn.setBackgroundColor(Color.parseColor("#AEEEEE"));
            if(min_do<60)
            {
                n="Через "+(min_do)+ " мин";
            }
            else
            {
                //DecimalFormat df = new DecimalFormat();
                //df.setMaximumFractionDigits(1);
                //n="Через "+df.format(min_do/60.0)+ " ч";
                n="Через "+min_do/60+ ":"+min_do%60;
            }
        }
        else if(min_do>-90)
        {
            osn.setBackgroundColor(Color.parseColor("#FFE875"));
            n="Ещё "+(90+min_do)+" мин";
        }
        else
        {
            osn.setBackgroundColor(Color.parseColor("#C4C4C4"));
        }

        vr_do.setText(n);
        vr_do.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        time.addView(vr_do);
        final TextView vr_begin=new TextView(context);
        vr_begin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        vr_begin.setText(simpleDateFormat.format(beginTime));
        vr_begin.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        time.addView(vr_begin);
        final TextView vr_end=new TextView(context);
        vr_end.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        vr_end.setText(simpleDateFormat.format(endTime));
        vr_end.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        time.addView(vr_end);
        final Button aud=new Button(context);
        aud.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        aud.setText(ShortString.ShortAud(auditorium));
        aud.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        aud.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(aud.getText()==auditorium)
                {
                    aud.setText(ShortString.ShortAud(auditorium));
                }
                else
                {
                    aud.setText(auditorium);
                }
            }
        });
        time.addView(aud);
        osn.addView(time);
        LinearLayout info=new LinearLayout(context);
        info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        info.setOrientation(LinearLayout.VERTICAL);
        final Button nazv=new Button(context);
        nazv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        nazv.setText(ShortString.ShortNazv(discipline));
        nazv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        nazv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(nazv.getText()==discipline)
                {
                    nazv.setText(ShortString.ShortNazv(discipline));
                }
                else
                {
                    nazv.setText(discipline);
                }
            }
        });
        info.addView(nazv);
        final Button prepod=new Button(context);
        prepod.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        prepod.setText(ShortString.ShortFio(lecturer));
        prepod.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        prepod.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(prepod.getText()==lecturer)
                {
                    prepod.setText(ShortString.ShortFio(lecturer));
                }
                else
                {
                    prepod.setText(lecturer);
                }
            }
        });
        info.addView(prepod);
        osn.addView(info);
        if(min_do<=-90)
        {
            prepod.setVisibility(View.GONE);
            vr_do.setVisibility(View.GONE);
            vr_begin.setVisibility(View.GONE);
            vr_end.setVisibility(View.GONE);
            aud.setVisibility(View.GONE);
            final Button k_begin=new Button(context);
            k_begin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            k_begin.setText(simpleDateFormat.format(beginTime));
            k_begin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    prepod.setVisibility(View.VISIBLE);
                    //vr_do.setVisibility(View.VISIBLE);
                    vr_begin.setVisibility(View.VISIBLE);
                    vr_end.setVisibility(View.VISIBLE);
                    aud.setVisibility(View.VISIBLE);
                    k_begin.setVisibility(View.GONE);
                }
            });
            time.addView(k_begin);
        }
        return  osn;
    }
}
