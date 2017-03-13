package ru.krat0s.tstmessenger;

import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Krat0S on 10.03.2017.
 */

public class XMPPConnectionListener implements ConnectionListener {

    private boolean connected;
    private boolean isToasted;
    private boolean chat_created;
    private boolean loggedin;
    private String mUserName;
    private String mPassWord;

    public XMPPConnectionListener(String userName, String passWord){
        mUserName = userName;
        mPassWord = passWord;
    }

    @Override
    public void connected(XMPPConnection connection) {
        Log.d("xmpp", "Connected!");
        connected = true;
        if (!connection.isAuthenticated()) {
            login(connection);
        }
    }

    private void login(XMPPConnection connection) {

        try {
            ((AbstractXMPPConnection)connection).login(mUserName, mPassWord);
            //Log.i("LOGIN", "Yey! We're connected to the Xmpp server!");

        } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        Log.d("xmpp", "Authenticated!");
        loggedin = true;

        chat_created = false;
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
        if (isToasted)

            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub



                }
            });
    }

    @Override
    public void connectionClosed() {
        if (isToasted)

            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub


                }
            });
        Log.d("xmpp", "ConnectionCLosed!");
        connected = false;
        chat_created = false;
        loggedin = false;
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        if (isToasted)

            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {

                }
            });
        Log.d("xmpp", "ConnectionClosedOn Error!");
        connected = false;

        chat_created = false;
        loggedin = false;
    }

    @Override
    public void reconnectionSuccessful() {
        if (isToasted)

            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub



                }
            });
        Log.d("xmpp", "ReconnectionSuccessful");
        connected = true;

        chat_created = false;
        loggedin = false;
    }

    @Override
    public void reconnectingIn(int seconds) {
        Log.d("xmpp", "Reconnectingin " + seconds);

        loggedin = false;
    }

    @Override
    public void reconnectionFailed(Exception e) {
        if (isToasted)

            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {



                }
            });
        Log.d("xmpp", "ReconnectionFailed!");
        connected = false;

        chat_created = false;
        loggedin = false;
    }
}
