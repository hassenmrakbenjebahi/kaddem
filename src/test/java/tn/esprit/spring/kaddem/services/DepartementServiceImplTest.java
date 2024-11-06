package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    private Departement departement;

    @BeforeEach
    void setUp() {
        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Informatique");
    }

    @Test
    void testRetrieveAllDepartements() {
        // Arrange
        List<Departement> departements = Arrays.asList(departement);
        when(departementRepository.findAll()).thenReturn(departements);

        // Act
        List<Departement> result = departementService.retrieveAllDepartements();

        // Assert
        assertEquals(departements, result);
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testAddDepartement() {
        // Arrange
        when(departementRepository.save(departement)).thenReturn(departement);

        // Act
        Departement result = departementService.addDepartement(departement);

        // Assert
        assertEquals(departement, result);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testUpdateDepartement() {
        // Arrange
        when(departementRepository.save(departement)).thenReturn(departement);

        // Act
        Departement result = departementService.updateDepartement(departement);

        // Assert
        assertEquals(departement, result);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testRetrieveDepartement() {
        // Arrange
        when(departementRepository.findById(departement.getIdDepart())).thenReturn(Optional.of(departement));

        // Act
        Departement result = departementService.retrieveDepartement(departement.getIdDepart());

        // Assert
        assertEquals(departement, result);
        verify(departementRepository, times(1)).findById(departement.getIdDepart());
    }

    @Test
    void testDeleteDepartement() {
        // Arrange
        when(departementRepository.findById(departement.getIdDepart())).thenReturn(Optional.of(departement));
        doNothing().when(departementRepository).delete(departement);

        // Act
        departementService.deleteDepartement(departement.getIdDepart());

        // Assert
        verify(departementRepository, times(1)).delete(departement);
    }
}
