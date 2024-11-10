package Model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventDTO {

    private Long id;
    private String name;
    private LocalDateTime date_start;
    private LocalDateTime date_end;
    private String name_location;
    private String categories;

    //Конструктор класса пустой
    public EventDTO() {
    }

    public EventDTO(Long id, String name, LocalDateTime date_start, LocalDateTime date_end, String name_location, String categories) {
        this.id = id;
        this.name = name;
        this.date_start = date_start;
        this.date_end = date_end;
        this.name_location = name_location;
        this.categories = categories;
    }

    public EventDTO(String name, LocalDateTime date_start, LocalDateTime date_end, String name_location, String categories) {
        this.name = name;
        this.date_start = date_start;
        this.date_end = date_end;
        this.name_location = name_location;
        this.categories = categories;
    }

    public EventDTO(String name, LocalDateTime date_start, String name_location, String categories) {
        this.name = name;
        this.date_start = date_start;
        this.date_end = date_end;
        this.name_location = name_location;
        this.categories = categories;
    }

    public EventDTO(Long id, String name, LocalDateTime date_start, String name_location, String categories){
        this.id = id;
        this.date_start = date_start;
        this.name = name;
        this.name_location = name_location;
        this.categories = categories;
    }

    //Конструктор класса 4 переменные
    public EventDTO(Long id, String name, LocalDateTime date_start, LocalTime time_start) {
        this.id = id;
        this.date_start = date_start;
        this.name = name;

    }

    //Конструктор класса 3 переменные
    public EventDTO(String name, LocalDateTime  date_start, LocalTime time_start) {
        this.date_start = date_start;
        this.name = name;

    }

    //НАЧАЛО Геттеры переменных
    public Long getId() {
        return id;
    }
    public LocalDateTime getDate_start() {
        return date_start;
    }
    public String getName() {
        return name;
    }
    public LocalDateTime getDate_end() {
        return date_end;
    }

    //КОНЕЦ Геттеры переменных

    //НАЧАЛО Сеттеры переменных
    public void setId(Long id) {
        this.id = id;
    }
    public void setDate_start(LocalDateTime date_start) {
        this.date_start = date_start;
    }
    public void setName(String name) {
        this.name = name;
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
    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }

    //КОНЕЦ Сеттеры переменных
}
