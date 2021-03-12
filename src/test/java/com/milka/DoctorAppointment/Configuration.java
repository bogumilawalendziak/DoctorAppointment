package com.milka.DoctorAppointment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    @Profile("integration")
    DataSource dataSource(){
        var result = new DriverManagerDataSource("jdbc:h2:mem:test","sa","");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }
}
