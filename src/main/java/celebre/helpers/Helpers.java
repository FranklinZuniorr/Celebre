package celebre.helpers;

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
import java.util.HashMap;
import java.util.Map;

@Component
public class Helpers {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

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
}
