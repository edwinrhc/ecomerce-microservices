package com.edwinrhc.orderservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

     // Ignorar propiedades nulas durante el mapeo
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return modelMapper;

    }
}
