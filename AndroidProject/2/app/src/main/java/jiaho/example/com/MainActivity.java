package jiaho.example.com;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.show.api.ShowApiRequest;

import java.util.ArrayList;
import java.util.List;

import jiaho.example.com.adapter.MyListViewAdapter;
import jiaho.example.com.bean.WhatHappened;
import jiaho.example.com.utils.PermissionsUtil;

public class MainActivity extends AppCompatActivity {

    private static final String appSecret = "1e3577aa6a3b4d6a9569cdb677490dd6";
    private static final String appid = "89041";
    private static final String url = "http://route.showapi.com/119-42";
    private static List<String> sNeedPermissions = new ArrayList<>();
    private PermissionsUtil permissionsUtil;
    private Button btn_search;
    private EditText et_date;
    private String date;
    private List<WhatHappened> dataList = new ArrayList<>();
    private MyListViewAdapter adapter;
    private ListView listView;
    /*
    * 所需要的权限
    * */
    static {
        sNeedPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        sNeedPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionsUtil = new PermissionsUtil(this);
        permissionsUtil.request(sNeedPermissions, 10230, new PermissionsUtil.CallBack() {
            @Override
            public void grantAll() {
                initView();

                search();
            }

            @Override
            public void denied() {
                Log.e("permission","权限申请失败");
            }
        });

    }

    public void search() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date = et_date.getText().toString();

                new Thread(){
                    @Override
                    public void run() {
                        if (date.length()==4){
                            final String res = new ShowApiRequest(url,appid,appSecret)
                                    .addTextPara("date",date)
                                    .post();

                            //解析json
                            jsonStrToJSONObject(res);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //设置适配器
                                    adapter.notifyDataSetChanged();
                                    listView.setAdapter(adapter);
                                }
                            });

                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"请输入要查询的日期",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }.start();
            }
        });
    }

    private void jsonStrToJSONObject(String res) {
        //将整个字符串转换成Json对象
        JSONObject js = JSONObject.parseObject(res);
        JSONObject jsonObject = js.getJSONObject("showapi_res_body");
        //提取JsonArray
        JSONArray list = jsonObject.getJSONArray("list");
        /*
        * 一句代码将JsonArray转换成List<>集合
        * */
        dataList = JSON.parseObject(list.toJSONString(),new TypeReference<List<WhatHappened>>(){});
        System.out.println(dataList);
        //创建适配器
        adapter = new MyListViewAdapter(this,dataList);
    }

    public void initView() {
        btn_search = findViewById(R.id.btn_search);
        et_date = findViewById(R.id.et_date);
        listView = findViewById(R.id.listView);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    /*
    * {
  "showapi_res_code": 0,
  "showapi_res_error": "",
  "showapi_res_body": {
    "ret_code": 0,
    "list": [
      {
        "title": "世界卫生组织宣布已经成功控制SARS",
        "month": 7,
        "img": "http://img.showapi.com/201107/5/099368663.jpg",
        "year": "2003",
        "day": 5
      }
    ]
  }
}
    * */
}
