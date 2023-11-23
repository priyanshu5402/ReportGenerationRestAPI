package com.ofss.processor;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Component
public class DataProcessor {

	public static void ToExcel(ArrayList<String> xmlDataList) {

		String csvFilePath = "output2.csv";

        try {
           // int numThreads = Math.min(xmlDataList.size(), Runtime.getRuntime().availableProcessors());
            int numThreads = 5;
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            // Create a CSV writer
            FileWriter writer = new FileWriter(csvFilePath);

            // Write CSV header
            writer.write("EmployeeID, EmployeeName, DepartmentID, DepartmentName\n");

            // Process the XML documents using multithreading
            for (String xmlData : xmlDataList) {
                executor.execute(() -> {
                    try {
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));

                        XPathFactory xPathFactory = XPathFactory.newInstance();
                        XPath xpath = xPathFactory.newXPath();

                        String employeeID = xpath.evaluate("/employee/id", document);
                        String employeeName = xpath.evaluate("/employee/name", document);
                        String departmentID = xpath.evaluate("/employee/dept/id", document);
                        String departmentName = xpath.evaluate("/employee/dept/name", document);

                        synchronized (writer) {
                            writer.write(employeeID + ", " + employeeName + ", " + departmentID + ", " + departmentName + "\n");
                        }
                    } catch (ParserConfigurationException | SAXException | IOException e) {
                        e.printStackTrace();
                    } catch (XPathExpressionException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Wait for all threads to finish
            }

            writer.close();
            System.out.println("CSV file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
