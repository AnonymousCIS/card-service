package org.anonymous.card.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.anonymous.card.constants.Category;

@Data
public class RequestCardData {

    private Long seq;

    @NotBlank
    private String cardname ; // 카드명

    private String mode;

    private Category status;


    @NotBlank
    private String cid; // 카드 ID

    @NotBlank
    private String gid;

}
