package com.wys.ssm.controller;

import com.wys.ssm.entity.DTO.UserDTO;
import com.wys.ssm.entity.User;
import com.wys.ssm.entity.VO.UserVO;
import com.wys.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2018/1/25/025.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/toFindAllUser")
    public String toFindAllUser (HttpServletRequest req) {
        List<UserVO> userList = userService.findUserByDTO(new UserDTO());
        req.setAttribute("userList", userList);
        return "/userInfoList";
    }

}
