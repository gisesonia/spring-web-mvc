package br.com.globallabs.springwebmvc.controller;

import br.com.globallabs.springwebmvc.model.Jedi;
import br.com.globallabs.springwebmvc.repository.JediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;//Versões posteriores a 8, tem List.of()
import javax.validation.Valid;//dependencia adicionado no Gradle - implementation 'org.springframework.boot:spring-boot-starter-validation'

@Controller
public class JediController {

    //Injeção de dependência. Está criando uma instância da classe repository. Acessa o método getAllJedi()
    @Autowired
    private JediRepository repository;

    @GetMapping("/jedi")
    public ModelAndView jedi(){
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jedi");

        //final Jedi luke = new Jedi("Luke","Skywalker"); Testando adicionando direto
        //modelAndView.addObject("allJedi", List.of(luke));

        modelAndView.addObject("allJedi", repository.getAllJedi());

        return modelAndView;
    }

    @GetMapping("/new-jedi")
    public ModelAndView newJedi(){

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-jedi");

        modelAndView.addObject("jedi", new Jedi());
        return modelAndView;
    }
    //Post
    @PostMapping("/jedi")
    public String createJedi(@Valid @ModelAttribute Jedi jedi, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return "new-jedi";
        }

        repository.add(jedi);

        redirectAttributes.addFlashAttribute("message","Jedi cadastrado com sucesso.");
        return "redirect:jedi";
    }
}
