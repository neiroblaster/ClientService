import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.MeasurementDTO;
import dto.SensorDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        String sensorName = "Sensor1";

        registration(sensorName);

        Random random = new Random();

        for(int i = 0; i < 50; i++)
            sendMeasurement(random.nextDouble() * 20.0, random.nextBoolean(), sensorName);

//        addMeasurement();
    }


    private static void registration(String sensorName) {
        String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();

        jsonData.put("name", sensorName);

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void sendMeasurement(Double value, Boolean raining, String sensorName) {
        String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        makePostRequestWithJSONData(url, jsonData);

    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("OK!");
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

//    private static void addMeasurement() {
//        String url = "http://localhost:8080/measurements/add";
//        RestTemplate restTemplate1 = new RestTemplate();
//        MeasurementDTO measurementDTO = new MeasurementDTO();
//        SensorDTO sensor = new SensorDTO();
//        Random random1 = new Random();
//        HttpHeaders header = new HttpHeaders();
//
//        header.setContentType(MediaType.APPLICATION_JSON);
//
//        measurementDTO.setValue(random1.nextDouble() * 30.0);
//        measurementDTO.setRaining(random1.nextBoolean());
//        sensor.setName("Sensor3");
//        measurementDTO.setSensor(sensor);
//
//        ObjectMapper om = new ObjectMapper();
//        String jsonData = "";
//        try {
//            jsonData = om.writeValueAsString(measurementDTO);
//            System.out.println("OK!");

//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        HttpEntity<String> request = new HttpEntity<>(jsonData, header);
//        restTemplate1.postForObject(url, request, String.class);
//    }


}




