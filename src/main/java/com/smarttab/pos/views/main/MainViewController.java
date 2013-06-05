package com.smarttab.pos.views.main;

import com.smarttab.pos.views.AbstractViewController;
import com.smarttab.pos.views.page1.Page1ViewController;
import com.smarttab.pos.views.page2.Page2ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;

public class MainViewController extends AbstractViewController {
    @FXML
    private BorderPane contentArea;

    @Autowired
    private Page1ViewController page1Controller;

    @Autowired
    private Page2ViewController page2Controller;

    public void showPage1(ActionEvent event) {
        contentArea.setCenter(page1Controller.getView());
    }

    public void showPage2(ActionEvent event) {
        contentArea.setCenter(page2Controller.getView());
    }
}
