package com.yoloho.lv.httpproject.utils.api;

/**
 * Created by mylinux on 16/03/23.
 */
public class ClientAPI {
    public final static boolean isDebug = true;

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
    public String getTopicEndPoint() {
        if (isDebug) {
            return getDomain() + "topic.test.haoyunma.com";
        } else {
            return getDomain() + "topic.haoyunma.com";
        }
    }
    private String getDomain() {
        if (isDebug) {
            return "http://";
        } else {
            return "https://";
        }
    }
}
