package br.uece.eesdevop.bancodedados.servlet;

import br.uece.eesdevop.bancodedados.model.BookEntity;
import br.uece.eesdevop.bancodedados.util.HibernateUtil;
import br.uece.eesdevop.util.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/book_entities")
public class BookEntityServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2702031734148412609L;
	
	private EntityManager entityManager;
    private Gson gson;

    @Override
    public void init() {
        entityManager = HibernateUtil.INSTANCE.getEntityManagerFactory().createEntityManager();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        List<BookEntity> books = new ArrayList<>();
        String requestURL = req.getRequestURL().toString();
        
        if (entityManager != null && entityManager.isOpen()) {
            books = entityManager.createQuery("select b from BookEntity b", BookEntity.class).getResultList();
        }

        PrintWriter writer = resp.getWriter();
        writer.println("<html>" +
                "<body>" +
                "<h1>Livros</h1>" +
                "<td><a href='"+Util.urlLimpa(requestURL)+""+"'>Adicionar</a>  </td>"+
                "<table border=\"1\">" + 
        		"    <tr>" + 
        		"        <td>Título</td>" + 
        		"        <td>Autor</td>" + 
        		"        <td>Resumo</td>" + 
        		"        <td>Ano de lançamento</td>" +
        		"    </tr>");
        for (BookEntity book : books) {
            writer.println("	<tr>"+
            				"	<td>"+book.getTitle()+"</td>"+
            				"	<td>"+book.getAuthor()+"</td>"+
            				"	<td>"+book.getAbstracts()+"</td>"+
            				"	<td>"+book.getYear()+"</td>"
            				+ "</tr>");
        }
        writer.println("</table>"+
        		"</body>" +
                "</html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookRequest request = gson.fromJson(req.getReader(), BookRequest.class);

        if (entityManager != null && entityManager.isOpen()) {
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                BookEntity entity = new BookEntity();
                entity.setTitle(request.title);
                entity.setAuthor(request.author);
                entityManager.persist(entity);
                entityManager.flush();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Unable to save the new book: " + e.getMessage());
            }

        }
    }

    @Override
    public void destroy() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    private static class BookRequest {

        public final String title;
        public final String author;

        public BookRequest(String title, String author) {
            this.title = title;
            this.author = author;
        }
    }

}
