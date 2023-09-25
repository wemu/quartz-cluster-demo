package ch.brontofundus.demos.quartz.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "quartzdemo.instance")
data class EnvPropsConfiguration(val name: String, val waitmillis: Long)
