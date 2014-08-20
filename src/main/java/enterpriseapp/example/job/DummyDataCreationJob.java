package enterpriseapp.example.job;

import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enterpriseapp.EnterpriseApplication;
import enterpriseapp.Utils;
import enterpriseapp.example.container.DummyDataCreator;
import enterpriseapp.job.LogFilesCollectorJob;

public class DummyDataCreationJob implements Job {

	private static Logger logger = LoggerFactory.getLogger(DummyDataCreationJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Executing job...");
		DummyDataCreator.bootstrap();
		schedule();
	}

	public static void schedule() {
		Calendar calendar;
		try {
			logger.info("Sheduling job...");
			calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			
			JobKey jobKey = new JobKey(calendar.getTime().toString());
			JobDetail job = JobBuilder.newJob(LogFilesCollectorJob.class).withIdentity(jobKey).build();
			Trigger trigger = TriggerBuilder.newTrigger().startAt(calendar.getTime()).build();
			EnterpriseApplication.getScheduler().scheduleJob(job, trigger);
			
		} catch (SchedulerException e) {
			logger.error("Error shcheduling job.", e);
			throw new RuntimeException(e);
		}
		
		logger.info("Job scheduled at " + Utils.dateTimeToString(calendar.getTime()) + ".");
	}
	
}
