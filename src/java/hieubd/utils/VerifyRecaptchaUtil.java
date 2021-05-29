/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author CND
 */
public class VerifyRecaptchaUtil implements Serializable {

    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }

        URL verifyUrl = new URL(SITE_VERIFY_URL);

        //Mở connect tới URL
        HttpURLConnection con = (HttpURLConnection) verifyUrl.openConnection();

        //add các thông tin Header vào Request chuẩn bị gửi tới server 
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //data sẽ gửi tới server 
        String postParams = "secret=" + MyConstants.SECRET_KEY + "&response=" + gRecaptchaResponse;

        //send request
        con.setDoOutput(true);

        //Lấy Output Stream (Luồng đầu ra) của kết nối tới server
        //Ghi dữ liệu vào Output Stream, có nghĩa là gửi thông tin đến Server
        OutputStream outStream = con.getOutputStream();
        outStream.write(postParams.getBytes());

        outStream.flush();
        outStream.close();

        //Mã trả lời được trả về từ Server
        int responseCode = con.getResponseCode();

        //Lấy Input Stream (đầu vào) của Connection
        //để đọc dữ liệu gửi đến server 
        InputStream is = con.getInputStream();

        JsonReader jsonReader = Json.createReader(is);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        return jsonObject.getBoolean("success");
    }
}
