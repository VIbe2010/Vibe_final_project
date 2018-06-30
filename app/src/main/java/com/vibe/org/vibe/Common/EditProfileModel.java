package com.vibe.org.vibe.Common;

/**
 * Created by Aman on 10/06/2018.
 */

public class EditProfileModel {
    private String key;
    private String value;

    public EditProfileModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
