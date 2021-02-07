package pl.messier.enterthedragon.service.model;

import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

public class Stock {

    private String tickerSymbol;
    private LinkedHashMap<GregorianCalendar, Price> timePrice;

    public Stock(String ticketSymbol, LinkedHashMap<GregorianCalendar, Price> timePrice) {
        this.tickerSymbol = ticketSymbol;
        this.timePrice = timePrice;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public LinkedHashMap<GregorianCalendar, Price> getTimePrice() {
        return timePrice;
    }
}
