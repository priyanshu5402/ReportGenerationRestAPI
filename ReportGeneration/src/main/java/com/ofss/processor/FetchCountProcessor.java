package com.ofss.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FetchCountProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String targetDate = exchange.getIn().getHeader("date", String.class);
		String msgType = exchange.getIn().getHeader("msgType", String.class);
		exchange.getIn().setBody("EXEC countPayload @targetDate = '" + targetDate + "', @msg = '" + msgType + "';");
	}
}
