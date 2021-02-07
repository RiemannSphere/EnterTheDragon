package pl.messier.enterthedragon.service.model;

import java.util.GregorianCalendar;

public final class TimePrice {
    private GregorianCalendar time;
    private Price price;

    public TimePrice(GregorianCalendar time, Price price) {
        this.time = time;
        this.price = price;
    }

    public GregorianCalendar getTime() {
        return time;
    }

    public Price getPrice() {
        return price;
    }

    public static TimePrice empty() {
        return new TimePrice(null, null);
    }

    public boolean isEmpty() {
        return time == null || price == null;
    }
}
