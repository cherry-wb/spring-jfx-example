package com.smarttab.pos.spring;

import com.smarttab.pos.views.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This factory bean creates new JavaFX view controllers with FXML files by the same name (without the word
 * "Controller") and autowires them with Spring.
 *
 * @author Geoffrey Chandler, gcc@smarttab.com
 */
public class JavaFXViewControllerFactoryBean<T extends ViewController> implements BeanFactoryAware, FactoryBean<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaFXViewControllerFactoryBean.class);

    private AutowireCapableBeanFactory beanFactory;

    private Class clazz;

    public JavaFXViewControllerFactoryBean(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (AutowireCapableBeanFactory) beanFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return (T) loadController(clazz);
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private ViewController loadController(Class clazz) throws IOException {
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
            beanFactory.autowireBean(viewController);
            return viewController;
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }
}
