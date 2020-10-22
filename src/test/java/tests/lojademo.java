package tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class lojademo {

	WebDriver driver;
	WebDriverWait wait;

	@Before
	public void Begin() {
		System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 4);
		driver.navigate().to("http://165.227.93.41/lojinha-web/");
		driver.manage().window().maximize();
	}

	@After
	public void After() {
		driver.close();
	}

	public void login(String user, String password) {
		driver.findElement(By.xpath("//input[@id='usuario']")).sendKeys(user);
		driver.findElement(By.xpath("//input[@id='senha']")).sendKeys(password);
		driver.findElement(By.xpath("//button")).click();
	}

	@Test
	public void Login_Failed() {
		login("usuario_invalido", "password_invalido");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast")));
		WebElement toast = driver.findElement(By.className("toast"));

		if (toast.getText().contains("Falha")) {
			assertTrue("Passou", true);
		} else {
			assertTrue("Falhou", false);
		}
	}
	
	@Test
	public void Login_Success() {
		login("fazevedo", "1!Qqqq");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		WebElement h3 = driver.findElement(By.xpath("//h3"));
		
		if (h3.getText().contains("Produtos")) {
			assertTrue("Passou", true);
		} else {
			assertTrue("Falhou", false);
		}
	}
	
	@Test
	public void Add_Product() {
		login("fazevedo", "1!Qqqq");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		WebElement h3 = driver.findElement(By.xpath("//h3"));
		
		if (h3.getText().contains("Produtos")) {
			assertTrue("Passou", true);
		} else {
			assertTrue("Falhou", false);
		}
		
		driver.findElement(By.className("waves-effect")).click();
		driver.findElement(By.id("produtonome")).sendKeys("Nome do Produto");
		driver.findElement(By.id("produtovalor")).sendKeys("10,00");
		driver.findElement(By.id("produtocores")).sendKeys("branco,azul,preto,cinza");
		driver.findElement(By.xpath("//button")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Lista')]")).click();
		driver.findElement(By.xpath("//span/a[contains(text(),'Nome do Produto')]")).isDisplayed();
		driver.findElement(By.xpath("//ul/li/p[contains(text(),'10,00')]")).isDisplayed();
		driver.findElement(By.xpath("//ul/li/a/i[contains(text(),'delete')]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast")));
		WebElement toast = driver.findElement(By.className("toast"));

		if (toast.getText().contains("removido")) {
			assertTrue("Passou", true);
		} else {
			assertTrue("Falhou", false);
		}
		
		driver.findElement(By.xpath("//ul/li/a[text()='Sair']")).click();
	}

}
