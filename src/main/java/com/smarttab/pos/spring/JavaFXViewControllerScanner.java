package com.smarttab.pos.spring;

import com.smarttab.pos.views.AbstractViewController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.Set;

/**
 * This registrar scans a given package for all AbstractViewController classes, loads their FXML with the JavaFX FXML
 * loader, then registers them with spring and autowires them.
 *
 * @author Geoffrey Chandler, gcc@smarttab.com
 */
public class JavaFXViewControllerScanner implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String value = (String) importingClassMetadata.getAnnotationAttributes(
                EnableJavaFXViewControllerScanning.class.getName()).get("value");
        postProcessBeanDefinitionRegistry(value, registry);
    }

    private void postProcessBeanDefinitionRegistry(String packageToScan, BeanDefinitionRegistry registry) throws BeansException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractViewController.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(packageToScan);

        for (BeanDefinition definition : components) {

            Class clazz = ClassUtils.resolveClassName(
                    definition.getBeanClassName(),
                    ClassUtils.getDefaultClassLoader());

            AbstractBeanDefinition newDefinition = BeanDefinitionBuilder.
                    genericBeanDefinition(JavaFXViewControllerFactoryBean.class).
                    addConstructorArgValue(clazz).
                    getBeanDefinition();

            newDefinition.setAutowireCandidate(true);
            newDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

            registry.registerBeanDefinition(clazz.getSimpleName(), newDefinition);
        }
    }

}
