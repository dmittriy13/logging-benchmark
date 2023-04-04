package ru.bcs;

import net.logstash.logback.marker.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.ObjectMessage;
import org.openjdk.jmh.annotations.*;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@State(Scope.Benchmark)
public class AsyncConsoleJsonAppenderBenchmark {

    public static Map<String, String> msgObject = Map.of("key", "value");
    public static final String MESSAGE = "This is a debug message";

    private org.apache.logging.log4j.Logger log4j2Logger;
    private ch.qos.logback.classic.Logger logbackLogger;

    @Setup
    public void setUp() {
        System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        System.setProperty("log4j.configurationFile", "log4j2-async-json-config.xml");
        System.setProperty("logback.configurationFile", "logback-async-json-config.xml");

        this.log4j2Logger = LogManager.getLogger(this.getClass());
        this.logbackLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(this.getClass());
    }

    @TearDown
    public void tearDown() {
        System.clearProperty("log4j2.contextSelector");
        System.clearProperty("log4j.configurationFile");
        System.clearProperty("logback.configurationFile");
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public void logbackJson() {
        logbackLogger.debug("", Markers.append("info", msgObject));
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public void log4j2Json() {
        log4j2Logger.debug(new ObjectMessage(msgObject));
    }
}
