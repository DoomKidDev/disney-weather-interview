# Weather Take-Home Interview

Reactive implementation for simple REST request to get today's weather forecast.

## Build

```ps1
.\mvnw clean install
```

## Run
```ps1
.\mvnw spring-boot:run
```

## Use

### Local

By default, you can access the service with this URL:

GET http://localhost:8080/forecast

Example Response:

```json
{
    "daily": [
        {
            "day_name": "THURSDAY",
            "temp_high_celsius": 32.2,
            "forecast_blurp": "Partly Sunny then Slight Chance Showers And Thunderstorms"
        }
    ]
}
```

## Configuration

| Property | Default | Description |
| --- | --- | --- |
| server.port | 8080 | Standard Spring Server Port |
| integration.weather.url | https://api.weather.gov | BaseUrl for the Weather Data Service Client | 
