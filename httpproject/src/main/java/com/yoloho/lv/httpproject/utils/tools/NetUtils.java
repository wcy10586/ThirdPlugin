package com.yoloho.lv.httpproject.utils.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yoloho.lv.httpproject.app.ApplicationManager;

/**
 * Created by mylinux on 16/05/10.
 */
public class NetUtils {
    /**
     * 是否连接WIFI
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * Check network connection which is connected.
     *
     * <p>
     * Usually, we need to check the network connection is connected, currently
     * the given type is either Wifi or Mobile. Network state is available does
     * not mean that the network connection is connected. So just check the
     * network state available is the wrong way.
     *
     * <p>
     * <Font color=red>Note</Font>:This method can only be run on a real device.
     *
     * @return if return true, the network connection has connected; else return
     *         false.
     * @throws Exception
     *             Any exceptions should ensure that this method will always
     *             return a value before the exception thrown. But in this
     *             method, it just catch, does not throw.
     *
     * @since 2013-12-1 17:00:00 V1.0.0
     *
     * @see {@link ConnectivityManager#getAllNetworkInfo()}
     * @see {@link NetworkInfo#getState()}
     */
    public static boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationManager.getInstance().getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            for (int index = 0; index < networkInfos.length; index++) {
                NetworkInfo netInfo = networkInfos[index];
                if ((netInfo != null) && netInfo.isConnected()) {
                    status = true;
                    ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 检查是否连接网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) ApplicationManager.getInstance().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }
}
