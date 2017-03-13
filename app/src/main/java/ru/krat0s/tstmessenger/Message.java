package ru.krat0s.tstmessenger;

/**
 * Created by Krat0S on 10.03.2017.
 */

public class Message {
    private String mTime;
    private String mFrom;
    private String mMsg;

    public Message(String time, String from, String msg){
        mTime = time;
        mFrom = from;
        mMsg = msg;
    }

    public String getTime() {
        return mTime;
    }
    public String getFrom() {
        return mFrom;
    }
    public String getMsg() {
        return mMsg;
    }
}
