package net.transino.lms;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"serverBean.xml"})
public class ConfigBean {
}