package ru.netology.IlyaRomanov.domain;

import lombok.Data;

@Data
public class Operation {
    private final int id;
    private final int sum;
    private final String currency;
    private final String merchant;
    private final int clientId;
}