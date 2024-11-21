package com.example.demo.Controller;

import com.example.demo.Converter.PetConventer;
import com.example.demo.Pet;
import com.example.demo.Service.PetService;
import com.example.demo.model.PetDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;
    private final PetConventer petConventer;

    public PetController(
            PetService petService,
            PetConventer petConventer
    ) {
        this.petService = petService;
        this.petConventer = petConventer;
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(
            @RequestBody @Valid PetDto petDto
    )
    {
       var savedPet = petService.createPet(petConventer.toPet(petDto));

       return ResponseEntity.ok(petConventer.toPetDto(savedPet));

    }



    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(
            @PathVariable("id") @Valid Long id,
            @RequestBody @Valid PetDto petDto
    ) {
        var petToUpdate = new Pet(
                id,
                petDto.userId(),
                petDto.name()
        );

        var updatedPet = petService.updatePet(id,petToUpdate);

        return ResponseEntity.ok(petConventer.toPetDto(updatedPet));
    }

    @GetMapping("/{id}")
    public Optional<Pet> getPetById(
            @PathVariable("id") @Valid Long id
    ) {
       return Optional.ofNullable(petService.findPetById(id))
               .orElseThrow(() -> new RuntimeException("Pet with id = %s, not found"
                       .formatted(id)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
           @PathVariable("id") @Valid Long id
    ){
        log.info("Received request to delete pet with id={}", id);
        petService.deletePet(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Pet> getAllPets() {
         return petService.getAllPets();
    }

}
