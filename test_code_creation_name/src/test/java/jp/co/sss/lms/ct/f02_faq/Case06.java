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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

		assertEquals("ログイン",
				title.getText());

		WebElement loginButton = webDriver.findElement(
				By.cssSelector(
						"input[type='submit']"));

		assertEquals("ログイン",
				loginButton.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		webDriver.findElement(
				By.id("loginId"))
				.sendKeys("StudentAA02");

		webDriver.findElement(
				By.id("password"))
				.sendKeys("864Catch");

		webDriver.findElement(
				By.cssSelector(
						"input[type='submit']"))
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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		// カテゴリ検索からカテゴリを選択
		WebElement categoryLink = webDriver.findElement(
				By.xpath(
						"//legend[normalize-space()='カテゴリ検索']"
								+ "/following-sibling::ul[1]//a"));

		categoryLink.click();

		// カテゴリ検索後のURLを確認
		assertTrue(
				webDriver.getCurrentUrl().contains(
						"frequentlyAskedQuestionCategoryId="));

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
		}

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		WebElement question = webDriver.findElement(
				By.cssSelector(
						"tbody tr dl dt"));

		WebElement answer = question.findElement(
				By.xpath(
						"./following-sibling::dd[1]"));

		// 質問をクリック
		question.click();

		// 回答が実際に表示されていることを確認
		assertTrue(answer.isDisplayed());

		// 回答内容が存在することを確認
		assertFalse(
				answer.getText().trim().isEmpty());

		getEvidence(new Object() {
		});
	}

}