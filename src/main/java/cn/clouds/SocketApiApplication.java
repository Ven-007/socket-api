package cn.clouds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author clouds
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "cn.clouds")
@EnableSwagger2
public class SocketApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocketApiApplication.class, args);
    }
}
