package cose.lexian.type.domain;

public class SubType {
    private String sub_id; //商品详细类别id
    private String sub_name; //详细类别名字
    private Type sub_type; //详细类别所属主类别

    @Override
    public String toString() {
        return "SubType{" +
                "sub_id='" + sub_id + '\'' +
                ", sub_name='" + sub_name + '\'' +
                ", sub_type=" + sub_type +
                '}';
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public Type getSub_type() {
        return sub_type;
    }

    public void setSub_type(Type sub_type) {
        this.sub_type = sub_type;
    }
}
