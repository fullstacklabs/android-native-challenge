package com.fullstacklabs.nodes.models;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Node extends ViewModel {
    private String title;
    private String status;
    private String url;
    public final List<String> blocks = new ArrayList<>();

    public Node(String title, String url, String status) {
        this.title = title;
        this.url = url;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
