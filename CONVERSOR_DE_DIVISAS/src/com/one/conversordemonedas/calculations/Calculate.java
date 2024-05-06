package com.one.conversordemonedas.calculations;
public class Calculate {
    private double result;

    public Calculate(CurrencyAPI currencyAPI) {
        this.result =currencyAPI.conversion_result();
    }

    public double getResult() {
        return result;
    }
}
