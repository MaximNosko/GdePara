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

    @Override
    public String toString()
    {
        if(list.size()==0)
        {
            return "-";
        }

        String rez="Вывод...";
        Date td=new Date();
        Date min=null;
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).beginTime.after(td))
            {
                if(min==null)
                {
                    min=list.get(i).beginTime;
                }
                else if(list.get(i).beginTime.before(min))
                {
                    min=list.get(i).beginTime;
                }
            }
        }
        if(min!=null)
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            rez=simpleDateFormat.format(min);
        }
        return rez;
    }
}
