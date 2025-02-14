package celebre.entities;

import celebre.models.Celebration;

public class UpdateCelebrationDto {
    private String celebrationTitle;
    private String personName;
    private String description;
    private String youtubeUrl;
    private String endPhrase;
    private String imageLink;
    private String email;

    public UpdateCelebrationDto
        (
            String celebrationTitle, 
            String personName,
            String description,
            String youtubeUrl,
            String endPhrase,
            String imageLink,
            String email
        ) 
        {
            this.setCelebrationTitle(celebrationTitle);
            this.setPersonName(personName);
            this.setDescription(description);
            this.setYoutubeUrl(youtubeUrl);
            this.setEndPhrase(endPhrase);
            this.setImageLink(imageLink);
            this.setEmail(email);
        }

    public void applyUpdates(Celebration celebration) {
        if (celebrationTitle != null) celebration.setCelebrationTitle(celebrationTitle);
        if (description != null) celebration.setDescription(description);
        if (email != null) celebration.setEmail(email);
        if (endPhrase != null) celebration.setEndPhrase(endPhrase);
        if (imageLink != null) celebration.setImageLink(imageLink);
        if (personName != null) celebration.setPersonName(personName);
        if (youtubeUrl != null) celebration.setYoutubeUrl(youtubeUrl);
    }

    public String getCelebrationTitle() {
        return celebrationTitle;
    }

    public void setCelebrationTitle(String celebrationTitle) {
        if (celebrationTitle == null) return;
        if (celebrationTitle.length() < 5) {
            throw new IllegalArgumentException("Celebration title should have at minimum five characters");
        }
        this.celebrationTitle = celebrationTitle;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        if (personName == null) return;
        if (personName.length() < 3) {
            throw new IllegalArgumentException("Person name should have at minimum three characters");
        }
        this.personName = personName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) return;
        if (description.length() < 10) {
            throw new IllegalArgumentException("Description should have at minimum ten characters");
        }
        this.description = description;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        if (youtubeUrl == null) return;
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
        if (endPhrase == null) return;
        if (endPhrase.length() < 5) {
            throw new IllegalArgumentException("End phrase should have at minimum five characters");
        }
        this.endPhrase = endPhrase;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        if (imageLink == null) return;
        if (imageLink.length() < 20) {
            throw new IllegalArgumentException("Image link should have at minimum twenty characters");
        }
        this.imageLink = imageLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) return;
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    
        this.email = email;
    }
}
