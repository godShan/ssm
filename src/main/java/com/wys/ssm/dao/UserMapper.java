package com.wys.ssm.dao;

import com.wys.ssm.entity.DTO.UserDTO;
import com.wys.ssm.entity.VO.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25/025.
 */
public interface UserMapper {

    List<UserVO> findUserByDTO (@Param("dto") UserDTO userDTO);

}
