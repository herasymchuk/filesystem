package filesystem.app.mvc.controllers;

import filesystem.app.model.entity.File;
import filesystem.app.model.entity.Folder;
import filesystem.app.model.entity.Locatable;
import filesystem.app.model.utils.Path;
import filesystem.app.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileSystemController
{
    @Autowired
    private FileSystemService fileSystemService;

    @RequestMapping("/")
    public String foo() {
        return "redirect:fs";
    }

    @RequestMapping(value = "/fs", method = RequestMethod.GET)
    protected ModelAndView get(@RequestParam (required = false) String path){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("filesystem");
        if(path == null) {
            path = "toor";
        }
        Locatable item = fileSystemService.getByPath(path);
        mav.addObject("item", item);
        mav.addObject("childrenList", fileSystemService.getNearestChildrenByPath(path));
        mav.addObject("newFile", new File());
        //Locatable newFolder = new Folder();
        //newFolder.setParent(item);
        mav.addObject("newFolder", new Folder((Folder)item));
        return mav;
    }

    @RequestMapping(value = "/fs/create", method = RequestMethod.POST)
    public String createItem(@ModelAttribute("newFolder") Locatable newFolder, BindingResult result) {
        //userValidator.validate(user, result);
        if (result.hasErrors())
            return "filesystem";
        //newFolder.setParent(parentItem);
        fileSystemService.create(newFolder);
        return "redirect:/fs?path=" + newFolder.getParentPath();
    }

    @RequestMapping(value = "/fs/{id}/{action}", method = RequestMethod.GET)
    public String edit(@PathVariable String id, @PathVariable String action, Model model) {
        Locatable item = fileSystemService.getById(Long.parseLong(id));

        if (action.equals("delete")) {
            fileSystemService.delete(item);
            return "redirect:/fs?path="+ item.getParentPath();
        }

        if (action.equals("edit")) {
            model.addAttribute("editItem", item);
        }
        return "filesystem";
    }
}
