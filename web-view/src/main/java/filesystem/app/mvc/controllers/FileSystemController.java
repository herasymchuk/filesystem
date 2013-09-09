package filesystem.app.mvc.controllers;

import filesystem.app.model.entity.File;
import filesystem.app.model.entity.Folder;
import filesystem.app.model.entity.Locatable;
import filesystem.app.mvc.validators.FileValidator;
import filesystem.app.mvc.validators.LocatableValidator;
import filesystem.app.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class FileSystemController
{
    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private FileValidator fileValidator;

    @Autowired
    private LocatableValidator locatableValidator;

    @RequestMapping("/")
    public String foo() {
        return "redirect:fs";
    }

    @RequestMapping(value = "/fs", method = RequestMethod.GET)
    protected ModelAndView get(@RequestParam (required = false) String path, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("filesystem");
        if(path == null) {
            path = "root";
        }
        Locatable item = fileSystemService.getByPath(path);
        if(null != item) {
            if(item.getClass() == File.class) {
                response.setContentType(((File) item).getType());
                response.setContentLength(((File) item).getData().length);
                response.setHeader("Content-Disposition","attachment; filename=\"" + item.getName() +"\"");
                response.getOutputStream().write(((File) item).getData());
                return null;
            } else {
                mav.addObject("itemLinkedPath", getLinkedPathString(item.getFullPath()));
                mav.addObject("item", item);
                mav.addObject("childrenList", fileSystemService.getNearestChildrenByPath(path));
                mav.addObject("newFile", new File(item));
                mav.addObject("newFolder", new Folder(item));
            }
        }  else {
            mav.addObject("errorMessage", "There is an error");
        }
        return mav;
    }

    @RequestMapping(value = "/addFolder", method = RequestMethod.POST)
    public String createItem(@ModelAttribute("newFolder") @Valid Folder newFolder, BindingResult result) {
        locatableValidator.validate(newFolder, result);
        if (result.hasErrors()) {
            //model.addObject("errors", result.getFieldErrors());
            return "filesystem";
        }
        try {
            fileSystemService.create(newFolder);
        } catch (IllegalArgumentException e) {
            return "filesystem";
        }
        return "redirect:/fs?path=" + newFolder.getPath();
    }

    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public String createItem(@ModelAttribute("newFile") File newFile, BindingResult result, @RequestParam("file")  MultipartFile file)  throws IOException {
        fileValidator.validate(file, result);
        if (!result.hasErrors()) {
            newFile.setName(file.getOriginalFilename());
            newFile.setData(file.getBytes());
            newFile.setSize(file.getSize());
            newFile.setType(file.getContentType());
            fileSystemService.create(newFile);
        }
        return "redirect:/fs?path=" + newFile.getPath();
    }

    @RequestMapping(value = "/fs/{id}/{action}", method = RequestMethod.GET)
    public String edit(@PathVariable String id, @PathVariable String action, Model model) {
        Locatable item = fileSystemService.getById(Long.parseLong(id));

        if (action.equals("delete")) {
            fileSystemService.delete(item);
            return "redirect:/fs?path="+ item.getPath();
        }

        if (action.equals("edit")) {
            model.addAttribute("editedItem", item);
        }
        return "filesystem";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String renameItem(@ModelAttribute("editedItem") Locatable editedItem, BindingResult result, @RequestParam String newName) {
        //locatableValidator.validate(editedItem, result);
        if (!isNameValid(newName) || newName.equals(editedItem.getName()))
            return "filesystem";
        try {
            fileSystemService.rename(editedItem, newName);
        } catch (IllegalArgumentException e) {
            return "filesystem";
        }

        return "redirect:/fs?path="+ editedItem.getPath();
    }

    private String getLinkedPathString(String path) {
        StringBuilder result = new StringBuilder("");
        int curItemNameStartIndex = 0;
        int curItemNameEndIndex = path.indexOf("/");
        String itemPath;
        String itemName;
        while(curItemNameEndIndex != -1) {
            itemPath = path.substring(0, curItemNameEndIndex);
            itemName = path.substring(curItemNameStartIndex, curItemNameEndIndex);
            result.append("<a href=\"?path=")
                    .append(itemPath).append("\">")
                    .append(itemName).append("</a> / ");
            curItemNameStartIndex = curItemNameEndIndex + 1;
            curItemNameEndIndex = path.indexOf("/", curItemNameStartIndex);
        }
        itemName = path.substring(curItemNameStartIndex);
        result.append(itemName);
        return result.toString();
    }

    private boolean isNameValid(String name) {
        return !(name == null ||
                name.isEmpty() ||
                name.contains("/") ||
                name.contains("\\") ||
                name.contains("?") ||
                name.contains(","));
    }
}
