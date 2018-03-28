package com.zguming.myapplication89;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //String ext="";
    //String extend="";
    int time;
    int tpl_id=48018;
    String mobile="15113993657";
    String nationcode="86";
    String[] params ={"1234","2"};
    String strSrc;
    String sig;
    /*public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取unix 时间戳（单位：秒）
        long timeStampSec = System.currentTimeMillis()/1000;
        String timestamp = String.format("%010d", timeStampSec);
        time = Integer.parseInt(timestamp);
        strSrc="appkey=867e0788667b4546dd1ac634ee71209a&random=7226249334&time="+timestamp+"&mobile=15113993657";
        sig=shaEncrypt(strSrc);
        fun();

    }
    public void fun(){
        Tg tg=new Tg();
        tg.setTime(time);
        tg.setTpl_id(tpl_id);
        tg.setSig(sig);
        tg.setParams(params);
        tg.setTel(new Tg.TelBean(mobile,nationcode));
        //Tel tel=new Tel();
        //tel.setTel("15113993657");
        Gson gson=new Gson();
        String route= gson.toJson(tg);//通过Gson将Bean转化为Json字符串形式
        //String route= gson.toJson(tel);//通过Gson将Bean转化为Json字符串形式
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://yun.tim.qq.com/v5/tlssmssvr/")
                //.baseUrl("http://btsc.botian120.com/admin/sms/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        PostRoute postRoute=retrofit.create(PostRoute.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Call<Tl> call=postRoute.postFlyRoute("1400045389","7226249334",body);
        call.enqueue(new Callback<Tl>() {
            @Override
            public void onResponse(Call<Tl> call, Response<Tl> response) {
                Log.d("sssss","-----------------------"+response.body().getErrmsg());//这里是用于测试，服务器返回的数据就是提交的数据。
            }

            @Override
            public void onFailure(Call<Tl> call, Throwable t) {
                Log.e("sssss","tyhtr");
            }
        });
    }
    /**
     * SHA加密
     *
     * @param strSrc
     *            明文
     * @return 加密之后的密文
     */

    public static String shaEncrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts
     *            数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
