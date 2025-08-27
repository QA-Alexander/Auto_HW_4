package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));

    }


    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");

    }

    @Test
    void shouldSendForm() {
        String planningDate = generateDate(4, "dd.MM.yyyy");
        SelenideElement form = $("form");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE))
                .setValue(planningDate);
        form.$("[data-test-id='name'] input").setValue("Иванов Иван");
        form.$("[data-test-id='phone'] input").setValue("+79990009999");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}
