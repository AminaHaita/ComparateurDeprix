package com.favoris;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.model.beans.Favori;
import com.model.dao.DAOException;
import com.model.dao.FavoriDao;

public class TraiterFavori {
	private FavoriDao favoriDao;
	private static final String CHAMP_PRODUCT = "idp";
	private static final String CHAMP_MEMBER = "idm";

	public TraiterFavori(FavoriDao favoriDao) {
		this.favoriDao = favoriDao;
	}

	public List<Long> traiterFavori(HttpServletRequest request) {
		Long idp = getValeurChamp(request, CHAMP_PRODUCT);
		Long idm = getValeurChamp(request, CHAMP_MEMBER);
		List<Long> list = new ArrayList<Long>();
		list = favoriDao.find(idm);
		Favori favori = new Favori();
		favori.setMemberID(idm);
		favori.setProductFID(idp);
		try {

			if (list.isEmpty() || (!list.contains(idp))) {
				favoriDao.create(favori);
			} else {
				favoriDao.delete(favori);
			}

		} catch (DAOException e) {

			e.printStackTrace();
		}
		list = favoriDao.find(idm);
		return list;
	}

	private static Long getValeurChamp(HttpServletRequest request, String nomChamp) {

		String valeur = request.getParameter(nomChamp);

		if (valeur == null || valeur.trim().length() == 0) {

			return null;
		} else {

			return Long.parseLong(valeur);
		}
	}
}
