package cose.lexian.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

//装饰request
public class EncodingRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public EncodingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String s) {
        String value = request.getParameter(s);

        //处理编码问题
        try {
            value = new String(value.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {}
        return value;
    }
}
