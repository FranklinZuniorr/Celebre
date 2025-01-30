package celebre.helpers;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Helpers {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${cloudinary.cloud.name}")
    private String cloudName;

    @Value("${cloudinary.api.key}")
    private String apiKey;

    @Value("${cloudinary.api.secret}")
    private String apiSecret;

    public boolean sendEmail(String to, String subject, String body) {
        String apiUrl = "https://api.brevo.com/v3/smtp/email";
        
        Map<String, Object> emailData = new HashMap<>();
        Map<String, String> sender = new HashMap<>();
        sender.put("email", "franklinbarbs763@gmail.com");
        
        emailData.put("sender", sender);
        emailData.put("to", new Object[] {
            new HashMap<String, String>() {{
                put("email", to);
            }}
        });
        emailData.put("subject", subject);
        emailData.put("htmlContent", body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoApiKey);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(emailData);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.postForObject(apiUrl, requestEntity, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public <T> ResponseEntity<T> generateResponse(HttpStatus status, T body) {
        return new ResponseEntity<>(body, status);
    }

    public <GenericClass> GenericClass normalizeJsonToObject(String payloadJson, Class<GenericClass> genericClass) {
        Gson gson = new Gson();
    
        GenericClass normalizedObject = gson.fromJson(payloadJson, genericClass);
    
        return normalizedObject;
    }
    

    public String getCurrentDomain(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        String baseUrl = scheme + "://" + serverName;
        if (serverPort != 80 && serverPort != 443) {
            baseUrl += ":" + serverPort;
        }

        return baseUrl;
    }

    public String uploadImageBase64ToCloudinary(String base64Image) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));

        Map<String, Object> uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(base64Image, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new IllegalArgumentException("It was not possible to upload the image.");
        }

        if (uploadResult != null) {
            return (String) uploadResult.get("secure_url");
        }
        
        throw new IllegalArgumentException("It was not possible to get secure url of image.");
    }
}
