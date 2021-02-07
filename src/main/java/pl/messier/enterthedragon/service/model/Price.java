package pl.messier.enterthedragon.service.model;

import pl.messier.enterthedragon.service.constances.MomentBuy;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;

public class Price {
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double ohlc;
    private Double hlc;

    public Price(Double open, Double high, Double low, Double close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.ohlc = (open + high + low + close) / 4;
        this.hlc = (high + low + close) / 3;
    }

    public Double getOpen() {
        return open;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Double getClose() {
        return close;
    }

    public Double getOhlc() {
        return ohlc;
    }

    public Double getHlc() {
        return hlc;
    }

    /**
     * Returnes price for given moment MomentBuy
     * @param momentBuy enum MomentBuy
     * @throws EnterTheDragonException in case given moment does not exist
     */
    public Double get(MomentBuy momentBuy) throws EnterTheDragonException {
        switch (momentBuy) {
            case OPEN: return getOpen();
            case HIGH: return getHigh();
            case LOW: return getLow();
            case CLOSE: return getClose();
            case OHLC: return getOhlc();
            case HLC: return getHlc();
            default: throw new EnterTheDragonException("There is no such moment to buy: " + momentBuy);
        }
    }
}
