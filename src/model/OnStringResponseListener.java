package model;


/**
 * Created by LockyLuo on 2017/9/20.
 * 成功则返回T，否则返回Exception
 */

public interface OnStringResponseListener{
    void onFinish(String responseBean, Exception e);
}


