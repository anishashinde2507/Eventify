package com.event.major.dto;

import lombok.Data;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private int categoryId;
    private double price;
    private double weight;
    private String description;
    private String imageName;
}
