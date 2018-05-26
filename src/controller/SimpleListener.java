package controller;

import model.bean.ErrInfo;

public interface SimpleListener<T> {
    void done(T data);
    void fail(ErrInfo errInfo);
    void fail(String s);
}
