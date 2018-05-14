package controller;

public interface IControllerListener<T> {
    void done(T data);
    void done(String data);
    void showMessage(String msg);
}
