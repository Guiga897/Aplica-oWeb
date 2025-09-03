package br.com.aweb.escola.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.escola.model.Aluno;
import br.com.aweb.escola.service.AlunoService;

@Controller
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @GetMapping("/")
    public String home() {
        return "redirect:/alunos";
    }

    @GetMapping("/alunos")
public String list(Model model) {
    var alunos = alunoService.listAll();
    System.out.println("Alunos encontrados: " + alunos); // log
    model.addAttribute("alunos", alunos != null ? alunos : List.of());
    return "list";
}

    @GetMapping("/alunos/new")
    public String newAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "form";
    }

    @PostMapping("/alunos/save")
    public String save(@Valid Aluno aluno, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors())
            return "form";
        alunoService.createAluno(aluno);
        attributes.addFlashAttribute("message", "Aluno salvo com sucesso!");
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("aluno", alunoService.findAluno(id));
            return "form";
        } catch (RuntimeException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alunos";
        }
    }

    @GetMapping("/alunos/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            alunoService.deleteAluno(id);
            attributes.addFlashAttribute("message", "Aluno excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/alunos";
    }
}