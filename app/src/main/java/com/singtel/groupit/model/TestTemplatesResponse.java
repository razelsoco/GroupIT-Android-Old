package com.singtel.groupit.model;

import com.google.gson.annotations.SerializedName;
import com.singtel.groupit.model.domain.Contact;
import com.singtel.groupit.model.domain.Template;
import com.singtel.groupit.model.domain.User;

import java.util.ArrayList;

/**
 * Created by razelsoco on 27/6/16.
 */

public class TestTemplatesResponse {
    @SerializedName("templates")
    public ArrayList<Template> templates;
}