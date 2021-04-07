package com.api.toolscript.controllers;

import java.util.Optional;
import com.api.toolscript.models.Story;
import com.api.toolscript.models.Module;
import com.api.toolscript.payload.response.MessageResponse;
import com.api.toolscript.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path="/modules")
public class ModuleController {
    @Autowired
    private ModuleRepository moduleRepository;

    //To display all the story's main modules
    @GetMapping(path="")
    public @ResponseBody Iterable<Module> getAllMainModules(@RequestBody Story story) {
        return moduleRepository.findAllMainModulesForStory(story.getId());
    }

    //To display a module
    @GetMapping(path="/{id_module}")
    public @ResponseBody Optional<Module> getModuleById(@PathVariable Integer id_module) {
        if (moduleRepository.findById(id_module).isPresent()) {
            Module module = moduleRepository.findById(id_module).get();
            module.setChildren(moduleRepository.findAllChildrenById(id_module));
            moduleRepository.save(module);
        }
        return moduleRepository.findById(id_module);
    }

    //To create a module
    @PostMapping("/create")
    public Module createModule(@RequestBody Module newModule) {
        return moduleRepository.save(newModule);
    }

    //To edit a module
    @PutMapping(path="/{id_module}/edit")
    public ResponseEntity<?> editModule (@PathVariable Integer id_module, @RequestBody Module newModule) {
        if (id_module.equals(newModule.getId()) && moduleRepository.findById(id_module).isPresent()) {
            Module updatedModule = moduleRepository.findById(id_module).get();
            updatedModule.setName(newModule.getName());
            moduleRepository.save(updatedModule);
            return ResponseEntity.ok(new MessageResponse("Le module a été modifié avec succès !"));
        }
        else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: id du module incohérent ou inexistant !"));
        }
    }

    //To delete a module
    @DeleteMapping("/{id_module}/delete")
    public ResponseEntity<?> deleteModule(@PathVariable Integer id_module) {
        if (moduleRepository.findById(id_module).isPresent()) {
            moduleRepository.delete(moduleRepository.findById(id_module).get());
            return ResponseEntity.ok(new MessageResponse("Le module a été supprimé avec succès !"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: id du module inexistant !"));
        }
    }
}
