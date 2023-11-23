package com.ofss.processor;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import org.xml.sax.SAXException;

@Component
public class XmlProcessor {

	private static final String csvFilePath = "output.csv";
	private static final int numThreads = 5;

	public static void xmlToExcel(ArrayList<String> xmlDataList) {
		try (FileWriter writer = new FileWriter(csvFilePath);) {
			ExecutorService executor = Executors.newFixedThreadPool(numThreads);
			writer.write("EmployeeID, EmployeeName, DepartmentID, DepartmentName\n");
			xmlDataList.forEach(xmlData -> {
				executor.execute(() -> xmlParser(xmlData, writer));
			});

			executor.shutdown();
			while (!executor.isTerminated()) {
				// Wait for all threads to finish
			}
			writer.close();
			
			System.out.println("CSV file generated successfully.");
		} catch (IOException e) {
			System.out.println("Error in xmlToExcel: "+e.getMessage());
		}
	}

	public static void xmlParser(String xmlData, FileWriter writer) {
		try (InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes())) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);

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
			System.out.println("Error in xmlParser: "+e.getMessage());
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}
}
