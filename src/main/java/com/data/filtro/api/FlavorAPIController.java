package com.data.filtro.api;

import com.data.filtro.model.Category;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Flavor;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.FlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flavor")
public class FlavorAPIController {

    @Autowired
    FlavorService flavorService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        Flavor flavor = flavorService.getFlavorById(id);
        if (flavor == null) {
            String message = "No flavor found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flavor, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Flavor> flavors = flavorService.getAll();
        if (flavors == null || flavors.isEmpty()) {
            String message = "No flavors found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flavors, HttpStatus.OK);
    }
}
