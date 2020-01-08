package com.whut;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class JImageClassify {
    private Properties props;
    private AipImageClassify client;
    private static volatile JImageClassify classify;

    private JImageClassify() {
        parseServerProperties();
        client = new AipImageClassify(
                props.getProperty("baiduAppId").substring(0, props.getProperty("baiduAppId").length() - 3),
                props.getProperty("baiduKeyId").substring(0, props.getProperty("baiduKeyId").length() - 3),
                props.getProperty("baidysecret").substring(0, props.getProperty("baidysecret").length() - 3)
        );
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    private void parseServerProperties() {
        props = new Properties();
        try (InputStream in = this.getClass().getResourceAsStream("/server.properties")) {
            props.load(in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static JImageClassify getInstance() {
        if (classify == null) {
            synchronized (JImageClassify.class) {
                if (classify == null) {
                    classify = new JImageClassify();
                }
            }
        }
        return classify;
    }

    public JSONObject plantDetectByImagePath(String image) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", "3");
        JSONObject jsonObject = client.plantDetect(image, options);
        return jsonObject;
    }

    public JSONObject plantDetectByImagePath(String image, int baikenum) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", String.valueOf(baikenum));
        JSONObject jsonObject = client.plantDetect(image, options);
        return jsonObject;
    }

    public JSONObject plantDetectByImageByte(byte[] bytes) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", "3");
        JSONObject jsonObject = client.plantDetect(bytes, options);
        return jsonObject;
    }

    public JSONObject plantDetectByImageByte(byte[] bytes, int baikenum) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", String.valueOf(baikenum));
        JSONObject jsonObject = client.plantDetect(bytes, options);
        return jsonObject;
    }
}
