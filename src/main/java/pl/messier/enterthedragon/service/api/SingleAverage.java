package pl.messier.enterthedragon.service.api;

import pl.messier.enterthedragon.service.calc.SingleAverageCalculator;
import pl.messier.enterthedragon.service.constances.Config;
import pl.messier.enterthedragon.service.constances.Interval;
import pl.messier.enterthedragon.service.constances.MomentBuy;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;
import pl.messier.enterthedragon.service.model.Price;
import pl.messier.enterthedragon.service.model.Stock;
import pl.messier.enterthedragon.service.util.StockDataStore;

import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleAverage {

    private static final String dataDirPathStr = Config.DATA_STOCKS_PATH_DIR;

    public Double getAverageSharePrice(
            String tickerSymbol,
            String dayStart,
            Interval interval,
            Integer numOfPayments,
            MomentBuy momentBuy) throws EnterTheDragonException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        SingleAverageCalculator single = new SingleAverageCalculator();
        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol(tickerSymbol);
        LinkedHashMap<String, Price> timePrice = stock.getTimePrice();
        Future<Double> average = exec.submit(single.calculateAverageSharePrice(timePrice, dayStart, interval, numOfPayments, momentBuy));
        try {
            return average.get();
        } catch (InterruptedException e) {
            throw new EnterTheDragonException("Calculating average for " + tickerSymbol + " was interrupted.", e);
        } catch (ExecutionException e) {
            throw new EnterTheDragonException("Error for " + tickerSymbol + ": " + e.getMessage(), e);
        }
    }
}
