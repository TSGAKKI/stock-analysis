package com.stockanalysis.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FinnhubProperties.class)
public class ProviderConfiguration {}
