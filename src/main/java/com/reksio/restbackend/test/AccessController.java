package com.reksio.restbackend.test;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(value = "Test controller check access.")
public class AccessController {

    @GetMapping("/public")
    public String publicAccess(){
        return "Test public access";
    }

    @GetMapping("/protected")
    public String protectedAccess(){
        return "Test protected access";
    }

    @GetMapping("/admin")
    public String adminAccess() {return "Admin access";}
}
