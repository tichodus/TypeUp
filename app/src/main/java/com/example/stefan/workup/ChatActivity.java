package com.example.stefan.workup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stefan.workup.models.ChatMessage;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ArrayAdapter<ChatMessage> _messageAdapter;

    //private  FirebaseListAdapter<ChatMessage> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ListView listView = (ListView) findViewById(R.id.list_view_chat);
        listView.setAdapter(new ArrayAdapter<ChatMessage>(this, android.R.layout.simple_list_item_1));


        DisplayChatMessages();


    }

    private void DisplayChatMessages() {

    }
}
