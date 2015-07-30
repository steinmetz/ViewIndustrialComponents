package com.bayninestudios.texturemodeldemo.beans;

import android.content.ContentValues;

import java.util.ArrayList;

/**
 * Created by charles on 27/07/15.
 */
public class Component {

    public int id;
    public String name;
    public NameValue values;

    public Component(){
        values = new NameValue();
    }

}
