package Adapter;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import Data.DataBaseHandler;
import Model.DataEvents;
import Model.Event;
import Model.ListEventAndTime;

//Инструменты обработки массива мероприятий для адаптера (из Серверной БД)
public class  ApiAdapterListHelper {

    //Реализуем интерфейс прокрутки
    public interface OnScrollToPosition{
        void onScroll(int count);
    }

    //Шаблоны для конфертации даты
    private SimpleDateFormat dfDB = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dfview = new SimpleDateFormat("EEEE dd MMMM");
    private SimpleDateFormat dfview2 = new SimpleDateFormat("EEEE dd MMMM yyyy");//формат даты если год не текущий
    private SimpleDateFormat dfrazn = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar calendar, today_calendar, tomorrow_calendar, yesterday_calendar;
    private Calendar now_calendar;//независимый календать текущей даты
    private int mYear , mMonth, mDay, now_mYear2;
    private DataEvents dataEvents;
    private ListEventAndTime listEventAndTime;
    private int numberFocusItem;
    public OnScrollToPosition onScrollToPosition;

    //коструктор(шаблонный)
    public ApiAdapterListHelper(Context context, OnScrollToPosition onScrollToPosition){
        this.onScrollToPosition = onScrollToPosition;
    }

    //Создание нового списка мероприятий с подгружением предыдущих 30 дней
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ListEventAndTime uploadListAbove(
                Calendar firstdateGlobal,
                Context context)
            throws ParseException {
                 firstdateGlobal.add(Calendar.DAY_OF_MONTH, -1);

        // создаем календари для работы с датой
        Calendar lasdateUpload =
                    new GregorianCalendar(
                        firstdateGlobal.get(Calendar.YEAR),
                        firstdateGlobal.get(Calendar.MONTH),
                        firstdateGlobal.get(Calendar.DAY_OF_MONTH)),
                firstdateUpload =
                        new GregorianCalendar(
                            firstdateGlobal.get(Calendar.YEAR),
                            firstdateGlobal.get(Calendar.MONTH),
                            firstdateGlobal.get(Calendar.DAY_OF_MONTH));

        // при формировании первой даты отступаем на 31 день ранее по календарю
        firstdateUpload.add(Calendar.DAY_OF_MONTH, -30);

        // запрашиваем из БД доп список мероприятий за период
        ArrayList<DataEvents> listEventsOneDay =
                                 new ArrayList<>(
                                        CreateUpdateAllLoadList(
                                            context,
                                            firstdateUpload,
                                            lasdateUpload,
                                            false));

        // формируес модель для возврата (список мероприятитй, первая и последняя дата общег осписка)
        //Log.d("mysrcl: ", "добавить С:  " + dfview.format(firstdateUpload.getTime()) + "ПО: " + dfview.format(lasdateUpload.getTime()));
        listEventAndTime = new ListEventAndTime(
                                    listEventsOneDay,
                                    firstdateUpload,
                                    lasdateUpload);

        return listEventAndTime;
    }

    //Создание нового списка мероприятий с подгружением следующих 30 дней
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ListEventAndTime uploadListbelow(
                                Calendar lastdateGlobal,
                                Context context)
        throws ParseException {
                //Переходим к следующей дате от последней в списке
                lastdateGlobal.add(Calendar.DAY_OF_MONTH, 1);

                // создаем календари для работы с датой
                Calendar firstdateUpload =
                            new GregorianCalendar(
                                    lastdateGlobal.get(Calendar.YEAR),
                                    lastdateGlobal.get(Calendar.MONTH),
                                    lastdateGlobal.get(Calendar.DAY_OF_MONTH)),
                        lasdateUpload =
                            new GregorianCalendar(
                                    lastdateGlobal.get(Calendar.YEAR),
                                    lastdateGlobal.get(Calendar.MONTH),
                                    lastdateGlobal.get(Calendar.DAY_OF_MONTH));

                 // при формировании последней даты отступаем на 31 день позже по календарю
                lasdateUpload.add(Calendar.DAY_OF_MONTH, 30);

                 // запрашиваем из БД доп список мероприятий за период
                ArrayList<DataEvents> listEventsOneDay =
                        new ArrayList<>(CreateUpdateAllLoadList(
                                context,
                                firstdateUpload,
                                lasdateUpload,
                                false));

                // формируес модель для возврата (список мероприятитй, первая и последняя дата общег осписка)
                listEventAndTime =
                        new ListEventAndTime(
                            listEventsOneDay,
                            firstdateUpload,
                            lasdateUpload);

                return listEventAndTime;
    }

    //Запрашиваем дополнительный список  мероприятий за период из БД
    private List<Event> getEventListFromDB(
                            Calendar firstdate,
                            Calendar lastdate,
                            Context context){

        DataBaseHandler dataBaseHandler =
                new DataBaseHandler(context);

        List<Event> eventList =
                dataBaseHandler.getAllEventsSorter(
                        dfDB.format(firstdate.getTime()),
                        dfDB.format(lastdate.getTime()));

        return eventList;
    }

    //Формирования массива для адаптера Всего загруженного списка (после прокрутки)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<DataEvents> CreateUpdateAllLoadList(
                                    Context context,
                                    Calendar firstdate,
                                    Calendar lastdate,
                                    boolean firs_Start)
            throws ParseException {
                        ArrayList<DataEvents> listEventsOneDay =
                                new ArrayList<>();

                        List<Event> eventList =
                                new ArrayList<>(
                                        getEventListFromDB(
                                            firstdate,
                                            lastdate,
                                            context));

        //Работа с датой
        calendar =
                new GregorianCalendar(
                        firstdate.get(Calendar.YEAR),
                        firstdate.get(Calendar.MONTH),
                        firstdate.get(Calendar.DAY_OF_MONTH));

        // разбираем дату на отдельные составные
        mYear = calendar.get(Calendar.YEAR);       //год
        mMonth = calendar.get(Calendar.MONTH);     //месяц
        mDay = calendar.get(Calendar.DAY_OF_MONTH);//день

        //переменные для текущей даты по составляющим
        now_calendar = Calendar.getInstance();
        today_calendar = Calendar.getInstance();
        yesterday_calendar = Calendar.getInstance();

        //находим даты
        yesterday_calendar.add(Calendar.DAY_OF_MONTH, -1); //вчерашняя
        tomorrow_calendar = Calendar.getInstance();           //текущая
        tomorrow_calendar.add(Calendar.DAY_OF_MONTH, 1);   //нзавтрашняя

        //Узнаем кол-во дней между датами для определения кол-ва циклов переборки дней
        DateTimeFormatter formatter =
                //шаблон для формата даты
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //первая дата в формате
        LocalDate localDate1 =
                LocalDate.parse(
                        dfrazn.format(
                                firstdate.getTime()),
                                formatter);
        //вторая дата в формате
        LocalDate localDate2 =
                LocalDate.parse(
                        dfrazn.format(
                                lastdate.getTime()),
                                formatter);

        //нахоид разность между датами в днях
        int count = (int)ChronoUnit.DAYS.between(localDate1,localDate2);

        //цикл формирования списка для адаптера согласно формату
        for(int i = 0; i <=  count; i++){
            // указатель на мероприяте (епждый раз обнуляем)
            boolean isEvent = false;

            // находим первую дату в списке
            calendar.set(
                    mYear,
                    mMonth,
                    mDay + i);

            //Log.d("mysrcl: ", "дата в цикле:  " + dfDB.format(calendar.getTime()) + "   текущая дата: " + dfDB.format(today_calendar.getTime()));

            //дописываем к дате год елси не текущий
            if(calendar.get(Calendar.YEAR) != now_calendar.get(Calendar.YEAR)){

                //объект формат даты с годом
                dataEvents =
                        new DataEvents(
                                0,
                                dfview.format(calendar.getTime()),
                                dfview2.format(calendar.getTime()),
                                false,
                                false);
            }
            // если это текущая даты
            else if(dfDB.format(calendar.getTime())
                    .equals(dfDB.format(today_calendar.getTime()))){

                //объект даты сегодняшнего деня
                dataEvents =
                        new DataEvents(
                                0,
                                dfview.format(calendar.getTime()),
                                "сегодня, " + dfview.format(calendar.getTime()),
                                1,
                                false,
                                false);

                //если первый запуск то запоминаем на сколько надо прокрутить до текущей даты
                if(firs_Start) {
                    numberFocusItem = listEventsOneDay.size();
                    onScrollToPosition.onScroll(numberFocusItem);
                }
            }
            // если вчерашняя дата от текущей
            else if(dfDB.format(calendar.getTime())
                    .equals(dfDB.format(yesterday_calendar.getTime()))){

                //объект даты вчерашнего деня
                dataEvents =
                        new DataEvents(
                                0,
                                dfview.format(calendar.getTime()),
                                "вчера, " + dfview.format(calendar.getTime()),
                                false,
                                false);
            }
            // если завтрашняя дата от текущей
            else if(dfDB.format(calendar.getTime())
                    .equals(dfDB.format(tomorrow_calendar.getTime()))){

                // объект даты завтрашнего деня
                dataEvents =
                        new DataEvents(
                                0, dfview.format(calendar.getTime()),
                                "завтра, " + dfview.format(calendar.getTime()),
                                false,
                                false);
            }
            //формат без года
            else {
                //объект без даты
                dataEvents =
                        new DataEvents(
                                0, dfview.format(calendar.getTime()),
                                dfview.format(calendar.getTime()),
                                false,
                                false);
            }

            // Добавляем в основной список
            listEventsOneDay.add(dataEvents);

            //находим все мероприятия в указанной дате
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("HH:mm");//шаблон для формата времени*/


            for(Event events : eventList){
                //Log.d("mysrcl: ", "локалдата:  " + events.getDate() +" "+ events.getTime_start());

                // находим мероприячтие из списка по дате
                if(events.getDate_start()
                        .equals(dfDB.format(calendar.getTime()))){
                            String date = events.getDate_start();
                            Date dataf = format.parse(date);
                            String lasting_time = "";

                    //первая дата в формате
                    LocalTime timeofTime1 =
                            LocalTime.parse(
                                    events.getTime_start(),
                                    time_formatter);
                    //вторая дата в формате
                    LocalTime  timeofTime2 =
                            LocalTime.parse(
                                    events.getTime_end(),
                                    time_formatter);

                    int count_time =
                            (int)ChronoUnit.HOURS.between(
                                    timeofTime1,
                                    timeofTime2);

                    int count_minute =
                            (int)ChronoUnit.MINUTES.between(
                                    timeofTime1,
                                    timeofTime2);

                    if(count_time > 0){
                        lasting_time = lasting_time + count_time+ "ч ";
                        if (count_minute > 60 * count_time) {
                            count_minute = count_minute - 60*count_time;
                            lasting_time = lasting_time + count_minute + "мин";
                        }
                    }else if(count_minute > 0) {
                        count_minute = count_minute - 60*count_time;
                        lasting_time = lasting_time + count_minute + "мин";
                    }

                    /*Log.d("mysrcl: ", "длительность:  "
                            +" c "+ events.getTime_start()
                            + " по " + events.getTime_end() + " длится: "
                            + count_time + " часов "
                            + count_minute + " минут   " + "длительность: " + lasting_time);*/

                    // формируем список мероприятий по указанной дате
                    dataEvents =
                            new DataEvents(
                                    events.getId() ,
                                    1,
                                    dfview.format(dataf),
                                    events.getTime_start(),
                                    events.getTime_end(),
                                    lasting_time,
                                    events.getName(),
                                    events.getName_location(),
                                    events.getCategories() ,
                                    false,
                                    false);

                    // добавляем список мероприятий одного дня к общему списку
                    listEventsOneDay.add(dataEvents);

                    // помечаем что хоть одно мероприятие было найдено
                    isEvent = true;
                }
            }
            // если по указанной дате не найдено мероприятий, создаем список без мероприятий
            if(!isEvent){
                // формируем пустой список мероприятией
                dataEvents =
                        new DataEvents(
                                2,
                                "0",
                                "Нет событий",
                                false,
                                false);

                // добавляем к общему списку
                listEventsOneDay.add(dataEvents);
            }
        }
        // возвращаем отформатированный список для адаптера
        return listEventsOneDay;
    }
}
