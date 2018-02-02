package com.wys.ssm.service.impl;

import com.wys.ssm.dao.UserMapper;
import com.wys.ssm.entity.DTO.UserDTO;
import com.wys.ssm.entity.User;
import com.wys.ssm.entity.VO.UserVO;
import com.wys.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25/025.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVO> findUserByDTO (UserDTO userDTO) {
        return userMapper.findUserByDTO(userDTO);
    }

}
