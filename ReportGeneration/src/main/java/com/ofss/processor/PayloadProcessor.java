package com.ofss.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class PayloadProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		try {
			List<Object> fetchedPayloadList = new ArrayList<>();
			ArrayList<String> xmlList = new ArrayList<String>();

			fetchedPayloadList = exchange.getIn().getBody(ArrayList.class);
			for (int i = 0; i < fetchedPayloadList.size(); i++) {
				LinkedHashMap<Integer, String> map = (LinkedHashMap<Integer, String>) fetchedPayloadList.get(i);
				for (Map.Entry<Integer, String> entry : map.entrySet()) {
					String xmlValue = entry.getValue();
					System.out.println("Value: " + xmlValue);
					xmlList.add(xmlValue);
				}
			}
			XmlProcessor.xmlToExcel(xmlList);
		} catch (Exception e) {
			System.out.println("error in body processor: " + e.getMessage());
		}
	}
}