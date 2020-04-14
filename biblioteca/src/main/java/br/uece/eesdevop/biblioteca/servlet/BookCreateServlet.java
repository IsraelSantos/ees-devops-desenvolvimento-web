package br.uece.eesdevop.biblioteca.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.uece.eesdevop.biblioteca.dao.BookDAO;
import br.uece.eesdevop.biblioteca.model.BookEntity;
import br.uece.eesdevop.util.Util;
@WebServlet("/book_save")
public class BookCreateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5437653871809151252L;
	
	BookDAO bookDAO;

	@Override
	public void init() throws ServletException {
		bookDAO = new BookDAO();
		super.init();
	}
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String requestURL = req.getRequestURL().toString();
        String sId = req.getParameter("id");
        
        Long id = (sId != null && sId != "")? Long.parseLong(sId): null;
        
        BookEntity book = new BookEntity();
        
        if (id != null) {
        	book = bookDAO.find(id);
        }else {
        	book.setAbstracts("");
        	book.setAuthor("");
        	book.setTitle("");
        	book.setYear(null);
        	book.setId(null);
        }

        PrintWriter writer = resp.getWriter();
        writer.println("<html>" +
                "<body>" +
                "<h1>Salvar Livro</h1>" +
                "<a href='"+Util.urlLimpa(requestURL)+""+"'>Voltar</a>");
        StringBuffer sb = new StringBuffer();
        sb.append("<div><form method='post' action='"+Util.urlLimpa(requestURL)+"'>");
        sb.append("</br>    Título: <input type='text' name='title' value = '"+book.getTitle()+"' maxlength='255' placeholder='Digite o título do livro'>");
        sb.append("</br></br>");
        sb.append("    Autor: <input type='text' name='author' value = '"+book.getAuthor()+"' maxlength='255' placeholder='Digite o autor do livro'>");
        sb.append("</br></br>");
        sb.append("    Resumo:");
        sb.append("</br>");
        sb.append(" <textarea cols=60 rows='10' name='abstracts' maxlength='500' wrap='hard' placeholder='Digite o resumo do livro'>"+book.getAbstracts()+"</textarea>");
        sb.append("</br></br>");
        if (book.getYear() != null) {
        	sb.append("    Ano: <input type='number' name='year' value='"+book.getYear()+"' placeholder='Digite o autor do livro'>"); 
        }else {
        	sb.append("    Ano: <input type='number' name='year' placeholder='Digite o autor do livro'>");
        }
        sb.append("</br></br>");
        if (book.getId() != null) {
        	sb.append("    <input type='hidden' name='id' value = '"+book.getId()+"'>");
        }else {
        	sb.append("    <input type='hidden' name='id'>");
        }
        sb.append("    <input type='submit' name='enviar' value='Salvar'> "); 
        sb.append("</form></div>");
        writer.println(sb.toString());
        writer.println("</body>" +
                "</html");
    }
    
    @Override
    public void destroy() {
    	bookDAO.destroy();
    	super.destroy();
    }
}
