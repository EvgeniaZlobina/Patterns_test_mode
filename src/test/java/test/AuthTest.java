package test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static date.DateGenerator.Registration.getRegistredUser;
import static date.DateGenerator.Registration.getUser;
import static date.DateGenerator.getRandomLogin;
import static date.DateGenerator.getRandomPassword;
import static java.nio.channels.SocketChannel.open;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    private void open(String url) {
    }

    // позитивный тест
    @Test
    @DisplayName("Should successfulle login aith active registred user")
    void sholdSuccessfullloginifRegistredActiveUser() {
        var registredUser = getRegistredUser("active");
        $("[data-test-id='login'] input").setValue(registredUser.getLogin());
        $("[data-test-id='passowrd'] input").setValue(registredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    //негативный тест. Пользователь не зарегистирован
    @Test
    @DisplayName("should get error message if login with registred user")
    void shouldgeterrornotregistreduser() {
        var notregistredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notregistredUser.getLogin());
        $("[data-test-id='passowrd'] input").setValue(notregistredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification']. motification__comtent")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    // негативный тест пользователь заблокирован
    @Test
    @DisplayName("should get error message if with blocked registred user")
    void shoulderrorifblockeduser() {
        var blockedUser = getRegistredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='passowrd'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification']. motification__comtent")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    // негативный тест вход с неверным именем
    @Test
    @DisplayName("should get error message if login with wrong login")
    void shouldGeterrorifwronglogin() {
        var registredUser = getRegistredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='passowrd'] input").setValue(registredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification']. motification__comtent")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    // негативный тест вход с неверным паролем
    @Test
    @DisplayName("should get error message if password with wrong password")
    void shouldGeterrorifwrongpassword() {
        var registredUser = getRegistredUser("active");
        var wrongpassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registredUser.getLogin());
        $("[data-test-id='passowrd'] input").setValue(wrongpassword);
        $("button.button").click();
        $("[data-test-id='error-notification']. motification__comtent")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

}

