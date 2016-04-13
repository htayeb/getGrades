package mypage.getgrades;
import java.io.IOException;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;


public class AuthHandler extends AsyncTask<String, Void, String> {

    //username and password passed in https POST
    String userName, passWord;
    //UVic login page
    String URL = "https://www.uvic.ca/cas/login?service=https%3a//www.uvic.ca/BAN2P/banuvic.gzcaslib.P_Service_Ticket%3ftarget=bwskogrd.P_ViewTermGrde";
    String URL2 = "https://www.uvic.ca/BAN2P/bwskogrd.P_ViewTermGrde";



    public AuthHandler(String userName, String passWord)
    {
        // TODO Auto-generated constructor stub
        this.userName = userName;
        this.passWord = passWord;
    }
   //hello svn2
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
            Map<String, String> loginCookies = loginForm.cookies();

            //Https Post with username, password, LT value, execution value,event ID and login cookies
            doc = Jsoup.connect(URL)
                    .data("username", userName)
                    .data("password", passWord)
                    .data("lt", ltValue)
                    .data("execution", executionValue)
                    .data("_eventId", "submit")
                    .data("term_in", "201509")
                    .referrer("https://www.uvic.ca/BAN2P/bwskogrd.P_ViewTermGrde")
                    .cookies(loginForm.cookies())
                    .post();
            //Map<String, String> loginCookies = loginForm.cookies();
            Log.d("String2", doc.toString());
            //String mWelcomeMessage = doc.select("a.glblLnK").html();
            String mWelcomeMessage = doc.title();
            if (mWelcomeMessage.equals("Term Grades"))
            {
            //if (mWelcomeMessage.length() >0)
                //goToGradesActivity();
                Log.v(mWelcomeMessage, doc.toString());
                //result = "proceed";
                //Document myDoc = loginForm.parse();


                //Element table = doc.select("table[class=datadisplaytable tablesorter]").first();

                //Iterator<Element> ite = table.select("td[class=default]").iterator();

                //String mConfirm = doc.select("table[id=grades-table]").html();
                //String mConfirm = doc.select("th.ddheader").html();
                //mConfirm = table.size();

                //Log.v("The mconfim", mConfirm);
                Log.v("String3", doc.toString());
                result = "proceed";
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
}