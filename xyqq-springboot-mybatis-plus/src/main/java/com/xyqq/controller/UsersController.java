package com.xyqq.controller;

import com.xyqq.model.Users;
import com.xyqq.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyqq
 * @since 2024-11-08
 */
@RestController
public class UsersController {
    @Autowired
    private IUsersService usersService;

    @RequestMapping("/users")
    public Object getUser() {
        return usersService.list();
    }

}
