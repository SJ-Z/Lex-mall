package cose.lexian.type.domain;

public class Type {
    private String t_id; //商品主类别id
    private String t_name; //主类别名字
    private String t_image; //主类别图片路径

    @Override
    public String toString() {
        return "Type{" +
                "t_id='" + t_id + '\'' +
                ", t_name='" + t_name + '\'' +
                ", t_image='" + t_image + '\'' +
                '}';
    }

    public String getT_image() {
        return t_image;
    }

    public void setT_image(String t_image) {
        this.t_image = t_image;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }
}
