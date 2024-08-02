package pitslify.api.enums;

public enum OrderType {
    CHECKOUT("checkout"),
    PUBLISH_APP("publish_app");

    public final String value;

    OrderType(String v){
        this.value = v;
    }
}
