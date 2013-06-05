package com.smarttab.pos.views;

import javafx.scene.Node;

public abstract class AbstractViewController implements ViewController {
    private Node view;

    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }
}
