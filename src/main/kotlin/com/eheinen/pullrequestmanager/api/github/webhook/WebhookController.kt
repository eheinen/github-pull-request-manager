package com.eheinen.pullrequestmanager.api.github.webhook

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WebhookController {

    @PostMapping("github-webhook")
    fun webhook(@RequestBody webhookDto: WebhookDto) {
        print("hiiiiiiiiiiii")
    }
}
