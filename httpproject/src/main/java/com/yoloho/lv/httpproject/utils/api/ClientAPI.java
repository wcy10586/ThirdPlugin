package com.yoloho.lv.httpproject.utils.api;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.google.common.collect.ImmutableMap;
import com.yoloho.lv.httpproject.app.ApplicationManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mylinux on 16/03/23.
 */
public class ClientAPI {
    private boolean isDebug = false;

    // 1.采用静态初始化器的方式,它可以由JVM来保证线程的安全性
    // 2.采用类级内部类,在此类里面创建对象,保证类在装载的时候不去初始化对象,达到延迟加载
    // 3.getInstance第一次调用的时候,读取SingletonHolder.INSTANCE,导致SingletonHolder类的初始化,而这个类在装载并初始化的时候,会初始化它的静态域
    private static class SingletonHolder {
        private static final ClientAPI INSTANCE = new ClientAPI();
    }

    private ClientAPI() {
    }

    public static final ClientAPI getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public final static String UIC_API_URL = "";

    /**
     * 获取UIC系统访问的域名
     */
    public String getUICEndPoint() {
        if (isDebug) {
            return getDomain() + "testapi.yoloho.com";
        } else {
            return getDomain() + "api.yoloho.com";
        }
    }

    /**
     * 获取宝宝日历系统访问的域名
     */
    public String getBABYCALEndPoint() {
        if (isDebug) {
            return getDomain() + "calapi.test.haoyunma.com";
        } else {
            return getDomain() + "calapi.haoyunma.com";
        }
    }

    private String getDomain() {
        if (isDebug) {
            return "http://";
        } else {
            return "https://";
        }
    }


    /**
     * 设置post数据
     * device=3e5b73a675f807be8af05ee1b4719a94c45a4414&ver=37&platform=android
     * &model=Dell+Mini+Duo&sdkver=8&releasever=2.2.2
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public  Map<String,String> setPostData() {
        Map<String,String> queryMap=new HashMap<String, String>();
        ImmutableMap<String, String> foo = null;
//        if (null == queryMap) {
//            String value = "android&device="+getDeviceCode()+"&ver=15&token=87650731%23f1d234a815285237e21bab9f3917e90";
////            queryMap = Collections.singletonMap("platform", value);
//            queryMap = new HashMap<>();
//        }
        try {
//            foo = ImmutableMap.of("platform", "android", "device", getDeviceCode(), "channel", "offical", "token", "87650731%23f1d234a815285237e21bab9f3917e90");
            queryMap.put("platform","android");
//            queryMap.put("device", getDeviceCode());
            queryMap.put("ver", "15");
            queryMap.put("model", "3");
            queryMap.put("channel", "offical");
            queryMap.put("softsource", "ubaby");
            queryMap.put("period", "1");
            queryMap.put("period_index", "7");

            queryMap.put("token", "87650731%23f1d234a815285237e21bab9f3917e90b");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryMap;
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
