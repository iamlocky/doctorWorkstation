package controller;

public interface SimpleListener<T> {
    void done(T data);
    void fail(String s);
}
