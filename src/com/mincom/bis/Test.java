package com.mincom.bis;

import java.io.FileReader;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.payload.JavaResult;
import org.xml.sax.SAXException;

import example.model.Order;

public class Test {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, SAXException {
		Smooks smooks = new Smooks("SampleToXML.xml");
		try{
			StreamSource source = new StreamSource(new FileReader("E:\\temp\\inputfile\\input-message.edi"));
			JavaResult result = new JavaResult();

			ExecutionContext execContext = smooks.createExecutionContext();
			execContext.setEventListener(new HtmlReportGenerator("/tmp/smooks-report.html"));
			smooks.filterSource(execContext, source, result);
			Order aOrder = (Order) result.getBean("order");
			System.out.println("Order Id: " + aOrder.getHeader().getOrderId());
			System.out.println("First Name: " + aOrder.getHeader().getCustomer().getFirstName());
			System.out.println("Net Amount: " + aOrder.getHeader().getNetAmount());
		} 
		finally 
		{   
			smooks.close();
		}
	}
}
