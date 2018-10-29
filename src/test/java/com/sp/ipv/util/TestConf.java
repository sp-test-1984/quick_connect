package com.sp.ipv.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.validation.*;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public class TestConf {

    private static final Config BASE_CONFIG = ConfigFactory.load();
    private static final ObjectMapper MAPPER = buildObjectMapper();
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final Logger LOG = LoggerFactory.getLogger(TestConf.class);
    private static final TestConf TEST_CONF = buildTestConfSingleton(BASE_CONFIG);
    private static final Screen SCREEN = new Screen();


    @Valid
    @NotNull
    private String searchUrl;

    @Valid
    @NotNull
    private String applicationPath;

    @Valid
    @NotNull
    private String ipvUser;

    @Valid
    @NotNull
    private String ipvPassword;

    @NotNull
    public String getApplicationPath() {
        return applicationPath;
    }

    public static TestConf getTestConf() {
        return TEST_CONF;
    }

    public static Screen getScreen() {
        return SCREEN;
    }

    @NotNull
    public String getIpvUser() {
        return ipvUser;
    }

    @NotNull
    public String getSearchUrl() {
        return searchUrl;
    }

    @NotNull
    public String getIpvPassword() {
        return ipvPassword;
    }

    private static ObjectMapper buildObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private static TestConf buildTestConfSingleton(Config config){
        Map<String, Object> unwrappedConfig = config.root().unwrapped();
        TestConf testConf = MAPPER.convertValue(unwrappedConfig, TestConf.class);
        Set<ConstraintViolation<TestConf>> constraintViolations =VALIDATOR.validate(testConf);
        if(!constraintViolations.isEmpty()){
            StringBuilder message = new StringBuilder();
            message.append("Config invalid, please correct the following: \n");
            for (ConstraintViolation<TestConf> violation:
                 constraintViolations) {
                message.append(" ");
                message.append(violation.getPropertyPath());
                message.append(" : ");
                message.append(violation.getMessage());
                message.append('\n');
            }
            LOG.error(message.toString());
            throw new IllegalStateException("App's config invalid.");
        }
        return testConf;
    }
}
