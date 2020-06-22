package com.eheinen.pullrequestmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PullRequestManagerApplication

fun main(args: Array<String>) {
	runApplication<PullRequestManagerApplication>(*args)
}
