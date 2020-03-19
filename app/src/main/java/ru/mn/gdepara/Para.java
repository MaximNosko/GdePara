package ru.mn.gdepara;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        try
        {
            auditorium=jsonObject.getString("auditorium");
            discipline=jsonObject.getString("discipline");
            lecturer=jsonObject.getString("lecturer");
            SimpleDateFormat shablon = new SimpleDateFormat("HH:mm");
            beginTime=shablon.parse(jsonObject.getString("beginLesson"));
            endTime=shablon.parse(jsonObject.getString("endLesson"));
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public String toString()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return auditorium+"\t"+simpleDateFormat.format(beginTime)+"\t"+simpleDateFormat.format(endTime)+"\t"+discipline+"\t"+lecturer+"\n\n";
    }
}
