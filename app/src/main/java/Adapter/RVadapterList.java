package Adapter;
import static Interface.ListViewItemType.Type_Date;
import static Interface.ListViewItemType.Type_Event;
import static Interface.ListViewItemType.Type_Null;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Model.DataEvents;

import Interface.ListViewItemType;
import com.example.pocketdiaryapp.R;

import java.util.ArrayList;

//Адаптер для полного списка мероприятий
public class RVadapterList extends RecyclerView.Adapter {



    //интерфейс кастомного слушателя нажатий
    public interface OnDataEventClickListener{
        //обязательная функция слушателя нажатий
        void onDataEventClick(View view, ArrayList<DataEvents> listEventsOneDay, int position, boolean isLongClick);
    }

    public interface ModeDelete{
        void onModeDelete(boolean isLongClickMode);
    }

    private ArrayList<DataEvents> listEventsOneDay = new ArrayList<>();
    private Context context;
    private ArrayList<DataEvents> dataEvents;
    int countholder = 0;
    public final OnDataEventClickListener onClickListener;
    public final ModeDelete modeDelete;
    private final LayoutInflater layoutInflater;
    private boolean isLongClickMode = false;
    private int countChekVisible = 0;

    //Конструктор адаптера с кастомным слушателем нажатий
    public RVadapterList(Context context, ArrayList<DataEvents> listEventsOneDay, OnDataEventClickListener onClickListener, ModeDelete modeDelete){
        this.onClickListener = onClickListener;
        this.listEventsOneDay = listEventsOneDay;
        this.layoutInflater = LayoutInflater.from(context);
        this.modeDelete = modeDelete;
    }

    public int getItemViewType(int position) {
        if (listEventsOneDay.get(position).getType() == 0) {
            return ListViewItemType.Type_Date;
        } else if (listEventsOneDay.get(position).getType() == 1) {
            return ListViewItemType.Type_Event;
        } else if (listEventsOneDay.get(position).getType() == 2) {
            return ListViewItemType.Type_Null;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override

    //Определяем тип эелемнета в списке и присваиваем ему свой холдер
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        countholder++;
        //Log.d("info: ", Integer.toString(countholder));
        if (i == ListViewItemType.Type_Date) {
            View view = LayoutInflater.from(context).inflate(R.layout.date_item_timetable, parent, false);
            return new DateItemViewHolder(view);
        } else if (i == ListViewItemType.Type_Event) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_design_onlocate_event, parent, false);
            return new EventItemViewHolder(view);
        } else if (i == ListViewItemType.Type_Null) {
            View view = LayoutInflater.from(context).inflate(R.layout.null_item_timetable, parent, false);
            return new NullItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder eventListViewHolder, int position) {

        //Отрисовываем объект Даты
        if (eventListViewHolder instanceof DateItemViewHolder) {
            ((DateItemViewHolder) eventListViewHolder).dataEvent.setText(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getNameEvent()); //Устанавливае текст из списка

            //Настраиваем вид даты
            if (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getType_time_of_week() == 1) {
                ((DateItemViewHolder) eventListViewHolder).dataEvent.setTextColor(Color.parseColor("#FFFFFF"));//Если сегодняшний день, то ыделить еблым
                ((DateItemViewHolder) eventListViewHolder).dataEvent.setTextSize(2, 20);
            } else {
                ((DateItemViewHolder) eventListViewHolder).dataEvent.setTextColor(Color.parseColor("#ADADAD"));//В другом случае по умолчанию
                ((DateItemViewHolder) eventListViewHolder).dataEvent.setTextSize(2, 15);
            }

            //Настраиваем отображение ЧекБокса
            if (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getIsVisibleCheck()) {
                ((DateItemViewHolder) eventListViewHolder).checkdata.setVisibility(View.VISIBLE);
            } else {
                ((DateItemViewHolder) eventListViewHolder).checkdata.setVisibility(View.GONE);
            }

            //Настраиваем состояние ЧекБокса
            if(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getIsChecked()){
                ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(true);
            }else {
                ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(false);
            }

            ((DateItemViewHolder) eventListViewHolder).checkdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickChekboxDate(eventListViewHolder);
                }
            });

          //Отрисовывает объект Мароприятия
        } else if (eventListViewHolder instanceof EventItemViewHolder) {

            //Настройка имени и времени начала мероприятия
            ((EventItemViewHolder) eventListViewHolder).nameEvent.setText(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getNameEvent());
            ((EventItemViewHolder) eventListViewHolder).timeEvent.setText(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getTimeStartEvent());

            //Настройка отображения длительности мероприятия
            if(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getLasting_time().equals("")) {
                ((EventItemViewHolder) eventListViewHolder).lasting_time.setVisibility(View.GONE);
            }else{
                ((EventItemViewHolder) eventListViewHolder).lasting_time.setVisibility(View.VISIBLE);
                ((EventItemViewHolder) eventListViewHolder).lasting_time.setText(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getLasting_time());
            }

            //Настраиваем отображение ЧекБокса
            if (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getIsVisibleCheck()) {
                ((EventItemViewHolder) eventListViewHolder).checkEvent.setVisibility(View.VISIBLE);
            } else {
                ((EventItemViewHolder) eventListViewHolder).checkEvent.setVisibility(View.GONE);
            }

            //Настраиваем состояние ЧекБокса
            if(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getIsChecked()){
                ((EventItemViewHolder) eventListViewHolder).checkEvent.setChecked(true);
            }else {
                ((EventItemViewHolder) eventListViewHolder).checkEvent.setChecked(false);
            }

            /*Log.d("mysrcl: ", "локация мероприятия: " + listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getNameEvent()
                    + "   "+ listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getName_location());*/

            //Настройка состояния занча Локации
            if(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getName_location() != null){

                ((EventItemViewHolder) eventListViewHolder).imgLocation.setVisibility(View.VISIBLE);
                ((EventItemViewHolder) eventListViewHolder).nameLocation.setVisibility(View.VISIBLE);
                ((EventItemViewHolder) eventListViewHolder).nameLocation.setText(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getName_location());
            }else {
                ((EventItemViewHolder) eventListViewHolder).imgLocation.setVisibility(View.GONE);
                ((EventItemViewHolder) eventListViewHolder).nameLocation.setVisibility(View.GONE);
            }

            ((EventItemViewHolder) eventListViewHolder).checkEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).isChecked) {
                        listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(false);
                    }else{
                        listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(true);
                    }
                }
            });

            //Отрисовываем объект Без мерпориятий
        } else if (eventListViewHolder instanceof NullItemViewHolder) {
            ((NullItemViewHolder) eventListViewHolder).nullItem.setText(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getNameEvent());
        }

        //Утсанавливаем слушатель нажатий на каждый элемент списка
        eventListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getType()){
                    case Type_Date:
                        OnClickDateItem(eventListViewHolder);
                        break;
                    case Type_Event:
                        OnClickEventItem(eventListViewHolder);
                        break;
                    case Type_Null:

                        break;
                }
                isLongClickMode = (countChekVisible<=0) ?  false: true;

            }
        });

        eventListViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                switch (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getType()){
                    case Type_Date:
                        OnLongClickDateItem(eventListViewHolder);
                        break;
                    case Type_Event:
                        OnLongClickEventItem(eventListViewHolder, view);
                        break;
                    case Type_Null:
                        break;
                }
                isLongClickMode = (countChekVisible<=0) ?  false: true;
                modeDelete.onModeDelete(isLongClickMode);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEventsOneDay.size();
    }

    public void setItems(ArrayList<DataEvents> dataEvents) {
        listEventsOneDay.clear();
        listEventsOneDay.addAll(dataEvents);
        notifyDataSetChanged();
    }

    // подгружаем список сверху
    public void insertItemAbove(ArrayList<DataEvents> dataEvents){
        this.listEventsOneDay.addAll(0, dataEvents);
        notifyItemRangeInserted(0, dataEvents.size());
    }

    // подгружаем список снизу
    public void insertItemBelow(ArrayList<DataEvents> dataEvents){
        int sizeOldlist = listEventsOneDay.size();
        this.listEventsOneDay.addAll(sizeOldlist, dataEvents);
        notifyItemRangeInserted(sizeOldlist, dataEvents.size());
    }

    public ArrayList<DataEvents> getListEventsOneDay(){
        return listEventsOneDay;
    }

    public void clearItems() {
        listEventsOneDay.clear();
        notifyDataSetChanged();
    }

    //Холдер даты
    public static class DateItemViewHolder extends RecyclerView.ViewHolder {
        TextView dataEvent;
        CheckBox checkdata;

        public DateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dataEvent = itemView.findViewById(R.id.item_data_event);
            checkdata = itemView.findViewById(R.id.checked_data);

        }
    }

    //Холдер дня с событиями
    public static class EventItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameEvent, timeEvent, nameLocation, lasting_time;
        CheckBox checkEvent;
        ImageView imgLocation;

        public EventItemViewHolder(@NonNull View itemView) {
            super(itemView);
            timeEvent = itemView.findViewById(R.id.item_time_event);
            nameEvent = itemView.findViewById(R.id.item_name_event);
            checkEvent = itemView.findViewById(R.id.checked_event);
            nameLocation = itemView.findViewById(R.id.item_name_location);
            imgLocation = itemView.findViewById(R.id.image_location);
            lasting_time = itemView.findViewById(R.id.item_lasting_event);
        }
    }

    //Холдер пустого дня
    public static class NullItemViewHolder extends RecyclerView.ViewHolder {
        TextView nullItem;

        public NullItemViewHolder(@NonNull View itemVIev) {
            super(itemVIev);
            nullItem = itemVIev.findViewById(R.id.null_data_event);
        }
    }

    //Фенкции долгого нажатия по объекту Дата
    private void OnLongClickDateItem(RecyclerView.ViewHolder eventListViewHolder){
        //Log.d("mysrcl: ", "долгое нажатие");
        //Проверяем режим нажатия (Выбора или Нажатия)
        if(isLongClickMode){
            //В режиме выбора
            //Видил элемент или нет
            if(((DateItemViewHolder) eventListViewHolder).checkdata.getVisibility() == View.VISIBLE)
            {   //Если видим
                ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(false);//Убираем флаг
                ((DateItemViewHolder) eventListViewHolder).checkdata.setVisibility(View.INVISIBLE);//Скрываем
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setVisibleCheck(false);//Запоминаем в элементе что Чекбоск был скрыт
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(false);//Запоминаем в эелементе что Чекбокс не активен
                int count = 0;
                while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())){
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setChecked(false);
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setVisibleCheck(false);
                    count++;
                }
                notifyDataSetChanged();
                countChekVisible--;//Уменьшаем кол-во выбраных элементов
            }else{
                //Если не видим
                ((DateItemViewHolder) eventListViewHolder).checkdata.setVisibility(View.VISIBLE);//Показывваем чекбокс
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setVisibleCheck(true);//Запоминаем в элементе что Чекбокс был показан
                countChekVisible++;//Увеличиваем кол-во выбраных элементов
                int count = 0;
                while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())){
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setVisibleCheck(true);
                    count++;
                }
                notifyDataSetChanged();
            }
        }else{//Если в режиме нажатия
            ((DateItemViewHolder) eventListViewHolder).checkdata.setVisibility(View.VISIBLE);//Показывваем чекбокс
            listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setVisibleCheck(true);//Запоминаем в элементе что Чекбокс был показан
            countChekVisible++;//Увеличиваем кол-во выбраных элементов
            int count = 0;
            while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())){
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setVisibleCheck(true);
                count++;
            }
            notifyDataSetChanged();
            isLongClickMode = true;//Устанавливаем флаг вкл Режима долгого нажатия (Выбора)
            modeDelete.onModeDelete(isLongClickMode);
        }
    }

    //Функции короткого нажатия по объекту Дата
    private void OnClickDateItem(RecyclerView.ViewHolder eventListViewHolder){
        //Проверяем режим нажатия (Выбора или Нажатия)
        if(isLongClickMode){
            //В режиме выбора
            //Видил элемент или нет
            if(((DateItemViewHolder) eventListViewHolder).checkdata.getVisibility() == View.VISIBLE)
            {
                //Если видим
                //Если Чекбокс активен
                if(((DateItemViewHolder) eventListViewHolder).checkdata.isChecked())
                {
                     ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(false);//Деактивируем чекбокс
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(false);//ЗАпоминаем что чектокс неактивен
                    int count = 0;
                    while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())){
                        listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setChecked(false);
                        count++;
                    }
                    notifyDataSetChanged();

                //Если Чекбокс не активен
                }else{
                    ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(true);//Активируем чекбокс
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(true);//Запоминаем что чекбокс активен
                    int count = 0;
                    while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())){
                        listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setChecked(true);
                        count++;
                    }
                    notifyDataSetChanged();
                }
            //Если не видим
            }else{
                //выбираем другой день полностью
                ((DateItemViewHolder) eventListViewHolder).checkdata.setVisibility(View.VISIBLE);//Показываем чекбокс
                ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(true);//Активируем чекбокс
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setVisibleCheck(true);//Запоминаем что чектобс показан
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(true);//Запоминаем что чекбокс активен
                int count = 0;
                while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())){
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setVisibleCheck(true);
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()+count).setChecked(true);
                    count++;
                }
                notifyDataSetChanged();
                countChekVisible++;//Увеличиваем кол-во выбраных элементов
            }

        }


    }
    //Функции короткого нажатия по Объекту Мероприятия
    private void OnClickEventItem(RecyclerView.ViewHolder eventListViewHolder){
        //Проверяем режим нажатия (Выбора или Нажатия)
        if(isLongClickMode){
            //В режиме выбора
            //Видил элемент или нет
            if(((EventItemViewHolder) eventListViewHolder).checkEvent.getVisibility() == View.VISIBLE)
            {
                //Если видим
                //Если Чекбокс активен
                if(((EventItemViewHolder) eventListViewHolder).checkEvent.isChecked())
                {
                    ((EventItemViewHolder) eventListViewHolder).checkEvent.setChecked(false);//Деактивируем чекбокс
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(false);//Запоминаем что чектокс неактивен

                    //Если Чекбокс не активен
                }else{
                    ((EventItemViewHolder) eventListViewHolder).checkEvent.setChecked(true);//Активируем чекбокс
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(true);//Запоминаем что чекбокс активен
                }
            }
        }
    }

    //Функции долгого нажатия по объект Мероприятия
    private void OnLongClickEventItem(RecyclerView.ViewHolder eventListViewHolder, View view){
        if(!isLongClickMode) {
            onClickListener.onDataEventClick(view, listEventsOneDay, eventListViewHolder.getAdapterPosition(), true);
        }
    }
    //Нажатие по чекбоску объекта Дата
    private void onClickChekboxDate(RecyclerView.ViewHolder eventListViewHolder){
            //Если Чекбокс активен
            if ((listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).isChecked)) {
                ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(false);//Деактивируем чекбокс
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(false);//ЗАпоминаем что чектокс неактивен
                int count = 0;
                while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition() + count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())) {
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition() + count).setChecked(false);
                    count++;
                }
                notifyDataSetChanged();

                //Если Чекбокс не активен
            } else {
                ((DateItemViewHolder) eventListViewHolder).checkdata.setChecked(true);//Активируем чекбокс
                listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).setChecked(true);//Запоминаем что чекбокс активен
                int count = 0;
                while (listEventsOneDay.get(eventListViewHolder.getAdapterPosition() + count).getDateEvent().equals(listEventsOneDay.get(eventListViewHolder.getAdapterPosition()).getDateEvent())) {
                    listEventsOneDay.get(eventListViewHolder.getAdapterPosition() + count).setChecked(true);
                    count++;
                }
                notifyDataSetChanged();

        }
    }

    public void exitModDelete(){
        for(DataEvents dataEvents : listEventsOneDay){
            dataEvents.setVisibleCheck(false);
            dataEvents.setChecked(false);
        }
        isLongClickMode = false;
        notifyDataSetChanged();
        modeDelete.onModeDelete(isLongClickMode);
    }
    public boolean isLongClickMode(){
        return isLongClickMode;
    }
}
