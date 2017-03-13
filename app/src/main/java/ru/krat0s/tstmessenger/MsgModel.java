package ru.krat0s.tstmessenger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krat0S on 10.03.2017.
 */

public class MsgModel {
    private List<Message> mDataset;


    public MsgModel(){
        mDataset = new ArrayList<>();
    }

    public void addMessage(Message msg){
        mDataset.add(msg);
    }

    public List<Message> getDataset(){
        return mDataset;
    }
}
