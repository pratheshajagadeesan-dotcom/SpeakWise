package com.speakwise.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.client.RestClientException;

@Service
public class SpeechToTextService {

    @Value("${assemblyai.api.key}")
    private String apiKey;

    @Value("${assemblyai.upload.url}")
    private String uploadUrl;

    @Value("${assemblyai.transcript.url}")
    private String transcriptUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String uploadAudio(File audioFile) throws IOException {

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.set("authorization", apiKey);

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            FileSystemResource resource = new FileSystemResource(audioFile);

            HttpEntity<FileSystemResource> request =
                    new HttpEntity<>(resource, headers);

            String response =
                    restTemplate.postForObject(
                            uploadUrl,
                            request,
                            String.class);

            return response;
        }

        catch (RestClientException e) {

            throw new RuntimeException(
                    "AssemblyAI API Error: " + e.getMessage());

        }

    }

    public String createTranscript(String uploadUrl) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode requestBody = mapper.createObjectNode();

        requestBody.put("audio_url", uploadUrl);

        requestBody.putArray("speech_models")
                .add("universal-3-pro");

        HttpHeaders headers = new HttpHeaders();

        headers.set("authorization", apiKey);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(requestBody.toString(), headers);

        String response =
                restTemplate.postForObject(
                        transcriptUrl,
                        request,
                        String.class);

        return response;

    }

    public String getTranscriptId(String response) throws Exception {

        JsonNode jsonNode = objectMapper.readTree(response);

        return jsonNode.get("id").asText();

    }

    public String getTranscriptResult(String transcriptId) {

        HttpHeaders headers = new HttpHeaders();

        headers.set("authorization", apiKey);

        HttpEntity<String> request =
                new HttpEntity<>(headers);

        String response =
                restTemplate.exchange(
                        transcriptUrl + "/" + transcriptId,
                        HttpMethod.GET,
                        request,
                        String.class
                ).getBody();

        return response;

    }

    public String getTranscriptText(String response) throws Exception {

        JsonNode jsonNode = objectMapper.readTree(response);

        return jsonNode.get("text").asText();

    }

    public String getUploadUrl(String response) throws Exception {

        JsonNode jsonNode = objectMapper.readTree(response);

        return jsonNode.get("upload_url").asText();
    }


    public void printWordTimings(String response) throws Exception {

        JsonNode jsonNode = objectMapper.readTree(response);

        JsonNode words = jsonNode.get("words");

        for (JsonNode word : words) {

            System.out.println(
                    word.get("text").asText()
                            + " : "
                            + word.get("start").asLong()
                            + " ms - "
                            + word.get("end").asLong()
                            + " ms");
        }

    }

    public String waitForCompletion(String transcriptId)
            throws Exception {

        String response;

        while (true) {

            response = getTranscriptResult(transcriptId);

            JsonNode jsonNode = objectMapper.readTree(response);

            String status = jsonNode.get("status").asText();

            if (status.equals("completed")) {

                return response;

            }

            Thread.sleep(3000);

        }

    }

}