package Model;

//Класс объекта Мероприятие
public class Event {

    private int id;
    private String name;
    private String date_start;
    private String date_end;
    private String time_start;
    private String time_end;
    private String name_location;
    private String categories;

    //Конструктор класса пустой
    public Event() {
    }

    public Event(int id, String name, String date_start, String time_start, String date_end,  String time_end, String name_location, String categories) {
        this.id = id;
        this.name = name;
        this.date_start = date_start;
        this.date_end = date_end;
        this.time_start = time_start;
        this.time_end = time_end;
        this.name_location = name_location;
        this.categories = categories;
    }

    public Event(String name, String date_start, String time_start, String date_end,  String time_end, String name_location, String categories) {
        this.name = name;
        this.date_start = date_start;
        this.date_end = date_end;
        this.time_start = time_start;
        this.time_end = time_end;
        this.name_location = name_location;
        this.categories = categories;
    }

    public Event(int id, String name, String date_start, String time_start, String time_end, String name_location, String categories){
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.time_end = time_end;
        this.name = name;
        this.name_location = name_location;
        this.categories = categories;
    }

    public Event(String name, String date_start, String time_start, String time_end,  String name_location, String categories){
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.time_end = time_end;
        this.name = name;
        this.name_location = name_location;
        this.categories = categories;
    }

    //Конструктор класса 4 переменные
    public Event(int id, String name, String date_start, String time_start) {
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.name = name;

    }

    //Конструктор класса 3 переменные
    public Event(String name, String  date_start, String time_start) {
        this.date_start = date_start;
        this.time_start = time_start;
        this.name = name;

    }

    //НАЧАЛО Геттеры переменных
    public int getId() {
        return id;
    }
    public String getDate_start() {
        return date_start;
    }
    public String getName() {
        return name;
    }
    public String getTime_start() {
        return time_start;
    }

    public String getDateTime(){
        return date_start +" "+ time_start;
    }
    //КОНЕЦ Геттеры переменных

    //НАЧАЛО Сеттеры переменных
    public void setId(int id) {
        this.id = id;
    }
    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }
    public void setDateTime(String dataTime){
        String[] data_time = dataTime.split(" ");
        this.date_start = data_time[0];
        this.time_start = data_time[1];
    }
    public String getTime_end() {
        return time_end;
    }
    public void setTime_end(String time_end) {
        this.time_end = time_end;
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
    public String getDate_end() {
        return date_end;
    }
    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    //КОНЕЦ Сеттеры переменных
}
