package celebre.entities;

public class MetadataPaymentProductBaseDto {
    private String celebrationTitle;
    private String personName;
    private String description;
    private String youtubeUrl;
    private String endPhrase;
    private String imageLink;

    public MetadataPaymentProductBaseDto 
        (
            String celebrationTitle, 
            String personName,
            String description,
            String youtubeUrl,
            String endPhrase,
            String imageLink
        ) {
            this.celebrationTitle = celebrationTitle;
            this.setPersonName(personName);
            this.description = description;
            this.youtubeUrl = youtubeUrl;
            this.endPhrase = endPhrase;
            this.imageLink = imageLink;
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
        if (personName == null || personName.trim().isEmpty()) {
            throw new IllegalArgumentException("Person name cannot be empty or null");
        }
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
}

