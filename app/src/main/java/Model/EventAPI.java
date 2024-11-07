package Model;

public class EventAPI {
    private Integer id;
    private String name;
    private String data;
    private String time;

    public EventAPI(String name, String data, String time){
        this.name = name;
        this.data = data;
        this.time = time;

    }
    public EventAPI(Integer id, String name, String data, String time){
        this.id = id;
        this.name = name;
        this.data = data;
        this.time = time;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" id= " + id);
        sb.append(" name= " + name);
        sb.append(" date= " + data);
        sb.append(" time= " + time);
        sb.append("   |   ");
        String EventString;
        EventString = sb.toString();

        return EventString;
    }
}
