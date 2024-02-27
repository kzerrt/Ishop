package com.fc.ishop.delayqueue;


import com.fc.ishop.util.ThreadPoolUtil;
import com.fc.ishop.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列工厂
 *
 * @author paulG
 * @since 2020/11/7
 **/
@Slf4j
public abstract class AbstractDelayQueueMachineFactory {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 插入任务id
     *
     * @param jobId 任务id(队列内唯一)
     * @param time  延时时间(单位 :秒)
     * @return 是否插入成功
     */
    public boolean addJobId(String jobId, Integer time) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, time);
        long delaySeconds = instance.getTimeInMillis() / 1000;
        boolean result = redisUtil.zadd(setDelayQueueName(), delaySeconds, jobId);
        log.info("redis add delay, key {}, delay time {}", setDelayQueueName(), time);
        return result;

    }

    /**
     * 延时队列机器开始运作
     */
    private void startDelayQueueMachine() {
        log.info(String.format("延时队列机器{%s}开始运作", setDelayQueueName()));

        // 发生异常捕获并且继续不能让战斗停下来
        while (true) {
            try {
                // 获取当前时间的时间戳
                long now = System.currentTimeMillis() / 1000;
                // 获取当前时间前的任务列表
                Set<DefaultTypedTuple> tuples = redisUtil.zrangeByScoreWithScores(setDelayQueueName(), 0, now);
                // 如果不为空则遍历判断其是否满足取消要求
                if (!CollectionUtils.isEmpty(tuples)) {
                    for (DefaultTypedTuple tuple : tuples) {

                        String jobId = (String) tuple.getValue();
                        Long num = redisUtil.zremove(setDelayQueueName(), jobId);
                        // 如果移除成功, 则执行
                        if (num > 0) {
                            ThreadPoolUtil.execute(() -> invoke(jobId));
                        }
                    }
                }

            } catch (Exception e) {
                log.error(String.format("处理延时任务发生异常,异常原因为{%s}", e.getMessage()), e);
            } finally {
                // 间隔一秒钟搞一次
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * 最终执行的任务方法
     *
     * @param jobId 任务id
     */
    public abstract void invoke(String jobId);


    /**
     * 要实现延时队列的名字
     */
    public abstract String setDelayQueueName();


    @PostConstruct
    public void init() {
        new Thread(this::startDelayQueueMachine).start();
    }

}
