package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.domain.TipoDocumento;

class TestClienteJPA {
    
	long clieId = 1234L;
	private final static Logger log = LoggerFactory.getLogger(TestClienteJPA.class);
	
	@Test
	@DisplayName("CrearCliente")
	void aTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente,"El cliente ya existe");
		
		cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(clieId);
		cliente.setDireccion("Avenida Simon Bolivar");
		cliente.setEmail("elloco@loquera.com");
		cliente.setNombre("Roberto Perez");
		cliente.setTelefono("320 80 50");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 2L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
			entityManager.persist(cliente);
        entityManager.getTransaction().commit();	
		
	}

	@Test
	@DisplayName("ModificarCliente")
	void bTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente no existe");
		
		cliente.setActivo("N");
		cliente.setClieId(clieId);
		cliente.setDireccion("Avenida Simon Bolivar");
		cliente.setEmail("elloco@loquera.com");
		cliente.setNombre("Roberto Perez Lopez");
		cliente.setTelefono("320 80 50");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 1L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
			entityManager.merge(cliente);
        entityManager.getTransaction().commit();	
		
	}

	@Test
	@DisplayName("BorrarCliente")
	void cTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente no existe");
		
		entityManager.getTransaction().begin();
			entityManager.remove(cliente);
        entityManager.getTransaction().commit();	
		
	}

	@Test
	@DisplayName("ConsultarTodosLosCliente")
	void dTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		String JPQL = "Select cli From Cliente cli";
		List<Cliente> losClientes = entityManager.createQuery(JPQL).getResultList();
		
		for (Cliente cliente : losClientes) {
			log.info("id: " + cliente.getClieId());
			log.info("nombre: " + cliente.getNombre());
		}
		
		losClientes.forEach(cliente->{
			log.info("id: " + cliente.getClieId());
			log.info("nombre: " + cliente.getNombre());
		});
		
	}

}
