package pitslify.api.enums;

public enum Disponibilidade {
    DISPONIVEL("Disponivel"),
    INDISPONIVEL("INDISPONIVEL");

    public final String value;

    Disponibilidade(String descricao){
        this.value = descricao;
    }
}
