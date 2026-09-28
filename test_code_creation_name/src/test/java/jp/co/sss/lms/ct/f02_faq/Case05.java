package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms");

		WebElement title = webDriver.findElement(
				By.tagName("h2"));
		assertEquals("ログイン", title.getText());

		WebElement loginButton = webDriver.findElement(
				By.cssSelector("input[type='submit']"));
		assertEquals("ログイン",
				loginButton.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId"))
				.sendKeys("StudentAA02");

		webDriver.findElement(By.id("password"))
				.sendKeys("864Catch");

		webDriver.findElement(
				By.cssSelector("input[type='submit']"))
				.click();

		WebElement courseDetail = webDriver.findElement(
				By.cssSelector("li.active"));

		assertEquals("コース詳細",
				courseDetail.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		// 「機能」メニューを開く
		webDriver.findElement(By.xpath(
				"//li[contains(@class,'dropdown')][.//a[normalize-space()='ヘルプ']]"
						+ "//a[contains(@class,'dropdown-toggle')]"))
				.click();

		// 「ヘルプ」を押下
		webDriver.findElement(By.xpath(
				"//li[contains(@class,'dropdown')][.//a[normalize-space()='ヘルプ']]"
						+ "//ul//a[normalize-space()='ヘルプ']"))
				.click();

		WebElement title = webDriver.findElement(
				By.tagName("h2"));

		assertEquals("ヘルプ",
				title.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		String currentWindow = webDriver.getWindowHandle();

		webDriver.findElement(
				By.cssSelector("a[href$='/faq']"))
				.click();

		for (String window : webDriver.getWindowHandles()) {

			if (!window.equals(currentWindow)) {
				webDriver.switchTo().window(window);
				break;
			}
		}

		WebElement title = webDriver.findElement(
				By.tagName("h2"));

		assertEquals("よくある質問",
				title.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		// 表示されている質問から検索キーワードを取得
		WebElement question = webDriver.findElement(
				By.cssSelector("tbody tr dl dt"));

		String keyword = question.getText()
				.replaceFirst("^Q\\.\\s*", "")
				.trim();

		// キーワードを入力
		webDriver.findElement(By.id("form"))
				.sendKeys(keyword);

		// 検索ボタンを押下
		webDriver.findElement(
				By.cssSelector(
						"input[type='submit'][value='検索']"))
				.click();

		// 検索結果を取得
		List<WebElement> results = webDriver.findElements(
				By.cssSelector("tbody tr dl"));

		assertFalse(results.isEmpty());

		// 表示された検索結果の質問内容を確認
		for (WebElement result : results) {

			WebElement resultQuestion = result.findElement(
					By.tagName("dt"));

			// 検索結果の質問が表示されていること
			assertTrue(resultQuestion.isDisplayed());

			// 検索結果の質問内容が存在すること
			String questionText = resultQuestion.getText().trim();

			assertFalse(questionText.isEmpty());

			// 表示された質問に検索キーワードが含まれること
			assertTrue(questionText.contains(keyword));
		}

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {

		webDriver.findElement(
				By.cssSelector(
						"input[type='button'][value='クリア']"))
				.click();

		WebElement form = webDriver.findElement(By.id("form"));

		assertEquals("",
				form.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

}