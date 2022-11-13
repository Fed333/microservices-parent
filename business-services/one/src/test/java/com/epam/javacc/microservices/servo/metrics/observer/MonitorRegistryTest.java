package com.epam.javacc.microservices.servo.metrics.observer;

import com.epam.javacc.microservices.servo.metrics.observer.base.MetricTestBase;
import com.netflix.servo.Metric;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.annotations.MonitorTags;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.tag.BasicTag;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.TagList;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.*;

public class MonitorRegistryTest extends MetricTestBase {

    @Monitor(
            name = "integerCounter",
            type = DataSourceType.COUNTER,
            description = "Total number of update operations."
    )
    private AtomicInteger updateCount = new AtomicInteger(0);

    @MonitorTags
    private TagList tags = new BasicTagList(new ArrayList<>(Arrays.asList(new BasicTag("tag-key", "tag-value"))));


    @Test
    public void givenAnnotatedMonitor_whenUpdated_thenDataCollected() throws Exception {
        System.setProperty("servo.pollers", "1000");
        Monitors.registerObject("testObject", this);

        MatcherAssert.assertThat(Monitors.isObjectRegistered("testObject", this), is(true));

        updateCount.incrementAndGet();
        updateCount.incrementAndGet();

        SECONDS.sleep(2);

        List<List<Metric>> metrics = observer.getObservations();

        MatcherAssert.assertThat(metrics, hasSize(greaterThanOrEqualTo(1)));

        Iterator<List<Metric>> it = metrics.iterator();
        it.next(); //skip empty observations

        while (it.hasNext()) {
            List<Metric> next = it.next();
            MatcherAssert.assertThat(next, hasItem(
                    hasProperty(
                            "config",
                            hasProperty("name", is("integerCounter")
                    )))
            );
            MatcherAssert.assertThat(next, hasItem(
                    hasProperty(
                            "value",
                            sameInstance(updateCount)
                    ))
            );
            MatcherAssert.assertThat(next.stream().map(m->((AtomicInteger)m.getValue()).get()).collect(Collectors.toList()), hasItem(
                is(2))
            );
        }
    }

}
