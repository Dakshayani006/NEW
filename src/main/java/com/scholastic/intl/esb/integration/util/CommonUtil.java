package com.scholastic.intl.esb.integration.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Base64;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.scholastic.intl.esb.integration.exception.BusinessException;


@Component
public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	public static final String INTERFACE_NAME="interfaceName";
	public static final String ENDPOINT="endpoint";
	public static final String CONTENT_TYPE="contentType";
	public static final String SOAP_ACTION="soapAction";
	public static final String APPLICATION_JSON="application/json";
	public static final String XML="xml";
	
	public static String getObjectAsJsonString(Object object) throws JsonProcessingException {
		logger.info("getPrettyPrintString ::::::");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(object);
	}
	public static <T> T getCastObjectFromJsonString(String jsonString, Class<T> type) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return type.cast(mapper.readValue(jsonString, type));
	}
	public static String getPrettyPrintString(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	}

	public static String getPrettyPrintString(String response)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		logger.info("getPrettyPrintString ::::::" + response);
		if (response.contains("<soapenv:Envelope")) {
			return getPrettyPrintStringForXml(response);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(objectMapper.readValue(response, Object.class));
	}
	
	public static String getPrettyPrintStringForXml(Object object) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        XmlMapper mapper = new XmlMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return getPrettyPrintStringForXml(mapper.writeValueAsString(object));
    }
	
	public static String getPrettyPrintStringForXml(String response)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(response.getBytes()));
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(doc), new StreamResult(out));
		return out.toString();
	}
	
	public static <T> String convertEnvelopeToXmlString(T envelope) throws JAXBException {
		String stringReq = "";
		JAXBContext jaxbContext = JAXBContext.newInstance(envelope.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper());
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(envelope, byteArrayOutputStream);
		stringReq = byteArrayOutputStream.toString();
		return stringReq;
	}
	
	public static String getObjectAsXmlString(Object object)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		logger.info("getPrettyPrintString ::::::");
		XmlMapper mapper = new XmlMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return getPrettyPrintStringForXml(mapper.writeValueAsString(object));
	}
	
	

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}

	public static boolean isNullOrEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNullOrEmpty(Object object) {
		return object == null;
	}

	public static boolean isValidAuthToken(String incomingToken,String validToken) throws BusinessException {
		boolean valid = false;
		String decodedToken = getDecodedAuthToken(incomingToken);
		logger.info("Validatin   "+decodedToken+"====="+validToken);
		if (decodedToken.equals(validToken)) {
			valid = true;
		} else
			throw new BusinessException("Authentication failure.");
		return valid;
	}

	public static String getDecodedAuthToken(String token) {
		return new String(Base64.getDecoder().decode(token.getBytes()));
	}
	public static String getEncodedAuthToken(String token) {
		return new String(Base64.getEncoder().encode(token.getBytes()));
	}

	public static String convertNullToEmpty(String string) {
		return (string == null ? "" : string);
	}
	public static String convertNullToEmpty(Object object) {
		return (object == null ? "" : String.valueOf(object));
	}
	public static String convertEmptyToNull(String string) {
		return ("".equals(string) ? null : string);
	}
	public static String convertEmptyToNull(Object object) {
		return (object == "" ? null : String.valueOf(object));
	}
	public static String convertToQuotedString(String string) {
		return (string == null ? "\"\"" : "\""+string+"\"");
	}
	public static String convertToAngleBracketString(String string) {
		return (string == null ? "><" : ">"+string+"<");
	}
	
	public static String extractXpathFromXml(String xml,XPathExpression successXp) throws JAXBException, SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		String xpathValue="";
		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
		document.getDocumentElement().normalize();
		xpathValue=successXp.evaluate(document);
		return xpathValue;
	}
	
	public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
