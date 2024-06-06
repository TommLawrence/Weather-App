package org.example.weatherapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherApp extends Application {

    // Variables for API integration
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_UNITS = "metric";

    private Label temperatureLabel;
    private Label humidityLabel;
    private Label windLabel;
    private Label conditionsLabel;
    private TextField locationField;
    private ComboBox<String> unitComboBox;

    // Method to fetch weather data from the API
    private void getWeather() {
        try {
            // Get location input from user
            final var jsonObject = getJsonObject();

            // Get relevant weather data from JSON response
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            int humidity = main.getInt("humidity");

            JSONObject wind = jsonObject.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            JSONArray weather = jsonObject.getJSONArray("weather");
            JSONObject weatherObject = weather.getJSONObject(0);
            String conditions = weatherObject.getString("description");

            // Set weather data in GUI components
            temperatureLabel.setText("Temperature: " + temperature + " " + unitComboBox.getValue());
            humidityLabel.setText("Humidity: " + humidity + "%");
            windLabel.setText("Wind Speed: " + windSpeed + " " + unitComboBox.getValue());
            conditionsLabel.setText("Conditions: " + conditions);

            // Set appropriate weather icon
            setWeatherIcon();

            // Call method to display forecast
            displayForecast();

        } catch (Exception e) {
            // Display error message in case of failed API request
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unable to fetch weather data. Please check your location input and try again.");
            alert.showAndWait();
        }
    }

    private JSONObject getJsonObject() throws IOException {
        String location = locationField.getText();

        // Create URL for API request
        String apiKey = readApiKeyFromFile();
        String url = API_URL + "?q=" + location + "&units=" + API_UNITS + "&appid=" + apiKey;

        // Create connection to API
        URLConnection connection = new URL(url).openConnection();

        // Read JSON response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();

        // Parse JSON response
        return new JSONObject(response);
    }

    private String readApiKeyFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("apikey.txt"));
        String apiKey = reader.readLine(); // Read the first line (your API key)
        reader.close();
        return apiKey;
    }

    // Method to switch between units for temperature and wind speed
    private void switchUnits() {
        // Get current unit selection from combo box
        String selectedUnit = unitComboBox.getValue();

        // Switch units and update labels accordingly
        if (selectedUnit != null) {
            if (selectedUnit.equals("Celsius")) {
                temperatureLabel.setText("Temperature: " + convertToFahrenheit(Double.parseDouble(temperatureLabel.getText().split(" ")[1])) + " " + unitComboBox.getValue());
                windLabel.setText("Wind Speed: " + convertToMph(Double.parseDouble(windLabel.getText().split(" ")[2])) + " " + unitComboBox.getValue());
            } else {
                temperatureLabel.setText("Temperature: " + convertToCelsius(Double.parseDouble(temperatureLabel.getText().split(" ")[1])) + " " + unitComboBox.getValue());
                windLabel.setText("Wind Speed: " + convertToKph(Double.parseDouble(windLabel.getText().split(" ")[2])) + " " + unitComboBox.getValue());
            }
        }
    }

    // Method to display short-term forecast for chosen location
    private void displayForecast() {
    }

    // Method to set appropriate weather icon based on conditions
    private void setWeatherIcon() {
    }

    // Method to convert temperature from Celsius to Fahrenheit
    private double convertToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    // Method to convert temperature from Fahrenheit to Celsius
    private double convertToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    // Method to convert wind speed from km/h to mph
    private double convertToMph(double kph) {
        return kph * 0.621371;
    }

    // Method to convert wind speed from mph to km/h
    private double convertToKph(double mph) {
        return mph * 1.60934;
    }

    // Method to create and show the GUI
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather Information App");

        // Initialize GUI components
        Label locationLabel = new Label("Location:");
        temperatureLabel = new Label("Temperature:");
        humidityLabel = new Label("Humidity:");
        windLabel = new Label("Wind Speed:");
        conditionsLabel = new Label("Conditions:");
        Label forecastLabel = new Label("Short-term Forecast:");

        locationField = new TextField();
        locationField.setPromptText("Enter the location for weather information");

        Button getWeatherButton = new Button("Get Weather");
        Button switchUnitsButton = new Button("Switch Units");
        unitComboBox = new ComboBox<>();
        unitComboBox.getItems().addAll("Celsius", "Fahrenheit");

        GridPane weatherGrid = new GridPane();
        weatherGrid.setHgap(10);
        weatherGrid.setVgap(10);
        weatherGrid.addRow(0, locationLabel, locationField);
        weatherGrid.addRow(1, temperatureLabel, new Label());
        weatherGrid.addRow(2, humidityLabel, new Label());
        weatherGrid.addRow(3, windLabel, new Label());
        weatherGrid.addRow(4, conditionsLabel, new Label());
        weatherGrid.addRow(5, getWeatherButton, switchUnitsButton);

        VBox forecastBox = new VBox(forecastLabel);

        BorderPane root = new BorderPane();
        root.setTop(weatherGrid);
        root.setCenter(forecastBox);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add action listeners to buttons
        getWeatherButton.setOnAction(e -> {
            // Call method to fetch weather data
            getWeather();
        });

        switchUnitsButton.setOnAction(e -> {
            // Call method to switch between units
            switchUnits();
        });
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
