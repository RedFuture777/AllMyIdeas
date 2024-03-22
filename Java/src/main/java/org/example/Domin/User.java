package org.example.Domin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: User
 * @author: muqingfeng
 * @date: 2024/3/22 09:48
 */
@Data
@TableName("user")
public class User {
    @TableId
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;
}
