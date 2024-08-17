package com.consdata.echo.synchronization;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
public class JobController {

    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final Job job;

    @SneakyThrows
    @GetMapping("start")
    public void handle() {
        jobLauncher.run(job, new JobParameters());
    }

    @GetMapping("status")
    public BatchStatus status() {
        return jobExplorer.findRunningJobExecutions("usersJob").stream().findFirst().map(e -> e.getStatus()).orElse(null);
    }
}
