package com.limjae.weather.batch.scheduler;

import com.limjae.weather.batch.job.LoadApiBatchConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadApiJobScheduler {
    private final JobLauncher jobLauncher;
    private final LoadApiBatchConfiguration batchConfiguration;

    //live + short
//    @Scheduled(cron = "15 * * * * *")
    @Scheduled(cron = "0 0 6 * * *")
    public void runAt6() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        confMap.put("executeHour", new JobParameter("6"));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(batchConfiguration.liveApiJob(), jobParameters);
            jobLauncher.run(batchConfiguration.shortApiJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }

    // run live + midterm
//    @Scheduled(cron = "30 * * * * *")
    @Scheduled(cron = "0 0 9 * * *")
    public void runAt9() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        confMap.put("executeHour", new JobParameter("9"));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(batchConfiguration.liveApiJob(), jobParameters);
            jobLauncher.run(batchConfiguration.shortApiJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }

//    @Scheduled(cron = "45 * * * * *")
    @Scheduled(cron = "0 0 18 * * *")
    public void runAt18() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        confMap.put("executeHour", new JobParameter("18"));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(batchConfiguration.liveApiJob(), jobParameters);
            jobLauncher.run(batchConfiguration.shortApiJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }

    // run live + midterm
//    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 21 * * *")
    public void runAt21() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        confMap.put("executeHour", new JobParameter("21"));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(batchConfiguration.liveApiJob(), jobParameters);
            jobLauncher.run(batchConfiguration.shortApiJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}