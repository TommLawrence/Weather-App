# Weather-App
The Weather Information App is a Java application that allows users to fetch and display weather data for a specific location. The app integrates with the OpenWeatherMap API to retrieve real-time weather information. This README file provided detailed instructions on how to use the app and explained the implementation details. Feel free to explore the code and make any modifications or enhancements to suit your needs. Happy weather tracking!

To use the Weather Information App, follow the steps below:

Clone or download the project from the GitHub repository: Weather Information App.

Open the project in your preferred Java IDE (Integrated Development Environment).

Ensure that you have the necessary dependencies installed. The app uses the JavaFX library for the graphical user interface (GUI) components. If you don't have JavaFX installed, refer to the official documentation for installation instructions.

Build and run the application using your IDE's build and run commands.

The Weather Information App GUI will appear, showing input fields for location, buttons to fetch weather data and switch units, and labels to display weather information.

Implementation Details
The Weather Information App is implemented using Java and JavaFX. The app follows the Model-View-Controller (MVC) architectural pattern, separating the logic into different components.

API Integration
The app integrates with the OpenWeatherMap API to fetch weather data. The API requires an API key for authentication, which is stored in the API_KEY variable. The API URL and units are also defined in the API_URL and API_UNITS variables, respectively.

The getWeather() method is responsible for fetching weather data from the API. It retrieves the user's location input, constructs the API request URL, and sends a request to the API. The JSON response is then parsed to extract the relevant weather data such as temperature, humidity, wind speed, and conditions. The weather data is displayed in the GUI components.

Unit Conversion
The app provides the ability to switch between Celsius and Fahrenheit for temperature and wind speed units. The switchUnits() method is called when the user clicks the "Switch Units" button. It retrieves the current unit selection from the combo box and updates the temperature and wind speed labels accordingly.

The app includes helper methods to convert temperature from Celsius to Fahrenheit and vice versa, as well as to convert wind speed from km/h to mph and vice versa. These methods are used in the switchUnits() method to perform the unit conversions.

GUI Components
The GUI components are created using JavaFX. The main window of the app is a Stage object, which represents the top-level container for the GUI. The GUI layout is implemented using a combination of GridPane and BorderPane containers.

The GridPane is used to arrange the labels, text field, buttons, and combo box in a grid-like structure. The BorderPane is used to position the GridPane at the top and a VBox container for the forecast display at the center.

The start() method is the entry point of the application and is called when the app is launched. It sets up the GUI components, adds event listeners to the buttons, and displays the main window.
