package hr.duby.monitorapp.activities;

import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import hr.duby.monitorapp.Const;
import hr.duby.monitorapp.R;

public class WebBrowserActivity extends AppCompatActivity {

    private WebView webView;
    String urlLink = "";
    private int loadCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        DLog("onCreate");

        trustAllCertificates();

        Bundle bundleExtras = getIntent().getExtras();
        if (bundleExtras != null) {
            urlLink = bundleExtras.getString(Const.KEY_URL_LINK);
            //urlLink = "http://duby.ddns.net:2200/";

            if (urlLink != null && urlLink.length() > 0) {
                prepareAndInitWebView();
            }
        }

    }

    private void prepareAndInitWebView() {
        webView = (WebView) findViewById(R.id.wvBrowser);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                Log.d("DTag", "onPageFinished[" + loadCnt + "] url -> " + url);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.d("DTag", "init -> onReceivedHttpError -> " + errorResponse);
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.d("DTag", "init -> onReceivedError -> " + error);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);

                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += "\"SSL Certificate Error\" Do you want to continue anyway?.. YES";

                handler.proceed();
            }

        });//webView.setWebViewClient

        webView.loadUrl(urlLink);
    }


    public void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {

                    //@@@.T - temporary manually add hostname to list
                    if (hostname.equalsIgnoreCase("duby.ddns.net")
                            || hostname.equalsIgnoreCase("e.crashlytics.com")
                            || hostname.equalsIgnoreCase("reports.crashlytics.com")) {
                        return true;
                    } else {
                        Log.e("DTag", "WebBrowserActivity: setDefaultHostnameVerifier: BLOCKING hostname -> " + hostname);
                        return false;
                    }

                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //handler.
    }


    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }
}
