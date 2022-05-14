package com.microservices.camelmicroservicea.routes;

import java.math.BigDecimal;

public class CurrencyExchange {
    private long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiple;

    public CurrencyExchange() {
    }

    public CurrencyExchange(long id, String from, String to, BigDecimal conversionMultiple) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
            "id=" + id +
            ", from='" + from + '\'' +
            ", to='" + to + '\'' +
            ", conversionMultiple=" + conversionMultiple +
            '}';
    }
}
