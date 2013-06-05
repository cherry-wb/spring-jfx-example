package com.smarttab.pos.config;

import com.smarttab.pos.views.ViewController;
import com.smarttab.pos.views.main.MainViewController;
import com.smarttab.pos.views.page1.Page1ViewController;
import com.smarttab.pos.views.page2.Page2ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Configuration
public class MainConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainConfiguration.class);

    @Bean
    public MainViewController mainController() throws IOException {
        return loadController(MainViewController.class);
    }

    @Bean
    public Page1ViewController page1Controller() throws IOException {
        return loadController(Page1ViewController.class);
    }

    @Bean
    public Page2ViewController page2Controller() throws IOException {
        return loadController(Page2ViewController.class);
    }

    @SuppressWarnings("unchecked")
    private static  <T> T loadController(Class<T> clazz) throws IOException {
        final String viewName = clazz.getSimpleName().split("Controller")[0];
        LOGGER.debug("loading controller: " + viewName);
        final URL resource = clazz.getResource(viewName + ".fxml");
        if (resource == null) {
            throw new IllegalStateException("Could not load " + viewName + ".fxml" + " in the " +
                    clazz.getPackage().getName() + " package with the controller class: " + clazz.getSimpleName());
        }
        LOGGER.debug(resource.toString());
        InputStream fxmlStream = null;
        try {
            fxmlStream = resource.openStream();
            FXMLLoader loader = new FXMLLoader();
            Node view = (Node) loader.load(fxmlStream);
            ViewController viewController = loader.getController();
            viewController.setView(view);
            return (T) viewController;
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

}
