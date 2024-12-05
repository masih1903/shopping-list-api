package app.dtos;

import app.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Integer id;
    private String name;

    public ProductDTO(String name) {

        this.name = name;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();

    }

    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }
}
