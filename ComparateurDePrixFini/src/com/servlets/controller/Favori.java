package com.servlets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.favoris.TraiterFavori;
import com.model.dao.DAOFactory;
import com.model.dao.FavoriDao;



public class Favori extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private FavoriDao favoriDao;
	
    public Favori() {
        super();
    
    }
	/* la methode init() s'execute une seule fois */
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.favoriDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getFavoriDao();
		
	}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		List<Long> listProductFavori = (List<Long>) session.getAttribute("pToFavori");
		 
		
		TraiterFavori traitement = new TraiterFavori(favoriDao);  
		listProductFavori=traitement.traiterFavori(request);
		session.setAttribute("pToFavori",listProductFavori);
		
		
		/*String resultat = "invalide";
		String idp = request.getParameter("idp");
		String idm = request.getParameter("idm");
		//System.out.println(idp+" "+idm);
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		if ((idp != null) && (idm != null)) {
		resultat = "valide";
		}*/
		
		//response.getWriter().write("<message>"+resultat+"</message>");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
