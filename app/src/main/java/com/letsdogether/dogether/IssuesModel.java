package com.letsdogether.dogether;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakeshk on 03-04-2018.
 */

public class IssuesModel {

    @SerializedName("title")
    public String title;
    public String state;
    public String comments;
    public String number;
    public String created_at;
    public String closed_at;
    public String html_url;

    public IssuesModel(String title, String state, String comments, String number, String created_at, String closed_at, String html_url){

        this.title = title;
        this.comments = comments;
        this.number = number;
        this.created_at = created_at;
        this.state = state;
        this.closed_at = closed_at;
        this.html_url = html_url;
    }

    public String getComments() {
        return comments;
    }

    public String getNumber() {
        return number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getClosed_at() {
        return closed_at;
    }

    public String getTitle() {
        return title;
    }

    public String getState() {
        return state;
    }

    public String getHtml_url() {
        return html_url;
    }
}