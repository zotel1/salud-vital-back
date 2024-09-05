package med.voll.api.service;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Page<DatosDetalleConsulta> getConsultasPorPacienteId(Long idPaciente, Pageable paginacion){
        List<Consulta> consultasPorId = consultaRepository.findByIdPaciente(idPaciente);

        List<DatosDetalleConsulta> datosDetalleConsultaList = consultasPorId.stream()
                .map(DatosDetalleConsulta::new)
                .toList();

        return new PageImpl<>(datosDetalleConsultaList,paginacion,datosDetalleConsultaList.size());
    }

    public DatosDetalleConsulta getConsultaPorId(Long id){
        return new DatosDetalleConsulta(consultaRepository.findById(id).get());
    }
}