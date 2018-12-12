package com.nvg.exam.phannguyen.data.models.api;

/**
 * Created by phannguyen on 1/15/18.
 */

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class CoinTickerModel{

    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("priceChange")
    @Expose
    private String priceChange;
    @SerializedName("priceChangePercent")
    @Expose
    private String priceChangePercent;
    @SerializedName("weightedAvgPrice")
    @Expose
    private String weightedAvgPrice;
    @SerializedName("prevClosePrice")
    @Expose
    private String prevClosePrice;
    @SerializedName("lastPrice")
    @Expose
    private String lastPrice;
    @SerializedName("lastQty")
    @Expose
    private String lastQty;
    @SerializedName("bidPrice")
    @Expose
    private String bidPrice;
    @SerializedName("bidQty")
    @Expose
    private String bidQty;
    @SerializedName("askPrice")
    @Expose
    private String askPrice;
    @SerializedName("askQty")
    @Expose
    private String askQty;
    @SerializedName("openPrice")
    @Expose
    private String openPrice;
    @SerializedName("highPrice")
    @Expose
    private String highPrice;
    @SerializedName("lowPrice")
    @Expose
    private String lowPrice;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("quoteVolume")
    @Expose
    private String quoteVolume;
    @SerializedName("openTime")
    @Expose
    private long openTime;
    @SerializedName("closeTime")
    @Expose
    private long closeTime;
    @SerializedName("firstId")
    @Expose
    private long firstId;
    @SerializedName("lastId")
    @Expose
    private long lastId;
    @SerializedName("count")
    @Expose
    private long count;




    public CoinTickerModel() {
    }



    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(String priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public String getWeightedAvgPrice() {
        return weightedAvgPrice;
    }

    public void setWeightedAvgPrice(String weightedAvgPrice) {
        this.weightedAvgPrice = weightedAvgPrice;
    }

    public String getPrevClosePrice() {
        return prevClosePrice;
    }

    public void setPrevClosePrice(String prevClosePrice) {
        this.prevClosePrice = prevClosePrice;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getLastQty() {
        return lastQty;
    }

    public void setLastQty(String lastQty) {
        this.lastQty = lastQty;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getBidQty() {
        return bidQty;
    }

    public void setBidQty(String bidQty) {
        this.bidQty = bidQty;
    }

    public String getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(String askPrice) {
        this.askPrice = askPrice;
    }

    public String getAskQty() {
        return askQty;
    }

    public void setAskQty(String askQty) {
        this.askQty = askQty;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(String quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public long getFirstId() {
        return firstId;
    }

    public void setFirstId(long firstId) {
        this.firstId = firstId;
    }

    public long getLastId() {
        return lastId;
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}