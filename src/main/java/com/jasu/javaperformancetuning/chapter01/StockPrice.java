package com.jasu.javaperformancetuning.chapter01;

/**
 * @author @Jasu
 * @date 2018-11-13 18:12
 */
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface StockPrice {
    String getSymbol();
    Date getDate();
    BigDecimal getClosingPrice();
    BigDecimal getHigh();
    BigDecimal getLow();
    BigDecimal getOpeningPrice();
    boolean isYearHigh();
    boolean isYearLow();
    Collection<? extends StockOptionPrice> getOptions();
}

