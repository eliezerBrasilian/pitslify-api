package pitslify.api.enums;

public enum AppStatus {
    WAITING_PUSH("waiting_push"),
    PUSHED("pushed_to_google"),
    IN_REVIEW("in_review"),
    DISAPPROVED("disapproved"),
    APPROVED("approved");

    public final String value;

    AppStatus(String v) {
        value = v;
    }
}
