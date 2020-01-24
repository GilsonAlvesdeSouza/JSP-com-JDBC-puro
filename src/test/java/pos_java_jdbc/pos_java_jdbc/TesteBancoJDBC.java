package pos_java_jdbc.pos_java_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import conexao.jdbc.SingleConnection;
import dao.UserDAO;
import model.User;

public class TesteBancoJDBC {

	@Test
	@Ignore
	public void initBanco() {
		SingleConnection.getConnection();
	}

	@Test
//	@Ignore
	public void salvar() {
		UserDAO udao = new UserDAO();
		User user = new User();
		user.setNome("Yago Alves");
		user.setEmail("Yago@gmail.com");
		try {
			udao.salvar(user);
			System.out.println("Dados Salvos com Sucesso!");// so aparece essa mensagem se der certo
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao salvar os dados");// so aparece essa mensagem se der errado
			e.printStackTrace();
		}
		listar();

	}

	@Test
	@Ignore
	public void listar() {
		UserDAO udao = new UserDAO();
		List<User> users = null;
		try {
			users = udao.listar();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao listar os dados!");
			e.printStackTrace();
		}
		if (users == null) {
			System.out.println("Nenhum dado encontrado!");
		} else {
			for (User u : users) {
				System.out.println(u.getId());
				System.out.println(u.getNome());
				System.out.println(u.getEmail());
				System.out.println();
			}
		}
	}

	@Test
	@Ignore
	public void buscar() {
		UserDAO udao = new UserDAO();
		User user = new User();
		try {
			user = udao.buscar(1L);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(user.getNome());
	}

	@Test
	@Ignore
	public void editar() {
		UserDAO udao = new UserDAO();
		User user = new User();
		user.setId(3L);
		user.setNome("Yago Alves Franca");
		user.setEmail("yagoalvesfranca2008@gmail.com");
		try {
			udao.editar(user);
			System.out.println("Dados Alterados com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ocorreu um erro al tentar alterar os dados!");
		}
		listar();

	}

	@Test
	@Ignore
	public void remover() {
		UserDAO udao = new UserDAO();
		try {
			User user = udao.buscar(5L);
			if (user.getId() == null) {
				System.out.println("Usuário não existe!");
			} else {
				udao.deletar(5L);
				System.out.println("Usuário removido com sucesso!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Ocorreu um erro ao Deletar os dados");
		}
		listar();
	}

}
