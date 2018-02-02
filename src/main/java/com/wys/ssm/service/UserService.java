package com.wys.ssm.service;

import com.wys.ssm.entity.DTO.UserDTO;
import com.wys.ssm.entity.User;
import com.wys.ssm.entity.VO.UserVO;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25/025.
 */
public interface UserService {

    List<UserVO> findUserByDTO(UserDTO userDTO);

}
