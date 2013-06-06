package com.smarttab.pos.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JavaFXViewControllerScanner.class)
public @interface EnableJavaFXViewControllerScanning {
    String value();
}
