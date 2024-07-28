package Model;

//Класс для формирования списка МЕРОПРИЯТИЕ
public class DataEvents {

    public String dateEvent;//даты мороприятия
    public String nameEvent;//названия мероприятия
    public String timeStartEvent;//время мероприятия
    public String timeEndEvent;//время мероприятия
    private String name_location;//место проведения
    private String categories;//тип мероприятия
    private String lasting_time;//длительность мероприятия
    public int type;//поле типа мероприятия для адаптера(дата, мероприятие, пустой)
    public int type_time_of_week;//поле флаг текущей даты для даты
    public int id_db;//поле идентификатора в БД
    public boolean isVisibleCheck;//состояние видимости чекбокса
    public boolean isChecked;//состояния активности чекбокса

    //Конструкторы объекта Мероприятие
    public DataEvents(int id_db, int type, String dateEvent, String timeStartEvent, String timeEndEvent, String  lasting_time,String nameEvent, String name_location, String categories,boolean isVisibleCheck, boolean isChecked){
        this.id_db = id_db;
        this.type= type;
        this.dateEvent = dateEvent;
        this.timeStartEvent = timeStartEvent;
        this.nameEvent = nameEvent;
        this.isVisibleCheck = isVisibleCheck;
        this.isChecked = isChecked;
        this.timeEndEvent = timeEndEvent;
        this.name_location = name_location;
        this.categories = categories;
        this.lasting_time = lasting_time;


    }
    //Конструктор объекта дата
    public DataEvents(int type, String dateEvent, String nameEvent, int type_time_of_week, boolean isVisibleCheck, boolean isChecked){
        this.type= type;
        this.dateEvent = dateEvent;
        this.type_time_of_week = type_time_of_week;
        this.isVisibleCheck = isVisibleCheck;
        this.isChecked = isChecked;
        this.nameEvent = nameEvent;
    }
    //Конструктор объекта без мероприятий
    public DataEvents(int type, String dateEvent, String nameEvent, boolean isVisibleCheck, boolean isChecked){
        this.type= type;
        this.dateEvent = dateEvent;
        this.isVisibleCheck = isVisibleCheck;
        this.isChecked = isChecked;
        this.nameEvent = nameEvent;
    }
    //Конструктор пустого объекта
    public DataEvents(){

    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getTimeStartEvent() {
        return timeStartEvent;
    }

    public void setTimeStartEvent(String timeStartEvent) {
        this.timeStartEvent = timeStartEvent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType_time_of_week() {
        return type_time_of_week;    }

    public void setType_time_of_week(int type_time_of_week) {
        this.type_time_of_week = type_time_of_week;
    }

    public int getId_db() {
        return id_db;
    }

    public void setId_db(int id_db) {
        this.id_db = id_db;
    }

    public boolean getIsVisibleCheck() {
        return isVisibleCheck;
    }

    public void setVisibleCheck(boolean visibleCheck) {
        isVisibleCheck = visibleCheck;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTimeEndEvent() {
        return timeEndEvent;
    }

    public void setTimeEndEvent(String timeEndEvent) {
        this.timeEndEvent = timeEndEvent;
    }

    public String getName_location() {
        return name_location;
    }

    public void setName_location(String name_location) {
        this.name_location = name_location;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLasting_time() {
        return lasting_time;
    }

    public void setLasting_time(String lasting_time) {
        this.lasting_time = lasting_time;
    }
}


