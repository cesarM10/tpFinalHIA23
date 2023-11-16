package ar.edu.unju.fi.tpfinal.controller;

import ar.edu.unju.fi.tpfinal.model.Usuario;
import ar.edu.unju.fi.tpfinal.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final IUsuarioService usuarioService;

    @Autowired
    public LoginController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/login", "/login?error=true"})
    public String cargarHome(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "logout";
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/registro")
    public String getRegistroPage(Model model) {
        model.addAttribute("user", new Usuario());
        return "formulario_login";
    }

    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute(name = "user") Usuario usuario, Model model) {

        if(usuarioService.findUserName(usuario.getUsuario())) {
            model.addAttribute("errorMessage", "El usuario que ingresaste, ya existe.");
            return "formulario_login";
        }

        Usuario newUser = new Usuario();
        newUser.setUsuario(usuario.getUsuario());
        newUser.setPassword(usuario.getPassword());
        newUser.setPerfil("CLIENTE");

        usuarioService.guardarUsuario(newUser);

        return "redirect:/index";
    }
}
