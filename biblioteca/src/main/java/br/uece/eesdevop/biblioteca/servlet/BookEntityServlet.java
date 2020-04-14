package br.uece.eesdevop.biblioteca.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.uece.eesdevop.biblioteca.dao.BookDAO;
import br.uece.eesdevop.biblioteca.model.BookEntity;

@WebServlet("/")
public class BookEntityServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2702031734148412609L;
	
	private BookDAO bookDAO;
    
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
        		"        <td>Controle</td>" +
        		"    </tr>");
        for (BookEntity book : books) {
            writer.println("	<tr>"+
            				"	<td>"+book.getTitle()+"</td>"+
            				"	<td>"+book.getAuthor()+"</td>"+
            				"	<td>"+book.getAbstracts()+"</td>"+
            				"	<td>"+book.getYear()+"</td>"+
            		        "   <td><a href='"+requestURL+"book_save?id="+book.getId()+"'>Editar</a></td>" 
            				+ "</tr>");
        }
        writer.println("</table>"+
        		"</body>" +
                "</html");
    }
    
    private void listaLivros(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        List<BookEntity> books = bookDAO.listaLivros();
        String requestURL = req.getRequestURL().toString();
        
        PrintWriter writer = resp.getWriter();
        pagina(writer, requestURL, books);
    }

    @Override
    public void init() {
        bookDAO = new BookDAO();
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
        String sId = req.getParameter("id");
        if(sId != null && sId != "") {
        	entity.setId(Long.parseLong(sId));
        }
        
        bookDAO.salve(entity);
        
        listaLivros(req, resp);

    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	resp.setContentType("text/html");
        String sId = req.getParameter("id");
        
        Long id = (sId != null && sId != "")? Long.parseLong(sId): null;
        
        bookDAO.remove(id);
        
        listaLivros(req, resp);
    }

    @Override
    public void destroy() {
        bookDAO.destroy();
    }

}
