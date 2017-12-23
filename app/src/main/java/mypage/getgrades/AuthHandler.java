package mypage.getgrades;
import java.io.IOException;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



    public class AuthHandler extends AsyncTask<String, Void, String> {


        //username and password passed in https POST
        String userName;
        String passWord;


        //UVic login page
        String URL = "https://www.uvic.ca/cas/login?service=https%3a//www.uvic.ca/BAN2P/banuvic.gzcaslib.P_Service_Ticket%3ftarget=bwskogrd.P_ViewTermGrde";


        public AuthHandler(String userName, String passWord) {
            // TODO Auto-generated constructor stub
            this.userName = userName;
            this.passWord = passWord;
        }
        //hello svn2

        @Override
        protected String doInBackground(String... params) {

            String result = "null";

            try {
                //Connect to the UVic login page inorder to parse the login page
                Connection.Response loginForm = Jsoup.connect(URL).method(Connection.Method.GET).timeout(10000).execute();

                //Parse login form to retrieve the unique UVic LT value and execution value
                Document doc = loginForm.parse();
                Log.d("String6", doc.toString());
                String ltValue = doc.select("input[name=lt]").attr("value");
                String executionValue = doc.select("input[name=execution]").attr("value");


                //Https Post with username, password, LT value, execution value,event ID and login cookies
                doc = Jsoup.connect(URL).data("username", userName).data("password", passWord).data("lt", ltValue).data("execution", executionValue).data("_eventId", "submit").cookies(loginForm.cookies()).ignoreHttpErrors(true).post();
                int statusCode = loginForm.statusCode();
                //Map<String, String> loginCookies = loginForm.cookies();
                Log.d("String2", doc.toString());
                //String mWelcomeMessage = doc.select("a.glblLnK").html();
                String mWelcomeMessage = doc.title();
                Log.v("mWelcomeMessage", mWelcomeMessage);
                if (mWelcomeMessage.equals("Term Grades")) {

                    Log.v(mWelcomeMessage, doc.toString());

                    result = "proceed";
                    Log.v("result", result);

                } else {
                    Log.v("MOOOOOOO", doc.toString());
                    result = "error";
                    Log.v("result", result);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


    }
