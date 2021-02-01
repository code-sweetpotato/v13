package com.qf.v13miaosha.scheduling;


import com.qf.v13miaosha.entity.TMiaosha;
import com.qf.v13miaosha.mapper.TMiaoshaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class MiaoshaTask {

    @Resource(name = "miaosharedis")
    private RedisTemplate<String,Object> redis;

    @Autowired
    private TMiaoshaMapper miaoshaMapper;

    @Scheduled(cron = "0/10 * * * * *")
    public void start(){

        List<TMiaosha> startList = miaoshaMapper.getCanStart();
        if(startList !=null && !startList.isEmpty()){


            for (TMiaosha tMiaosha : startList) {
                tMiaosha.setStatus("1");

                miaoshaMapper.updateByPrimaryKeySelective(tMiaosha);
                String key = new StringBuilder("miaosha:t_miaosha_id:").append(tMiaosha.getId()).toString();
                Integer count = tMiaosha.getCount();

                for (int i=0;i<count;i++){
                    redis.opsForList().leftPush(key,tMiaosha.getProductId());
                }
                String miaoshaKey = new StringBuilder("miaosha:").append(tMiaosha.getId()).toString();

                redis.opsForValue().set(miaoshaKey,tMiaosha);
            }
        }
    }


    @Scheduled(cron = "0/10 * * * * *")
    public void end(){
        List<TMiaosha> endList = miaoshaMapper.getCanEnd();
        if(endList != null && !endList.isEmpty()){
            for (TMiaosha tMiaosha : endList) {

                tMiaosha.setStatus("2");

                miaoshaMapper.updateByPrimaryKeySelective(tMiaosha);
                Integer count = tMiaosha.getCount();
                String key = new StringBuilder("miaosha:t_miaosha_id:").append(tMiaosha.getId()).toString();
                String miaoshaKey = new StringBuilder("miaosha:").append(tMiaosha.getId()).toString();
                redis.delete(key);
                redis.delete(miaoshaKey);
            }
        }

    }


}
