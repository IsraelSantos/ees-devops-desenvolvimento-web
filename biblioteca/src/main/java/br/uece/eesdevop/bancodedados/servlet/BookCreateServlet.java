package br.uece.eesdevop.bancodedados.servlet;

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
                "<td><a href='"+Util.urlLimpa(requestURL)+""+"'>Voltar</a>  </td>");
        writer.println("<form method='post' action='"+Util.urlLimpa(requestURL)+"'>" + 
        		"    Título: <input type='text' name='title' placeholder='Digite o título do livro'>" +
        		"</br>"+
        		"    Autor: <input type='text' name='author' placeholder='Digite o autor do livro'>" + 
        		"</br>"+
        		"    Resumo: <textarea cols=60 rows='10' name='abstracts' maxlength='500' wrap='hard' placeholder='Digite o resumo do livro'></textarea>" + 
        		"</br>"+
        		"    Ano: <input type='number' name='year' placeholder='Digite o autor do livro'>" + 
        		"</br>"+
        		"    <input type='submit' name='enviar' value='Salvar'> " + 
        		"</form>");
        writer.println("</body>" +
                "</html");
    }
}
