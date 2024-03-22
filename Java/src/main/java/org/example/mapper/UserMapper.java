package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.Domin.User;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/3/22 10:03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
