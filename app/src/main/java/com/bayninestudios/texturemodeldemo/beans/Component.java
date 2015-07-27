package com.bayninestudios.texturemodeldemo.beans;

import android.content.ContentValues;

/**
 * Created by charles on 27/07/15.
 */
public class Component {

    public int id;
    public String name;
    public ContentValues values;

    public Component(){
        values = new ContentValues();
    }

}
