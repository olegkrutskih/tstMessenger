package ru.krat0s.tstmessenger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChat;
    private EditText etMsg;
    private Button btSend;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MsgModel mDataset;
    private Xmpp xmpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvChat = (RecyclerView)findViewById(R.id.rvChat);
        etMsg = (EditText)findViewById(R.id.etMsg);
        btSend = (Button)findViewById(R.id.btSend);

        mLayoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(mLayoutManager);
        // создаем адаптер
        mDataset = new MsgModel();
        mAdapter = new ChatAdapter(mDataset);
        rvChat.setAdapter(mAdapter);
        xmpp = Xmpp.getInstance();

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                String tm = c.getTime().toString();
                ru.krat0s.tstmessenger.Message msg = new ru.krat0s.tstmessenger.Message(tm, xmpp.getUserName(), etMsg.getText().toString());
                mDataset.addMessage(msg);
                mAdapter.notifyDataSetChanged();
                xmpp.sendMsg("oleg1982@jabber.ru", etMsg.getText().toString());
            }
        });
        initXmpp();
    }

    private void initXmpp(){

        ChatManager.getInstanceFor(xmpp.connection).addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                chat.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        if (message.getType() == Message.Type.chat || message.getType() == Message.Type.normal) {
                            if(message.getBody()!=null) {
                                //Toast.makeText(getApplicationContext(),message.getFrom() + " : " + message.getBody(),Toast.LENGTH_LONG).show();
                                Log.e("message trigger", message.getFrom() + " : " + message.getBody());
                                Calendar c = Calendar.getInstance();
                                String tm = c.getTime().toString();
                                ru.krat0s.tstmessenger.Message msg = new ru.krat0s.tstmessenger.Message(tm, message.getFrom(), message.getBody());
                                mDataset.addMessage(msg);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        }
                    }
                });
            }
        });
    }

}
