package com.eheinen.pullrequestmanager.api.config

import com.eheinen.pullrequestmanager.api.github.GithubApi
import feign.Feign
import feign.Logger
import feign.gson.GsonDecoder
import feign.slf4j.Slf4jLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration(
    @Value("\${api.github.host}") val githubHost: String
) {

    @Bean
    fun githubApiClient(): GithubApi {
        return Feign.builder()
            .decoder(GsonDecoder())
            .contract(SpringMvcContract())
            .logger(Slf4jLogger())
            .logLevel(Logger.Level.FULL)
            .target(GithubApi::class.java, githubHost)
    }
}
