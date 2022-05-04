package com.example.haircutscheduling.classes.Data;

import com.example.haircutscheduling.classes.DataModels.UpdateDataModel;

import java.util.HashMap;

public class UpdatesData
{
    public HashMap<String,UpdateDataModel> updatesList;

    public UpdatesData()
    {
        this.updatesList = new HashMap();
    }
}
