package ch.brontofundus.demos.quartz.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class SomeService {

    private val logger = LoggerFactory.getLogger(SomeService::class.java)

    fun saySomething(who: String) {
        logger.info("    ${who}: Something! " + Date())
    }

}
