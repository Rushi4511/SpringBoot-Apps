package com.codingshuttle.project.uber.uberApp;

import com.codingshuttle.project.uber.uberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberAppApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"harshalwadne1978@gmail.com",
				"Hi I am Harshal Wadne ,A technology mad person,I can put my screwdriver anywhere,from Cambridge School ,Not from LOndon,But from Ranchi surusgandha",
				"If You want to learn SupaBase vs Firebase"
		);
	}

	@Test
	void setEmailSenderService() {

		String emails[]  = {
				"rushikarle45@gmail.com",
				"r1ruru219@gmail.com",
				"shivanilalwani22@gmail.com",


		};
		emailSenderService.sendEmail(
				emails,
				"Hi I am From Coding Shuttle",
				"I got your Mail form Anuj Sirs Uber App Lecture,I am sending this mail from my Uber Application for testing purpose" +
						" if it is working then reply .Thank you !!!"
		);
	}

}
