package br.uece.eesdevop.biblioteca.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.uece.eesdevop.util.Util;
@WebServlet("/book_save")
public class BookCreateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5437653871809151252L;

	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String requestURL = req.getRequestURL().toString();
        

        PrintWriter writer = resp.getWriter();
        writer.println("<html>" +
                "<body>" +
                "<h1>Salvar Livro</h1>" +
                "<a href='"+Util.urlLimpa(requestURL)+""+"'>Voltar</a>");
        writer.println("<div><form method='post' action='"+Util.urlLimpa(requestURL)+"'>" + 
        		"</br>    Título: <input type='text' name='title' maxlength='255' placeholder='Digite o título do livro'>" +
        		"</br></br>"+
        		"    Autor: <input type='text' name='author' maxlength='255' placeholder='Digite o autor do livro'>" + 
        		"</br></br>"+
        		"    Resumo:"+ 
        		"</br>"+
        		" <textarea cols=60 rows='10' name='abstracts' maxlength='500' wrap='hard' placeholder='Digite o resumo do livro'></textarea>" + 
        		"</br></br>"+
        		"    Ano: <input type='number' name='year' placeholder='Digite o autor do livro'>" + 
        		"</br></br>"+
        		"    <input type='submit' name='enviar' value='Salvar'> " + 
        		"</form></div>");
        writer.println("</body>" +
                "</html");
    }
}
