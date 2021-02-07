package pl.messier.enterthedragon.service.calc;

import pl.messier.enterthedragon.service.constances.Config;
import pl.messier.enterthedragon.service.constances.Interval;
import pl.messier.enterthedragon.service.constances.MomentBuy;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;
import pl.messier.enterthedragon.service.model.Price;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Callable;

public class SingleAverageCalculator {


    /**
     * Calculate average stock price under the assumption we start investing in dayStart and buy 1 stock every interval.
     * The convention for intervals is that 1M=4W and 1Y=48W in order to simplify calculations.
     * Doing it this way we make sure every next day will be the same day of the week ex. only Mondays.
     * Now we make sure we will not stumble upon a weekend.
     * To ake sure me will not stumble upon a holiday there is a convention:
     * if there is no price for given day, take next closest day in the future that has price.
     * There is no parametrization for it as we assume it does not impact much the long-term strategy.
     * @param timePrice series of data, pricing for every day
     * @param dayStart
     * @param interval Interval ex. 1D, 1W, 1M etc.
     * @param numOfPayments
     * @param momentBuy MomentBuy ex. open, high, low, close, OHLC, HLC etc.
     * @return
     */
    public Callable<Double> calculateAverageSharePrice(
            LinkedHashMap<String, Price> timePrice,
            String dayStart,
            Interval interval,
            Integer numOfPayments,
            MomentBuy momentBuy) {
        return () -> {
            if(timePrice == null || timePrice.isEmpty() || timePrice.containsKey(null) || timePrice.containsValue(null))
                throw new EnterTheDragonException("Data is null, empty or contains nulls.");
            if(timePrice.get(dayStart) == null){
                throw new EnterTheDragonException("There is no price for the date: " + dayStart);
            }
            if(dayStart == null)
                throw new EnterTheDragonException("Day start is null.");
            if(!Arrays.asList(Interval.values()).contains(interval))
                throw new EnterTheDragonException("Such interval has not been declared: " + interval);
            if(numOfPayments == null || numOfPayments < 1)
                throw new EnterTheDragonException("Number of payments cannot be null or less than 1");
            if(!Arrays.asList(MomentBuy.values()).contains(momentBuy))
                throw new EnterTheDragonException("Such moment to buy has not been declared: " + momentBuy);

            LocalDate dayStartDate = LocalDate.parse(dayStart);
            Double priceSum = 0d;
            for(int i=0; i!=numOfPayments; i++) {
                Price nextBuyPrice = timePrice.get(dayStartDate.format(Config.INTERNAL_DATE_FORMATTER));
                if (nextBuyPrice == null) {
                    LocalDate nextDayToTry = dayStartDate;
                    while (nextBuyPrice == null) {
                        nextDayToTry = nextDayToTry.plusDays(1);
                        nextBuyPrice = timePrice.get(nextDayToTry.format(Config.INTERNAL_DATE_FORMATTER));
                    }
                }
                if(nextBuyPrice == null) {
                    throw new EnterTheDragonException("There is no price for the date: " +
                            dayStartDate.format(Config.INTERNAL_DATE_FORMATTER));
                }
                priceSum += nextBuyPrice.get(momentBuy);
                switch (interval) {
                    // TODO D1 is problematic for now, need to code it later
                    case W1:
                        dayStartDate = dayStartDate.plusDays(7);
                        break;
                    case W2:
                        dayStartDate = dayStartDate.plusDays(2*7);
                        break;
                    case M1:
                        dayStartDate = dayStartDate.plusDays(4*7);
                        break;
                    case M2:
                        dayStartDate = dayStartDate.plusDays(2*4*7);
                        break;
                    case M3:
                        dayStartDate = dayStartDate.plusDays(3*4*7);
                        break;
                    case M4:
                        dayStartDate = dayStartDate.plusDays(4*4*7);
                        break;
                    case M6:
                        dayStartDate = dayStartDate.plusDays(6*4*7);
                        break;
                    case Y1:
                        dayStartDate = dayStartDate.plusDays(12*4*7);
                        break;
                    default:
                        throw new EnterTheDragonException("Such interval " + interval + " is not defined.");
                }
            }
            Double average = priceSum / numOfPayments;
            return average;
        };
    }

}
