package com.epam.javacc.microservices.servo.metrics.service;

import com.epam.javacc.microservices.servo.metrics.utils.DataTimeUtils;
import com.epam.javacc.microservices.servo.metrics.utils.MapUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
public class ServiceOneService {

    public Map<String,Object> handlePost() {
        randomSleep(1000, 4000);
        String timestamp = getCurrentTimestamp();
        return MapUtils.createMap(new String[][]{
                {"message", "post request was accepted!"},
                {"timestamp", timestamp}
        });
    }

    public Map<String, String> getAuthorsInfo() {
        randomSleep(200, 2000);
        String timestamp = getCurrentTimestamp();
        return MapUtils.createMap(new String[][]{
                {"service", "service-one"},
                {"author", "Roman Kovalchuk"},
                {"timestamp", timestamp}
        });
    }

    private String getCurrentTimestamp() {
        return DataTimeUtils.getCurrentTimestamp();
    }
    public Map<String, Object> getLinks() {
        randomSleep(0, 500);
        String timestamp = getCurrentTimestamp();
        return MapUtils.createMap(new Object[][]{
                {"_links", MapUtils.createMap(new Object[][]{
                        {"info", "http://localhost:9393/business-services/one/info"}
                })},
                {"timestamp", timestamp}
        });
    }

    /**
     * Sleep program in random duration.
     * @param from lower bound of random range in milliseconds
     * @param to upper bound of random range in milliseconds
     * */
    @SneakyThrows
    private void randomSleep(int from, int to) {
        if (to < from) {
            throw new RuntimeException("to cannot be less than from");
        }
        Thread.sleep(new Random().nextInt(to-from)+from);
    }

}
