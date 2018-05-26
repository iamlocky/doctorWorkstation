package model.bean;

public class Pointer {
    /**
     * __type : Pointer
     * className : Game
     * objectId : DdUOIIIW
     */

    public Pointer(String __type,String className,String objectId){
        this.__type=__type;
        this.className=className;
        this.objectId=objectId;
    }

    private String __type;
    private String className;
    private String objectId;

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
