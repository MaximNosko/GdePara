package ru.mn.gdepara;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParaSet
{
    public List<Para> list;
    public ParaSet()
    {
        list=new ArrayList<Para>();
    }

    public String getCloser()
    {
        if(list.size()==0)
        {
            return "-";
        }

        String rez="Вывод...";
        Date td=new Date();
        Date min=null;
        int min_i=0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        //rez+=simpleDateFormat.format(td);
        for(int i=0;i<list.size();i++)
        {

            //rez+=i;
            //rez+=simpleDateFormat.format(list.get(i).beginTime);
            //if(list.get(i).beginTime.after(td))
            if(list.get(i).beginTime.getHours()*60+list.get(i).beginTime.getMinutes()>td.getHours()*60+td.getMinutes())
            {
                rez+=i;
                if(min==null)
                {
                    min=list.get(i).beginTime;
                    min_i=i;
                }
                else if(list.get(i).beginTime.before(min))
                {
                    min=list.get(i).beginTime;
                    min_i=i;
                }
            }
        }
        rez+=min;
        if(min!=null)
        {
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            rez=list.get(min_i).discipline+"\n";
            rez+=list.get(min_i).lecturer+"\n";
            rez+=list.get(min_i).auditorium+"\n";
            rez+=simpleDateFormat.format(min)+"\n"+"До пары "+((min.getHours()*60+min.getMinutes())-(td.getHours()*60+td.getMinutes()))+" мин";
        }
        return rez;
    }
}
