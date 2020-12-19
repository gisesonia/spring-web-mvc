package br.com.globallabs.springwebmvc.rest;

import br.com.globallabs.springwebmvc.exception.JediNotFoundException;
import br.com.globallabs.springwebmvc.model.Jedi;
import br.com.globallabs.springwebmvc.repository.JediRepository;
import br.com.globallabs.springwebmvc.service.JediService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class JediResource {

    @Autowired      //Substitui a classe repository pela classe service que importa o repository
    private JediService service;

    @GetMapping("/api/jedi")
    public List<Jedi> getAllJedi() {
        return service.findAll();
    }

    @GetMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> getJedi(@PathVariable("id") Long id) {//ResponseEntity do Spring
        final Jedi jedi = service.findById(id);

        return ResponseEntity.ok(jedi);//http status 200
    }

    @PostMapping("/api/jedi")
    @ResponseStatus(HttpStatus.CREATED)
    public Jedi createJedi(@Valid @RequestBody Jedi jedi) {//Aqui é do tipo json por isso @RequestBody
        return service.save(jedi);
    }

    @PutMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> updateJedi(@PathVariable("id") Long id, @Valid @RequestBody Jedi dto){//dto é data transfer object, o objeto recebido json

        final Jedi jedi = service.update(id, dto);

        return ResponseEntity.ok(jedi);
    }

    @DeleteMapping("/api/jedi/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
