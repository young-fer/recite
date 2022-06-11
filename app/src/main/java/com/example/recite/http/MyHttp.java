package com.example.recite.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttp {
    private static String baseUrl = "http://dict.youdao.com/dictvoice?audio=";

    public static byte[] getPronunciationData(String wordHead) throws Exception {
        String path = baseUrl + wordHead;
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//        if (conn.getResponseCode() != 200) {
//            throw new RuntimeException("请求url失败");
//        }
//
//        InputStream inStream = conn.getInputStream();
//        byte[] bt = StreamTool.read(inStream);
//        inStream.close();

        InputStream inStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader( new InputStreamReader(inStream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }



        byte [] bt = response.toString().getBytes();
//        System.out.println(bt);
        return bt;
    }

    public static class StreamTool {
        //从流中读取数据
        public static byte[] read(InputStream inStream) throws Exception{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = inStream.read(buffer)) != -1)
            {
                outStream.write(buffer,0,len);
            }
            inStream.close();
            return outStream.toByteArray();


        }
    }
}
