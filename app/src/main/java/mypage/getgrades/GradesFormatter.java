package mypage.getgrades;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by haltayeb on 11-Apr-16.
 */

// Array of options --> ArrayAdabter --> ListView

//List view: {views: the_items.xml}
public class GradesFormatter extends Activity{



    ListView listView;
 protected void onCreate(Bundle savedInstanceState){

     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_list);

     ArrayList<String> courseList=getIntent().getExtras().getStringArrayList("courseList");
     ArrayList<String> gradeList=getIntent().getExtras().getStringArrayList("gradeList");

//     Log.d("Courses", String.valueOf(courseList));

     CustomListAdapter whatever = new CustomListAdapter(this, courseList, gradeList);
     listView = (ListView) findViewById(R.id.listView);
     listView.setAdapter(whatever);

 }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        }

}
