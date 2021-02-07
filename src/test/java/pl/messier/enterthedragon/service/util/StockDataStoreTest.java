package pl.messier.enterthedragon.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.messier.enterthedragon.service.constances.Config;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;
import pl.messier.enterthedragon.service.model.Stock;

import java.util.LinkedHashMap;

public class StockDataStoreTest {

    private static final String dataDirPathStr = Config.DATA_STOCKS_PATH_DIR;

    @Test
    public void getStockByTickerSymbol_existingTicker_doesNotThrow() {
        StockDataStore store = new StockDataStore(dataDirPathStr);
        Assertions.assertDoesNotThrow(() -> {
            store.getStockByTickerSymbol("CDR");
        });
    }

    @Test
    public void getStockByTickerSymbol_nonExistingTicker_throws() {
        StockDataStore store = new StockDataStore(dataDirPathStr);
        Assertions.assertThrows(EnterTheDragonException.class, () -> {
            store.getStockByTickerSymbol("9743a66f914cc249");
        });
    }

    @Test
    public void getStockByTickerSymbol_existingTicker_inNotEmpty() throws EnterTheDragonException {
        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap timePrice = stock.getTimePrice();
        Assertions.assertFalse(timePrice.isEmpty());
    }

    @Test
    public void getStockByTickerSymbol_existingTicker_doesNotContainNull() throws EnterTheDragonException {
        StockDataStore store = new StockDataStore(dataDirPathStr);
        Stock stock = store.getStockByTickerSymbol("CDR");
        LinkedHashMap timePrice = stock.getTimePrice();
        Assertions.assertFalse(timePrice.containsKey(null));
        Assertions.assertFalse(timePrice.containsValue(null));
    }
}
