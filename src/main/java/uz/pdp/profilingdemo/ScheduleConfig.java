package uz.pdp.profilingdemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
public class ScheduleConfig {
    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
//    public void fixedDelay() throws InterruptedException {
//        System.out.println(formatter.format(new Date()));
//        TimeUnit.SECONDS.sleep(1);
//    }

//    @Scheduled(initialDelay = 5, fixedRate = 1, timeUnit = TimeUnit.SECONDS)
//    public void fixedRate() throws InterruptedException {
//        System.out.println(formatter.format(new Date()));
//        TimeUnit.SECONDS.sleep(1);
//    }

    @Scheduled(cron = "10-20/3 15-16 * * * *")
    public void cronJob(){
        System.out.println(formatter.format(new Date()));
    }

}
