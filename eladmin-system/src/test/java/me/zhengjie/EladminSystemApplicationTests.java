package me.zhengjie;

import me.zhengjie.anomalydetection.context.AnomalyDetectionStrategyContext;
import me.zhengjie.anomalydetection.strategy.AnomalyDetectionStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EladminSystemApplicationTests {

    @Autowired
    private AnomalyDetectionStrategyContext adContext;

    @Test
    public void contextLoads() {
    }

    @Test
    public void anomalyDetectionTest() {
        System.out.println(1);
    }

    public static void main(String[] args) {
    }
}

