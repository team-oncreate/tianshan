package me.yuruekis.tianshan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    private static final String TAG = "CommentActivity";
    private List<Msg> msgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("留言板");
        //OkHttp begin
        final OkHttpClient client = new OkHttpClient();
        //OkHttp end
        initMsgs();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        //http begin
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestBody loginRequestBody = new FormBody.Builder()
                            .add("username", "ts")
                            .build();
                    Request loginRequest = new Request.Builder()
                            .url("http://0.0.0.0/login")
                            .post(loginRequestBody)
                            .build();
                    Response response = client.newCall(loginRequest).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //http end

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    //OkHttp POST begin
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String time = String.valueOf(System.currentTimeMillis() / 1000L);
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("time", time)
                                        .add("author", "ts")
                                        .add("title", "Message")
                                        .add("content", content)
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://0.0.0.0:8989/msg")
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                Log.d(TAG, "Network" + response.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    //OkHttp POST end
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText(""); //debug response
                }
                }});}

    private void initMsgs() {
        Msg msg1 = new Msg("你好w", Msg.TYPE_RECEIVED);
        Msg msg2 = new Msg("这里可以给天山留言喵w", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        msgList.add(msg2);
    }
}
