package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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

import co.edu.usbcali.banco.domain.TipoUsuario;
import co.edu.usbcali.banco.domain.Usuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestUsuarioSpring {

	@PersistenceContext
	EntityManager entityManager;

	private final static Logger log = LoggerFactory.getLogger(TestUsuarioSpring.class);
	String usuarioId = "ygiron"; 
	
	@Test
	@DisplayName("CrearUsuario")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void aTest() {

		assertNotNull(entityManager,"El entityManager es nulo");
		
		Usuario usuario = entityManager.find(Usuario.class, usuarioId);
		assertNull(usuario,"El usuario ya existe");
		
		usuario = new Usuario();
		usuario.setUsuUsuario(usuarioId);
		usuario.setClave("1234");
		BigDecimal ident = new BigDecimal(94497505);
		usuario.setIdentificacion(ident);
		usuario.setNombre("Yefferson Giron");
		usuario.setActivo("S");
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 3L);
		usuario.setTipoUsuario(tipoUsuario);
		assertNotNull(tipoUsuario,"El tipo de usuario no existe");
		
		entityManager.persist(usuario);
		
	}

	@Test
	@DisplayName("ModificarUsuario")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void bTest() {
		assertNotNull(entityManager,"El entityManager es nulo");

		Usuario usuario = entityManager.find(Usuario.class, usuarioId);
		assertNotNull(usuario,"El usuario no existe");
		
		usuario.setUsuUsuario(usuarioId);
		usuario.setClave("9876");
		BigDecimal ident = new BigDecimal(94497505);
		usuario.setIdentificacion(ident);
		usuario.setNombre("Yefferson Giron");
		usuario.setActivo("S");
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 2L);
		usuario.setTipoUsuario(tipoUsuario);
		assertNotNull(tipoUsuario,"El tipo de usuario no existe");

		entityManager.merge(usuario);

	}

	@Test
	@DisplayName("BorrarUsuario")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void cTest() {
		assertNotNull(entityManager,"El entityManager es nulo");

		Usuario usuario = entityManager.find(Usuario.class, usuarioId);
		assertNotNull(usuario,"El usuario no existe");
		
		entityManager.remove(usuario);

	}

	@Test
	@DisplayName("ConsultarTodosUsuarios")
	@Transactional(readOnly=true)
	void dTest() {
		assertNotNull(entityManager,"El entityManager es nulo");

		String JPQL = "Select usr From Usuario usr";
		List<Usuario> losUsuarios = entityManager.createQuery(JPQL).getResultList();

		losUsuarios.forEach(usuarios->{
			log.info("usuUsuario: " + usuarios.getUsuUsuario());
			log.info("identificacion: " + usuarios.getIdentificacion());
			log.info("nombre: " + usuarios.getNombre());
		});

	}	
}
