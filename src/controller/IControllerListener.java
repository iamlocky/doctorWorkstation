package controller;

public interface IControllerListener<T> {
    void done(T data);
    void doneRaw(String data);
    void showMessage(String msg);
}
