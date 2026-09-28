package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	@BeforeAll
	static void before() {
		createDriver();
	}

	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms");

		WebElement title = webDriver.findElement(By.tagName("h2"));
		assertEquals("ログイン", title.getText());

		WebElement loginButton = webDriver.findElement(
				By.cssSelector("input[type='submit']"));
		assertEquals("ログイン", loginButton.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");
		webDriver.findElement(By.id("password")).sendKeys("864Catch");

		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		WebElement courseDetail = webDriver.findElement(
				By.cssSelector("li.active"));
		assertEquals("コース詳細", courseDetail.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		WebElement detailButton = webDriver.findElement(By.xpath(
				"//tr[.//span[normalize-space()='未提出']]"
						+ "//input[@type='submit' and @value='詳細']"));

		detailButton.click();

		WebElement title = webDriver.findElement(
				By.cssSelector("li.active"));
		assertEquals("セクション詳細", title.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		WebElement submitButton = webDriver.findElement(
				By.cssSelector("input[type='submit'][value$='を提出する']"));

		submitButton.click();

		WebElement title = webDriver.findElement(By.tagName("h2"));
		assertTrue(title.getText().contains("日報"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		List<WebElement> textareas = webDriver.findElements(
				By.cssSelector("textarea[id^='content_']"));

		for (WebElement textarea : textareas) {
			String id = textarea.getAttribute("id");
			String index = id.substring("content_".length());

			String inputType = webDriver.findElement(
					By.id("type_" + index)).getAttribute("value");

			if ("0".equals(inputType)) {
				String rangeFrom = webDriver.findElement(
						By.id("rangeFrom_" + index)).getAttribute("value");

				textarea.sendKeys(rangeFrom);
			} else {
				textarea.sendKeys("テスト内容");
			}
		}

		webDriver.findElement(
				By.cssSelector("button[type='submit']")).click();

		WebDriverWait wait = new WebDriverWait(
				webDriver, Duration.ofSeconds(5));

		wait.until(driver -> driver.getCurrentUrl().contains("/section/detail"));

		WebElement checkButton = webDriver.findElement(By.xpath(
				"//input[@type='submit' "
						+ "and contains(@value,'提出済み') "
						+ "and contains(@value,'確認する')]"));

		assertTrue(checkButton.getAttribute("value").contains("提出済み"));
		assertTrue(checkButton.getAttribute("value").contains("確認する"));

		getEvidence(new Object() {
		});
	}
}