import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		//Convetr csv -> json
		String[] columnMap = {"id", "firstName", "lastName", "country", "age"};
		List<Employee> listCSV = parseCSV(columnMap, "/Users/greddyd/IdeaProjects/ConvertFormat/src/data.csv");
		String jsonCsv = listToJson(listCSV);
		String resultFileCsv = "data.json";
		writeString(jsonCsv, resultFileCsv);

		//Convert xml - > json
		List<Employee> list = parseXML("/Users/greddyd/IdeaProjects/ConvertFormat/src/data.xml");
		String jsonXml = listToJson(list);
		String resultFileXml = "data2.json";
		writeString(jsonXml, resultFileXml);
	}

	public static List<Employee> parseCSV(String[] columnMap, String fileName){
		try (CSVReader readerFile = new CSVReader(new FileReader(fileName))){
			ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
			strategy.setType(Employee.class);
			strategy.setColumnMapping(columnMap);
			CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(readerFile)
							.withMappingStrategy(strategy)
							.build();
			List<Employee> dataCSV = csv.parse();
			return dataCSV;
	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static List<Employee> parseXML(String pathFile) throws ParserConfigurationException, IOException, SAXException {
		List<Employee> dataXml = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(pathFile));
		Node root = doc.getDocumentElement();
		System.out.println("Корневой элемент: " + root.getNodeName());
		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("Teкyщий элeмeнт: " + node.getNodeName());
				Element element = (Element) node;
				NodeList nlEmp = element.getChildNodes();
//				System.out.println("id: " + nlEmp.item(1).getTextContent() +
//								"\nfirstName: " + nlEmp.item(3).getTextContent() +
//								"\nlastName: " + nlEmp.item(5).getTextContent() +
//								"\ncountry: " + nlEmp.item(7).getTextContent() +
//								"\nage: " + nlEmp.item(9).getTextContent());
				Employee jEmp = new Employee(Long.parseLong(nlEmp.item(1).getTextContent()),
																		nlEmp.item(3).getTextContent(),
																		nlEmp.item(5).getTextContent(),
																		nlEmp.item(7).getTextContent(),
																		Integer.parseInt(nlEmp.item(9).getTextContent()));
				dataXml.add(jEmp);
			}
		}
		return dataXml;
	}

	public static String listToJson(List<Employee> list){
		Type listType = new TypeToken<List<Employee>>() {}.getType();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json = gson.toJson(list, listType);
		return json;
	}

	public static void writeString(String json, String resultFile){
		try (Writer writeJson = new FileWriter("/Users/greddyd/IdeaProjects/ConvertFormat/src/" + resultFile)){
			writeJson.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
