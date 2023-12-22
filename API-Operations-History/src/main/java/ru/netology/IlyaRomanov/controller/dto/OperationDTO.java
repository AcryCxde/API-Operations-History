package ru.netology.IlyaRomanov.controller.dto;

import lombok.Data;

@Data
public class OperationDTO {
    private final int id;
    private final int sum;
    private final String currency;
    private final String merchant;
    private final int clientId;
}
