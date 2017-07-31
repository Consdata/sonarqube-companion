package pl.consdata.ico.sqcompanion.sync;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author pogoma
 */
@Configuration
public class SynchronizationScheduler {

    public static final int POOL_SIZE = 2;

    @Bean
    public TaskScheduler synchronizationTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        return threadPoolTaskScheduler;
    }

}
