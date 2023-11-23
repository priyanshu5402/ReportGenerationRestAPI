package com.ofss.processor;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BodyProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		List<Object> arrayList = new ArrayList<>();
		ArrayList<String> list = new ArrayList<String>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

		arrayList = exchange.getIn().getBody(ArrayList.class);
		for (int i = 0; i < arrayList.size(); i++) {
			map = (LinkedHashMap<Integer, String>) arrayList.get(i);
			for (Map.Entry<Integer, String> entry : map.entrySet()) {
				String value = entry.getValue();
				System.out.println("Value: " + value);
				list.add(value);
			}
		}
		DataProcessor.ToExcel(list);
	}
}