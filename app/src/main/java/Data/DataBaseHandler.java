package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Utils.Util;

public class DataBaseHandler extends SQLiteOpenHelper {

    //Конструкток класса БД обработчика
    public DataBaseHandler(Context context) {
        super(context, Util.DATABASAE_NAME, null, Util.DATABASAE_VERSION);
    }

    //Создание БД
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_EVENT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_DATA_EVENT + " DATA, "
                + Util.KEY_TIME_START_EVENT + " TIME, "
                + Util.KEY_TIME_END_EVENT + " TIME, "
                + Util.KEY_NAME_EVENT + " TEXT, "
                + Util.KEY_NAME_LOCATION_EVENT + " TEXT, "
                + Util.KEY_CATEGORIES_EVENT + " TEXT, "
                + " TIMESTAMP" + " )" ;

        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //Добавление мероприятия в БД
    public void addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME_EVENT, event.getName());
        contentValues.put(Util.KEY_DATA_EVENT, event.getDate());
        contentValues.put(Util.KEY_TIME_START_EVENT, event.getTime_start());
        contentValues.put(Util.KEY_TIME_END_EVENT, event.getTime_end());
        contentValues.put(Util.KEY_NAME_LOCATION_EVENT, event.getName_location());
        contentValues.put(Util.KEY_CATEGORIES_EVENT, event.getCategories());
        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    //Получение одного мероприятия из БД по id
    public Event getEvent(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_DATA_EVENT, Util.KEY_TIME_START_EVENT, Util.KEY_TIME_END_EVENT, Util.KEY_NAME_EVENT, Util.KEY_NAME_LOCATION_EVENT, Util.KEY_CATEGORIES_EVENT},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Event event = new Event(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        db.close();
        return event;
    }

    //Получение всго списка мероприятий
    public List<Event> getAllEvents(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> eventlist = new ArrayList<>();
        String selectAllEvent = "Select * from " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllEvent, null);
        if(cursor.moveToFirst()){
            do{
                int id_collum_ID = cursor.getColumnIndex(Util.KEY_ID);
                int id_collum_Date = cursor.getColumnIndex(Util.KEY_DATA_EVENT);
                int id_collum_TimeStart = cursor.getColumnIndex(Util.KEY_TIME_START_EVENT);
                int id_collum_TimeEnd = cursor.getColumnIndex(Util.KEY_TIME_END_EVENT);
                int id_collum_Name = cursor.getColumnIndex(Util.KEY_NAME_EVENT);
                int id_collum_NameLocation = cursor.getColumnIndex(Util.KEY_NAME_LOCATION_EVENT);
                int id_collum_Categories = cursor.getColumnIndex(Util.KEY_CATEGORIES_EVENT);
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(id_collum_ID)));
                event.setDate(cursor.getString(id_collum_Date));
                event.setTime_start(cursor.getString(id_collum_TimeStart));
                event.setTime_end(cursor.getString(id_collum_TimeEnd));
                event.setName(cursor.getString(id_collum_Name));
                event.setName_location(cursor.getString(id_collum_NameLocation));
                event.setCategories(cursor.getString(id_collum_Categories));
                eventlist.add(event);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return eventlist;
    }

    //Изменения мероприятия
    public int updateEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_DATA_EVENT, event.getDate());
        contentValues.put(Util.KEY_TIME_START_EVENT, event.getTime_start());
        contentValues.put(Util.KEY_TIME_END_EVENT, event.getTime_end());
        contentValues.put(Util.KEY_NAME_EVENT, event.getName());
        contentValues.put(Util.KEY_NAME_LOCATION_EVENT, event.getName_location());
        contentValues.put(Util.KEY_CATEGORIES_EVENT, event.getCategories());
        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?", new String[]{String.valueOf(event.getId())});
    }

    //Удаление всех мероприятий
    public void deletAllEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, null, null);
        db.close();
    }

    //Удаление одного мероприятия
    public void deletOneEvents(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID+" = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    //Выввод в лог сисок мероприятий
    public void printEventsOnLog(){
        List<Event> eventList = getAllEvents();
                Log.d("mysrcl: ", "============================================================");
                for (Event events : eventList){
                    Log.d("mysrcl: ",
                                 "ID: " + events.getId()
                                    + "  date: " + events.getDate()
                                    + "  time start: " + events.getTime_start()
                                    + "  time end: " + events.getTime_end()
                                    + "  name: " + events.getName()
                                    + "  location: " + events.getName_location()
                                    + "  categories: " + events.getCategories());
                }
    }

    ///Получение списка мероприятий за пеериод с сортировкой по дате
    public List<Event> getAllEventsSorter(String firstdate, String lastdate){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> eventlist = new ArrayList<>();
        String selectAllEvent = "Select * from " + Util.TABLE_NAME + " Where " +  Util.KEY_DATA_EVENT + " BETWEEN " + "'" + firstdate + "'" + " AND " + "'" + lastdate + "'" + " ORDER BY " + Util.KEY_DATA_EVENT + " ASC, " + Util.KEY_TIME_START_EVENT + " ASC";
        Cursor cursor = db.rawQuery(selectAllEvent, null);
        if(cursor.moveToFirst()){
            do{
                int id_collum_ID = cursor.getColumnIndex(Util.KEY_ID);
                int id_collum_Date = cursor.getColumnIndex(Util.KEY_DATA_EVENT);
                int id_collum_TimeStart = cursor.getColumnIndex(Util.KEY_TIME_START_EVENT);
                int id_collum_TimeEnd= cursor.getColumnIndex(Util.KEY_TIME_END_EVENT);
                int id_collum_Name = cursor.getColumnIndex(Util.KEY_NAME_EVENT);
                int id_collum_NameLocation = cursor.getColumnIndex(Util.KEY_NAME_LOCATION_EVENT);
                int id_collum_Categories = cursor.getColumnIndex(Util.KEY_CATEGORIES_EVENT);
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(id_collum_ID)));
                event.setDate(cursor.getString(id_collum_Date));
                event.setTime_start(cursor.getString(id_collum_TimeStart));
                event.setTime_end(cursor.getString(id_collum_TimeEnd));
                event.setName(cursor.getString(id_collum_Name));
                event.setName_location(cursor.getString(id_collum_NameLocation));
                event.setCategories(cursor.getString(id_collum_Categories));
                eventlist.add(event);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return eventlist;
    }
}
