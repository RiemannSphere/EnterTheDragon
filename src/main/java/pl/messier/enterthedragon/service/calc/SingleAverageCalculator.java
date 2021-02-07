package pl.messier.enterthedragon.service.calc;

import pl.messier.enterthedragon.service.constances.Interval;
import pl.messier.enterthedragon.service.constances.MomentBuy;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;
import pl.messier.enterthedragon.service.model.Price;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
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
            LinkedHashMap<GregorianCalendar, Price> timePrice,
            GregorianCalendar dayStart,
            Interval interval,
            Integer numOfPayments,
            MomentBuy momentBuy) {
        return () -> {
            if(timePrice == null || timePrice.isEmpty() || timePrice.containsKey(null) || timePrice.containsValue(null))
                throw new EnterTheDragonException("Data is null, empty or contains nulls.");
            if(timePrice.get(dayStart) == null){
                timePrice.forEach((time, price) -> {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(time.getTime()) + ", " + price.getOpen());
                });
                String dayStartStr = new SimpleDateFormat("yyyy-MM-dd").format(dayStart.getTime());
                throw new EnterTheDragonException("There is no price for the date: " + dayStartStr);
            }
            if(dayStart == null)
                throw new EnterTheDragonException("Day start is null.");
            if(!Arrays.asList(Interval.values()).contains(interval))
                throw new EnterTheDragonException("Such interval has not been declared: " + interval);
            if(numOfPayments == null || numOfPayments < 1)
                throw new EnterTheDragonException("Number of payments cannot be null or less than 1");
            if(!Arrays.asList(MomentBuy.values()).contains(momentBuy))
                throw new EnterTheDragonException("Such moment to buy has not been declared: " + momentBuy);

            Double priceSum = 0d;
            for(int i=0; i!=numOfPayments; i++) {
                Price nextBuyPrice = timePrice.get(dayStart);
                if (nextBuyPrice == null) {
                    GregorianCalendar nextDayToTry = (GregorianCalendar) dayStart.clone();
                    while (nextBuyPrice == null) {
                        nextDayToTry.add(Calendar.DAY_OF_MONTH, 1);
                        nextBuyPrice = timePrice.get(nextDayToTry);
                    }
                }
                if(nextBuyPrice == null) {
                    String dayStartStr = new SimpleDateFormat("yyyy-MM-dd").format(dayStart.getTime());
                    throw new EnterTheDragonException("There is no price for the date: " + dayStartStr);
                }
                priceSum += nextBuyPrice.get(momentBuy);
                switch (interval) {
                    // TODO D1 is problematic for now, need to code it later
                    /*case D1: dayStart.add(Calendar.DAY_OF_MONTH, 1); break;*/
                    case W1: dayStart.add(Calendar.DAY_OF_MONTH, 7); break;
                    case W2: dayStart.add(Calendar.DAY_OF_MONTH, 2*7); break;
                    case M1: dayStart.add(Calendar.DAY_OF_MONTH, 4*7); break;
                    case M2: dayStart.add(Calendar.DAY_OF_MONTH, 2*4*7); break;
                    case M3: dayStart.add(Calendar.DAY_OF_MONTH, 3*4*7); break;
                    case M4: dayStart.add(Calendar.DAY_OF_MONTH, 4*4*7); break;
                    case M6: dayStart.add(Calendar.DAY_OF_MONTH, 6*4*7); break;
                    case Y1: dayStart.add(Calendar.DAY_OF_MONTH, 12*4*7); break;
                }
            }
            Double average = priceSum / numOfPayments;
            return average;
        };
    }

}
