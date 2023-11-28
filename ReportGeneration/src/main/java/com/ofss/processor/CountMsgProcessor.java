package com.ofss.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CountMsgProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		List<LinkedHashMap<Integer, Integer>> countList = exchange.getIn().getBody(ArrayList.class);
		List<Integer> list = extractValues(countList);

		System.out.println("Incomig messages:- " + list.get(0));
		System.out.println("outgoing messages:- " + list.get(1));
	}
	
	private List<Integer> extractValues(List<LinkedHashMap<Integer, Integer>> countList) {
        List<Integer> list = new ArrayList<>();
        for (LinkedHashMap<Integer, Integer> linkedHashMap : countList) {
            for (Map.Entry<Integer, Integer> entry : linkedHashMap.entrySet()) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

}
