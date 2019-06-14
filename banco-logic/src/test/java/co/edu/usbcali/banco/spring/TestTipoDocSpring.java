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

import co.edu.usbcali.banco.domain.TipoDocumento;
import co.edu.usbcali.banco.domain.TipoUsuario;
import co.edu.usbcali.banco.domain.Usuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestTipoDocSpring {

	@PersistenceContext
	EntityManager entityManager;

	private final static Logger log = LoggerFactory.getLogger(TestTipoDocSpring.class);
	Long tipoDoc = 4L;
	
	@Test
	@DisplayName("CrearTipoDocumentos")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)

	void aTest() {
		
		assertNotNull(entityManager,"El entityManager es nulo");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tipoDoc);
		assertNull(tipoDocumento,"El tipo de documento ya existe");
		
		tipoDocumento = new TipoDocumento();
		tipoDocumento.setTdocId(tipoDoc);
		tipoDocumento.setActivo("S");
		tipoDocumento.setNombre("PASAPORTE");

		entityManager.persist(tipoDocumento);

	}

	@Test
	@DisplayName("ModificarTipoDocumentos")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void bTest() {
		assertNotNull(entityManager,"El entityManager es nulo");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tipoDoc);
		assertNotNull(tipoDocumento,"El tipo de docuemnto no existe");
		
		tipoDocumento.setTdocId(tipoDoc);
		tipoDocumento.setActivo("N");
		tipoDocumento.setNombre("PASAPORTE");
		
		entityManager.merge(tipoDocumento);

	}

	@Test
	@DisplayName("BorrarTipoDocumentos")
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	void cTest() {
		assertNotNull(entityManager,"El entityManager es nulo");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tipoDoc);
		assertNotNull(tipoDocumento,"El tipo de documento no existe");
		
		entityManager.remove(tipoDocumento);

	}

	@Test
	@DisplayName("ConsultarTodosTipoDocumentos")
	@Transactional(readOnly=true)
	void dTest() {
		assertNotNull(entityManager,"El entityManager es nulo");

		String JPQL = "Select tipoDoc From TipoDocumento tipoDoc";
		List<TipoDocumento> tiposDctos = entityManager.createQuery(JPQL).getResultList();

		tiposDctos.forEach(tiposDoctos->{
			log.info("tdocId: " + tiposDoctos.getTdocId());
			log.info("nombre: " + tiposDoctos.getNombre());
			log.info("activo: " + tiposDoctos.getActivo());
		});

	}	
	
}
