package Model;

import java.util.ArrayList;
import java.util.Calendar;

public class ListEventAndTime {

    public Calendar firstdateGlobal;
    public Calendar lastdateGlobal;
    public ArrayList<DataEvents> listEventsOneDay;

    public ListEventAndTime(ArrayList<DataEvents> listEventsOneDay,Calendar firstdateGlobal,Calendar lastdateGlobal){
        this.listEventsOneDay = listEventsOneDay;
        this.firstdateGlobal = firstdateGlobal;
        this.lastdateGlobal = lastdateGlobal;
    }

    public Calendar getFirstdateGlobal() {
        return firstdateGlobal;
    }

    public void setFirstdateGlobal(Calendar firstdateGlobal) {
        this.firstdateGlobal = firstdateGlobal;
    }

    public Calendar getLastdateGlobal() {
        return lastdateGlobal;
    }

    public void setLastdateGlobal(Calendar lastdateGlobal) {
        this.lastdateGlobal = lastdateGlobal;
    }

    public ArrayList<DataEvents> getListEventsOneDay() {
        return listEventsOneDay;
    }

    public void setListEventsOneDay(ArrayList<DataEvents> listEventsOneDay) {
        this.listEventsOneDay = listEventsOneDay;
    }
}
