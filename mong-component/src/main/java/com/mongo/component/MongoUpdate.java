package com.mongo.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Configuration("mongoUpdate")
public class MongoUpdate {

	@Autowired
	MongoComponentOperations mongoComponentOperations;

	@EventListener(ApplicationReadyEvent.class)
	public void transform() throws Exception {
		String param1 = "";
				System.getenv("PARAM1");
		String param2 = null;
				System.getenv("PARAM2");
		
		param1 = "D:\\Timepass\\mongo-component\\";

		if (null != param1 && null != param2) {
			mongoComponentOperations.updateMultipleDocs(param1, param2);
		} else if (null != param1) {
			try {
				System.out.println(param1);
				mongoComponentOperations.updateMultipleDocs(param1);
			} catch (IOException e) {
				throw new Exception();
			}
		} else {
			System.out.println("Error");
		}
//		return new HashMap();
	}
}
