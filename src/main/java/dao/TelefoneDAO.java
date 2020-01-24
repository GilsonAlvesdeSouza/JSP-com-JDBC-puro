package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.jdbc.SingleConnection;
import model.Telefone;
import model.User;

public class TelefoneDAO {

	private Connection connection;

	public TelefoneDAO() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Telefone telefone) throws SQLException {
		String sql = "INSERT INTO telefoneuser(numero, tipo, id_user)VALUES (?, ?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUser().getId());
			insert.execute();
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
//			connection.close();
		}
	}

	public List<Telefone> listar(Long idUser) throws SQLException {
		List<Telefone> telefones = new ArrayList<>();
		String sql = "select * from telefoneuser as t inner join userposjava as u on u.id = t.id_user where u.id = "
				+ idUser;
		PreparedStatement statment = connection.prepareStatement(sql);
		ResultSet resultado = statment.executeQuery();

		while (resultado.next()) {
			Telefone tel = new Telefone();
			tel.setId(resultado.getLong("id"));
			tel.setNumero(resultado.getString("numero"));
			tel.setTipo(resultado.getString("tipo"));
			tel.setIdUser(resultado.getLong("id_user"));
			telefones.add(tel);
		}
		return telefones;

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
