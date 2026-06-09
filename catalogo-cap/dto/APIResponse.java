package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Questa annotazione nasconde i campi nulli nel JSON finale per una risposta più pulita
public class APIResponse<T> {
    
    private String status; // "success", "fail", "error"
    private T data;        // Dati di risposta o mappa degli errori di validazione
    private String message; // Messaggio d'errore (usato principalmente in caso di "error")

    // Metodo scorciatoia per generare una risposta di Successo
    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>("success", data, null);
    }

    // Metodo scorciatoia per generare una risposta di Fallimento (es. validazione input)
    public static <T> APIResponse<T> fail(T data) {
        return new APIResponse<>("fail", data, null);
    }

    // Metodo scorciatoia per generare una risposta di Errore del Server
    public static <T> APIResponse<T> error(String message) {
        return new APIResponse<>("error", null, message);
    }
}