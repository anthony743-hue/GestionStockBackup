package utilities;

public class Param {
    private String name;
    private Object val;
    public Param(String name, Object val) {
        this.name = name;
        this.val = val;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Object getVal() {
        return val;
    }
    public void setVal(Object val) {
        this.val = val;
    }
}
