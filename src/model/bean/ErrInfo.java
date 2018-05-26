package model.bean;

import java.io.Serializable;

public class ErrInfo implements Serializable {
    Integer code = -1;
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ErrInfo{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}';
    }
}
