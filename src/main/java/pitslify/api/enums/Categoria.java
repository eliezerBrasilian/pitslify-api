package pitslify.api.enums;

public enum Categoria {
    SALGADONOCOPO("Salgado no Copo"),
    RISOLE("Risole"),
    COXINHA("Coxinha"),
    PASTEL("Pastel"),
    HOTDOG("Hot Dog"),
    COMBO("Combo"),
    CONGELADOS("Congelados"),
    BATATAS("Batatas");

    public final String value;

    Categoria(String descricao){
        this.value = descricao;
    }
}
