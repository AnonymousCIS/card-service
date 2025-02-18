package org.anonymous.card.controllers;

import lombok.Data;

import java.util.List;

@Data
public class RecommendCardSearch extends CardSearch{

    private List<String> email;

    private String mode;
}
