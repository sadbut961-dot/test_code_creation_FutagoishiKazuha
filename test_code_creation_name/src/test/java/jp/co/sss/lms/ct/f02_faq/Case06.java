package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
				By.cssSelector("h2"));

		WebElement loginButton = webDriver.findElement(
				By.cssSelector("input[type='submit']"));

		assertEquals("ログイン", title.getText());
		assertEquals("ログイン", loginButton.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		WebElement userId = webDriver.findElement(
				By.name("loginId"));

		WebElement password = webDriver.findElement(
				By.name("password"));

		WebElement loginButton = webDriver.findElement(
				By.cssSelector("input[type='submit']"));

		userId.sendKeys("StudentAA02");
		password.sendKeys("864Catch");
		loginButton.click();

		WebElement activeMenu = webDriver.findElement(
				By.cssSelector("li.active"));

		assertEquals("コース詳細", activeMenu.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		WebElement functionMenu = webDriver.findElement(
				By.xpath("//a[normalize-space()='機能']"));

		functionMenu.click();

		WebElement helpLink = webDriver.findElement(
				By.xpath("//a[normalize-space()='ヘルプ']"));

		helpLink.click();

		WebElement title = webDriver.findElement(
				By.cssSelector("h2"));

		assertEquals("ヘルプ", title.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		String originalWindow = webDriver.getWindowHandle();

		WebElement faqLink = webDriver.findElement(
				By.cssSelector("a[href$='/faq']"));

		faqLink.click();

		Set<String> windowHandles = webDriver.getWindowHandles();

		for (String windowHandle : windowHandles) {
			if (!windowHandle.equals(originalWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		WebElement title = webDriver.findElement(
				By.cssSelector("h2"));

		assertEquals("よくある質問", title.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		// カテゴリ検索の最初のカテゴリを選択
		WebElement categoryLink = webDriver.findElement(
				By.xpath("//legend[normalize-space()='カテゴリ検索']"
						+ "/following-sibling::ul[1]//a"));

		categoryLink.click();

		// カテゴリ検索後のURLになっていることを確認
		assertTrue(
				webDriver.getCurrentUrl()
						.contains("frequentlyAskedQuestionCategoryId="));

		// 検索結果を取得
		List<WebElement> results = webDriver.findElements(
				By.cssSelector("tbody tr"));

		// カテゴリに該当する検索結果が存在することを確認
		assertFalse(results.isEmpty());

		// 期待する質問内容
		Set<String> expectedQuestions = new HashSet<>();

		expectedQuestions.add("キャンセル料・途中退校について");
		expectedQuestions.add("研修の申し込みはどのようにすれば良いですか？");

		// 実際の質問内容を取得
		Set<String> actualQuestions = new HashSet<>();

		for (WebElement row : results) {

			// 「Q.」ではなく質問本文だけを取得
			WebElement question = row.findElement(
					By.cssSelector("dl dt span:nth-of-type(2)"));

			assertTrue(question.isDisplayed());

			actualQuestions.add(question.getText());
		}

		// 件数が期待値と一致することを確認
		assertEquals(
				expectedQuestions.size(),
				actualQuestions.size());

		// 検索結果の質問内容が期待値と一致することを確認
		assertEquals(
				expectedQuestions,
				actualQuestions);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		WebElement question = webDriver.findElement(
				By.cssSelector("tbody tr dl dt"));

		WebElement answer = question.findElement(
				By.xpath("./following-sibling::dd[1]"));

		question.click();

		assertTrue(answer.isDisplayed());

		getEvidence(new Object() {
		});
	}

}