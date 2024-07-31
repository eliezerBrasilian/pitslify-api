package pitslify.api.utils;

import pitslify.api.enums.Item;
import pitslify.api.enums.MensagemRetorno;
import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Component
public class AppUtils {

    public static final String baseUrl = "/pitslify/api/v1";

    public static String generatePassword(){
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }
    
    public static OffsetDateTime getBrasiliaOffsetTime(){
        return OffsetDateTime.now(ZoneOffset.ofHours(-3));
    }
    
    public ResponseEntity<Object> AppCustomJson(MensagemRetorno mensagemRetorno, Item item){

        return switch (mensagemRetorno) {
            case ITEM_NAO_EXISTE ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", item.toString() + " nao existe"));
            case EXCLUIDO_COM_SUCESSO ->
                    ResponseEntity.ok().body(Map.of("message", item.toString() + " excluido com sucesso"));
            case EDITADO_COM_SUCESSO ->
                    ResponseEntity.ok().body(Map.of("message", item.toString() + " atualizado com sucesso no banco de dados"));
            default -> ResponseEntity.ok().body(Map.of("message", "sucesso ao adicionar"));
        };
    }

    public static String CustomMensagemExcessao(MensagemRetorno mensagemRetorno, String mensagem){
        return switch (mensagemRetorno){
            case FALHA_AO_ADICIONAR ->
                "falha ao adicionar devido a uma excessao" + mensagem;

            case FALHA_AO_EDITAR->
                "falha ao deletar devido a uma excessao" + mensagem;

            case FALHA_AO_DELETAR->
               "falha ao deletar devido a uma excessao" + mensagem;

            default -> "";
        };
    }


    public static boolean verificaExpiracao(Date expirationDate){
        // Obtém a data e hora atuais
        LocalDateTime now = LocalDateTime.now();

        // Converte a data de expiração do cupom (java.util.Date) para uma String no formato ISO 8601
        String expirationDateString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(expirationDate);

        // Formata a data de expiração do cupom para LocalDateTime
        LocalDateTime expirationDateTime = LocalDateTime.parse(expirationDateString, DateTimeFormatter.ISO_DATE_TIME);

        // Verifica se a data de expiração é anterior à data atual
        boolean expired = expirationDateTime.isBefore(now);

       return expired;
    }

    public static String obtemPrimeiroNome(String nomeCompleto){
        // Dividir a string pelo espaço em branco
        String[] partes = nomeCompleto.split(" ");

        return  partes[0];
    }
}
