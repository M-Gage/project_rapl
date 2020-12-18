package com.qingting.middleware.common.filter;

import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class BodyRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;
    private HttpServletRequest request;

    public BodyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.request = request;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return getServletInputStream(byteArrayInputStream);
    }

    public BodyRequestWrapper copyRequest(){
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(Code.PARAM_ERROR);
        }
        return this;
    }

   public ServletInputStream getServletInputStream(InputStream is) {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) { }

            @Override
            public int read() throws IOException {
                return is.read();
            }
        };
    }
}
