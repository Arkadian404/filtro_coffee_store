package com.data.filtro.api;

import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Feedback;
import com.data.filtro.model.Product;
import com.data.filtro.service.FeedbackService;
import com.data.filtro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackAPIController {

    @Autowired
    FeedbackService feedbackService;


    @Autowired
    ProductService productService;

    @GetMapping("/getAll/{productId}")
    public ResponseEntity<?> getAllFeedback(@PathVariable int productId) {
        List<Feedback> feedbacks = feedbackService.getAllFeedBackByProductId(productId);
        if (feedbacks == null) {
            return new ResponseEntity<>("Ko co id can tim", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        }

    }


    @PutMapping("/sentfeedback/{productId}")
    public ResponseEntity<?> addFeedbackToProduct(@PathVariable int productId, @RequestBody Feedback feedback) {

        Product product = productService.getProductById(productId);
        if (product != null) {
            feedback.setProduct(product);
            Feedback newFeedback = feedbackService.addFeedback(feedback);
            return new ResponseEntity<>(newFeedback, HttpStatus.OK);
        } else {
            String message = "Product not found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
