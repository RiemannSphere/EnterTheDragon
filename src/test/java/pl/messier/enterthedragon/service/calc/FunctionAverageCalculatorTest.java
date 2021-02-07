package pl.messier.enterthedragon.service.calc;

import org.junit.jupiter.api.Test;
import pl.messier.enterthedragon.service.constances.Config;
import pl.messier.enterthedragon.service.constances.Const;
import pl.messier.enterthedragon.service.constances.Interval;
import pl.messier.enterthedragon.service.constances.MomentBuy;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;
import pl.messier.enterthedragon.service.model.Price;
import pl.messier.enterthedragon.service.model.Stock;
import pl.messier.enterthedragon.service.util.StockDataStore;

import java.util.*;
import java.util.concurrent.*;

public class FunctionAverageCalculatorTest {

    private static final String dataDirPathStr = Config.DATA_STOCKS_PATH_DIR;

    @Test
    public void calculateAverageSharePrice_FiveFollowingDays_displaysSomeResult() throws EnterTheDragonException, InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newFixedThreadPool(Const.numOfCores);
        List<Callable<Double>> averagesCallables = new ArrayList<>();

        SingleAverageCalculator single = new SingleAverageCalculator();
        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap<GregorianCalendar, Price> timePrice = stock.getTimePrice();
        Interval interval = Interval.M1;
        Integer numOfPayments = 3;
        MomentBuy momentBuy = MomentBuy.OPEN;

        // check every day of the week as a start day
        for(int i=0; i!=5; i++) {
            GregorianCalendar dayStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 3 + i);
            averagesCallables.add(single.calculateAverageSharePrice(timePrice, dayStart, interval, numOfPayments, momentBuy));
        }
        List<Future<Double>> averages = exec.invokeAll(averagesCallables);
        for (Future<Double> average : averages) {
            System.out.println(average.get());
        }
    }

}
