package au.com.telstra.simcardactivator.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SimActivationRequest {

    private String iccid;
    private String email;
}
