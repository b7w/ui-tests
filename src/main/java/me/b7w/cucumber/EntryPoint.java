package me.b7w.cucumber;

import me.b7w.cucumber.test.SimpleTest;
import me.b7w.cucumber.test.SpringTest;
import org.junit.runner.JUnitCore;
import org.springframework.stereotype.Component;

@Component
public class EntryPoint {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
//        junit.addListener(new TextListener(System.out));
        junit.run(SimpleTest.class);
        junit.run(SpringTest.class);
    }

}
