package StepDefinition;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {
	int cont;
	String resultadoLink;
	int contador = 0;
	private  ChromeDriver driver;
    private static ExtentTest extentTest;
    private static ExtentReports report ;
    private static ExtentTest test;
    private String nombreDePrueba = "CF_TEST";
    private String autorTest = "Federico Gregorio";

    WebElement searchbox;
    List<WebElement> listResults;
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    By resultsLocator = By.id("result-stats");
    By By_searchButton = By.className("gNO89b");
    By results = By.className("yuRUbf");
    By listSuggestion = By.className("UUbT9");
    By elementsSuggestion = By.className("sbct");    

  
    @Given("^I am on the homepage \"([^\"]*)\" \"([^\"]*)\"$")
    public void i_am_on_the_homepage(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
        this.cargarReporte(arg1);
        this.openWebDriver();
        this.cont = Integer.parseInt(arg2);
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		//valido que el titulo sea Bienvenidos a MercadoLibre
		String title = driver.getTitle();
		if(title.equals("Google")) {
			//saco captura
			test.log(LogStatus.PASS, "Se ingreso a Google");
			capturaPantalla("step"+cont);
			this.cont++;
		}
		
    }

    @When("^I type 'The name of the wind' into the search field$")
    public void i_type_The_name_of_the_wind_into_the_search_field() throws Throwable {
		String input = "The name of the wind";
		typeSearchField(input);
    }
    
    @When("^I type 'The name of the w' into the search field$")
    public void i_type_The_name_of_the_w_into_the_search_field() throws Throwable {
		String input = "The name of the w";
		typeSearchField(input);
    }
    
    private void typeSearchField(String input) throws HeadlessException, AWTException, IOException {
    	this.searchbox = driver.findElement(By.name("q"));
		//limpia cualquier cosa que este en el box
		searchbox.clear();
		//escribe sobre el box
		searchbox.sendKeys(input);
		test.log(LogStatus.PASS, "Se ingresa el input en el buscador");
		capturaPantalla("step"+cont);
		this.cont++;
    }
    
    @When("^I click the Google Search button$")
    public void i_click_the_Google_Search_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions.
    	WebElement searchbutton = driver.findElement(By_searchButton);
    	searchbutton.click();
    }

    @Then("^I go to the search results page$")
    public void i_go_to_the_search_results_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	if(driver.findElement(resultsLocator).isDisplayed()) {
    		test.log(LogStatus.PASS, "Se ingreso a la busqueda");
    		capturaPantalla("step"+cont);
    		this.cont++;
    		listResults = driver.findElements(results);
        	String aux = listResults.get(0).getText();
        	String[] arrOfStr = aux.split("\n");
        	resultadoLink = arrOfStr[0];
        	
    	}
    }

    @When("^I click on the first result link$")
    public void i_click_on_the_first_result_link() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	listResults.get(0).click();
    }

    @Then("^the first result is 'The Name of the Wind - Patrick Rothfuss'$")
    public void the_first_result_is_The_Name_of_the_Wind_Patrick_Rothfuss() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	this.checkFirstLink("The Books - Patrick Rothfuss");
    }
    
	@Then("^the first result is 'El nombre del viento - Wikipedia, la enciclopedia libre'$")
    public void the_first_result_is_El_nombre_del_viento_Wikipedia_la_enciclopedia_libre() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	this.checkFirstLink("El nombre del viento - Wikipedia, la enciclopedia libre");
    }

    
    private void checkFirstLink(String input) throws HeadlessException, AWTException, IOException {
        if(resultadoLink.equals(input)) {
    		test.log(LogStatus.PASS, "El primer resultado de la busqueda es el CORRECTO");
    		capturaPantalla("step"+cont);
    		this.cont++;
        }else {
        	test.log(LogStatus.FAIL, "El primer resultado de la busqueda es INCORRECTO");
    		capturaPantalla("step"+cont);
    		this.cont++;
        }
    }

    @Then("^I go to the 'Patrick Rothfuss - The Books' page$")
    public void i_go_to_the_Patrick_Rothfuss_The_Books_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	
    	this.go_to_the_correct_page("The Books - Patrick Rothfuss");
    
    }
    
    @Then("^I go to the 'Wikipedia' page$")
    public void i_go_to_the_Wikipedia_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	
    	this.go_to_the_correct_page("El nombre del viento - Wikipedia, la enciclopedia libre");
    
    }

    private void go_to_the_correct_page(String input) throws AWTException, IOException {
		if(driver.getTitle().equals(input)) {
			//saco captura
			test.log(LogStatus.PASS, "Se ingreso a la pagina correcta");
			capturaPantalla("step"+cont);
    		this.cont++;
		}
		else {
			this.searchCorrectLink(input);
			
		}
		this.generarReporte();
		//driver.close();
    }
	
    private void generarReporte() {
		// TODO Auto-generated method stub
    	report.endTest(extentTest);
    	extentTest.log(LogStatus.INFO, getClass().getName() + " -- Finaliza");
        report.flush();
	}




    @When("^the suggestions list is displayed$")
    public void the_suggestions_list_is_displayed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	if(driver.findElement(listSuggestion).isDisplayed()) {
    		test.log(LogStatus.PASS, "La lista de sugerencias esta activa");
    		capturaPantalla("step"+cont);
    		this.cont++;
    	}
    	else {
    		test.log(LogStatus.FAIL, "La lista de sugerencias no esta activa");
    	}
    }

    @When("^I click on the first suggestion in the list$")
    public void i_click_on_the_first_suggestion_in_the_list() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	List<WebElement> listResults = driver.findElements(elementsSuggestion);
    	listResults.get(0).click();
    }

        
    
	private void capturaPantalla(String nombre) throws HeadlessException, AWTException, IOException {
		//nombre del archivo
		String nombreArchivo=nombre+".jpg";
		//ruta del archivo
		String rutaArchivo= "C:\\Users\\federico.gregorio\\Desktop\\capturas\\"+nombreArchivo;
	    BufferedImage captura = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );

	    // Guardar Como JPG
	    File file = new File(rutaArchivo);
	    ImageIO.write(captura, "jpg", file);	    
	}
	
	public void cargarReporte(String data) {
		String dateNow = LocalDateTime.now().toString().substring(0, 19).replace('.', '-').replace('T', '(').replace(':', '-')+ ")";
        //Nombre del archivo del reporte con la direccion del test
        report = new ExtentReports("C:\\Users\\federico.gregorio\\Desktop\\Reportes\\reporteScenario"+data+".html");

        //Configuracion del reporte
        report.loadConfig(new File(System.getProperty("user.dir")+ "\\extent-config.xml"));
        //Nombre del test
        extentTest = report.startTest(nombreDePrueba);
        //Autor del test
        extentTest.assignAuthor(autorTest);
        //Descripcion del test
        extentTest.setDescription("Horarios de inicio y finalizacion de los test");
		
    	
        test = report.startTest(getClass().getName());
        test.assignAuthor(autorTest);
        test.setDescription(getClass().getName());
        extentTest.log(LogStatus.INFO, getClass().getName() + " --Inicia");
		
	}
	
	
	public void openWebDriver() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\federico.gregorio\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String urlGoogle = "https://www.google.com/";
		driver.get(urlGoogle);
	}
	
	private void searchCorrectLink(String input) throws HeadlessException, AWTException, IOException {
		driver.navigate().back();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int j=1;
		List<WebElement> list = driver.findElements(results);
		int i = 0;
    	while(i<list.size()) {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    		System.out.println(i);
	    	String aux = list.get(i).getText();
	    	String[] arrOfStr = aux.split("\n");
    		int pixeles = j*28;
			String scroll = "window.scrollBy(0,"+pixeles+")";
			j++;
			js.executeScript(scroll);
	    	if(arrOfStr[0].equals(input)) {
	    		test.log(LogStatus.PASS, "Se encontro el link dentro de la busqueda");
	    		capturaPantalla("step"+cont);
	    		this.cont++;
	    		list.get(i).click();

	    		test.log(LogStatus.PASS, "Se accede al link encontrado");
	    		capturaPantalla("step"+cont);
	    		this.cont++;
	    		i = list.size();
	    	}
	    	else {
	    		i++;
	    	}
    	}
	}
}
