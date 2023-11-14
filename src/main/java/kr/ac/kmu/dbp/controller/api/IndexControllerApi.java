package kr.ac.kmu.dbp.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexControllerApi {

    @GetMapping("/")
    public void index() {
    }
}
