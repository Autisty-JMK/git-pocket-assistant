package Model;

import android.os.Build;

import java.util.List;

public class ResponAPI {
    private boolean isSuccessful;
    private List<EventDTO2> eventList;


    public ResponAPI(boolean isSuccessful, List<EventDTO2> eventList) {
        this.isSuccessful = isSuccessful;
        this.eventList = eventList;
    }

    public ResponAPI(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public List<EventDTO2> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventDTO2> eventList) {
        this.eventList = eventList;
    }

   /* public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        stringBuilder.append(" isSuccessful= " + isSuccessful);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            eventList.forEach(event -> {
                        stringBuilder.append(" id= " + event.getId());
                        stringBuilder.append(" name= " + event.getName());
                        stringBuilder.append(" date_start= " + event.getDate_start());
                        stringBuilder.append(" date_end= " + event.getDate_end());
                        stringBuilder.append(" categories= " + event.getCategories());
                        stringBuilder.append(" name_location= " + event.getName_location());

                    }
            );
        }
        s = stringBuilder.toString();
        return s;
    }*/
}
