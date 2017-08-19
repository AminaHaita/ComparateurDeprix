package com.servlets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.beans.Member;
import com.model.beans.Product;
import com.model.dao.CategoryDao;
import com.model.dao.DAOFactory;
import com.model.dao.FavoriDao;
import com.model.dao.ProductDao;

/**
 * Servlet implementation class category
 */
@WebServlet("/category")
public class category extends HttpServlet {
	
	public static final String ATT_SESSION_MEMBER = "sessionMember";
	public static final String LISTFAVORIS = "listFavoris";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private ProductDao productDao;
	private CategoryDao categoryDao;
	private FavoriDao favoriDao;
	
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.productDao = daoFactory.getProductDao();
        this.categoryDao = daoFactory.getCategoryDao();
        this.favoriDao = daoFactory.getFavoriDao();
    }
    public category() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (null == session.getAttribute("pToCompare"))
		{
			List<Integer> numList = new ArrayList<Integer>();
			session.setAttribute("pToCompare",numList);
		}
		
		if (null == session.getAttribute("pToFavori"))
		{
			List<Long> favoriList = new ArrayList<Long>();
			session.setAttribute("pToFavori",favoriList);
		}
		
		
		
		
		
		String keyword = request.getParameter("searchkeyword");
		request.setAttribute("keyword", keyword);
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		request.setAttribute("categories", categoryDao.lister());
		if (keyword!=null) request.setAttribute("products", productDao.listerParMotCle(keyword));
		else request.setAttribute("products", productDao.listerParCategorie(id));

		
		if (session.getAttribute(ATT_SESSION_MEMBER) != null) {
			Member membre= (Member)session.getAttribute(ATT_SESSION_MEMBER) ;
			List<Product> listProductFavori= new ArrayList<Product>() ;
			listProductFavori=productDao.listerFavoriProductByIdList(favoriDao.find(membre.getMemberId()));
			request.setAttribute(LISTFAVORIS , listProductFavori);
		}
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String keyword = request.getParameter("searchkeyword");
		request.setAttribute("keyword", keyword);
		request.setAttribute("categories", categoryDao.lister());
		request.setAttribute("products", productDao.listerParMotCle(keyword));
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
	}

}
