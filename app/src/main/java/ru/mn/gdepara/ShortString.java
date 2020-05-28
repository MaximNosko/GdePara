package ru.mn.gdepara;

public class ShortString
{
    public static String ShortNazv(String txt)
    {
        String rez="";
        for(String slovo : txt.split(" "))
        {
            rez+=ShortWord(slovo)+" ";
        }
        return rez.trim();
    }
    public static String ShortFio(String txt)
    {
        String rez=txt.split(" ")[0]+" ";

        for(int i=1;i<txt.split(" ").length;i++)
        {
            rez+=txt.split(" ")[i].charAt(0)+". ";
        }
        return rez.trim();
    }
    public static String ShortAud(String txt)
    {
        return txt.split("/")[txt.split("/").length-1];
    }
    public static String ShortWord(String slovo)
    {
        if(slovo.length()<=3)
        {
            return slovo;
        }
        int kvo=2;
        if(arrayGlasProv(slovo,"bbab"))
        {
            kvo=4;
        }
        else if(arrayGlasProv(slovo,"babb"))
        {
            kvo=4;
        }
        else if(arrayGlasProv(slovo,"abbb"))
        {
            kvo=4;
        }
        else if(arrayGlasProv(slovo,"abb"))
        {
            kvo=3;
        }
        else if(arrayGlasProv(slovo,"bab"))
        {
            kvo=3;
        }
        return  slovo.substring(0,kvo)+".";
    }
    public static boolean isGlas(char t)
    {
        return "ауоыиэяюёеАУОЫИЭЯЮЁЕaeiouAEIOU".indexOf(t)>=0;
    }
    public static boolean arrayGlasProv(String txt,String shablon)
    {
        if(txt.length()<shablon.length())
        {
            return false;
        }
        for(int i=0;i<shablon.length();i++)
        {
            if(isGlas(txt.charAt(i))!=isGlas(shablon.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}
