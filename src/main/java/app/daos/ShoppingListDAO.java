package app.daos;

import app.dtos.ShoppingListDTO;
import app.entities.ShoppingList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class ShoppingListDAO implements IDAO<ShoppingList, ShoppingListDTO> {

    private final EntityManagerFactory emf;

    public ShoppingListDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public ShoppingList getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(ShoppingList.class, id);
        }
    }

    @Override
    public List<ShoppingList> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM ShoppingList s", ShoppingList.class).getResultList();
        }
    }

    @Override
    public ShoppingList create(ShoppingListDTO shoppingListDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            ShoppingList shoppingList = new ShoppingList(shoppingListDTO);
            em.persist(shoppingList);
            em.getTransaction().commit();
            return shoppingList;
        }
    }

    @Override
    public ShoppingList update(Integer id, ShoppingListDTO shoppingListDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            ShoppingList shoppingList = em.find(ShoppingList.class, id);
            if(shoppingList == null) {
                throw new EntityNotFoundException("ShoppingList with ID " + id + " not found");

            }
            shoppingList.setName(shoppingListDTO.getName());
            em.getTransaction().commit();
            return shoppingList;
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            ShoppingList shoppingList = em.find(ShoppingList.class, id);
            em.remove(shoppingList);
            em.getTransaction().commit();
        }
    }
}
