package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pocketdiaryapp.R;

import java.util.ArrayList;

import Model.Event;
//Адатер для каждого мероприятия в списке
public class ItemListAdapter extends ArrayAdapter<Event> {
    private LayoutInflater inflater;
    private Context context;
    int resourse;

    public ItemListAdapter(@NonNull Context context, int resource, ArrayList<Event> itemlistevent) {
        super(context, resource, itemlistevent);
        this.context = context;
        this.resourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
        String time = getItem(position).getTime_start();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resourse, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) convertView.findViewById(R.id.item_name_event);
        viewHolder.time = (TextView) convertView.findViewById(R.id.item_time_event);

        viewHolder.name.setText(name);
        viewHolder.time.setText(time);

        return convertView;
    }


    private class ViewHolder{
        TextView time;
        TextView name;
    }
}
