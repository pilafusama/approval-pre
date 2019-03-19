package com.tenpay.wxwork.approval.presvr;

import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.ApplicationListener;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
	private static final Logger LG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(App.class);
		String logHome = System.getProperty("log.home");
		String logPath = "";

		if (StringUtils.isNotEmpty(logHome)) {
			logPath = logHome + File.separator;
		}
		System.setProperty("spring.pid.file", logPath + "application.pid");
		System.out.println("logPath" + logPath);
		ApplicationPidFileWriter pidFileWriter = new ApplicationPidFileWriter();
		pidFileWriter.setTriggerEventType(ApplicationReadyEvent.class);
		application.addListeners(pidFileWriter);
		application
				.addListeners((ApplicationListener<ApplicationFailedEvent>) event -> {
					LG.error("spring init error exit process", event.getException());
					System.exit(-2);
				});
		application.run(args);
	}

}
