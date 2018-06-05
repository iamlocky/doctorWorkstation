package Utils;

import controller.SimpleListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {
    private Thread thread;
    private String urls;
    private String filename;
    private Runnable downloadRunnable;

    public void download(String urlString,String filename, SimpleListener simpleListener){
        if (StringUtil.isEmpty(urlString)||StringUtil.isEmpty(filename)){
            simpleListener.fail("url is null");
            return;
        }
        this.urls=urlString;
        this.filename=filename;
        downloadRunnable=new Runnable() {
            @Override
            public void run() {
                File file ;

                URL url = null;
                BufferedInputStream bufferedInputStream=null;
                try {
                    url=new URL(urls);
                    if (url!=null){
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        if (conn.getResponseCode() == 200) {
                            bufferedInputStream = new BufferedInputStream(conn.getInputStream());
                        }else {
                            simpleListener.fail(conn.getResponseCode()+"");
                            return;
                        }
                    }
                    file=new File(filename);
                    byte[] buffer = new byte[4096];
                    int len = 0;
                    FileOutputStream outStream = new FileOutputStream(file);
                    while ((len = bufferedInputStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    outStream.close();
                    bufferedInputStream.close();
                    simpleListener.done("sucess");
                } catch (Exception e) {
                    e.printStackTrace();
                    simpleListener.fail(e.getMessage());
                }

            }
        };

        if (thread==null){
            thread=new Thread(downloadRunnable);
        }else {
            thread.interrupt();
        }

        thread.start();
    }


}
