package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestClienteSpring {

	@PersistenceContext
	EntityManager entityManager;
	
	long clieId = 1234L;
	private final static Logger log = LoggerFactory.getLogger(TestClienteSpring.class);
	
	@Test
	@DisplayName("CrearCliente")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void aTest() {
		assertNotNull(entityManager);

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
		
		entityManager.persist(cliente);

	}

	@Test
	@DisplayName("ModificarCliente")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void bTest() {
		assertNotNull(entityManager);

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente no existe");
		
		cliente.setActivo("N");
		cliente.setClieId(clieId);
		cliente.setDireccion("Avenida Simon Bolivar");
		cliente.setEmail("elloco@loquera.com");
		cliente.setNombre("Roberto Perez");
		cliente.setTelefono("320 80 50");
		
		
		entityManager.merge(cliente);

	}

	@Test
	@DisplayName("BorrarCliente")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void cTest() {
		assertNotNull(entityManager);

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente no existe");
		
		entityManager.remove(cliente);

	}

	@Test
	@DisplayName("ConsultarTodos")
	@Transactional(readOnly=true)
	void dTest() {
		assertNotNull(entityManager,"El entityManager es nulo");

		String JPQL = "Select cli From Cliente cli";
		List<Cliente> losClientes = entityManager.createQuery(JPQL).getResultList();

		losClientes.forEach(cliente->{
			log.info("id: " + cliente.getClieId());
			log.info("nombre: " + cliente.getNombre());
		});

	}

}
