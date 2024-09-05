package med.voll.api.mapper;

import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.Medico;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper implements  Mapper<Medico, DatosListadoMedico> {

    private final ModelMapper modelMapper;

    public MedicoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DatosListadoMedico mapTo(Medico medico) {
        return modelMapper.map(medico, DatosListadoMedico.class);
    }

    @Override
    public Medico mapFrom(DatosListadoMedico datosListadoMedico) {
        return modelMapper.map(datosListadoMedico, Medico.class);
    }
}