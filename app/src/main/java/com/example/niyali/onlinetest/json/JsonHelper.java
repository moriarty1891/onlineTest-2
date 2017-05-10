package com.example.niyali.onlinetest.json;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niyali on 17/4/15.
 */

//{
//        "title":"各省考试时间",
//        "message":"河南省5月19日，河北省6月17日"
//        }
public class JsonHelper {
    public static Map<String,String> parseJson(String json)
    {
        Map<String,String> map=new HashMap<String, String>();
        try
        {
            JSONObject jsonObject=new JSONObject(json);
            map.put("title",jsonObject.getString("title"));
            map.put("message",jsonObject.getString("message"));

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return map;

    }
}
