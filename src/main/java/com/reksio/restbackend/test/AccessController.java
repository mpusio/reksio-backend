package com.reksio.restbackend.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
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
