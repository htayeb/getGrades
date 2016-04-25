package mypage.getgrades;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Grades extends Activity {


    //private Button button;
    private WebView webView;
    String userName = "hsa";
    String passWord = "humAm92";
    String mUrl = "https://www.uvic.ca/cas/login?service=https%3a//www.uvic.ca/BAN2P/banuvic.gzcaslib.P_Service_Ticket%3ftarget=bwskogrd.P_ViewTermGrde";
    boolean loadingFinished = true;
    boolean redirect = false;

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

    @SuppressLint("SetJavaScriptEnabled")
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
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
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
                    int imSmart = 0;
                    if ((progressDialog.isShowing()) && (userName != null || passWord != null)) {
                        webView.loadUrl("javascript: {" +

                                "document.getElementById('username').value = '" + userName + "';" +
                                "document.getElementById('password').value = '" + passWord + "';" +
                                "document.getElementsByName('submit')[1].click();" +
                                "};");
                        //onSecondPageFinished(view, url);
                        //progressDialog.dismiss();


                        onSecPageFinished(view, url);
                        progressDialog.dismiss();
                        webView.setVisibility(View.VISIBLE);
                        //webView.loadUrl("javascript:document.getElementsByTagName('input')[2].click();");

                        //String data;
                        //webView.loadData(data, "text/html", null);
                        //Parse login form to retrieve the unique UVic LT value and execution value
                        // Log.d("String88", webUrl);
                    }
                }

                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
            private void onSecPageFinished(WebView view, String url)
            {
                SystemClock.sleep(5000);
                webView.loadUrl("javascript:document.getElementsByTagName('input')[2].click();");
                //progressDialog.dismiss();
                //webView.setVisibility(View.VISIBLE);
                //webView.loadUrl("javascript:document.getElementsByTagName('html')[0].innerHTML+'</html>';");
                //webView.setVisibility(View.VISIBLE);
            }

        });


        // Javascript inabled on webview
       webView.getSettings().setJavaScriptEnabled(true);



    // Detect when the back button is pressed


}}