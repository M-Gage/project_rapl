package com.qingting.middleware.filter;

import com.qingting.middleware.common.filter.BodyRequestWrapper;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GzipRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;

    GzipRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream stream;
        String contentEncoding = request.getHeader("Content-Encoding");
        // 如果对内容进行了压缩，则解压
        if (null != contentEncoding && contentEncoding.equals("gzip")) {
            stream = request.getInputStream();
            try {
                //解压
                final GZIPInputStream gzipInputStream = new GZIPInputStream(stream);
                //将解压后的内容放入ServletInputStream方便后面的request可以获取
                return new BodyRequestWrapper(request).getServletInputStream(gzipInputStream);
            } catch (Exception e) {
                throw new MyException(Code.GZIP_UNCOMPRESS_ERROR);
            }
        }
        return super.getInputStream();
    }
}
