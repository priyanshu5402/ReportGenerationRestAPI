package com.ofss.route;

import javax.sql.DataSource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ofss.processor.PayloadProcessor;
import com.ofss.processor.FetchPayloadProcessor;

@Component
public class ReportGenerationRoute extends RouteBuilder {

	@Autowired
	DataSource dataSource;

	@Override
	public void configure() throws Exception {
		rest("/hello")	
			.get("/helloWorld")
			.param()
			.name("date")
			.type(RestParamType.query)
			.endParam()
			.to("direct:payloadToCSV");

		from("direct:payloadToCSV")
			.process(new FetchPayloadProcessor())
			.to("jdbc:dataSource")
			.process(new PayloadProcessor())
			.log("${body}")
			.transform()
			.constant("File Generated");
	}
}
