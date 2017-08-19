package com.model.dao;

import java.util.List;

import com.model.beans.Favori;


public interface FavoriDao {
	void create( Favori favori ) throws DAOException ;
	void delete( Favori favori ) throws DAOException ; 
	List<Long> find( Long MemberID ) throws DAOException ;
}
