package cose.lexian.admin.domain;

public class Admin {
    private String a_name;
    private String a_pwd;
    private String a_verifyCode;

    @Override
    public String toString() {
        return "Admin{" +
                "a_name='" + a_name + '\'' +
                ", a_pwd='" + a_pwd + '\'' +
                ", a_verifyCode='" + a_verifyCode + '\'' +
                '}';
    }

    public String getA_verifyCode() {
        return a_verifyCode;
    }

    public void setA_verifyCode(String a_verifyCode) {
        this.a_verifyCode = a_verifyCode;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_pwd() {
        return a_pwd;
    }

    public void setA_pwd(String a_pwd) {
        this.a_pwd = a_pwd;
    }
}
