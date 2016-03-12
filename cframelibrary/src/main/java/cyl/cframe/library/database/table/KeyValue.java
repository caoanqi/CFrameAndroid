
package cyl.cframe.library.database.table;


public class KeyValue {
    private String key;
    private Object value;

    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }


    public KeyValue() {
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
