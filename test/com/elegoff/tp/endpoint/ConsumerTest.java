package com.elegoff.tp.endpoint;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.elegoff.tp.bean.Trade;
import com.elegoff.tp.bean.TradeConverter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

/**
 * TODO Class description
 */
public class ConsumerTest
{

    private final static String TRADE_WS_URI = "http://127.0.0.1:8080/trade-processor/trade";

    @Test
    public void testExample()
    {

        Trade t = new Trade();
        t.setUserId(123456);
        t.setTimePlaced("25-JAN-15 10:27:44");
        t.setCurrencyFrom("USD");
        t.setCurrencyTo("CHF");
        t.setRate(0.99f);
        t.setAmountBuy(36f);
        t.setAmountSell(41f);
        t.setOriginatingCountry("DE");

        String myJson = TradeConverter.toDBObject(t).toString();
        RestAssured.baseURI = TRADE_WS_URI;
        Response r = given().contentType("application/json").body(myJson).when().post("");
        String body = r.getBody().asString();
        final JsonPath jsonPath = new JsonPath(body);
        assertThat(jsonPath.getString("success"), equalTo("true"));
    }

    @Test
    public void testExampleLoad()
    {

        String[] currencies = { "EUR", "USD", "AUD", "YEN", "CHF", "CAD" };
        String[] countries = { "FR", "UK", "DE", "IRL", "ITA", "SPA", "USA", "AU" };

        Trade t = new Trade();

        int load = 1000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < load; i++)
        {

            t.setUserId(this.rndInt(1, 20000));
            t.setTimePlaced(this.rndInt(1, 31) + "-JAN-15 10:27:44");
            t.setCurrencyFrom(currencies[this.rndInt(0, currencies.length - 1)]);
            t.setCurrencyTo(currencies[this.rndInt(0, currencies.length - 1)]);
            t.setRate((double) this.rndInt(1, 10) / this.rndInt(1, 100));
            t.setAmountBuy(this.rndInt(1, 20000) / this.rndInt(1, 500));
            if (t.getAmountBuy() == 0)
            {
                t.setAmountBuy(this.rndInt(1, 666));
            }

            t.setAmountSell(t.getAmountBuy() * t.getRate());
            if (t.getAmountSell() == 0)
            {
                t.setAmountSell(this.rndInt(1, 666));
            }
            t.setOriginatingCountry(countries[this.rndInt(0, countries.length - 1)]);

            String myJson = TradeConverter.toDBObject(t).toString();
            RestAssured.baseURI = TRADE_WS_URI;

            Response r = given().contentType("application/json").body(myJson).when().post("");
            String body = r.getBody().asString();
            final JsonPath jsonPath = new JsonPath(body);
            assertThat(jsonPath.getString("success"), equalTo("true"));
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        long avg = duration / load;

        System.out.println("Processed " + load + " trades in " + duration + " ms => " + avg + " per trade");
    }

    private int rndInt(int min, int max)
    {
        return min + (int) (Math.random() * (max - min + 1));
    }

}
