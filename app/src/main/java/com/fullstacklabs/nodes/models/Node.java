package com.fullstacklabs.nodes.models;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String title;
    public String status;
    public String url;
    public final List<String> blocks = new ArrayList<>();

    public Node(String title, String url) {
        this.title = title;
        this.url = url;
        this.status = "OFFLINE";
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
}
