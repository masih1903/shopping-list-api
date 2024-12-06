package app.dtos;

import app.entities.ShoppingList;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ShoppingListDTO {

    private Integer id;
    private String name;

    public ShoppingListDTO(ShoppingList shoppingList) {
        this.id = shoppingList.getId();
        this.name = shoppingList.getName();
    }

    public static List<ShoppingListDTO> toShoppingListDTOList(List<ShoppingList> shoppingLists) {
        return shoppingLists.stream().map(ShoppingListDTO::new).collect(Collectors.toList());
    }
}
