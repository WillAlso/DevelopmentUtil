package com.whut;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.io.*;
import java.util.Properties;

public class JSendSingleMailRequest extends SingleSendMailRequest {
    private DefaultProfile profile;
    private Properties props;
    private IAcsClient client;
    private volatile static JSendSingleMailRequest request;

    private JSendSingleMailRequest() {
        parseServerProperties();
        profile = DefaultProfile.getProfile("cn-hangzhou",
                props.getProperty("accessKeyId").substring(0, props.getProperty("accessKeyId").length() - 3),
                props.getProperty("secret").substring(0, props.getProperty("secret").length() - 3));

        client = new DefaultAcsClient(profile);
        setRegionId("cn-hangzhou");
        setAccountName("sendcode@mail.youngfool.top");
        setAddressType(1);
        setReplyToAddress(false);
        setTagName("test");
    }

    public static JSendSingleMailRequest getInstance() {
        if (request == null) {
            synchronized (JSendSingleMailRequest.class) {
                if (request == null) {
                    request = new JSendSingleMailRequest();
                }
            }
        }
        return request;
    }

    private void parseServerProperties() {
        props = new Properties();
        String path = this.getClass().getResource("/server.properties").getPath();
        File file = new File(path);
        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
            props.load(in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public IAcsClient getClient() {
        return client;
    }

    public String sendSingleTextMail(String toAddress, String subject, String alias, String text) {
        String responseBody = null;
        JSendSingleMailRequest request = JSendSingleMailRequest.getInstance();
        request.setToAddress(toAddress);
        request.setSubject(subject);
        request.setFromAlias(alias);
        request.setTextBody(text);
        try {
            SingleSendMailResponse response = request.getClient().getAcsResponse(request);
            responseBody = new Gson().toJson(response);
        } catch (ServerException e) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("ErrCode:" + e.getErrCode() ));
            builder.append(String.format("ErrMsg:" + e.getErrMsg() ));
            builder.append(String.format("RequestId:" + e.getRequestId() ));
            responseBody = builder.toString();
        } catch (ClientException e) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("ErrCode:" + e.getErrCode() ));
            builder.append(String.format("ErrMsg:" + e.getErrMsg() ));
            builder.append(String.format("RequestId:" + e.getRequestId() ));
            responseBody = builder.toString();
        }
        return responseBody;
    }

    public String sendSingleHtmlMail(String toAddress, String subject, String alias, String html) {
        String responseBody = null;
        JSendSingleMailRequest request = JSendSingleMailRequest.getInstance();
        request.setToAddress(toAddress);
        request.setSubject(subject);
        request.setFromAlias(alias);
        request.setTextBody(html);
        try {
            SingleSendMailResponse response = request.getClient().getAcsResponse(request);
            responseBody = new Gson().toJson(response);
        } catch (ServerException e) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("ErrCode:" + e.getErrCode() ));
            builder.append(String.format("ErrMsg:" + e.getErrMsg() ));
            builder.append(String.format("RequestId:" + e.getRequestId() ));
            responseBody = builder.toString();
        } catch (ClientException e) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("ErrCode:" + e.getErrCode() ));
            builder.append(String.format("ErrMsg:" + e.getErrMsg() ));
            builder.append(String.format("RequestId:" + e.getRequestId()));
            responseBody = builder.toString();
        }
        return responseBody;
    }
}
