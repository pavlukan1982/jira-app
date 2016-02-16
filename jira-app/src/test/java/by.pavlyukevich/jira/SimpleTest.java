package by.pavlyukevich.jira;

import by.pavlyukevich.common.Properties;
import org.apache.log4j.Logger;
import org.junit.Test;

public class SimpleTest {

    final static Logger logger = Logger.getLogger(SimpleTest.class);

    @Test
    public void test() {
        logger.error("OK");
    }
}
