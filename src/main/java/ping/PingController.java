package ping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class PingController {
    @RequestMapping("/")
    public String index() {
	return "Ping Acknowledged.";
    }
}