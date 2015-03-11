package at.bro.code.log4j.appender.solr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import at.bro.code.log4j.appender.solr.helper.LogEntity;
import at.bro.code.log4j.appender.solr.helper.LogEntityRepository;

@Test
@ContextConfiguration(classes = { AppenderTestConfiguration.class })
public class SolrLog4jAppenderTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private LogEntityRepository repository;

    @Test
    void testLogAppender() throws InterruptedException {
        final Logger log = LoggerFactory.getLogger(SolrLog4jAppenderTest.class);

        repository.deleteAll();

        final String msg = "one message to the solr log";
        log.error(msg);
        Thread.sleep(1000L);

        final long count = repository.count();
        Assert.assertTrue(count > 0);
        for (final LogEntity e : repository.findAll()) {
            Assert.assertEquals(e.getMessage(), msg);
        }
    }
}
