package app.controllers;

import app.daos.ShoppingListDAO;
import app.dtos.ProductDTO;
import app.dtos.ShoppingListDTO;
import app.entities.Product;
import app.entities.ShoppingList;
import app.exceptions.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class ShoppingListController implements IController {
    private final ShoppingListDAO shoppingListDAO;

    public ShoppingListController(ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    @Override
    public void getById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            ShoppingList shoppingList = shoppingListDAO.getById(id);
            if (shoppingList == null) {
                throw new ApiException(404, "Shopping-list with ID " + id + " not found");
            }
            ShoppingListDTO shoppingListDTO = new ShoppingListDTO(shoppingList);
            ctx.res().setStatus(200);
            ctx.json(shoppingListDTO, ShoppingListDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }

    @Override
    public void getAll(Context ctx) {
        List<ShoppingList> shoppingLists = shoppingListDAO.getAll();
        if (shoppingLists.isEmpty()) {
            throw new ApiException(404, "No shopping-lists found.");
        }
        List<ShoppingListDTO> shoppingListDTOs = ShoppingListDTO.toShoppingListDTOList(shoppingLists);
        ctx.status(200).json(shoppingListDTOs, ShoppingListDTO.class);

    }

    @Override
    public void create(Context ctx) {
        try {
            ShoppingListDTO shoppingListDTO = ctx.bodyAsClass(ShoppingListDTO.class);
            ShoppingList shoppingList = shoppingListDAO.create(shoppingListDTO);
            ctx.status(201).json(new ShoppingListDTO(shoppingList));
        } catch (Exception e) {
            throw new ApiException(500, "An error occurred while creating the trip. Please try again later.");
        }

    }

    @Override
    public void update(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            ShoppingListDTO shoppingListDTO = ctx.bodyAsClass(ShoppingListDTO.class);
            ShoppingList updatedShoppingList = shoppingListDAO.update(id, shoppingListDTO);
            if (updatedShoppingList == null) {
                throw new ApiException(404, "Shopping-list with ID " + id + " not found");
            }
            ctx.status(200).json(updatedShoppingList, ShoppingListDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            ShoppingList shoppingList = shoppingListDAO.getById(id);
            if (shoppingList == null) {
                throw new ApiException(404, "Shopping-list with ID " + id + " not found");
            }
            shoppingListDAO.delete(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }
}
