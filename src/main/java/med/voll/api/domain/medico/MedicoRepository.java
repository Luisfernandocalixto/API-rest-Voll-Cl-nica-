package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

//    @Query(value = """
//            select m.* from medicos m
//            where m.activo = 1
//            and m.especialidad = :especialidad
//            and m.id not in(
//                select c.medico_id from Consultas c
//                where
//                c.fecha = :fecha
//            )
//            order by rand()
//            limit 1
//            """, nativeQuery = true)
//    Medico seleccionarMedicoConEspecialidadEnFecha(@Param("especialidad") Especialidad especialidad, @Param("fecha") LocalDateTime fecha);

    @Query(value = """
            select m.* from medicos m
            where m.activo= 1 
            and
            m.especialidad=:especialidad 
            and
            m.id not in(  
                select c.medico_id from consultas c
                where
                c.fecha=:fecha
            )
            order by rand()
            limit 1
            """, nativeQuery = true)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);



    @Query( """
            select m.activo 
            from Medico m
            where m.id=:idMedico
            """)
    Boolean findActivoById( Long idMedico);
}
