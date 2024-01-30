package cn.cariton.apps.douyin.utils;

import jakarta.annotation.Nonnull;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author icnz
 * @date 2023-12-19 12:10
 */
public class HttpClientUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static final int KEEP_ALIVE_TIMEOUT = 120;
    private static final int MAX_TIMEOUT = 5000;
    private static final CloseableHttpClient httpClient;

    static {
        // 自定义 SSL 策略
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", createSSLConnSocketFactory())
                .build();
        // 设置连接池
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(registry);
        connMgr.setMaxTotal(100); // 设置连接池大小
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal()); // 设置每条路由的最大并发连接数
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setValidateAfterInactivity(KEEP_ALIVE_TIMEOUT, TimeUnit.SECONDS) // 设置长连接
                .setConnectTimeout(MAX_TIMEOUT, TimeUnit.MILLISECONDS) // 建立连接超时时间
                .build();
        connMgr.setDefaultConnectionConfig(connectionConfig);
        // connMgr.setValidateAfterInactivity(TimeValue.ofSeconds(KEEP_ALIVE_TIMEOUT)); // 设置长连接(httpclient5.3已废弃)

        RequestConfig requestConfig = RequestConfig.custom()
                // .setConnectTimeout(MAX_TIMEOUT, TimeUnit.MILLISECONDS) // 建立连接超时时间(httpclient5.3已废弃)
                .setResponseTimeout(MAX_TIMEOUT, TimeUnit.MILLISECONDS) // 传输超时
                .setConnectionRequestTimeout(MAX_TIMEOUT, TimeUnit.MILLISECONDS) // 设置从连接池获取连接实例的超时
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(connMgr)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setRetryStrategy(new DefaultHttpRequestRetryStrategy()) // 重试 1 次，间隔1s
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    /**
     * Get请求
     */
    public static String get(@Nonnull String url, Map<String, String> params, Map<String, Object> headers) {
        // logger.info("httpclient5 get start url={}, headers={}, params={}", url, headers, params);
        final String[] result = {null};
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (Objects.nonNull(params) && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    uriBuilder.setParameter(entry.getKey(), Objects.isNull(value) ? "" : value);
                }
            }
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(uriBuilder.build()).build();
            // HttpGet httpGet = new HttpGet(uriBuilder.build());
            logger.info("httpclient5 get requestUri={}", httpGet.getRequestUri());
            if (Objects.nonNull(headers) && !headers.isEmpty()) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            httpClient.execute(httpGet, response -> {
                // 获取状态码
                // logger.info("{}, {}, {}", response.getVersion(), response.getCode(), response.getReasonPhrase());
                HttpEntity httpEntity = response.getEntity();
                // 获取响应信息
                result[0] = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                EntityUtils.consume(httpEntity);
                return null; // execute方法不需要返回信息
            });

            // logger.info("httpclient5 get end result={}", result[0]);
            return result[0];
        } catch (URISyntaxException e) {
            logger.error("HttpClient5 URIBuilder Exception: ", e);
            return "error";
        } catch (IOException e) {
            logger.error("HttpClient5 Get Exception: ", e);
            return "error";
        }
    }

    /**
     * Post Form请求
     */
    public static String post(@Nonnull String url, Map<String, Object> headers, Map<String, Object> params) {
        logger.info("httpclient5 post start url={}, headers={}, params={}", url, headers, params);
        final String[] result = {null};
        ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url).build();
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (Objects.nonNull(params) && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                pairs.add(new BasicNameValuePair(entry.getKey(), Objects.isNull(value) ? "" :
                        String.valueOf(value)));
            }
            // 将请求参数和url进行拼接
            // url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        }
        try {
            // HttpEntity httpEntity = httpClient.execute(httpPost, res -> new BufferedHttpEntity(res.getEntity()));
            httpClient.execute(httpPost, response -> {
                System.out.println(response.getVersion()); // HTTP/1.1
                System.out.println(response.getCode()); // 200
                System.out.println(response.getReasonPhrase()); // OK
                HttpEntity httpEntity = response.getEntity();
                // 获取响应信息
                result[0] = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                EntityUtils.consume(httpEntity);
                return null; // execute方法不需要返回信息
            });
            logger.info("httpclient5 post end result={}", result[0]);
            return result[0];
        } catch (IOException e) {
            logger.error("HttpClient5 Post 请求异常：", e);
            return "error";
        }
    }

    /**
     * Post Json请求
     */
    public static String postJson(@Nonnull String url, String jsonBody) {
        logger.info("httpclient5 postJson start url={}, jsonBody={}", url, jsonBody);
        final String[] result = {null};
        ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url).build();
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

        try {
            // HttpEntity httpEntity = httpClient.execute(httpPost, res -> new BufferedHttpEntity(res.getEntity()));
            httpClient.execute(httpPost, response -> {
                System.out.println(response.getVersion()); // HTTP/1.1
                System.out.println(response.getCode()); // 200
                System.out.println(response.getReasonPhrase()); // OK
                HttpEntity httpEntity = response.getEntity();
                // 获取响应信息
                result[0] = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                EntityUtils.consume(httpEntity);
                return null; // execute方法不需要返回信息
            });
            logger.info("httpclient5 postJson end result={}", result[0]);
            return result[0];
        } catch (IOException e) {
            logger.error("HttpClient5 PostJson 请求异常：", e);
            return "error";
        }
    }

    /**
     * Post Stream请求(file,json,xml转stream)
     */
    public static String postStream(@Nonnull String url, String params, ContentType contentType) {
        logger.info("httpclient5 postStream start url={}, params={}", url, params);
        final String[] result = {null};
        ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url).build();
        InputStream inputStream = new ByteArrayInputStream(params.getBytes());
        InputStreamEntity reqEntity = new InputStreamEntity(inputStream, -1, contentType);
        httpPost.setEntity(reqEntity);
        try {
            // HttpEntity httpEntity = httpClient.execute(httpPost, res -> new BufferedHttpEntity(res.getEntity()));
            httpClient.execute(httpPost, response -> {
                System.out.println(response.getVersion()); // HTTP/1.1
                System.out.println(response.getCode()); // 200
                System.out.println(response.getReasonPhrase()); // OK
                HttpEntity httpEntity = response.getEntity();
                // 获取响应信息
                result[0] = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                EntityUtils.consume(httpEntity);
                return null; // execute方法不需要返回信息
            });
            logger.info("httpclient5 postStream end result={}", result[0]);
            return result[0];
        } catch (IOException e) {
            logger.error("HttpClient5 PostStream 请求异常：", e);
            return "error";
        }
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        TrustStrategy trustStrategy = (x509Certificates, s) -> true;
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build();
            return new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
