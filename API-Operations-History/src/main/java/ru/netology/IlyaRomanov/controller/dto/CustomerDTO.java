package ru.netology.IlyaRomanov.controller.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private final int id;
    private final String name;

    @Override
    public String toString() {
        return '{' +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}