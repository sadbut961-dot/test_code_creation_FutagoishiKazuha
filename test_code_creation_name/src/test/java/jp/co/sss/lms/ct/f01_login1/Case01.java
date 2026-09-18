package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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
		// テストNo.1 トップページURLにアクセス
		goTo("http://localhost:8080/lms");

		// テストNo.2 ログイン画面のタイトルを確認
		WebElement title = webDriver.findElement(By.tagName("h2"));
		assertEquals("ログイン", title.getText());

		// テストNo.3 ログインボタンを確認
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[type='submit']"));
		assertEquals("ログイン", loginButton.getAttribute("value"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}