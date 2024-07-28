package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.pocketdiaryapp.Fragment_appeal;
import com.example.pocketdiaryapp.Fragment_timetable;
import com.example.pocketdiaryapp.R;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goTOViev(View view){
        Fragment fragment = null;

        switch (view.getId()){
            case R.id.button_go_timeTable:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fragment = new Fragment_timetable();
                }
                break;
            case R.id.button_go_appeal:
                fragment = new Fragment_appeal();
                break;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.item_fragment_timetable, fragment);
        ft.commit();
    }


}