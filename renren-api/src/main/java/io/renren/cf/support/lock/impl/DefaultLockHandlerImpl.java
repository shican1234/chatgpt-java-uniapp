package io.renren.cf.support.lock.impl;


import cn.hutool.core.util.StrUtil;
import io.renren.cf.annotation.PreventRepeat;
import io.renren.cf.support.lock.LockHandler;
import io.renren.common.redis.RedisKeys;
import io.renren.common.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class DefaultLockHandlerImpl implements LockHandler {

    private Set<String> keySet = new HashSet<>();
    @Autowired
    private RedisService redisService;
    /**
     * 加锁
     *
     * @return
     * @author sunkl
     * @date 2020/3/18 17:33
     */
    @Override
    @PreventRepeat(lockTime = 2)
    public boolean lock(String idempotentId) {
        String value = (String) redisService.get(RedisKeys.SUBMIT_FORM,idempotentId);
        if (StrUtil.isNotBlank(value)) {
            return false;
        } else {
            synchronized (this) {
                if (StrUtil.isNotBlank(value)) {
                    return false;
                } else {
                    redisService.set(RedisKeys.SUBMIT_FORM,idempotentId,idempotentId,1);
                    return true;
                }
            }
        }
    }

    /**
     * 解锁
     *
     * @return
     * @author sunkl
     * @date 2020/3/18 17:32
     */
    @Override
    public boolean unLock(String idempotentId) {
        if (keySet.contains(idempotentId)) {
            synchronized (this) {
                keySet.remove(idempotentId);
            }
        }
        return true;
    }
}
