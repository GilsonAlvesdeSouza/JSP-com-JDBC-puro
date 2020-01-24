package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.jdbc.SingleConnection;
import model.User;

public class UserDAO {

	private Connection connection;

	public UserDAO() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(User user) throws SQLException {
		String sql = "INSERT INTO userposjava (nome, email) VALUES (?,?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, user.getNome());
			insert.setString(2, user.getEmail());
			insert.execute();
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
//			connection.close();
		}
	}

	public List<User> listar() throws SQLException {
		List<User> users = new ArrayList<>();
		String sql = "select * from userposjava";
		PreparedStatement statment = connection.prepareStatement(sql);
		ResultSet resultado = statment.executeQuery();

		while (resultado.next()) {
			User user = new User();
			user.setId(resultado.getLong("id"));
			user.setNome(resultado.getString("nome"));
			user.setEmail(resultado.getString("email"));
			users.add(user);
		}
		return users;

	}

	public User buscar(Long id) throws SQLException {
		User user = new User();
		String sql = "select * from userposjava where id = " + id;
		PreparedStatement statment = connection.prepareStatement(sql);
		ResultSet resultado = statment.executeQuery();

		while (resultado.next()) {
			user.setId(resultado.getLong("id"));
			user.setNome(resultado.getString("nome"));
			user.setEmail(resultado.getString("email"));
		}
		return user;
	}

	public void editar(User user) {
		try {
			String sql = "update userposjava set nome = ?, email = ? where id =" + user.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getNome());
			statement.setString(2, user.getEmail());
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
				throw e;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void deletar(Long id) throws SQLException {
		try {
			String sql = "delete from userposjava where id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}
}
