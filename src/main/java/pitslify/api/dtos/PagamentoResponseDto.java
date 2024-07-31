package pitslify.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadopago.resources.payment.PaymentAdditionalInfo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

public record PagamentoResponseDto(
        @JsonProperty("id") long id,
        @JsonProperty("date_created") OffsetDateTime dateCreated,
        @JsonProperty("date_approved") OffsetDateTime dateApproved,
        @JsonProperty("date_last_updated") OffsetDateTime dateLastUpdated,
        @JsonProperty("money_release_date") OffsetDateTime moneyReleaseDate,
        @JsonProperty("payment_method_id") String paymentMethodId,
        @JsonProperty("payment_type_id") String paymentTypeId,
        @JsonProperty("status") String status,
        @JsonProperty("status_detail") String statusDetail,
        @JsonProperty("currency_id") String currencyId,
        @JsonProperty("description") String description,
        @JsonProperty("collector_id") long collectorId,
        @JsonProperty("payer") PayerDto payer,
        @JsonProperty("metadata") Map<String, Object> metadata,
        @JsonProperty("additional_info") PaymentAdditionalInfo additionalInfo,
        @JsonProperty("external_reference") String externalReference,
        @JsonProperty("transaction_amount") BigDecimal transactionAmount,
        @JsonProperty("transaction_amount_refunded") BigDecimal transactionAmountRefunded,
        @JsonProperty("coupon_amount") BigDecimal couponAmount,
        @JsonProperty("transaction_details") TransactionDetailsDto transactionDetails,
        @JsonProperty("installments") int installments,
        @JsonProperty("card") com.mercadopago.resources.payment.PaymentCard card
) {
    public record PayerDto(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("identification") IdentificationDto identification,
            @JsonProperty("type") String type
    ) {
        public record IdentificationDto(
                @JsonProperty("type") String type,
                @JsonProperty("number") String number
        ) {}
    }

    public record TransactionDetailsDto(
            @JsonProperty("net_received_amount") BigDecimal netReceivedAmount,
            @JsonProperty("total_paid_amount") BigDecimal totalPaidAmount,
            @JsonProperty("overpaid_amount") BigDecimal overpaidAmount,
            @JsonProperty("installment_amount") BigDecimal installmentAmount
    ) {}
}

