package br.com.lojaJava.controller;

import br.com.lojaJava.dao.ProdutoDAO;
import br.com.lojaJava.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoDAO produtoDAO;

    @GetMapping
    public String listarProdutos(Model model){
        List<Produto> produtos = produtoDAO.getProdutos();
        model.addAttribute("produtos", produtos);
        return "lista-produtos";
    }

    @GetMapping("/novo")
    public String mostarFormularioNovo(Model model){
        model.addAttribute("produto", new Produto());
        return "form-produto";
    }

@PostMapping("/salvar")//refatorado
public String salvarProduto(@ModelAttribute Produto produto, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
    try {
        if (produto.getId() > 0) {
            produtoDAO.update(produto);
        } else {
            produtoDAO.save(produto);
        }
        return "redirect:/produtos";
    } catch (br.com.lojaJava.exception.ValidationException e) {
        redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        redirectAttributes.addFlashAttribute("produto", produto); // Mantém os dados preenchidos
        return produto.getId() > 0 ? "redirect:/produtos/editar/" + produto.getId() : "redirect:/produtos/novo";
    } catch (Exception e) {
        // Erros inesperados
        redirectAttributes.addFlashAttribute("mensagemErro", "Erro interno no sistema. Tente novamente mais tarde.");
        return "redirect:/produtos";
    }
}

    @GetMapping("/editar/{id}")
    public String mostraFormularioEditar(@PathVariable int id, Model model){
        Produto produto = produtoDAO.getProduto(id);
        model.addAttribute("produto", produto);
        return "form-produto";
    }

    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable int id){
        produtoDAO.delete(id);
        return "redirect:/produtos";
    }
}
