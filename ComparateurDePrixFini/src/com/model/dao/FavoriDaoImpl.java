package com.model.dao;

import static com.model.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.model.dao.DAOUtilitaire.initializingPreparedQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.beans.Favori;

public class FavoriDaoImpl implements FavoriDao {

	private DAOFactory daoFactory;
	private static final String SQL_SELECT_PAR_ID_MEMBER = "SELECT favoriID,productFID,memberID "
			+ "FROM favoris WHERE memberID = ?";
	private static final String SQL_INSERT = "INSERT INTO favoris (productFID, memberID)" + " VALUES (?, ?)";
	private static final String SQL_DELETE = "DELETE FROM favoris WHERE productFID = ? AND  memberID = ?  ";

	FavoriDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static Favori map(ResultSet resultSet) throws SQLException {
		Favori favori = new Favori();
		favori.setFavoriID(resultSet.getLong("favoriID"));
		favori.setProductFID(resultSet.getLong("productFID"));
		favori.setMemberID(resultSet.getLong("memberID"));

		return favori;
	}

	@Override
	public List<Long> find(Long MemberID) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Long> listFavori = new ArrayList<Long>();

		try {
			/* Récupération d'une connexion depuis la Factory */
			connection = daoFactory.getConnection();

			preparedStatement = initializingPreparedQuery(connection, SQL_SELECT_PAR_ID_MEMBER, false, MemberID);

			resultSet = preparedStatement.executeQuery();

			/*
			 * Parcours de la ligne de données de l'éventuel ResulSet retourné
			 */
			while (resultSet.next()) {
				
				listFavori.add(map(resultSet).getProductFID());
				
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return listFavori;
	}

	@Override
	public void create(Favori favori) throws IllegalArgumentException, DAOException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connection = daoFactory.getConnection();
			preparedStatement = initializingPreparedQuery(connection, SQL_INSERT, true, favori.getProductFID(),
					favori.getMemberID());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException("Échec de la création du favori, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				/*
				 * Puis initialisation de la propriété id du bean Utilisateur
				 * avec sa valeur
				 */
				favori.setFavoriID(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Échec de la création du favori dans la base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connection);
		}
	}

	public void delete(Favori favori) throws IllegalArgumentException, DAOException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connection = daoFactory.getConnection();
			preparedStatement = initializingPreparedQuery(connection, SQL_DELETE, false, favori.getProductFID(),
					favori.getMemberID());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException("Échec de la suppression du favori, aucune ligne supprimée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connection);
		}
	}

}
