package com.phicomm.product.manger.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.phicomm.product.manger.enumeration.HttpProtocolType;
import com.phicomm.product.manger.enumeration.RequestType;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.exception.FirmwareTriggerFailException;
import com.phicomm.product.manger.model.user.AdminUserInfo;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;

/**
 * 网络请求
 *
 * @author wei.yang on 2017/5/31.
 */
public class HttpUtil {

    private static final String CONTENT_TYPE = "application/json";

    private static final String CHART_SET = "UTF-8";

    private static final Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 请求url
     *
     * @param url    url
     * @param method 请求方法
     * @param params 参数
     * @param ctype  content-type
     * @return 请求结果
     * @throws IOException io异常
     */
    public static String openUrl(String url, String method, String ctype, JSONObject params) throws IOException {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        if (RequestType.GET.getKeyName().equalsIgnoreCase(method)) {
            url = url + "?" + encodeUrl(params);
            httpURLConnection = getUrlConnection(url, method, ctype);
        } else {
            httpURLConnection = getUrlConnection(url, method, ctype);
            String data = encodeUrl(params);
            byte[] datas = data.getBytes(CHART_SET);
            httpURLConnection.getOutputStream().write(datas);
        }
        int responseCode = httpURLConnection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            inputStream = httpURLConnection.getInputStream();
        } else {
            inputStream = httpURLConnection.getErrorStream();
        }
        String response = getResult(inputStream);
        logger.info("network request result is:\t" + response);
        if (inputStream != null) {
            inputStream.close();
        }
        return response;
    }


    /**
     * 请求url
     *
     * @param url    url
     * @param method 请求方法
     * @param params 参数
     * @return 请求结果
     * @throws IOException io异常
     */
    @SuppressWarnings("all")
    public static String openPostRequest(String url, String method, JSONObject params) throws IOException, FirmwareTriggerFailException {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        httpURLConnection = getUrlConnection(url, method, CONTENT_TYPE);
        byte[] datas = params.toJSONString().getBytes(CHART_SET);
        httpURLConnection.getOutputStream().write(datas);
        int responseCode = httpURLConnection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            inputStream = httpURLConnection.getInputStream();
        } else {
            throw new FirmwareTriggerFailException();
        }
        String response = getResult(inputStream);
        logger.info("network request result is:\t" + response);
        if (inputStream != null) {
            inputStream.close();
        }
        return response;
    }

    /**
     * 带有授权的请求
     *
     * @param url    url
     * @param method 请求方法
     * @param params 参数
     * @param ctype  content-type
     * @param auths  授权字段
     * @return 请求结果
     * @throws IOException              io异常
     * @throws NoSuchAlgorithmException 算法不支持
     * @throws KeyManagementException   keyManager错误
     */
    @SuppressWarnings("unused")
    public static String openUrlWithAuth(String url, String method, JSONObject params, String ctype, JSONObject auths)
            throws IOException, NoSuchAlgorithmException, KeyManagementException {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        if (RequestType.GET.getKeyName().equalsIgnoreCase(method)) {
            url = url + "?" + encodeUrl(params);
            httpURLConnection = getUrlConnectionWithAuth(url, method, ctype, auths);
        } else {
            httpURLConnection = getUrlConnectionWithAuth(url, method, ctype, auths);
            String data = encodeUrl(params);
            byte[] datas = data.getBytes(CHART_SET);
            httpURLConnection.getOutputStream().write(datas);
        }
        int responseCode = httpURLConnection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            inputStream = httpURLConnection.getInputStream();
        } else {
            inputStream = httpURLConnection.getErrorStream();
        }
        String result = getResult(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return result;
    }

    /**
     * 解析结果
     */
    private static String getResult(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] data = new byte[1024];
        int len;
        while ((len = inputStream.read(data)) > 0) {
            builder.append(new String(data, 0, len,CHART_SET));
        }
        return builder.toString();
    }

    /**
     * 获取httpConnection
     */
    private static HttpURLConnection getUrlConnection(String url, String method, String ctype) throws IOException {
        URL urlObj = new URL(url);
        Object conn;
        if (HttpProtocolType.HTTPS.getKeyName().equalsIgnoreCase(urlObj.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            assert ctx != null;
            connection.setSSLSocketFactory(ctx.getSocketFactory());
            connection.setHostnameVerifier((s, sslSession) -> true);
            conn = connection;
        } else {
            conn = urlObj.openConnection();
        }
        ((HttpURLConnection) conn).setRequestMethod(method);
        if (RequestType.POST.getKeyName().equalsIgnoreCase(method)) {
            ((HttpURLConnection) conn).setDoOutput(true);
            ((HttpURLConnection) conn).setDoInput(true);
        }
        ((HttpURLConnection) conn).setRequestProperty("Content-Type", ctype);
        return ((HttpURLConnection) conn);
    }


    /**
     * 带有授权的请求
     *
     * @param url    链接
     * @param method 请求方法
     * @param ctype  content-type
     * @param auths  授权
     * @return 链接
     * @throws NoSuchAlgorithmException 算法不支持
     * @throws IOException              io异常
     * @throws KeyManagementException   keyManager异常
     */
    private static HttpURLConnection getUrlConnectionWithAuth(String url, String method, String ctype, JSONObject auths)
            throws NoSuchAlgorithmException, IOException, KeyManagementException {
        HttpURLConnection httpURLConnection = getUrlConnection(url, method, ctype);
        if (auths == null) {
            return httpURLConnection;
        }
        Set<String> keys = auths.keySet();
        for (String key : keys) {
            String value = auths.getString(key);
            if (!Strings.isNullOrEmpty(value)) {
                httpURLConnection.setRequestProperty(key, value);
            }
        }
        return httpURLConnection;
    }

    /**
     * 编码
     */
    private static String encodeUrl(JSONObject params) throws UnsupportedEncodingException {
        if (params == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        Set<String> sets = params.keySet();
        String value;
        for (Object set : sets) {
            value = params.getString(set.toString());
            if (!Strings.isNullOrEmpty(value)) {
                if (first) {
                    first = false;
                } else {
                    builder.append("&");
                }
                builder.append(URLEncoder.encode(set.toString(), CHART_SET)).append("=")
                        .append(URLEncoder.encode(value, CHART_SET));
            }
        }
        return builder.toString();
    }

    /**
     * 获取baseUrl
     */
    public static String getBaseUrl() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    /**
     * 获取baseUrl
     */
    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    /**
     * 获取当前的用户信息
     */
    public static AdminUserInfo getCurrentUserInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        return (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
    }


    private static class DefaultTrustManager implements X509TrustManager {
        private DefaultTrustManager() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] cert, String oauthType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] cert, String oauthType) throws CertificateException {
        }
    }
}
