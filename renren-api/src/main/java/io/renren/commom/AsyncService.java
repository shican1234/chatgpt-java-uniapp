package io.renren.commom;

import cn.hutool.core.util.StrUtil;
import io.renren.common.redis.RedisKeys;
import io.renren.common.redis.RedisService;
import io.renren.common.utils.IpUtils;
import io.renren.entity.QuestionAnswerEntity;
import io.renren.entity.UserEntity;
import io.renren.service.QuestionAnswerService;
import io.renren.service.SysParamsService;
import io.renren.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class AsyncService {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionAnswerService questionAnswerService;
    @Autowired
    private RedisService redisService;






    /**
     * 存用户的问题和答案
     * @param userId 用户ID
     * @param msg 用户提的问题
     * @param lastMessage GPT的回答
     */
    @Async
    public void saveQuestionAndAnswer(Long userId, String msg, String lastMessage) {
        QuestionAnswerEntity questionAnswer = new QuestionAnswerEntity();
        questionAnswer.setQuestion(msg);
        questionAnswer.setAnswer(lastMessage);
        questionAnswer.setUserId(userId);
        questionAnswer.setCreateTime(new Date());
        questionAnswerService.insert(questionAnswer);
    }

    /**
     * 5分钟更新下最后活跃时间和IP
     * @param userId
     * @param request
     */
    @Async
    public void updateLoginTimeAndIp(Long userId, HttpServletRequest request) {
        String s = (String) redisService.get(RedisKeys.LOGIN_IP, userId.toString());
        if(StrUtil.isBlank(s)){
            String ipAddr = IpUtils.getIpAddr(request);
            UserEntity user = new UserEntity();
            user.setIp(ipAddr);
            user.setId(userId);
            user.setLastLogin(new Date());
            userService.updateById(user);
            redisService.set(RedisKeys.LOGIN_IP,userId.toString(),userId.toString(),5 * 60);
        }
    }

    @Async
    public void updateOpenID(UserEntity user, String openid) {
        UserEntity neeUser = new UserEntity();
        neeUser.setId(user.getId());
        neeUser.setWxOpenid(openid);
        userService.updateById(neeUser);
    }
}
