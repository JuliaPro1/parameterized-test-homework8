import com.codeborne.selenide.Configuration;
import data.LitresTabs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class LitresParameterizedTests {
    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1920x1080";
    }

    @EnumSource(LitresTabs.class)
    @ParameterizedTest(name = "Проверка отображения таба {0}")
    @Tag("WEB_SMOKE")
    @DisplayName("Проверка отображения табов")
    void tabsShouldBeDisplayedTest(LitresTabs tab) {
        open("https://www.litres.ru/");
        $$("[data-testid='tab__link']").findBy(text(tab.title)).shouldBe(visible);
    }

    @ValueSource(strings = {
            "Java",
            "Автоматизация тестирования"
    }
    )
    @ParameterizedTest(name = "Для поискового запроса {0} должны получить не пустой список книг")
    @Tag("WEB_SMOKE")
    @DisplayName("Проверка получения не пустого списка книг")
    void searchResultsShouldNotBeEmptyTest(String searchQuery) {
        open("https://www.litres.ru/");
        $("[data-testid='search__input']").setValue(searchQuery).pressEnter();
        $$("[data-testid='search__content--wrapper']").shouldBe(sizeGreaterThan(0));
    }

    @CsvFileSource(resources = "/test_data/searchResultsShouldContainSpecificBook.csv")
    @ParameterizedTest(name = "Для поискового запроса {0} должны получить книгу {1}")
    @Tag("WEB_SMOKE")
    @DisplayName("Проверка получения конкретной книги")
    void searchResultsShouldContainSpecificBookTest(String authorWithTitle, String title) {
        open("https://www.litres.ru/");
        $("[data-testid='search__input']").setValue(authorWithTitle).pressEnter();
        $("[data-testid='search__content--wrapper']").shouldHave(text(title));
    }
}


