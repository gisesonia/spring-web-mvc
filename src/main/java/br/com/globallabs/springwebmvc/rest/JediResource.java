package br.com.globallabs.springwebmvc.rest;

import br.com.globallabs.springwebmvc.exception.JediNotFoundException;
import br.com.globallabs.springwebmvc.model.Jedi;
import br.com.globallabs.springwebmvc.repository.JediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class JediResource {
    @Autowired
    private JediRepository repository;

    @GetMapping("/api/jedi")
    public List<Jedi> getAllJedi() {
        return repository.findAll();
    }

    /* Rota por id
     @GetMapping("/api/jedi/{id}")
      public Jedi getJedi(@PathVariable("id") Long id){
          final Optional<Jedi> jedi = repository.findById(id);

          if(jedi.isPresent()){
              return jedi.get();
          } else {
              throw new JediNotFoundException();
          }
      }*/
    @GetMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> getJedi(@PathVariable("id") Long id) {//ResponseEntity do Spring
        final Optional<Jedi> jedi = repository.findById(id);

        if (jedi.isPresent()) {
            return ResponseEntity.ok(jedi.get());//http status 200
        } else {
            return ResponseEntity.notFound().build();// status code 404
        }
    }

    @PostMapping("/api/jedi")
    @ResponseStatus(HttpStatus.CREATED)
    public Jedi createJedi(@Valid @RequestBody Jedi jedi) {//Aqui é do tipo json por isso @RequestBody
        return repository.save(jedi);
    }

    @PutMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> updateJedi(@PathVariable("id") Long id, @Valid @RequestBody Jedi dto){//dto é data transfer object, o objeto recebido json

        final Optional<Jedi> jediEntity = repository.findById(id);
        final Jedi jedi;

        if(jediEntity.isPresent()){
            jedi = jediEntity.get();
        }else {
            return ResponseEntity.notFound().build();
        }
        //jedi é do banco de dados e dto é do json e são inseridos no banco com o set
        jedi.setName(dto.getName());
        jedi.setLastName(dto.getLastName());

        return ResponseEntity.ok(repository.save(jedi));
    }

    @DeleteMapping("/api/jedi/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
       final Optional<Jedi> jedi = repository.findById(id);

       if(jedi.isPresent()){
           repository.delete(jedi.get());
           return ResponseEntity.noContent().build(); //404
       } else {
           return  ResponseEntity.notFound().build();
       }
    }
}
