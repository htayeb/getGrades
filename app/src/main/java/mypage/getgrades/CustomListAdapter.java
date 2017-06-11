package mypage.getgrades;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by haltayeb on 11-Jun-17.
 */

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of courses
    private ArrayList<String> courseMatches;

    //to store the list of grades
    private ArrayList<String> gradeMatches;




    public CustomListAdapter(Activity context, ArrayList<String> courseMatches, ArrayList<String> gradeMatches) {

        super(context, R.layout.listview_row, courseMatches);

        this.context = context;
        this.courseMatches = courseMatches;
        this.gradeMatches = gradeMatches;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        int[] color_arr={Color.parseColor("#5ebcb3"),Color.parseColor("#93E5AB"),Color.parseColor("#4E878C"),Color.parseColor("#5DB2D4"),};
        rowView.setBackgroundColor(color_arr[position]);// this set background color
//        Color.parseColor("#5db2d4")

        //this code gets references to objects in the listview_row.xml file
        TextView courseTextField = (TextView) rowView.findViewById(R.id.textView5);
        TextView gradeTextField = (TextView) rowView.findViewById(R.id.textView4);

        //this code sets the values of the objects to values from the arrays
        courseTextField.setText(courseMatches.get(position));
        gradeTextField.setText(gradeMatches.get(position));

        return rowView;
    }
}