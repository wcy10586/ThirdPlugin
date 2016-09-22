package com.yoloho.lv.httpproject.activity.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by mylinux on 16/05/25.
 */
public class PublicWebActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private WebView mWebView;
    private View loadurlBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubaby_act_publicweb);
        initPage();
    }

    private String current_url;

    private void initPage() {
        mWebView = (WebView) findViewById(R.id.webview);
        loadurlBtn = findViewById(R.id.loadurlBtn);
        loadurlBtn.setOnClickListener(this);
        initWebViews();
        String url = "http://whale.test.yoloho.com/index.php/AskDoctor/order_detail?order_id=582&token=104974037-8e8cb8a1a63ba0750a10a4a12af65e49&openId=&partnerId=";
        String url2="https://mall.meiyue.com/h5/hym/product/detail.html?productid=76532&a=ubaby&uid=221666631&token=221666631-95da8695b3885e17bdf54b2816a7f8d4&platform=android&channel=official&ver=19";
        String url3 = "http://marketing.test.yoloho.com/index.php/IbuySurvey/index?channel=1";
        String url4="http://dove.test.yoloho.com/dove/index.html?share=1";
        current_url = url3;
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("D-Platform", "android");
        mWebView.loadUrl(current_url, headerMap);
    }

    @SuppressLint("JavascriptInterface")
    private void initWebViews() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (null != mWebView && null != mWebView.getSettings())
                    mWebView.getSettings().setBlockNetworkImage(false);
            }
        }, 1000);

        mWebView.requestFocus();
        WebSettings settings = mWebView.getSettings();
        if (settings != null) {
            // 这个功能其实在4.4以后不再建议使用
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setGeolocationEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            // 将图片调整到适合webview的大小,可任意比例缩放
            settings.setUseWideViewPort(true);
//            // 以下是v2.4增加,据说是解决视频播放问题
//            settings.setAllowFileAccess(true);
//            settings.setDatabaseEnabled(true);
//            settings.setSaveFormData(false);
//            settings.setAppCacheEnabled(true);
//            //
//            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//            // 一定要设置为false，不然有声音没图像
//            // setLoadWithOverviewMode方法是设置webview加载的页面的模式。
//            settings.setLoadWithOverviewMode(true);
//            settings.setSupportMultipleWindows(true);// 新加
//            // 在网络情况较差的情况下，过多的网络请求就会造成带宽紧张，影响到css或js文件加载完成的时间，造成页面空白loading过久。解决的方法就是告诉WebView先不要自动加载图片，等页面finish后再发起图片加载。
//            // 4.4以上系统在onPageFinished时再恢复图片加载时,如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。
            settings.setLoadsImagesAutomatically(true);
        }
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, new Paint());
        setUserAgent();
        // 打开浏览器下载功能
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mWebView.setVisibility(View.VISIBLE);
        mWebView.setWebViewClient(new DefaultWebViewClientClient());
        mWebView.setWebChromeClient(new BrowserClient());
    }

    private void callHiddenWebViewMethod(final WebView wv, final String name) {
        if (mWebView != null) {
            try {
                Method method = WebView.class.getMethod(name);
                method.setAccessible(true);
                method.invoke(mWebView);
            } catch (final Exception e) {
            }
        }
    }

    public class DefaultWebViewClientClient extends WebViewClient {

        // 打开链接前的事件,这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，
        // 取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("tag_web", " url = " + url);
            // 如果传入的url是有效的
            if (url.startsWith("tel:")) {
                // 处理电话链接，启动本地通话应用。
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                return true;
            }
            Log.e("tag_web", "shouldOverrideUrlLoading url = " + url);
            // return true;
            return super.shouldOverrideUrlLoading(view, url);
        }

        // 接收到Http请求的事件
        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        int pageCount = 0;

        // 载入页面开始的事件
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        // 载入页面完成的事件
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
//                mWebView.getSettings().setLoadsImagesAutomatically(true);
//            }
            view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");

            final String rurl = mWebView.getUrl();
            Thread parseHtmlRun = new Thread(new Runnable() {
                @Override
                public void run() {
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(rurl).get();
//                        Jsoup.connect(rurl).header("User-Agent",
//                                "Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
//                                .header("Accept", "text ml,application/xhtml+xml").header(
//                                "Accept-Language", "zh-cn,zh;q=0.5").header(
//                                "Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
//                                .get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Elements metas = (Elements) doc.getElementsByTag("meta");
                    for (Element meta : metas) {
                        if ("HaoyunmaBtnName".equals(meta.attr("name"))) {
                        }
                    }
                }
            });
            //parseHtmlRun.start()
            String param = getuserinfo().toString().replace("\"", "\\\"");
            // 由于前端在接受到传递set_userinfo数据时候会刷新页面,故此处加以判断
            mWebView.loadUrl("javascript:var str = '" + param + "'; try{if(typeof(eval(set_userinfo))=='function'){set_userinfo(str);}}catch(e){}");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }
    }

    public class BrowserClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
        }

        //做选择图片处理
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }
    }

    public void setUserAgent() {
        if (mWebView != null) {
            String user = mWebView.getSettings().getUserAgentString();
            mWebView.getSettings().setUserAgentString("Dayima/" + "19 " + user);
        }
    }

    public String getuserinfo() {
        URL url = null;
        try {
            url = new URL(current_url);
        } catch (MalformedURLException e) {
            url = null;
        }

        if (url != null) {
            final String host = url.getHost();
            if (!TextUtils.isEmpty(host)) {
                JSONObject json = new JSONObject();
                try {
                    PackageInfo info = null;
                    json.put("uid", "104974037");
                    json.put("platform", "android");
                    if (info != null) {
                        json.put("ver", "19");
                    }
                    json.put("channel", "offical");

                    String days = "1";
                    json.put("pregnantDays", days);

                    json.put("yuchanDate", "20160722");
                    json.put("userNick", "g1");
                    json.put("babyBirthday", "19900516");
                    return json.toString();
                } catch (JSONException e) {
                }

            }
        }
        return "0";
    }

    @Override
    protected void onResume() {
        super.onResume();
        callHiddenWebViewMethod(mWebView, "onResume");
        mWebView.resumeTimers();
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        callHiddenWebViewMethod(mWebView, "onPause");
        mWebView.pauseTimers();
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.loadurlBtn) {
            mWebView.loadUrl("http://news.baidu.com/");
        }
    }
    final class InJavaScriptLocalObj{

        public void showSource(String html) {
            Log.e("HTML", html);
        }
    }


}
