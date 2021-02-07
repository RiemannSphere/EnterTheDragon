package pl.messier.enterthedragon.service.util;

import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;
import pl.messier.enterthedragon.service.model.Price;
import pl.messier.enterthedragon.service.model.Stock;
import pl.messier.enterthedragon.service.model.TimePrice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class StockDataStore {
    private final static Logger log = Logger.getLogger(StockDataStore.class.getName());
    private static String separator = ",";
    private static Integer numberOfColumns = 10;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private String dataDirPathStr;

    public StockDataStore(String dataDirPathStr) {
        this.dataDirPathStr = dataDirPathStr;

        log.setLevel(Level.INFO);
    }

    /**
     * Parse data file into Stock object
     * @param tickerSymbol ex. CDR
     * @return stock object
     * @throws EnterTheDragonException in case of error during opening the file containing data
     */
    public Stock getStockByTickerSymbol(String tickerSymbol) throws EnterTheDragonException {
        String stockFilePathStr = dataDirPathStr + "\\" + tickerSymbol.toLowerCase() + ".txt";
        try {
            Path stockFilePath = Paths.get(stockFilePathStr);
            Stream<String> lines = Files.lines(stockFilePath);
            LinkedHashMap<GregorianCalendar, Price> timePriceMap = lines
                    .map(StockDataStore::separateLine)
                    .skip(1)
                    .map(StockDataStore::toTimePriceTuple)
                    .collect(
                            LinkedHashMap::new,
                            (linkedHashMap, timePrice) -> linkedHashMap.put(timePrice.getTime(), timePrice.getPrice()),
                            Map::putAll);
            if(timePriceMap.containsKey(null) || timePriceMap.containsValue(null)) {
                throw new EnterTheDragonException("Error during parsing numbers or date from file: " + stockFilePathStr);
            }
            return new Stock(tickerSymbol, timePriceMap);
        } catch (IOException e) {
            throw new EnterTheDragonException("Cannot read the lines of file: " + stockFilePathStr, e);
        }

    }

    private static String[] separateLine(String line) {
        return line.split(separator);
    }

    /**
     * Obtain data from a row and put it inside a TimePrice object
     * @param splitLine single row from the file containing data
     * @return TimePrice tuple or NULL in case of problems with parsing data.
     */
    private static TimePrice toTimePriceTuple(String[] splitLine) {
        if(splitLine.length != numberOfColumns) {
            return null;
        }
        Double open, high, low, close;
        try {
            open = Double.parseDouble(splitLine[4]);
            high = Double.parseDouble(splitLine[5]);
            low = Double.parseDouble(splitLine[6]);
            close = Double.parseDouble(splitLine[7]);
        } catch(NumberFormatException e) {
            log.log(Level.SEVERE,
                    "Cannot parse prices: " + Arrays.asList(splitLine[4], splitLine[5], splitLine[6], splitLine[7]),
                    e);
            return TimePrice.empty();
        }
        GregorianCalendar time = new GregorianCalendar();
        try {
            Date timeDate = dateFormat.parse(splitLine[2]);
            time.setTime(timeDate);
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Cannot parse date: " + splitLine[2], e);
            return TimePrice.empty();
        }

        Price price = new Price(open, high, low, close);
        return new TimePrice(time, price);
    }

}
