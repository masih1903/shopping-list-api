package app.entities;

import app.dtos.ShoppingListDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public ShoppingList(ShoppingListDTO shoppingListDTO) {
        this.id = shoppingListDTO.getId();
        this.name = shoppingListDTO.getName();

    }


}
