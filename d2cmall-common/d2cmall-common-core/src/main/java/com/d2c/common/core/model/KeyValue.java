package com.d2c.common.core.model;

/**
 * 键值对
 *
 * @author wull
 */
public class KeyValue implements java.io.Serializable {

    private static final long serialVersionUID = -4171182992648470567L;
    protected Object id;
    protected Object key;
    protected Number value;

    public KeyValue() {
    }

    public KeyValue(Object key, Number value) {
        setKey(key);
        setValue(value);
    }

    public int intValue() {
        return value.intValue();
    }

    public long longValue() {
        return value.longValue();
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    public float floatValue() {
        return value.floatValue();
    }

    public String stringValue() {
        return value.toString();
    }

    public Object getId() {
        if (key == null) {
            key = id;
        }
        return id;
    }

    public void setId(Object id) {
        this.id = id;
        this.key = id;
    }

    public Object getKey() {
        if (key == null) {
            key = id;
        } else {
            id = key;
        }
        return key;
    }

    public void setKey(Object key) {
        this.id = key;
        this.key = key;
    }
    //**********************

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

}
