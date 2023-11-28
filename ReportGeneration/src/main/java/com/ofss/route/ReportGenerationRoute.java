package com.ofss.route;

import javax.sql.DataSource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ofss.processor.PayloadProcessor;
import com.ofss.processor.CountMsgProcessor;
import com.ofss.processor.FetchCountProcessor;
import com.ofss.processor.FetchPayloadProcessor;

@Component
public class ReportGenerationRoute extends RouteBuilder {

	@Autowired
	DataSource dataSource;

	@Override
	public void configure() throws Exception {
		rest("/hello")					//REST Api for fetching data and parsing the xml
			.get("/fetchData")
			.param().name("date").type(RestParamType.query).endParam()
			.to("direct:payloadToCSV");

		from("direct:payloadToCSV")
			.process(new FetchPayloadProcessor())
			.to("jdbc:dataSource")
			.process(new PayloadProcessor())
			.transform()
			.constant("File Generated");
		
		rest("/hello")					//REST APi for count of incoming and outgoing messages
			.get("/countData")
			.param().name("date").type(RestParamType.query).endParam()
			.param().name("msgType").type(RestParamType.query).endParam()
			.to("direct:hello");

	from("direct:hello")
		.process(new FetchCountProcessor())
		.to("jdbc:dataSource")
		.log("${body}")
		.process(new CountMsgProcessor())
		.transform()
		.constant("task done");
	}
}
