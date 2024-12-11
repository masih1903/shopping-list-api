package app.controllers;

import app.daos.ProductDAO;
import app.dtos.ProductDTO;
import app.entities.Product;
import app.exceptions.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class ProductController implements IController {

    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void getById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Product product = productDAO.getById(id);
            if (product == null) {
                throw new ApiException(404, "Product with ID " + id + " not found");
            }
            ProductDTO productDTO = new ProductDTO(product);
            ctx.res().setStatus(200);
            ctx.json(productDTO, ProductDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }

    @Override
    public void getAll(Context ctx) {
        List<Product> products = productDAO.getAll();
        List<ProductDTO> productDTOs = ProductDTO.toProductDTOList(products);
        ctx.status(200).json(productDTOs, ProductDTO.class);
    }

    @Override
    public void create(Context ctx) {
        try {
            ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
            Product product = productDAO.create(productDTO);
            ctx.status(201).json(new ProductDTO(product));
        } catch (Exception e) {
            throw new ApiException(500, "An error occurred while creating the trip. Please try again later.");
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            ProductDTO productDTO = ctx.bodyAsClass(ProductDTO.class);
            Product updatedProduct = productDAO.update(id, productDTO);
            if (updatedProduct == null) {
                throw new ApiException(404, "Product with ID " + id + " not found");
            }
            ctx.status(200).json(updatedProduct, ProductDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Product product = productDAO.getById(id);
            if (product == null) {
                throw new ApiException(404, "Product with ID " + id + " not found");
            }
            productDAO.delete(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }
}
