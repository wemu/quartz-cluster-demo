package ch.brontofundus.demos.quartz.controller

import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import ch.brontofundus.demos.quartz.service.SchedulerAdministrationService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PagesController(val envConfiguration: EnvPropsConfiguration,
                      val schedulerAdministrationService: SchedulerAdministrationService) {

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("instanceName", envConfiguration.name)
        model.addAttribute("instanceWaitMillis", envConfiguration.waitmillis)

        model.addAttribute("isInStandby", schedulerAdministrationService.isInStandby())

        model.addAttribute("jobs", schedulerAdministrationService.readAllJobs())

        return "index"
    }

    @GetMapping("/frames")
    fun frames(): String {
        return "frames"
    }

}
