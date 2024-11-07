package Model;

public class ElementList {

    public String dateEvent;//дата мороприятия
    private String lasting_time;//длительность мероприятия

    public boolean isVisibleCheck;//состояние видимости чекбокса
    public boolean isChecked;//состояния активности чекбокса

    public int type;//типа мероприятия для адаптера(дата, мероприятие, пустой)
    public int type_time_of_week;//флаг текущей даты для даты

    public Event event; //объект мероприятия

    public ElementList(String dateEvent, String lasting_time, boolean isVisibleCheck, boolean isChecked, int type, int type_time_of_week, Event event) {
        this.dateEvent = dateEvent;
        this.lasting_time = lasting_time;
        this.isVisibleCheck = isVisibleCheck;
        this.isChecked = isChecked;
        this.type = type;
        this.type_time_of_week = type_time_of_week;
        this.event = event;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getLasting_time() {
        return lasting_time;
    }

    public void setLasting_time(String lasting_time) {
        this.lasting_time = lasting_time;
    }

    public boolean isVisibleCheck() {
        return isVisibleCheck;
    }

    public void setVisibleCheck(boolean visibleCheck) {
        isVisibleCheck = visibleCheck;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType_time_of_week() {
        return type_time_of_week;
    }

    public void setType_time_of_week(int type_time_of_week) {
        this.type_time_of_week = type_time_of_week;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
