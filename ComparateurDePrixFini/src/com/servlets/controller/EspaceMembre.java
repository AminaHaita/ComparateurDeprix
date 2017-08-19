package com.servlets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.beans.Member;
import com.model.beans.Product;
import com.model.dao.DAOFactory;
import com.model.dao.FavoriDao;
import com.model.dao.ProductDao;

public class EspaceMembre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ATT_SESSION_MEMBER = "sessionMember";
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String LISTFAVORIS = "listFavoris";
	
	private ProductDao productDao;
	private FavoriDao favoriDao;
	public void init() throws ServletException {
this.productDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProductDao();
this.favoriDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getFavoriDao();
		
	}

	public EspaceMembre() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        
		HttpSession session = request.getSession();
		if (session.getAttribute(ATT_SESSION_MEMBER) == null) {
			/* Redirection vers la page publique */
			response.sendRedirect("connexion");
		} else {
			Member membre= (Member)session.getAttribute(ATT_SESSION_MEMBER) ;
			List<Product> listProductFavori= new ArrayList<Product>() ;
			listProductFavori=productDao.listerFavoriProductByIdList(favoriDao.find(membre.getMemberId())) ;
			//session.setAttribute(LISTFAVORIS, listProductFavori);
			request.setAttribute(LISTFAVORIS , listProductFavori);
			
			
			/* Affichage de la page restreinte */
			this.getServletContext().getRequestDispatcher("/WEB-INF/espaceMembre.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/espaceMembre.jsp").forward(request, response);

	}

}
