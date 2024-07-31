package pitslify.api.records;

public record Address(
        String cidade,
        String rua,
        int numero,
        String bairro,
        String complemento
        ) { }
