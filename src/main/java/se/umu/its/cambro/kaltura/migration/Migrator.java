package se.umu.its.cambro.kaltura.migration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Migrator {

    private static final Logger logger = LogManager.getLogger(Migrator.class);

    public static void main(String[] args) throws Exception{

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(AppConfig.class, SpringJDBCConfiguration.class);

        annotationConfigApplicationContext.refresh();

        KalturaMigrator migrator = (KalturaMigrator) annotationConfigApplicationContext.getBean("migrator");

        logger.info("Starting migration");
        migrator.migrate();
    }
}