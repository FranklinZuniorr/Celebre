package celebre.entities;

import celebre.enums.EnumEventType;

public class PaymentConfirmationProductBaseDto {
    private DataProp data;
    private String type;

    public PaymentConfirmationProductBaseDto(DataProp data, String type) {
        this.data = data;
        this.type = type;
    }

    public DataProp getdata() {
        return this.data;
    }

    public void setdata(DataProp data) {
        this.data = data;
    }

    public String getType() {
        return this.type;
    }

    public EnumEventType getTypeFromString() {
        for (EnumEventType eventType : EnumEventType.values()) {
            if (eventType.getValue().equals(this.type)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + this.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class DataProp {
        private ObjectProp object;

        public DataProp(ObjectProp object) {
            this.object = object;
        }

        public ObjectProp getObject() {
            return this.object;
        }
    
        public void setdata(ObjectProp object) {
            this.object = object;
        }
    }

    public static class ObjectProp {
        private MetadataPaymentProductBaseDto metadata;

        public ObjectProp(MetadataPaymentProductBaseDto metadata) {
            this.metadata = metadata;
        }

        public MetadataPaymentProductBaseDto getMetadata () {
            return this.metadata;
        }

        public void setMetadata(MetadataPaymentProductBaseDto metadata) {
            this.metadata = metadata;
        }
    }
}
