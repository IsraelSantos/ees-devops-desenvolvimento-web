package br.uece.eesdevop.biblioteca.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.uece.eesdevop.biblioteca.model.BookEntity;
import br.uece.eesdevop.biblioteca.util.HibernateUtil;

@WebServlet("/")
public class BookEntityServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2702031734148412609L;
	
	private EntityManager entityManager;
    
    private void pagina(PrintWriter writer, String requestURL, List<BookEntity> books) {
        writer.println("<html>" +
                "<body>" +
                "<h1>Livros</h1>" +
                "<a href='"+requestURL+"book_save"+"'>Adicionar livro</a>"+
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
    
    private void listaLivros(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        List<BookEntity> books = new ArrayList<>();
        String requestURL = req.getRequestURL().toString();
        
        if (entityManager != null && entityManager.isOpen()) {
            books = entityManager.createQuery("select b from BookEntity b", BookEntity.class).getResultList();
        }

        PrintWriter writer = resp.getWriter();
        pagina(writer, requestURL, books);
    }

    @Override
    public void init() {
        entityManager = HibernateUtil.INSTANCE.getEntityManagerFactory().createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        listaLivros(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	resp.setContentType("text/html");
    	BookEntity entity = new BookEntity();
        entity.setTitle(req.getParameter("title"));
        entity.setAuthor(req.getParameter("author"));
        entity.setAbstracts(req.getParameter("abstracts"));
        entity.setYear(Integer.parseInt(req.getParameter("year")));
        
        if (entityManager != null && entityManager.isOpen()) {
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                entityManager.persist(entity);
                entityManager.flush();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Unable to save the new book: " + e.getMessage());
            }
            
            listaLivros(req, resp);

        }
    }

    @Override
    public void destroy() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

}
