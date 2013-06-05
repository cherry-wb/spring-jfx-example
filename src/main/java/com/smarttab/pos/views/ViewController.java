package com.smarttab.pos.views;

import javafx.scene.Node;

public interface ViewController {

    Node getView();

    /**
     * This method is needed only to allow the loadController() method in SampleAppFactory to
     * inject the view into this controller. Ideally FXML would allow us to specify @FXML on
     * in AbstractViewController on the 'view' field and this method would not be needed.
     */
    void setView(Node view);
}
