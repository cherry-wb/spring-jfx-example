package com.smarttab.pos.config;

import com.smarttab.pos.spring.EnableJavaFXViewControllerScanning;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJavaFXViewControllerScanning("com.smarttab.pos.views")
public class MainConfiguration {
}
