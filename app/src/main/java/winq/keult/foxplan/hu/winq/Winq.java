package winq.keult.foxplan.hu.winq;

import android.app.Application;
import android.widget.TextView;

import com.example.keult.networking.model.EventData;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Tomi on 2017. 05. 30..
 */

public class Winq extends Application {

    public static String username;
    public static String password;
    public static List<EventData> eventDatas;

    Winq () {

    }

    public static void setTheRealTime (TextView yearText, TextView montAndDayText){
        //A headerre kiírjuk a valós dátumot
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        yearText.setText(String.valueOf(year));
        montAndDayText.setText(String.valueOf("0" + (month+1) + "." + day));
    }
}
