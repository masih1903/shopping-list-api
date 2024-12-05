package app.daos;

import app.dtos.ProductDTO;
import app.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ProductDAO implements IDAO<Product, ProductDTO> {

    private final EntityManagerFactory emf;

    public ProductDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Product getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Product.class, id);
        }
    }

    @Override
    public List<Product> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        }
    }

    @Override
    public Product create(ProductDTO productDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Product product = new Product(productDTO);
            em.persist(product);
            em.getTransaction().commit();
            return product;
        }
    }

    @Override
    public Product update(Integer id, ProductDTO productDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Product product = em.find(Product.class, id);
            product.setName(productDTO.getName());
            em.getTransaction().commit();
            return product;
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Product product = em.find(Product.class, id);
            em.remove(product);
            em.getTransaction().commit();
        }
    }
}
