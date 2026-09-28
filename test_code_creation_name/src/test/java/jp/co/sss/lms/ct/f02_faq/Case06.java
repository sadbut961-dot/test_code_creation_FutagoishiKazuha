package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
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
import org.openqa.selenium.support.ui.WebDriverWait;

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

		// トップページへアクセス
		goTo("http://localhost:8080/lms");

		// ログイン画面のタイトルを取得
		WebElement title = webDriver.findElement(By.tagName("h2"));

		// タイトルの期待値確認
		assertEquals("ログイン", title.getText());

		// ログインボタンを取得
		WebElement loginButton = webDriver.findElement(
				By.cssSelector("input[type='submit']"));

		// ログインボタンの期待値確認
		assertEquals("ログイン", loginButton.getAttribute("value"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		// ログインIDを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");

		// パスワードを入力
		webDriver.findElement(By.id("password")).sendKeys("864Catch");

		// ログインボタンを押下
		webDriver.findElement(
				By.cssSelector("input[type='submit']")).click();

		// コース詳細画面のパンくずを取得
		WebElement courseDetail = webDriver.findElement(
				By.cssSelector("li.active"));

		// 期待値確認
		assertEquals("コース詳細", courseDetail.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		// 「機能」メニューを開く
		webDriver.findElement(By.xpath(
				"//a[contains(@class,'dropdown-toggle') and contains(normalize-space(.),'機能')]"))
				.click();

		// 「ヘルプ」を押下
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// ヘルプ画面のタイトルを取得
		WebElement title = webDriver.findElement(By.tagName("h2"));

		// 期待値確認
		assertEquals("ヘルプ", title.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		// 現在のタブを取得
		String currentWindow = webDriver.getWindowHandle();

		// 現在のタブ数を取得
		int windowCount = webDriver.getWindowHandles().size();

		// 「よくある質問」を押下
		webDriver.findElement(By.linkText("よくある質問")).click();

		// 新しいタブが開くまで待機
		WebDriverWait wait = new WebDriverWait(
				webDriver, Duration.ofSeconds(5));

		wait.until(driver -> driver.getWindowHandles().size() > windowCount);

		// 新しいタブへ切り替え
		Set<String> windowHandles = webDriver.getWindowHandles();

		for (String windowHandle : windowHandles) {
			if (!windowHandle.equals(currentWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		// よくある質問画面のタイトルを取得
		WebElement title = webDriver.findElement(By.tagName("h2"));

		// 期待値確認
		assertEquals("よくある質問", title.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		// カテゴリ検索の最初のカテゴリを取得
		WebElement categoryLink = webDriver.findElement(By.xpath(
				"//legend[normalize-space()='カテゴリ検索']/following::ul[1]//a"));

		// カテゴリリンクのURLを取得
		String categoryUrl = categoryLink.getAttribute("href");

		// カテゴリを選択
		categoryLink.click();

		// 画面遷移完了を待機
		WebDriverWait wait = new WebDriverWait(
				webDriver, Duration.ofSeconds(5));

		wait.until(driver -> driver.getCurrentUrl().contains(
				"frequentlyAskedQuestionCategoryId="));

		// 選択したカテゴリのURLへ遷移していることを確認
		assertTrue(webDriver.getCurrentUrl().contains(
				"frequentlyAskedQuestionCategoryId="));

		// 検索結果を取得
		List<WebElement> results = webDriver.findElements(
				By.cssSelector("tbody tr dl"));

		// カテゴリに該当する検索結果が表示されていることを確認
		assertTrue(results.size() > 0);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		// 検索結果の最初の質問を取得
		WebElement question = webDriver.findElement(
				By.cssSelector("tbody tr dl dt"));

		// 対応する回答を取得
		WebElement answer = webDriver.findElement(
				By.cssSelector("tbody tr dl dd"));

		// 質問をクリック
		question.click();

		// 回答が表示されていることを確認
		assertEquals("fs18", answer.getAttribute("class"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}