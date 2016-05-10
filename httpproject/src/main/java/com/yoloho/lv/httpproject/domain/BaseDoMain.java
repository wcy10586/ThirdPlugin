package com.yoloho.lv.httpproject.domain;

import java.io.Serializable;

/**
 * Created by mylinux on 16/03/24.
 */
public abstract class BaseDoMain implements Serializable {
    public String timestamp;
    public String errno;
    public String errdesc;
}
