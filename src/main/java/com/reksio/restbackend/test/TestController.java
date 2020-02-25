package com.reksio.restbackend.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(value = "Test controller")
public class TestController {

    @ApiOperation("Check public access")
    @GetMapping("/public")
    public String publicAccess(){
        return "Test public access";
    }

    @ApiOperation("Check protected access (as logged user)")
    @GetMapping("/protected")
    public String protectedAccess(){
        return "Test protected access";
    }

    @ApiOperation("Check admin access (as logged user with admin auth)")
    @GetMapping("/admin")
    public String adminAccess() {return "Admin access";}

    @ApiOperation("Example charge template")
    @GetMapping("/charge-template")
    public String paymentStripeExampleTemplate(){
        return "charge";
    }
}
