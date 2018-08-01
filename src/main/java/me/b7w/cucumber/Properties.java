package me.b7w.cucumber;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    @Value("${me.b7w.project.name}")
    public String projectName;

}
