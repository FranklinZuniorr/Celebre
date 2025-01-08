package celebre.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "celebrations")
public class Celebration {

    @Id
    private String id;
    private String celebrationTitle;
    private String personName;
    private String description;
    private String youtubeUrl;
    private String endPhrase;
    private String imageLink;
    private String email;

    public Celebration(
        String celebrationTitle, 
        String personName,
        String description,
        String youtubeUrl,
        String endPhrase,
        String imageLink,
        String email
    ) {
        this.celebrationTitle = celebrationTitle;
        this.personName = personName;
        this.description = description;
        this.youtubeUrl = youtubeUrl;
        this.endPhrase = endPhrase;
        this.imageLink = imageLink;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCelebrationTitle() {
        return celebrationTitle;
    }

    public void setCelebrationTitle(String celebrationTitle) {
        this.celebrationTitle = celebrationTitle;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getEndPhrase() {
        return endPhrase;
    }

    public void setEndPhrase(String endPhrase) {
        this.endPhrase = endPhrase;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
