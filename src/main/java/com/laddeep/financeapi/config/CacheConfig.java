package com.laddeep.financeapi.config;

import com.dlocal.library.contract.config.BaseCacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig extends BaseCacheConfig {

    public static final String QUOTE_CODE_NAMES = "quote_code_names";
}
