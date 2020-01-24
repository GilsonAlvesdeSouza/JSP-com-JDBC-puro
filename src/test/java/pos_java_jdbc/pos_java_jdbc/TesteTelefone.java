package pos_java_jdbc.pos_java_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import dao.TelefoneDAO;
import dao.UserDAO;
import model.Telefone;
import model.User;

public class TesteTelefone {

	@Test
	@Ignore
	public void salvar() {
		Telefone tel = new Telefone();
		TelefoneDAO teldao = new TelefoneDAO();
		UserDAO udao = new UserDAO();
		tel.setNumero("(62) 99557-3067");
		tel.setTipo("celular");

		try {
			User u = udao.buscar(1L);
			tel.setUser(u);
			teldao.salvar(tel);
			System.out.println("Telefone salvo com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Ocorreu um erro ao salvar o telefone!");
		}
	}

	@Test
	public void listar() throws SQLException {
		TelefoneDAO tdao = new TelefoneDAO();
		List<Telefone> telefones = null;
		try {
			telefones = tdao.listar(1L);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Telefone telefone : telefones) {
			UserDAO udao = new UserDAO();

			User user = udao.buscar(telefone.getIdUser());
			System.out.println(telefone + " / " + user);
		}
	}
}
