package main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class DeploymentController {

    @RequestMapping("/")
    public String welcome() {
        System.out.println("called rishi");
        return "welcome";
    }
}
