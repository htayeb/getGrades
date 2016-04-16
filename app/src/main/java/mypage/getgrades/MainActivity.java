package mypage.getgrades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends android.app.Activity
{
    public static final String TAG = MainActivity.class.getSimpleName();
    TextView outputText;
    Button sendData;
    EditText edtUser, edtPass;
    String URL = "https://www.uvic.ca/cas/login?service=https%3A%2F%2Fwww.uvic.ca%2F";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = (TextView) findViewById(R.id.textView1);
        sendData = (Button) findViewById(R.id.button1);
        edtUser = (EditText) findViewById(R.id.editText1);
        edtPass = (EditText) findViewById(R.id.editText2);
        sendData.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
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
                    goToGradesActivity();
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

            }
        });

    }

    private void goToGradesActivity()
    {
        Intent intent = new Intent(this, Grades.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}