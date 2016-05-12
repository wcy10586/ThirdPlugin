package com.yoloho.lv.httpproject.utils.api;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.yoloho.lv.httpproject.app.ApplicationManager;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by mylinux on 16/03/23.
 * <p/>
 * 参考:http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0915/3460.html
 * Retrofit源码: http://www.boyunjian.com/javasrc/com.squareup.retrofit/retrofit/1.6.0/_/retrofit/http/QueryMap.java
 * https://github.com/square/retrofit
 * 如何用 Retrofit 2 在安卓上实现 HTTP 访问
 * http://news.oneapm.com/retrofit-url/
 * 入门讲解
 * http://www.th7.cn/Program/Android/201510/603107.shtml
 * http://www.tuicool.com/articles/fQju2uQ
 * http://www.jianshu.com/p/c1a3a881a144
 * <p/>
 * 扩展
 * http://www.cnblogs.com/peiandsky/p/4394779.html---OkHttp+Stetho+Chrome调试android网络部分
 * http://www.jianshu.com/p/aad5aacd79bf/comments/1183989
 * Retrofit2 源码解析
 * http://bxbxbai.github.io/2015/12/13/retrofit2/
 * <p/>
 * Converter	Library
 * Gson	com.squareup.retrofit:converter-gson:2.0.0-beta2
 * Jackson	com.squareup.retrofit:converter-jackson:2.0.0-beta1
 * Moshi	com.squareup.retrofit:converter-moshi:2.0.0-beta1
 * Protobuf	com.squareup.retrofit:converter-protobuf:2.0.0-beta1
 * Wire	com.squareup.retrofit:converter-wire:2.0.0-beta1
 * Simple XML	com.squareup.retrofit:converter-simplexml:2.0.0-beta1
 */
public abstract class RetrofitAPIManager {

    public OkHttpClient genericClient() {
        return OkHttpUtils.getInstance().getOkHttpClient();
    }

    /**
     * 设置post数据
     * device=3e5b73a675f807be8af05ee1b4719a94c45a4414&ver=37&platform=android
     * &model=Dell+Mini+Duo&sdkver=8&releasever=2.2.2
     */
    protected Map<String, String> getPublicParams() {
        Map<String, String> queryMap = new HashMap<String, String>();
        try {
            queryMap.put("platform", "android");
//            queryMap.put("device", getDeviceCode());
            queryMap.put("ver", "15");
            queryMap.put("model", "3");
            queryMap.put("channel", "offical");
            queryMap.put("softsource", "ubaby");
            queryMap.put("period", "1");
            queryMap.put("period_index", "7");
            queryMap.put("token", getUserToken());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryMap;
    }

    public String getUserToken() {
        if (ClientAPI.isDebug) {
            //线下
            return "37898042-612f43972821fd6eed94cf8638afbae3";
        } else {
            //线上
            return "219732187-660cc5fd6998e40872e01359f4bcde0e";
        }

    }
    // //////////////////////////////////////////////////////设备相关///////////////////////////////////////////////////////////////////

    /**
     * 获取设备号
     */
    private static Object lock_device = new Object();
    private static String deviceCode = null;

    public String getDeviceCode() {
        synchronized (lock_device) {
            if (TextUtils.isEmpty(deviceCode)) {
                setDeviceCode();
            }
            return deviceCode;
        }
    }

    public void setDeviceCode() {
        if (deviceCode == null) {
            deviceCode = "NotFound";
            try {
                String m_szImei = null;
                // imei
                try {
                    TelephonyManager TelephonyMgr = (TelephonyManager) ApplicationManager.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
                    m_szImei = TelephonyMgr.getDeviceId(); // Requires
                    // READ_PHONE_STATE
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // devIdShort
                String m_szDevIDShort = null;
                try {
                    m_szDevIDShort = Build.BOARD + Build.BRAND + Build.CPU_ABI + Build.DEVICE + Build.DISPLAY + Build.HOST + Build.ID + Build.MANUFACTURER + Build.MODEL + Build.PRODUCT + Build.TAGS + Build.TYPE + Build.USER;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // androidId
                String m_szAndroidId = null;
                try {
                    m_szAndroidId = Secure.getString(ApplicationManager.getInstance().getContentResolver(), Secure.ANDROID_ID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Wlan Mac
                String m_szWLANMAC = null;
                try {
                    WifiManager wm = (WifiManager) ApplicationManager.getInstance().getSystemService(Context.WIFI_SERVICE);
                    m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String m_szBTMAC = ""; // adapter
                // bluetooth
                if (!(Build.MODEL.equals("vivo X1w") && Build.VERSION.RELEASE.equals("4.1.1"))) {
                    try {
                        BluetoothAdapter m_BluetoothAdapter = null; // Local
                        // Bluetooth
                        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (m_BluetoothAdapter != null) {
                            m_szBTMAC = m_BluetoothAdapter.getAddress();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String m_szLongID = m_szImei + m_szAndroidId + m_szDevIDShort + m_szWLANMAC + m_szBTMAC;
                MessageDigest sha1 = MessageDigest.getInstance("sha-1");
                sha1.update(m_szLongID.getBytes());
                byte hex[] = sha1.digest();
                StringBuffer buf = new StringBuffer();
                int length = hex.length;
                for (int i = 0; i < length; i++) {
                    String t = Integer.toHexString(hex[i] & 0xff).toLowerCase(Locale.getDefault());
                    buf.append(t.length() < 2 ? "0" + t : t);
                }
                deviceCode = buf.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
