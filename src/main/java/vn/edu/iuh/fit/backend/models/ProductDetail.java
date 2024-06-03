package vn.edu.iuh.fit.backend.models;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDetail {
    private long id;
    private String name;
    private String description;
    private String unit;
    private String manufacturer;
    private String productImage;
    private double price;
}

