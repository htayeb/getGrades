package mypage.getgrades;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Grades extends Activity {

    //private Button button;
    private WebView webView;
    String userName = "hsa";
    String passWord = "humAm92";
    String mUrl = "https://www.uvic.ca/cas/login?service=https%3a//www.uvic.ca/BAN2P/banuvic.gzcaslib.P_Service_Ticket%3ftarget=bwskogrd.P_ViewTermGrde";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        //Get webview
        webView = (WebView) findViewById(R.id.webView1);
        webView.loadUrl(mUrl);
        webView.setVisibility(View.INVISIBLE);
        //webView.clearFormData();
       // webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setJavaScriptEnabled(true);

        //startWebView("http://www.androidexample.com/media/webview/login.html");


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(Grades.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url)
            {
                try{

                    if((progressDialog.isShowing()) &&(userName != null || passWord != null) )
                    {
                        progressDialog.dismiss();
                        progressDialog = null;
                        webView.loadUrl("javascript: {" +

                                "document.getElementById('username').value = '"+userName+"';" +
                                "document.getElementById('password').value = '"+passWord+"';" +
                               "document.getElementsByName('submit')[1].click();" +
                                "};");

                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }

            }

            private void onPageFinshed2(WebView view, String url)
            {
                webView.loadUrl("javascript:document.getElementsByTagName('input')[2].click();");
                webView.clearFormData();

            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
	    /*
	    webView.getSettings().setLoadWithOverviewMode(true);
	    webView.getSettings().setUseWideViewPort(true);
	    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	    webView.setScrollbarFadingEnabled(false);
	    webView.getSettings().setBuiltInZoomControls(true);
	    */


	     //String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         //webView.loadData(summary, "text/html", null);


        //Load url in webview
        //webView.loadUrl(url);


    }



    // Open previous opened link from history on webview when back button pressed
   /* @SuppressWarnings("deprecation")
    public static void ClearCookies(Context context)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            //Log.d(C.TAG, "Using ClearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
           // Log.d(C.TAG, "Using ClearCookies code for API <" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }*/
    //}
    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

}