package com.example.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 假数据工具类
 */
public class YuencyFakeDataTool {

    /**
     * 读取本地的json文件,转换成为字符串
     */
    public static String getJson(String fileName, Context context) {

        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();

        try {

            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();

            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));

            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (IOException e) {
            Log.e("yuency", "假数据工具出了问题");
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}


/*

 //将读出的字符串转换成JSONobject
 JSONObject jsonObject = new JSONObject(str);
 //获取JSONObject中的数组数据
 jsonArray = jsonObject.getJSONArray(arrName);

 */