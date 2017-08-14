package com.phicomm.product.manger.wei.yang;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Created by wei.yang on 2017/8/11.
 */
public class LianXinTest {

    private static final String REQUEST_URL = "https://api.tookok.cn/v2/broadcastlist/1";

    private static final String QUERY = "?sex=%s";

    @Test
    public void test() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        request();
    }

    private void request() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, new TrustManager[]{new XTrustManager()}, new SecureRandom());
        URL url = new URL(REQUEST_URL + String.format(QUERY, "男"));
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestProperty("cs-app-id", "ebcad75de0d42a844d98a755644e30");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setSSLSocketFactory(connection.getSSLSocketFactory());
        connection.setHostnameVerifier(new Verifier());
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(12000);
        connection.setReadTimeout(12000);
        connection.connect();
        InputStream is = connection.getInputStream();
        if (is != null) {
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, len));
            }
            JSONObject jsonObject = JSONObject.parseObject(builder.toString());
            List lianXinBeans = (List) jsonObject.get("data");
            for (Object obj : lianXinBeans) {
                System.out.println(obj);
                LianXinBean lianXinBean=JSONObject.toJavaObject((JSONObject)obj,LianXinBean.class);
                System.out.println(lianXinBean);
            }
            is.close();
        }
    }


    private static class XTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class Verifier implements HostnameVerifier {

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }
}
