package cn.clouds.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clouds
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/test")
@Api(value = "TestController")
public class TestController {

    @GetMapping()
    @ApiOperation(value = "test", notes = "test")
    public String test() {
        return "test";
    }
}
