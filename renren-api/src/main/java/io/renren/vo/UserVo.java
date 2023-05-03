package io.renren.vo;

import io.renren.entity.UserEntity;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    /**
     * 用户id(主键)
     */
    private Long id;

    /**
     * 普通用户的标识，对当前开发者帐号唯一
     */
    private String wxOpenid;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * ip
     */
    private String ip;

    /**
     *提问次数
     */
    private int msgCount;
    private String token;

    public UserVo(){}
    public UserVo(UserEntity user){
        this.id = user.getId();
        this.avatar = user.getAvatar();
        this.ip = user.getIp();
        this.nickName = user.getNickName();
        this.wxOpenid =user.getWxOpenid();
        this.createTime = user.getCreateTime();
    }
}
