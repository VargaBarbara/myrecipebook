package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfo {

    private Integer id;
    private String name;
    private String email;
    private List<ReceiptInfo> receipts;

}
