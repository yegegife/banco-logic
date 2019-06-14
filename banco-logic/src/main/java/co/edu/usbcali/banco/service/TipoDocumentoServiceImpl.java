package co.edu.usbcali.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.domain.TipoDocumento;
import co.edu.usbcali.banco.repository.TipoDocumentoRepository;

@Service
@Scope("singleton")
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository; 
	
	@Override
	@Transactional(readOnly=true)
	public TipoDocumento findById(Long id) {

		return tipoDocumentoRepository.findById(id);
		
	}

}
