package celebre.entities;

public class MetadataPaymentProductBaseDto {
    private String celebrationTitle;
    private String personName;
    private String description;
    private String youtubeUrl;
    private String endPhrase;
    private String imageLink;
    private String email;

    public MetadataPaymentProductBaseDto 
        (
            String celebrationTitle, 
            String personName,
            String description,
            String youtubeUrl,
            String endPhrase,
            String imageLink,
            String email
        ) {
            this.setCelebrationTitle(celebrationTitle);
            this.setPersonName(personName);
            this.setDescription(description);
            this.setYoutubeUrl(youtubeUrl);
            this.setEndPhrase(endPhrase);
            this.setImageLink(imageLink);
            this.setEmail(email);
        }

    public String getCelebrationTitle() {
        return celebrationTitle;
    }

    public void setCelebrationTitle(String celebrationTitle) {
        if (celebrationTitle == null || celebrationTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Celebration title cannot be empty or null");
        }
        if (celebrationTitle.length() < 5) {
            throw new IllegalArgumentException("Celebration title should have at minimum five characters");
        }
        this.celebrationTitle = celebrationTitle;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        if (personName == null || personName.trim().isEmpty()) {
            throw new IllegalArgumentException("Person name cannot be empty or null");
        }
        if (personName.length() < 3) {
            throw new IllegalArgumentException("Person name should have at minimum three characters");
        }
        this.personName = personName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty or null");
        }
        if (description.length() < 10) {
            throw new IllegalArgumentException("Description should have at minimum ten characters");
        }
        this.description = description;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Youtube URL cannot be empty or null");
        }
        
        String youtubeUrlPattern = "^(https?://)?(www\\.)?(youtube\\.com|youtu\\.be)/.+$";
        if (!youtubeUrl.matches(youtubeUrlPattern)) {
            throw new IllegalArgumentException("Invalid YouTube URL");
        }
        
        this.youtubeUrl = youtubeUrl;
    }
    

    public String getEndPhrase() {
        return endPhrase;
    }

    public void setEndPhrase(String endPhrase) {
        if (endPhrase == null || endPhrase.trim().isEmpty()) {
            throw new IllegalArgumentException("End phrase cannot be empty or null");
        }
        if (endPhrase.length() < 5) {
            throw new IllegalArgumentException("End phrase should have at minimum five characters");
        }
        this.endPhrase = endPhrase;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        if (imageLink == null || imageLink.trim().isEmpty()) {
            throw new IllegalArgumentException("Image link cannot be empty or null");
        }
        if (imageLink.length() < 20) {
            throw new IllegalArgumentException("Image link should have at minimum twenty characters");
        }
        this.imageLink = imageLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty or null");
        }
    
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    
        this.email = email;
    }
}