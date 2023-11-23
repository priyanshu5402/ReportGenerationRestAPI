package com.ofss.processor;

import java.sql.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FetchPayloadProcessor implements Processor{
	
	public void process(Exchange exchange) throws Exception{
		String date=exchange.getIn().getHeader("date", String.class);
		System.out.println(date);
		exchange.getIn().setBody("EXEC fetchStgId @targetDate = '"+date+"'");
	}
}
