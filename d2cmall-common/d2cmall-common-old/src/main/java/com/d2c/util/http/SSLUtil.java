package com.d2c.util.http;

import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SSLUtil {

    private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(30000)
            .setConnectionRequestTimeout(60000).setCookieSpec(CookieSpecs.STANDARD).setExpectContinueEnabled(true)
            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
    private static TrustManager manager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };

    private static SSLConnectionSocketFactory getSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{manager}, null);
            // https
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return socketFactory;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
//	public static HttpClient getHttpClient() {
//		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
//		params.setConnectionTimeout(2000);
//		params.setSoTimeout(2000);
//		// 最大连接数
//		params.setMaxTotalConnections(500);
//		params.setDefaultMaxConnectionsPerHost(500);
//		params.setStaleCheckingEnabled(true);
//		HttpConnectionManager connectionManager = new SimpleHttpConnectionManager();
//		connectionManager.setParams(params);
//		HttpClientParams httpClientParams = new HttpClientParams();
//		// 设置httpClient的连接超时，对连接管理器设置的连接超时是无用的
//		httpClientParams.setConnectionManagerTimeout(5000); // 等价于4.2.3中的CONN_MANAGER_TIMEOUT
//		httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
//		final HttpClient client = new HttpClient(connectionManager);
//		client.setParams(httpClientParams);
//		return client;
//	}

    public static CloseableHttpClient createClientDefault() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", getSocketFactory()).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

}
