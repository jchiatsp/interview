package sp.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.validation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;


public class TestConf {
    private static final Config BASE_CONFIG = ConfigFactory.load();
    private static final ObjectMapper MAPPER = buildObjectMapper();
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final TestConf TEST_CONF = buildTestConfSingleton(BASE_CONFIG);

    @Valid
    @Min(1)
    private Integer ajaxWaitSeconds;

    @Valid
    @NotNull
    private String searchUrl;

    public String getSearchUrl() {
        return searchUrl;
    }

    public Integer getAjaxWaitSeconds() {
        return ajaxWaitSeconds;
    }

    public static TestConf getInstance(){
        return TEST_CONF;
    }

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private static TestConf buildTestConfSingleton(Config config){
        Map<String, Object> unwrappedConfig = config.root().unwrapped();
        TestConf testConf = MAPPER.convertValue(unwrappedConfig, TestConf.class);
        Set<ConstraintViolation<TestConf>> constraintViolations = VALIDATOR.validate(testConf);
        if(!constraintViolations.isEmpty()){
            StringBuilder message = new StringBuilder();
            message.append(" ");
            for (ConstraintViolation<TestConf> violation :
                 constraintViolations) {
                message.append(" ");
                message.append(violation.getPropertyPath());
                message.append(" : ");
                message.append(violation.getMessage());
                message.append('\n');
            }
            System.out.println(message.toString());
            throw new IllegalStateException("Config invalid!!");
        }

        return testConf;
    }

}
