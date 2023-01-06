package aprendendo.api.blog.exceptions.user;

import java.util.HashMap;
import java.util.Map;

public class EmailUsedException extends RuntimeException{

    public Map<String,String> getErros() {
        Map<String,String> erros = new HashMap<>();
        erros.put("ERROR","EMAIL já está cadastrado");
        return erros;
    }
}
