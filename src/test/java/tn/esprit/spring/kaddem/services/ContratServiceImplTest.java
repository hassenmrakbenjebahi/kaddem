package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllContrats() {
        List<Contrat> mockContrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratRepository.findAll()).thenReturn(mockContrats);

        List<Contrat> result = contratService.retrieveAllContrats();
        assertEquals(2, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testUpdateContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.updateContrat(contrat);
        assertNotNull(result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testAddContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.addContrat(contrat);
        assertNotNull(result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testRetrieveContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(1);
        assertNotNull(result);
        verify(contratRepository, times(1)).findById(1);
    }

    @Test
    void testRemoveContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        contratService.removeContrat(1);
        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void testAffectContratToEtudiant() {
        Etudiant etudiant = new Etudiant();
        Contrat contrat = new Contrat();
        Set<Contrat> contrats = new HashSet<>();
        etudiant.setContrats(contrats);

        when(etudiantRepository.findByNomEAndPrenomE("John", "Doe")).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(1, "John", "Doe");
        assertNotNull(result);
        assertEquals(etudiant, result.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }


}
