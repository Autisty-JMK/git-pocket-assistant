package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import Model.DataEvents;

public class ListAdapterTimeTable extends ArrayAdapter<DataEvents> {

    private static final String TAG = "ListAdapterTimeTable";
    private LayoutInflater inflater;
    private ArrayList<DataEvents> listDataEventslocal;
    //private ArrayList<Event> eventArrayList;
    private Context context;
    int resourse;
    private ListAdapter item_list_adapter;

    public ListAdapterTimeTable(@NonNull Context context, int resource, ArrayList<DataEvents> listEventsOneDay) {
        super(context, resource, listEventsOneDay);
        this.context = context;
        this.resourse = resource;
        this.listDataEventslocal = new ArrayList<>(listEventsOneDay);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        return convertView;
    }

    private class ViewHolder{
        TextView dataEvent;
        ListView eventlist;
    }
}
