package work;

import freemarker.template.utility.DateUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务类
 */
public class ScheduledScanningService {

    //每天凌晨执行一次任务
    @Scheduled(cron = "${query.loan.custInfo.result:0 0 4 * * ?}")
    @Async("scheduledAsyncPoolExecutor")//异步执行任务，value值为配置的线程池
    public void scheduledQueryLoanAndCustInfo() {
        //项目部署至多台服务器上，使用redis锁，只需一台服务器执行即可
        String lockId = "redis_lock_key";
        //获得redis锁
        Long canRun = getRedisLock(lockId, String.valueOf(System.currentTimeMillis()), "600");
        String ip = getServiceIP();
        //将redis锁标识，redis锁，拿到redis锁的时间，拿到redis锁服务器的ip记录至mongo日志中
        addLogScheduledBymongolog(lockId, String.valueOf(canRun), System.currentTimeMillis(), ip);
    }

    private void addLogScheduledBymongolog(String lockId, String s, long l, String ip) {
    }

    public Long getRedisLock(String lockId, String s, String s1) {
        return null;
    }

    public String getServiceIP() {
        return null;
    }
}
