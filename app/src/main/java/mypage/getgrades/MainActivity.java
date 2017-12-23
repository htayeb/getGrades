package mypage.getgrades;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import io.rmiri.buttonloading.ButtonLoading;

public class MainActivity extends android.app.Activity
{
    public static final String TAG = MainActivity.class.getSimpleName();
    TextView outputText;
//    Button sendData;
    EditText edtUser, edtPass;
    String URL = "https://www.uvic.ca/cas/login?service=https%3A%2F%2Fwww.uvic.ca%2F";
    ButtonLoading buttonLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = (TextView) findViewById(R.id.textView1);
//        sendData = (Button) findViewById(R.id.button1);
        edtUser = (EditText) findViewById(R.id.editText1);
        edtPass = (EditText) findViewById(R.id.editText2);
        buttonLoading = (ButtonLoading) findViewById(R.id.btnDown);

        buttonLoading.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                buttonLoading.setProgress(true);

                authenticate();
            }

            @Override
            public void onStart() {

            }




            @Override
            public void onFinish() {

            }
        });


    }



    private void goToGradesActivity(String userName, String passWord)
    {
        Intent intent = new Intent(this, Grades.class);
        intent.putExtra("userName",userName);
        intent.putExtra("passWord",passWord);
        startActivity(intent);
    }



    private void authenticate() {
        //call setProgress(false) after 5 second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userName = edtUser.getText().toString();
                String passWord = edtPass.getText().toString();
                AuthHandler handler = new AuthHandler(userName, passWord);
                String result = null;
                try
                {
                    result = handler.execute(URL).get();
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //outputText.append(result + "\n");
                if (result.contains("proceed"))
                {

                    goToGradesActivity(userName, passWord);
                }
                else if(result.contains("error"))
                {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_LONG).show();
                }
                else if (result.contains("serverError"))
                {
                    Toast.makeText(getApplicationContext(), "Server temporary overloaded. Please try again later", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Restart the app!", Toast.LENGTH_SHORT).show();
                    //outputText.append(result + "\n");
                }
                Log.v(TAG, String.valueOf(result));
                buttonLoading.setProgress(false);
            }
        }, 2000);
    }
}