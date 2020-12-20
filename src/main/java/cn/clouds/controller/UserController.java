package cn.clouds.controller;

import cn.clouds.entity.User;
import cn.clouds.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author clouds
 * @version 1.0
 */
@Api(value = "user基础接口")
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "create user")
    public ResponseEntity<Object> create(@ApiParam(name = "user", value = "用户实体", type = "User", example = "{user}")
                                         @RequestBody @Valid User user) {
        userService.create(user);
        return ResponseEntity.ok(null);
    }

    @PutMapping
    @ApiOperation(value = "update user")
    public ResponseEntity<User> updateById(@ApiParam(name = "user", value = "用户实体", type = "User", example = "{user}")
                                           @RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping
    @ApiOperation(value = "delete user")
    public ResponseEntity<Integer> deleteById(@ApiParam(name = "id", value = "用户id", type = "int", example = "1")
                                                      int id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping
    @ApiOperation(value = "select user")
    public ResponseEntity<List<User>> selectAll() {
        return ResponseEntity.ok(userService.selectAll());
    }
}
