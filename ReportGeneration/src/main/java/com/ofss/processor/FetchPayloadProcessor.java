package com.ofss.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FetchPayloadProcessor implements Processor{
	
	public void process(Exchange exchange) throws Exception{
		String targetDate=exchange.getIn().getHeader("date", String.class);
		exchange.getIn().setBody("EXEC fetchStgId @targetDate = '"+targetDate+"'");
	}
}
