package com.whut;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class JSendSms extends CommonRequest {
    private DefaultProfile profile;
    private Properties props;
    private IAcsClient client;
    private static volatile JSendSms request;

    private JSendSms() {
        parseServerProperties();
        profile = DefaultProfile.getProfile("cn-hangzhou",
                props.getProperty("accessKeyId").substring(0, props.getProperty("accessKeyId").length() - 3),
                props.getProperty("secret").substring(0, props.getProperty("secret").length() - 3));

        client = new DefaultAcsClient(profile);
        setMethod(MethodType.POST);
        setDomain("dysmsapi.aliyuncs.com");
        setVersion("2017-05-25");
        setAction("SendSms");
        putQueryParameter("RegionId", "cn-hangzhou");
    }

    private void parseServerProperties() {
        props = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream("server.properties"))) {
            props.load(in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static JSendSms getInstance() {
        if (request == null) {
            synchronized (JSendSms.class) {
                if (request == null) {
                    request = new JSendSms();
                }
            }
        }
        return request;
    }

    public IAcsClient getClient() {
        return client;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public String sendSignUpCode(String phonenum, String param) {
        String resopnseBody = null;
        request.putQueryParameter("SignName", "Funbook");
        request.putQueryParameter("TemplateCode", "SMS_170352160");
        request.putQueryParameter("PhoneNumbers", phonenum);
        request.putQueryParameter("TemplateParam", param);
        try {
            CommonResponse response = getInstance().getClient().getCommonResponse(request);
            resopnseBody = response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
            resopnseBody = "error";
        } catch (ClientException e) {
            e.printStackTrace();
            resopnseBody = "error";
        }
        return resopnseBody;
    }
}
