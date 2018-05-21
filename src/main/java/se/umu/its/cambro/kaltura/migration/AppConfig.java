package se.umu.its.cambro.kaltura.migration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("se.umu.its.cambro.kaltura.migration")
public class AppConfig {

    @Bean
    public KalturaMigrator migrator() {
        return new KalturaMigrator();
    }
}
