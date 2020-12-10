# spring-cloud-stream-kafka
Application fetches every 20 [sec] all the countries from the https://api.covid19tracking.narrativa.com/api/countries -> pullCountries
 
Then for Poland and Germany fetches all the Covid results (https://api.covid19tracking.narrativa.com/api/country/:country?date_from=:from&date_to=:to) -> pullResults

Sum results by country -> sumResultsByCountry 

Print sum results to console -> printSumResultsByCountry

## How to run the application 
Run Kafka locally on localhost:9093 \
Run the application mvn spring-boot:run


