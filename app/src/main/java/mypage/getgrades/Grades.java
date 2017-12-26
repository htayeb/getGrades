package mypage.getgrades;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.io.IOException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grades extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private WebView webView;
    String mUrl = "https://www.uvic.ca/cas/login?service=https%3a//www.uvic.ca/BAN2P/banuvic.gzcaslib.P_Service_Ticket%3ftarget=bwskogrd.P_ViewTermGrde";


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);


        //Get webview
        webView = (WebView) findViewById(R.id.webView1);
        webView.loadUrl(mUrl);
        webView.setVisibility(View.INVISIBLE);
        startWebView(mUrl);

    }


    private void startWebView(final String url)
    {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link
        webView.setWebViewClient(new WebViewClient()

        {

            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }


            //Show loader on url load
            public void onLoadResource (WebView view, String url)
            {
                if (progressDialog == null)
                {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(Grades.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

            }

            public void onPageFinished(WebView view, String url)
            {
                try {
                    Intent intent = getIntent();
                    String userName = intent.getExtras().getString("userName");
                    String passWord = intent.getExtras().getString("passWord");
                    Log.d("HTML3",userName);
                    if ((progressDialog.isShowing()) && (userName != null || passWord != null)) {
//                        progressDialog.dismiss();

                        view.loadUrl("javascript: {" +

                                "document.getElementById('username').value = '" + userName + "';" +
                                "document.getElementById('password').value = '" + passWord + "';" +
//                                "document.getElementById('workstationType').click();" +
                                "document.getElementsByName('form-submit')[0].click();" +
                                "};");
                        SystemClock.sleep(1000);
                        webView.setVisibility(View.INVISIBLE);
                        final String newUrl=view.getUrl();

                        startNewWebView(newUrl);

                    }
                }

                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);
//        //Enable caching
//       webView.getSettings().setAppCacheEnabled(true);

    }

    private void startNewWebView(final String newUrl) throws IOException {
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView newView, String url) {
                newView.loadUrl("javascript:document.getElementsByTagName('input')[2].click();");
                final String newUrl=newView.getUrl();
                webView.setVisibility(View.INVISIBLE);
                startLastWebView(newUrl);
            }

            private void startLastWebView(String newUrl) {
                webView.setWebViewClient(new WebViewClient(){
                    public void onPageFinished(final WebView newView, final String newUrl) {
                        newView.loadUrl("javascript:document.getElementsByClassName('default');");
                        webView.setVisibility(View.INVISIBLE);
                        newView.evaluateJavascript(
//                                "(function() { return (document.getElementsByClassName('default');for(var x=0; x < elements.length; x++){html += elements[x].innerHTML;};);})();",
                                "(function() { return (document.getElementById('grades-table').outerHTML); })();",

                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String html) {
                                        Log.d("HTML", html);

                                        String re1="([A-Z]{3,4})";	// Any Single Character 1
                                        String re2="(.)";	// Any Single Word Character (Not Whitespace) 1
                                        String re3="([0-9]{3})";	// Any Single Word Character (Not Whitespace) 2

                                        String re4="(.)";	// Any Single Character 7
                                        String re5="(.)";	// Any Single Character 8
                                        String re6="(%)";	// Any Single Character 9

                                        Pattern p = Pattern.compile(re1+re2+re3,Pattern.MULTILINE| Pattern.DOTALL);
                                        Matcher m = p.matcher(html);

                                        Pattern p1 = Pattern.compile(re4+re5+re6,Pattern.MULTILINE| Pattern.DOTALL);
                                        Matcher m1 = p1.matcher(html);

                                        ArrayList<String> courseMatches = new ArrayList<String>();
                                        ArrayList<String> gradeMatches = new ArrayList<String>();
                                        while (m.find())
                                        {
                                            courseMatches.add(m.group());
                                        }
                                        Log.d("HTML", String.valueOf(courseMatches));
                                        while (m1.find())
                                        {
                                            gradeMatches.add(m1.group());
                                        }
                                        Log.d("HTML", String.valueOf(gradeMatches));
                                        goToGradesFormatterActivity(courseMatches, gradeMatches);
                                    }
                                });
                    }
                });
            }});}

    private void goToGradesFormatterActivity(ArrayList<String> courseMatches, ArrayList<String> gradeMatches)
    {
        Intent intent = new Intent(this, GradesFormatter.class);
        intent.putStringArrayListExtra("courseList", courseMatches);
        intent.putStringArrayListExtra("gradeList", gradeMatches);
        startActivity(intent);

    }


}






