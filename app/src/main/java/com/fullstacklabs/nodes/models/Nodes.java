package com.fullstacklabs.nodes.models;

import java.util.ArrayList;
import java.util.List;

public class Nodes {
    public String string;
    public final List<String> children = new ArrayList<String>();

    public Nodes(String string) {
        this.string = string;
    }
}
