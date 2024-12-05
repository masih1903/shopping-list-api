package app.daos;

import app.config.HibernateConfig;
import app.dtos.ProductDTO;
import app.entities.Product;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductDAOTest {

    private static EntityManagerFactory emf;
    private static ProductDAO productDAO;
    private static ProductDTO p1, p2, p3;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryConfigTest();
        productDAO = new ProductDAO(emf);
    }

    @BeforeEach
    void setUp() {
        p1 = new ProductDTO("Product 1");
        p2 = new ProductDTO("Product 2");
        p3 = new ProductDTO("Product 3");
        productDAO.create(p1);
        productDAO.create(p2);
        productDAO.create(p3);
    }

    @AfterEach
    void tearDown() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Product").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE product_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void getById() {
        Product product = productDAO.getById(1);
        assertEquals(p1.getName(), product.getName());
    }

    @Test
    void getAll() {
        List<Product> products = productDAO.getAll();
        assertEquals(3, products.size());
    }

    @Test
    void create() {
        ProductDTO p4 = new ProductDTO("Product 4");
        productDAO.create(p4);
        List<Product> products = productDAO.getAll();
        assertEquals(4, products.size());

    }

    @Test
    void update() {
        Integer id = 1;
        p1.setName("Product 1 Updated");
        productDAO.update(id, p1);
        Product updatedProduct = productDAO.getById(id);
        assertEquals(p1.getName(), updatedProduct.getName());
    }

    @Test
    void delete() {
        productDAO.delete(1);
        List<Product> products = productDAO.getAll();
        assertEquals(2, products.size());
    }
}