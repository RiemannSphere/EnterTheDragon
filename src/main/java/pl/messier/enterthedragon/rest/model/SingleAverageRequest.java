package pl.messier.enterthedragon.rest.model;

import pl.messier.enterthedragon.service.constances.Interval;
import pl.messier.enterthedragon.service.constances.MomentBuy;

import java.util.GregorianCalendar;

public class SingleAverageRequest {
    private String tickerSymbol;
    private GregorianCalendar dayStart;
    private Interval interval;
    private Integer numOfPayments;
    private MomentBuy momentBuy;

    public SingleAverageRequest() {
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public GregorianCalendar getDayStart() {
        return dayStart;
    }

    public void setDayStart(GregorianCalendar dayStart) {
        this.dayStart = dayStart;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public Integer getNumOfPayments() {
        return numOfPayments;
    }

    public void setNumOfPayments(Integer numOfPayments) {
        this.numOfPayments = numOfPayments;
    }

    public MomentBuy getMomentBuy() {
        return momentBuy;
    }

    public void setMomentBuy(MomentBuy momentBuy) {
        this.momentBuy = momentBuy;
    }
}
