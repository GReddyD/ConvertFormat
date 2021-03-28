import com.opencsv.CSVWriter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MainTest {
	private static String csv = "./src/test/resources/test.csv";
	private static String xml = "./src/test/resources/data.xml";
	private static String [] columnMap = {"id", "firstName", "lastName", "country", "age"};
	private static List<Employee> listCheack= new ArrayList<>();
	private static String jsonCheack = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";

	@BeforeAll
	//Генерируем CSV File для теста
	public static void genFile(){
		File testFile = new File(csv);
		try {
			if (testFile.createNewFile()) {
				System.out.println("Файл для теста создан");
			}

			FileWriter output = new FileWriter(testFile);
			CSVWriter write = new CSVWriter(output);

			// Value
			List<String[]> testData = new ArrayList<>();
			testData.add(new String[]{"1", "John", "Smith", "USA", "25"});
		  testData.add(new String[]{"2", "Inav", "Petrov", "RU", "23"});
			write.writeAll(testData);
			write.close();

			System.out.println("Тестовые данные записаны в файл");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeAll
	//Формируем объект List(Employee) для сравнения в тесте
	public static void genListCheack(){
		listCheack.add(new Employee(1L, "John", "Smith", "USA", 25));
		listCheack.add(new Employee(2L, "Inav", "Petrov", "RU", 23));
	}

	@Test
	//Тестируем метод чтения CSV
	public void testParseCsvMetod_success() {
		// given:
		final String pathCsv = csv;

		// when:
		final List<Employee> dataCsv = Main.parseCSV(columnMap, pathCsv);

		// then:
		System.out.println("Проверка корректности чтения данных из CSV. Файл не пустой");
		Assert.assertNotNull(dataCsv);
		System.out.println("Проверка корректности List при чтении CSV");
		Assert.assertEquals(dataCsv, listCheack);
	}

	@Test
	//Тестируем запись JSON в файл
	public void testListToJsonMetod_success() {
		// given:
		final String pathCsv = csv;
		final List<Employee> dataCsv = Main.parseCSV(columnMap, pathCsv);

		// when:
		System.out.println("Вызывем метод записи данных из CSV в JSON");
		final String json = Main.listToJson(dataCsv);

		// then:
		System.out.println("Проверка корректности чтения данных из CSV для JSON");
		Assert.assertNotNull(json);
		System.out.println("JSON корректный");
		Assert.assertEquals(json, jsonCheack);
	}

	@Test
	//Метод для
	public void testParseXmlMetod_success() throws IOException, SAXException, ParserConfigurationException {
		// given:
		final String pathXml = xml;

		// when:
		System.out.println("Вызывае метод для чтения XML");
		final List<Employee> dataXml = Main.parseXML(pathXml);

		// then:
		System.out.println("Проверка корректности чтения данных из XML. Файл не пустой");
		Assert.assertNotNull(dataXml);
		System.out.println("Проверка корректности List при чтении XML");
		Assert.assertEquals(dataXml, listCheack);
	}

}
