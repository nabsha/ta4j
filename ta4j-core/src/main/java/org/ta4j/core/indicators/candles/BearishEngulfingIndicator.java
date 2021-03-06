/*
  The MIT License (MIT)

  Copyright (c) 2014-2017 Marc de Verdelhan & respective authors (see AUTHORS)

  Permission is hereby granted, free of charge, to any person obtaining a copy of
  this software and associated documentation files (the "Software"), to deal in
  the Software without restriction, including without limitation the rights to
  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
  the Software, and to permit persons to whom the Software is furnished to do so,
  subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.indicators.candles;

import org.ta4j.core.Decimal;
import org.ta4j.core.Bar;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.CachedIndicator;

/**
 * Bearish engulfing pattern indicator.
 * <p></p>
 * @see <a href="http://www.investopedia.com/terms/b/bearishengulfingp.asp">
 *     http://www.investopedia.com/terms/b/bearishengulfingp.asp</a>
 */
public class BearishEngulfingIndicator extends CachedIndicator<Boolean> {

    private final TimeSeries series;

    /**
     * Constructor.
     * @param series a time series
     */
    public BearishEngulfingIndicator(TimeSeries series) {
        super(series);
        this.series = series;
    }

    @Override
    protected Boolean calculate(int index) {
        if (index < 1) {
            // Engulfing is a 2-candle pattern
            return false;
        }
        Bar prevBar = series.getBar(index-1);
        Bar currBar = series.getBar(index);
        if (prevBar.isBullish() && currBar.isBearish()) {
            final Decimal prevOpenPrice = prevBar.getOpenPrice();
            final Decimal prevClosePrice = prevBar.getClosePrice();
            final Decimal currOpenPrice = currBar.getOpenPrice();
            final Decimal currClosePrice = currBar.getClosePrice();
            return currOpenPrice.isGreaterThan(prevOpenPrice) && currOpenPrice.isGreaterThan(prevClosePrice)
                    && currClosePrice.isLessThan(prevOpenPrice) && currClosePrice.isLessThan(prevClosePrice);
        }
        return false;
    }
}
