package Model;

//Класс объекта Мероприятие
public class Event {

    private int id;
    private String date;
    private String name;
    private String time_start;
    private String time_end;
    private String name_location;
    private String categories;

    //Конструктор класса пустой
    public Event() {
    }

    public Event(int id, String date, String time_start, String time_end, String name, String name_location, String categories){
        this.id = id;
        this.date = date;
        this.time_start = time_start;
        this.time_end = time_end;
        this.name = name;
        this.name_location = name_location;
        this.categories = categories;
    }

    public Event(String date, String time_start, String time_end, String name, String name_location, String categories){
        this.id = id;
        this.date = date;
        this.time_start = time_start;
        this.time_end = time_end;
        this.name = name;
        this.name_location = name_location;
        this.categories = categories;
    }

    //Конструктор класса 4 переменные
    public Event(int id, String date, String time_start, String name) {
        this.id = id;
        this.date = date;
        this.time_start = time_start;
        this.name = name;

    }

    //Конструктор класса 3 переменные
    public Event(String date, String time_start, String name) {
        this.date = date;
        this.time_start = time_start;
        this.name = name;

    }

    //НАЧАЛО Геттеры переменных
    public int getId() {
        return id;
    }
    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getTime_start() {
        return time_start;
    }

    public String getDateTime(){
        return date+" "+ time_start;
    }
    //КОНЕЦ Геттеры переменных

    //НАЧАЛО Сеттеры переменных
    public void setId(int id) {
        this.id = id;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }
    public void setDateTime(String dataTime){
        String[] data_time = dataTime.split(" ");
        this.date = data_time[0];
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

    //КОНЕЦ Сеттеры переменных
}
