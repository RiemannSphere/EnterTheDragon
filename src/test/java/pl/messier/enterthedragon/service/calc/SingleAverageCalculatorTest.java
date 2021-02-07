package pl.messier.enterthedragon.service.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.messier.enterthedragon.service.constances.Config;
import pl.messier.enterthedragon.service.constances.Interval;
import pl.messier.enterthedragon.service.constances.MomentBuy;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;
import pl.messier.enterthedragon.service.model.Price;
import pl.messier.enterthedragon.service.model.Stock;
import pl.messier.enterthedragon.service.util.StockDataStore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleAverageCalculatorTest {

    private static final String dataDirPathStr = Config.DATA_STOCKS_PATH_DIR;

    @Test
    public void calculateAverageSharePrice_existingStock_isNotNull() throws EnterTheDragonException, ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        SingleAverageCalculator single = new SingleAverageCalculator();

        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap<GregorianCalendar, Price> timePrice = stock.getTimePrice();
        GregorianCalendar dayStart = new GregorianCalendar(2020, Calendar.JANUARY, 3);
        Interval interval = Interval.M1;
        Integer numOfPayments = 3;
        MomentBuy momentBuy = MomentBuy.OPEN;

        Future<Double> average = exec.submit(single.calculateAverageSharePrice(timePrice, dayStart, interval, numOfPayments, momentBuy));
        Assertions.assertNotNull(average.get());
    }

    @Test
    public void calculateAverageSharePrice_W1Interval_returnPreciseAverage() throws EnterTheDragonException, ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        SingleAverageCalculator single = new SingleAverageCalculator();

        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap<GregorianCalendar, Price> timePrice = stock.getTimePrice();
        GregorianCalendar dayStart = new GregorianCalendar(2020, Calendar.JANUARY, 3);
        Interval interval = Interval.W1;
        Integer numOfPayments = 3;
        MomentBuy momentBuy = MomentBuy.OPEN;

        Future<Double> average = exec.submit(single.calculateAverageSharePrice(timePrice, dayStart, interval, numOfPayments, momentBuy));
        Assertions.assertEquals(average.get(), (287d + 289.5d + 253.8d)/3);
    }

    @Test
    public void calculateAverageSharePrice_M1Interval_returnPreciseAverage() throws EnterTheDragonException, ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        SingleAverageCalculator single = new SingleAverageCalculator();

        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap<GregorianCalendar, Price> timePrice = stock.getTimePrice();
        GregorianCalendar dayStart = new GregorianCalendar(2020, Calendar.JANUARY, 3);
        Interval interval = Interval.M1;
        Integer numOfPayments = 3;
        MomentBuy momentBuy = MomentBuy.OPEN;

        Future<Double> average = exec.submit(single.calculateAverageSharePrice(timePrice, dayStart, interval, numOfPayments, momentBuy));
        Assertions.assertEquals(average.get(), (287d + 278d + 276d)/3);
    }

    @Test
    public void calculateAverageSharePrice_dayStartWithoutPrice_throws() throws EnterTheDragonException, ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        SingleAverageCalculator single = new SingleAverageCalculator();

        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap<GregorianCalendar, Price> timePrice = stock.getTimePrice();
        // no price for this day
        // if the dayStart is without price then throw and exception
        GregorianCalendar dayStart = new GregorianCalendar(2020, Calendar.JANUARY, 1);
        Interval interval = Interval.M1;
        Integer numOfPayments = 3;
        MomentBuy momentBuy = MomentBuy.OPEN;

        Future<Double> average = exec.submit(single.calculateAverageSharePrice(timePrice, dayStart, interval, numOfPayments, momentBuy));
        Assertions.assertThrows(ExecutionException.class, () -> average.get());
    }

    @Test
    public void calculateAverageSharePrice_thereIsHoliday_doesNotThrow() throws EnterTheDragonException, ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        SingleAverageCalculator single = new SingleAverageCalculator();

        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap<GregorianCalendar, Price> timePrice = stock.getTimePrice();
        // there will be Christmas in 2 months
        // if the day without price is in the middle (not the dayStart) then we should take next possible day with price
        GregorianCalendar dayStart = new GregorianCalendar(2020, Calendar.OCTOBER, 29);
        Interval interval = Interval.M1;
        Integer numOfPayments = 3;
        MomentBuy momentBuy = MomentBuy.OPEN;

        Future<Double> average = exec.submit(single.calculateAverageSharePrice(timePrice, dayStart, interval, numOfPayments, momentBuy));
        Assertions.assertDoesNotThrow(() -> average.get());
    }

}
