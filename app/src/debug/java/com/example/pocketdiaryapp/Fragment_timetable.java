package com.example.pocketdiaryapp;

import static Interface.ListViewItemType.Type_Date;
import static Interface.ListViewItemType.Type_Event;
import static Interface.ListViewItemType.Type_Null;
import static Interface.TypeMenu.Menu_For_Date;
import static Interface.TypeMenu.Menu_For_Event;
import static Interface.TypeMenu.Menu_For_Nullobj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Adapter.AdapterListHelper;
import Adapter.ListAdapterTimeTable;

import Adapter.RVadapterList;


import Data.DataBaseHandler;
import Model.DataEvents;
import Model.Event;
import Model.ListEventAndTime;

//Активити РАСПИСАНИЕ
@RequiresApi(api = Build.VERSION_CODES.M)
public class Fragment_timetable extends Fragment implements View.OnClickListener {
    private SimpleDateFormat dfview = new SimpleDateFormat("EEEE dd MMMM");
    private ListView listView;
    private Event event;
    private DataEvents dataEvents;
    private ArrayList<Event> listEvents;
    private ArrayList<DataEvents> listEventsOneDay = new ArrayList<>();
    private ListAdapterTimeTable adapter_timeTable;
    private RecyclerView rv_list;
    private RVadapterList rv_adapterList;
    private Calendar calendar;
    private int numberFocusItem;
    private Dialog dialog;
    private EditText editTextDate, editStartTextTime, editEndTextTime , editTextName, editNameLocation, editCategories ;
    private Button btnClose, btnSave;
    private boolean isLoading = false;
    private AdapterListHelper createListForAdapter;
    private Calendar firstdateGlobal = null, lastdateGlobal= null;
    private SimpleDateFormat dfDB = new SimpleDateFormat("dd-MM-yyyy");
    private boolean isLoadMode = false;

    Button addEventButton, exitMopdeDelte;
    Toast toast;
    PopupMenu menu1, menu2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addEventButton = view.findViewById(R.id.add_Event_Button);
        exitMopdeDelte = view.findViewById(R.id.exit_mode_delete);
        rv_list = view.findViewById(R.id.rv_list_timetable);

        addEventButton.setOnClickListener(this);
        exitMopdeDelte.setOnClickListener(this);

        Log.d("mysrcl: ", "создается клас списка");
        super.onCreate(savedInstanceState);
        Log.d("mysrcl: ", "создается адаптер");
        createListForAdapter = new AdapterListHelper(getContext(), new AdapterListHelper.OnScrollToPosition() {
            @Override
            public void onScroll(int count) {
                Log.d("mysrcl: ", "удачно");
                numberFocusItem = count;
            }
        });
        initRecyclerView();
        try {
            loadEvents();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rv_list.scrollToPosition(numberFocusItem);
        //addEventButton = findViewById(R.id.add_Event_Button);
        //exitMopdeDelte = findViewById(R.id.exit_mode_delete);
        dialog = new Dialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("mysrcl: ", "создается вью клас списка");
        View rootView = inflater.inflate(R.layout.activity_timetable, container, false);

        return rootView;
    }



    //Создание окна списка мероприятий
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //Переход на главное окно
    private void closeActivity() {
       // this.finish();
    }

    //Переход на главное окно
    public void goMain(View view){
        closeActivity();
    }

    //Надо вынести в отделный класс !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void goAddEvent(View view) {
        Intent intent1 = new Intent(getContext(), AddEvent.class);
        startActivity(intent1);
    }

    //Функция диалогового окна
    public void showDialogAddEvent(View view){
        isLoadMode = rv_adapterList.isLongClickMode();
        if(!isLoadMode) {
            dialog.setContentView(R.layout.dialog_add_event);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            //Инициализируем объекты диалогово окна
            editTextName = (EditText) dialog.findViewById(R.id.editTextName);
            editTextDate = (EditText) dialog.findViewById(R.id.editDatatext);
            editStartTextTime = (EditText) dialog.findViewById(R.id.editStartTimetext);
            editEndTextTime = (EditText) dialog.findViewById(R.id.editEndTimetext);
            editNameLocation = (EditText) dialog.findViewById(R.id.editNameLocation);
            editCategories = (EditText) dialog.findViewById(R.id.editCategories);
            btnClose = (Button) dialog.findViewById(R.id.btnClose);
            btnSave = (Button) dialog.findViewById(R.id.btnSave);

            //Устанавливаем слушатели нажатий
            editTextDate.setOnClickListener(this);
            editStartTextTime.setOnClickListener(this);
            editEndTextTime.setOnClickListener(this);
            btnClose.setOnClickListener(this);
            btnSave.setOnClickListener(this);
        }else{
            Log.d("mysrcl: ", "удалить выбранные мероприятия ");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(getContext());
            ArrayList<DataEvents> deletEvents_List = new ArrayList<>(rv_adapterList.getListEventsOneDay());
            for(DataEvents deletEvent : deletEvents_List){
                if(deletEvent.getType() == 1 & deletEvent.isChecked){
                    Log.d("mysrcl: ", Integer.toString(deletEvent.getId_db()));
                    dataBaseHandler.deletOneEvents(deletEvent.getId_db());
                }
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    UpdateListAfterAddElement();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            exitModeDelete(view);
        }

    }
    //Обработчик нажатий кнопок
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.editDatatext:
                // вызываем диалог с выбором даты
                TimeDataPicker.callDatePicker(getContext(), editTextDate);
                break;
            case R.id.editStartTimetext:
                // вызываем диалог с выбором времени
                TimeDataPicker.callTimePicker(getContext(), editStartTextTime);
                break;
            case R.id.editEndTimetext:
                TimeDataPicker.callTimePicker(getContext(), editEndTextTime);
                break;
            case R.id.btnClose:
                // закрываем окно
                dialog.cancel();
                break;
            case R.id.btnSave:
                // вызываем сохранение
                if(editTextDate.getText().toString().equals("") ){
                    toast =  Toast.makeText(getContext(), "Укажите дату !",Toast.LENGTH_SHORT);
                }else if(editStartTextTime.getText().toString().equals("")){
                    toast =  Toast.makeText(getContext(), "Установите время начала мероприятия!",Toast.LENGTH_SHORT);
                 }else if(editTextName.getText().toString().equals("")) {
                    toast = Toast.makeText(getContext()   , "Нет названия мероприятия !", Toast.LENGTH_SHORT);
                }else {
                    EventSaveFunctions.save(getContext(), editTextDate, editStartTextTime, editEndTextTime , editTextName, editNameLocation, editCategories);
                    try {
                        UpdateListAfterAddElement();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                    break;
                }
                toast.show();
                break;
            case R.id.add_Event_Button:
                showDialogAddEvent(v);
                break;
            case R.id.exit_mode_delete:
                exitModeDelete(v);
                break;
        }
    }

    //Инициализация объекта
    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        rv_list.setLayoutManager(layoutManager);// добавляем лояутменеджер
        rv_adapterList = new RVadapterList(getContext(), listEventsOneDay, new RVadapterList.OnDataEventClickListener() {
            @Override //Переопределяем метод слушателя нажатий
            public void onDataEventClick(View view, ArrayList<DataEvents> listEventsOneDay, int position, boolean isLongClick) {
                listEventsOneDay.get(position).getType();
                if(isLongClick){
                    //Функция долгого нажатия
                    switch (listEventsOneDay.get(position).getType()){
                        case Type_Date:
                            callMenu(Menu_For_Date, view, listEventsOneDay.get(position));
                            break;
                        case Type_Event:
                            callMenu(Menu_For_Event, view, listEventsOneDay.get(position));
                            break;
                        case Type_Null:
                            break;
                    }
                }else{
                    //Функция короткого нажатия
                }
            }
        }, new RVadapterList.ModeDelete() {
            @Override
            public void onModeDelete(boolean isLongClickMode) {
                isLoadMode = isLongClickMode;
                if(isLongClickMode) {
                    exitMopdeDelte.setVisibility(View.VISIBLE);
                    addEventButton.setBackgroundResource(R.drawable.delet_button);
                }else{
                    isLoadMode = isLongClickMode;
                    exitMopdeDelte.setVisibility(View.INVISIBLE);
                    addEventButton.setBackgroundResource(R.drawable.add_button_oval_vectr);
                }
            }
        });// инициализируем адаптер с кастомным слушателем нажатий
        rv_list.setAdapter(rv_adapterList);// присваем адаптер к рецайклеру

        // Слушатель прокрутки
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int visibleItemCount = layoutManager.getChildCount();// смотрим сколько элементов на экране
                int totalItemCount = layoutManager.getItemCount();// сколько всего элементов
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition(); // первый видимый элемент


                    // алгоритм подгрузки списка
                    if (!isLoading) { //если ничего не подгружаем то проверяем видим ли мы край списка

                        // подгружаем список снизу
                        if ((visibleItemCount + firstVisibleItems) >= (totalItemCount)) {
                            //Log.d("mysrcl: ", "добавить список НИЖЕ ");
                            isLoading = true;// помечаем что начинаем подгрузку
                            ListEventAndTime listEventAndTime = null;
                            try {
                                listEventAndTime = createListForAdapter.uploadListbelow(lastdateGlobal, getContext());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            rv_adapterList.insertItemBelow(listEventAndTime.getListEventsOneDay());
                            lastdateGlobal = listEventAndTime.getLastdateGlobal();
                            isLoading = false; // помечаем что подгрузка окончена

                        // подгружаем список сверху
                        } else if (firstVisibleItems <= 10) {
                            //Log.d("mysrcl: ", "добавить список ВЫШЕ ");
                            isLoading = true;// помечаем что начинаем подгрузку
                            ListEventAndTime listEventAndTime = null;
                            try {
                                listEventAndTime = createListForAdapter.uploadListAbove(firstdateGlobal, getContext());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            rv_adapterList.insertItemAbove(listEventAndTime.getListEventsOneDay());
                            firstdateGlobal = listEventAndTime.getFirstdateGlobal();
                            isLoading = false;// помечаем что подгрузка окончена
                        }
                    }
                }
            };
        rv_list.setOnScrollListener(onScrollListener); // установили слушатель прокрутки к рецайклеру
    }

    //загрузить мероприятия в адаптер
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadEvents() throws ParseException {
        ArrayList<DataEvents> dataEvents = getEventsFilterDate();// получием мероприятия из БД
        rv_adapterList.setItems(dataEvents);// передаем в адаптер
    }

    //Формируем начальный список
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<DataEvents> getEventsFilterDate() throws ParseException {
        // переменные для работы с датой
        int mYear , mMonth, mDay;
        Calendar firstdate = null, lastdate = null;

        //Получение локального календаря
        calendar = Calendar.getInstance();

        //Разбиваем дату на Год,Месяц,День
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        //Создаем календари с для определения начальной и конечной даты периода для формирования списка
        firstdate = new GregorianCalendar(mYear,mMonth,mDay);
        lastdate = new GregorianCalendar(mYear,mMonth,mDay);

        //Находим начальную и конечную дату периода
        firstdate.add(Calendar.DAY_OF_MONTH, -15);
        lastdate.add(Calendar.DAY_OF_MONTH, 15);

        //Запоминаем даты периода для дальнейших запросов
        firstdateGlobal = new GregorianCalendar(firstdate.get(Calendar.YEAR),firstdate.get(Calendar.MONTH),firstdate.get(Calendar.DAY_OF_MONTH));
        lastdateGlobal = new GregorianCalendar(lastdate.get(Calendar.YEAR),lastdate.get(Calendar.MONTH),lastdate.get(Calendar.DAY_OF_MONTH));

        //Возвращаем результат обработаный запрос в БД по получению списка мероприятий за период
        return  createListForAdapter.CreateUpdateAllLoadList(getContext(), firstdate, lastdate, true);
    }

    //Обновление списка после добавления мероприятия
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void UpdateListAfterAddElement() throws ParseException {
        ArrayList<DataEvents> dataEvents = createListForAdapter.CreateUpdateAllLoadList(getContext(), firstdateGlobal, lastdateGlobal, false);
        rv_adapterList.setItems(dataEvents);
    }

    public void callMenu(int typeMenu,View view, DataEvents dataEvents){
        switch (typeMenu){
            case Menu_For_Date:
                menu1 = new PopupMenu(getContext(), view );
                menu1.inflate(R.menu.menu1);
                menu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        toast =  Toast.makeText(getContext(), "Выделить все ",Toast.LENGTH_SHORT);
                        toast.show();
                        return true;
                    }
                });
                menu1.show();
                break;
            case Menu_For_Event:
                menu2 = new PopupMenu(getContext(), view);
                menu2.inflate(R.menu.menu2);
                menu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_menu_2_1:
                                toast =  Toast.makeText(getContext(), "Редактировать ",Toast.LENGTH_SHORT);
                                break;
                            case R.id.item_menu_2_2:
                                toast =  Toast.makeText(getContext(), "Удалить ",Toast.LENGTH_SHORT);
                                Log.d("mysrcl: ", "удалить выбранные мероприятия ");
                                DataBaseHandler dataBaseHandler = new DataBaseHandler(getContext());

                                Log.d("mysrcl: ", Integer.toString(dataEvents.getId_db()));
                                dataBaseHandler.deletOneEvents(dataEvents.getId_db());

                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        UpdateListAfterAddElement();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        toast.show();
                        return true;
                    }
                });
                menu2.show();
                break;
            case Menu_For_Nullobj:
                toast =  Toast.makeText(getContext(), "В этот день нет мероприятий !",Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }

    public void exitModeDelete(View view){
        isLoadMode = false;
        rv_adapterList.exitModDelete();
    }
}
