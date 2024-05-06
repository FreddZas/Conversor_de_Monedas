import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.one.conversordemonedas.calculations.Calculate;
import com.one.conversordemonedas.calculations.CurrencyAPI;
import com.one.conversordemonedas.connection.Connection;
import com.one.conversordemonedas.startinput.Input;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner data = new Scanner(System.in);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userName;
        String localCurrency;
        String currencyExchange;
        double quantity;
        String currency2 = "";

        String currenciesLink = "https://openexchangerates.org/api/currencies.json";
        String currenciesList = Connection.fetchHTTP(currenciesLink);
        JsonObject currenciesNameJson = new Gson().fromJson(currenciesList, JsonObject.class);

        Input.input();
        userName = data.nextLine();
        while (userName.length() < 3 || !userName.matches("[a-zA-Z]+")) {
            System.out.println("\n❌ Por favor, vuelva a ingresar su nombre correctamente: ");
            userName = data.nextLine();
        }
        String keep = "1";

        while(keep.equals("1")){
            String FreddyApiKey = "b6a03abe7e3e8f80a93f8bdf";

            System.out.println("\n**********************************************");
            System.out.println("              Bienvenido " + userName);
            System.out.println("**********************************************");

            boolean validCurrency1 = false;
            boolean validCurrency2 = false;

            do {
                System.out.println("\n ▶ Ingrese la simbologia de la divisa que desea cambiar: ");
                localCurrency = data.nextLine().toUpperCase();
                if(currenciesNameJson.has(localCurrency)) {
                    validCurrency1 = true;
                } else {
                    System.out.println("\n❌Divisa No Encontrada❗❗F");
                }
            } while (!validCurrency1);

            do {
                System.out.println("\n▶ Ingrese la simbologia de la divisa a la que desea cambiar: ");
                currencyExchange = data.nextLine().toUpperCase();
                if(currenciesNameJson.has(currencyExchange)) {
                    validCurrency2 = true;
                    currency2 = currenciesNameJson.get(currencyExchange).getAsString();
                } else {
                    System.out.println("\n❌Divisa No Encontrada❗❗");
                }
            } while (!validCurrency2);

            System.out.println("\n▶ Ingrese la cantidad que desea cambiar: ");
            quantity = data.nextDouble();
            data.nextLine();

            String uriConnection = "https://v6.exchangerate-api.com/v6/"+FreddyApiKey+"/pair/"+localCurrency+"/"+currencyExchange+"/"+quantity;

            String trade = Connection.fetchHTTP(uriConnection);
            CurrencyAPI currencyAPI = gson.fromJson(trade, CurrencyAPI.class);
            Calculate calculate = new Calculate(currencyAPI);

            System.out.println("\n▶ La conversion es: "+ calculate.getResult()+" "+currency2+"\n");

            System.out.println("🟩Desea continuar❓: [SI = 1 / NO = 2]\n");
            keep = data.nextLine().trim();

        }
    }
}