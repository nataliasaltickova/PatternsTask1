import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

class DeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and reschedule meeting")
    void shouldSuccessfulPlanAndRescheduleMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $x("//*[@placeholder=\"Город\"]").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("// input [@placeholder ='Дата встречи']").setValue(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $x("//label [contains (@class,\"checkbox_size_m\")]").click();
        $x("//span [text() =\"Запланировать\"]").click();
        $("[class= notification__title]").shouldBe(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[class= notification__content]").shouldBe(Condition.text("Встреча успешно запланирована на "), Condition.text(DataGenerator.generateDate(daysToAddForFirstMeeting)));

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("// input [@placeholder ='Дата встречи']").setValue(DataGenerator.generateDate(daysToAddForSecondMeeting));
        $x("//span [text() =\"Запланировать\"]").click();
        $x("//span [text() =\"Перепланировать\"]").click();
        $("[class= notification__title]").shouldBe(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[class= notification__content]").shouldBe(Condition.text("Встреча успешно запланирована на "), Condition.text(DataGenerator.generateDate(daysToAddForSecondMeeting)));
    }
}




