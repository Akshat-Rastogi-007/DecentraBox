package com.codesmashers.decentrabox.service.IPFS;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.codesmashers.decentrabox.config.PinataConfig;
import com.codesmashers.decentrabox.exception.ResourceNotFoundException;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class IPFSService {

        private static final Logger log = LoggerFactory.getLogger(IPFSService.class);

        private final RestTemplate template;
        private final PinataConfig pinataConfig;
        private final ObjectMapper objectMapper;

        public IPFSService(RestTemplate template, PinataConfig pinataConfig, ObjectMapper objectMapper) {
                this.template = template;
                this.pinataConfig = pinataConfig;
                this.objectMapper = objectMapper;
        }

        public String uploadFile(MultipartFile file, String fileName) throws IOException {

                log.info("Uploading {} to Pinata", fileName);

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

                ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                                return fileName;
                        }
                };

                body.add("file", fileResource);
                body.add("filename", fileName);

                body.add("keyvalues", objectMapper.writeValueAsString(
                                Map.of("private", "true"))

                );

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                headers.setBearerAuth(pinataConfig.getJwt());

                HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

                ResponseEntity<String> response = template.exchange(
                                pinataConfig.getUploadUrl() + "/v3/files",
                                HttpMethod.POST,
                                request,
                                String.class);

                JsonNode json = objectMapper.readTree(response.getBody());
                @SuppressWarnings("deprecation")
                String cid = json.path("data").path("cid").asText();

                if (cid == null || cid.isEmpty()) {
                        throw new RuntimeException(
                                        "Failed to get CID from Pinata response");
                }

                log.info("File uploaded successfully. CID: {}", cid);

                // Make it private explicitly
                makePrivate(cid);

                return cid;
        }

        private void makePrivate(String cid) {
                log.info("Making file private. CID: {}", cid);

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(pinataConfig.getJwt());
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<Void> request = new HttpEntity<>(headers);

                try {
                        template.exchange(
                                        pinataConfig.getBaseUrl()
                                                        + "/v3/files/" + cid + "/make_private",
                                        HttpMethod.PUT,
                                        request,
                                        String.class);
                        log.info("File made private. CID: {}", cid);
                } catch (Exception e) {
                        // Don't fail upload if this fails
                        // file is still uploaded, just log the warning
                        log.warn("Could not make file private: {}",
                                        e.getMessage());
                }
        }

        public String generateSignedUrl(String cid) {
                log.info("Generating signed URL for CID: {}", cid);

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(pinataConfig.getJwt());
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Request body — expiry in seconds
                Map<String, Object> body = Map.of(
                                "url", "https://" + pinataConfig.getGateway()
                                                + "/files/" + cid,
                                "expires", pinataConfig.getSignedUrlExpiry(),
                                "method", "GET");

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

                try {
                        ResponseEntity<String> response = template.exchange(
                                        pinataConfig.getBaseUrl() + "/v3/files/sign",
                                        HttpMethod.POST,
                                        request,
                                        String.class);

                        JsonNode json = objectMapper.readTree(
                                        response.getBody());
                        @SuppressWarnings("deprecation")
                        String signedUrl = json.path("data").asText();

                        log.info("Signed URL generated for CID: {}", cid);
                        return signedUrl;

                } catch (Exception e) {
                        log.error("Failed to generate signed URL: {}",
                                        e.getMessage());
                        throw new RuntimeException(
                                        "Could not generate file access URL");
                }
        }

        public void deleteFile(String cid) {
                log.info("Deleting file from IPFS. CID: {}", cid);

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(pinataConfig.getJwt());

                HttpEntity<Void> request = new HttpEntity<>(headers);

                try {
                        template.exchange(
                                        pinataConfig.getBaseUrl() + "/v3/files/" + cid,
                                        HttpMethod.DELETE,
                                        request,
                                        String.class);
                        log.info("File deleted from IPFS. CID: {}", cid);
                } catch (Exception e) {
                        log.error("Failed to delete file from IPFS: {}",
                                        e.getMessage());
                        throw new RuntimeException(
                                        "Could not delete file from IPFS");
                }
        }

        public JsonNode getFileMetadata(String cid) {
                log.info("Fetching metadata for CID: {}", cid);

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(pinataConfig.getJwt());

                HttpEntity<Void> request = new HttpEntity<>(headers);

                try {
                        ResponseEntity<String> response = template.exchange(
                                        pinataConfig.getBaseUrl() + "/v3/files/" + cid,
                                        HttpMethod.GET,
                                        request,
                                        String.class);

                        return objectMapper.readTree(response.getBody())
                                        .path("data");

                } catch (Exception e) {
                        log.error("Failed to fetch file metadata: {}",
                                        e.getMessage());
                        throw new ResourceNotFoundException(
                                        "File not found on IPFS: " + cid);
                }
        }
}
