package data;

public enum LitresTabs {
    BONUSES("Бонусы"),
    CART("Корзина"),
    MY_BOOKS("Мои книги"),
    LOGIN("Войти");

    public final String title;

    LitresTabs(String title) {
        this.title = title;
    }
}
