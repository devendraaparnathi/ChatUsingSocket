package com.example.kotlinstart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kotlinstart.database.MessageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MessaegActivity extends AppCompatActivity {

    private String name;
    private WebSocket webSocket;
    private String SERVER_PATH = "ws://192.168.2.6:3000";
    private EditText editText;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaeg);

        name = getIntent().getStringExtra("name");
        initialSocketConnection();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void initialSocketConnection() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());

    }

    private class SocketListener extends WebSocketListener implements TextWatcher {
        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);

            runOnUiThread(()->{

                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent",false);
                    messageAdapter.addItem(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(MessaegActivity.this, "Connection establish", Toast.LENGTH_SHORT).show();
                initializeView();
            });

        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(()->{
                Toast.makeText(MessaegActivity.this, "Connection establish", Toast.LENGTH_SHORT).show();
                initializeView();
            });
        }

        private void initializeView() {

            editText = findViewById(R.id.edtMessage);
            imageView = findViewById(R.id.btnSend);
            recyclerView = findViewById(R.id.recyclerview);
            messageAdapter = new MessageAdapter(getLayoutInflater());
            recyclerView.setAdapter(messageAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            editText.addTextChangedListener(this);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("name",name);
                        jsonObject.put("message",editText.getText().toString());
                        jsonObject.put("isSent",true);
                        messageAdapter.addItem(jsonObject);

                        webSocket.send(jsonObject.toString());
                        resetMessageEdit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String string = s.toString().trim();
            if (string.isEmpty())
            {
                resetMessageEdit();
            }
        }

        private void resetMessageEdit() {
        editText.removeTextChangedListener(this);
        editText.setText("");
        editText.addTextChangedListener(this);
        }
    }
}