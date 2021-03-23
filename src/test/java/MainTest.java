import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static java.nio.file.Files.writeString;


public class MainTest {

	@Test
	public void testParseCsvMetod_success() {
		// given:
		final String pathCsv = "/Users/greddyd/IdeaProjects/ConvertFormat/src/data.csv";
		final String[] columnMap = {"id", "firstName", "lastName", "country", "age"};

		// when:
		System.out.println("Проверка корректности чтения данных из CSV");
		final List<Employee> dataCsv = Main.parseCSV(columnMap, pathCsv);

		// then:
		Assert.assertNotNull(dataCsv);
	}

	@Test
	public void testListToJsonMetod_success() {
		// given:
		final String pathCsv = "/Users/greddyd/IdeaProjects/ConvertFormat/src/data.csv";
		final String[] columnMap = {"id", "firstName", "lastName", "country", "age"};
		final List<Employee> dataCsv = Main.parseCSV(columnMap, pathCsv);

		// when:
		System.out.println("Проверяем корректность записи данных из CSV в JSON");
		final String json = Main.listToJson(dataCsv);

		// then:
		Assert.assertNotNull(json);
	}

	@Test
	public void testParseXmlMetod_success() throws IOException, SAXException, ParserConfigurationException {
		// given:
		final String pathXml = "/Users/greddyd/IdeaProjects/ConvertFormat/src/data.xml";

		// when:
		System.out.println("Проверка корректности чтения данных из XML");
		final List<Employee> dataXml = Main.parseXML(pathXml);

		// then:
		Assert.assertNotNull(dataXml);
	}

}
