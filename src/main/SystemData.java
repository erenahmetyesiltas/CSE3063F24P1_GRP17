package main;

public class SystemData {
    private Object object;
    private Class objectClass;
    private String jsonFile;

    public Class getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(Class objectClass) {
        this.objectClass = objectClass;
    }


    public SystemData() {

    }
    public SystemData(Object object, Class objectClass, String jsonFile) {
        this.object = object;
        this.objectClass = objectClass;
        this.jsonFile = jsonFile;
    }

    public Object getObject() {
        return object;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }
}
