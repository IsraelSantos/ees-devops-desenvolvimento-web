package br.uece.eesdevop.biblioteca.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.uece.eesdevop.biblioteca.model.BookEntity;
import br.uece.eesdevop.biblioteca.util.HibernateUtil;

public class BookDAO {
	
	private EntityManager entityManager;
	
	public BookDAO() {
		entityManager = HibernateUtil.INSTANCE.getEntityManagerFactory().createEntityManager();
	}
	
	public List<BookEntity> listaLivros() throws IOException{
		List<BookEntity> res = new ArrayList<BookEntity>();
        
        if (entityManager != null && entityManager.isOpen()) {
            res = entityManager.createQuery("select b from BookEntity b order by b.id ", BookEntity.class).getResultList();
        }
        
        return res;
	}
	
	public BookEntity find(long id){
		BookEntity res = null;
        
        if (entityManager != null && entityManager.isOpen()) {
            res = entityManager.find(BookEntity.class, id);
        }
        
        return res;
	}
	
	public BookEntity salve(BookEntity book){
		BookEntity res = book;
        if (entityManager != null && entityManager.isOpen()) {
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                if (book.getId() == null) {
                	entityManager.persist(book);
                }else {
                	res = entityManager.merge(book);
                }
                entityManager.flush();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Unable to save the new book: " + e.getMessage());
            }

        }
        return res;
	}
	
	public void remove(long id) {
		if (entityManager != null && entityManager.isOpen()) {
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                BookEntity tmp = entityManager.find(BookEntity.class, id);
                if (tmp != null) {
                	entityManager.remove(tmp);
                }
                entityManager.flush();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Unable to remove the book: " + e.getMessage());
            }

        }
	}
	
    public void destroy() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

}
