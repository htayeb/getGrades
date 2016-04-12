package mypage.getgrades;
import java.io.IOException;
import java.net.Proxy;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class AuthHandler extends AsyncTask<String, Void, String> {

    //username and password passed in https POST
    String userName, passWord;
    //UVic login page
    String URL = "https://www.uvic.ca/cas/login?service=https%3a//www.uvic.ca/BAN2P/banuvic.gzcaslib.P_Service_Ticket%3ftarget=bwskogrd.P_ViewTermGrde";

    public AuthHandler(String userName, String passWord)
    {
        // TODO Auto-generated constructor stub
        this.userName = userName;
        this.passWord = passWord;
    }

    @Override
    protected String doInBackground(String... params)
    {
        String result = "null";
        try
        {
            //Connect to the UVic login page inorder to parse the login page
            Connection.Response loginForm = Jsoup.connect(URL)
                    .method(Connection.Method.GET)
                    .timeout(10000)
                    .execute();
            //Parse login form to retrieve the unique UVic LT value and execution value
            Document doc = loginForm.parse();
            String ltValue = doc.select("input[name=lt]").attr("value");
            String executionValue = doc.select("input[name=execution]").attr("value");
            //Https Post with username, password, LT value, execution value,event ID and login cookies
            doc = Jsoup.connect(URL)
                    .data("username", userName)
                    .data("password", passWord)
                    .data("lt", ltValue)
                    .data("execution", executionValue)
                    .data("_eventId", "submit")
                    .cookies(loginForm.cookies())
                    .post();
            Log.d("String", doc.toString());
            //String mWelcomeMessage = doc.select("a.glblLnK").html();
            String mWelcomeMessage = doc.title();
            if (mWelcomeMessage.equals("Term Grades"))
            {
            //if (mWelcomeMessage.length() >0)
                //goToGradesActivity();
                Log.v(mWelcomeMessage, doc.toString());
                //result = "proceed";
                URL = "https://www.uvic.ca/BAN2P/bwskogrd.P_ViewTermGrde";
                doc = Jsoup.connect(URL)
                        .data("submit", "submit")
                        .cookies(loginForm.cookies())
                        .post();
                Log.v("String", doc.toString());
                //return doc
            }
                //Log.v(mWelcomeMessage, doc.toString());}
            else
            {
                Log.v("MOOOOOOO", doc.toString());
                result = "error";
            }

            //return "MOO";
            //Toast.makeText("Wrong Credentials", Toast.LENGTH_SHORT).show();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
       //if(mWelcomeMessage.equals("Term Grades")){return "DONE";}
        //else Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
        return result;
    }

   // private void goToGradesActivity()
   // {
      //  Intent intent = new Intent(this, Grades.class);
      //  startActivity(intent);
   // }
}