package ru.itis.technicalstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.technicalstore.dto.request.product.ProductRequestDto;
import ru.itis.technicalstore.entity.Category;
import ru.itis.technicalstore.entity.Option;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.entity.Review;
import ru.itis.technicalstore.service.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final OptionService optionService;
    private final ReviewService reviewService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Value("${image.directory}")
    private String imageDirectory;

    @GetMapping
    public String getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String categoryName,
            Model model
    ) {
        List<Product> products = productService.getProducts(categoryId, keyword, categoryName);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/{id}")
    public String getProduct(
            @PathVariable long id,
            Model model
    ) {
        Product product = productService.getSingleProduct(id);
        List<Option> options = optionService.getOptionsByCategory(product.getCategory());
        List<Review> reviews = reviewService.getPublishedReviews(product);

        model.addAttribute("product", product);
        model.addAttribute("options", options);
        model.addAttribute("reviews", reviews);
        return "product";
    }

    @GetMapping("/add")
    public String createProduct(
            @RequestParam(required = false) Long categoryId,
            Model model) {
        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            List<String> optionValues = new ArrayList<>();
            for (int i = 0; i < category.getOptions().size(); i++) {
                optionValues.add("");
            }
            model.addAttribute("optionValues", optionValues);
            model.addAttribute("options", optionService.getOptionsByCategory(category));
            model.addAttribute("category", category);
        } else {
            model.addAttribute("categories", categoryService.getCategories());
        }
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("product", new ProductRequestDto());
        return "product_add";
    }

    @PostMapping("/add")
    public String saveCreatedProduct(
            @RequestParam long categoryId,
            @RequestParam List<String> optionValues,
            ProductRequestDto productDto,
            RedirectAttributes ra) throws IOException {

        MultipartFile file = productDto.getFile();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(imageDirectory);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(imageDirectory + "/" + resultFilename));
            productDto.setFilename(resultFilename);
        }

        productService.addProduct(categoryId, productDto, optionValues);
        ra.addFlashAttribute("message", "Товар был успешно добавлен");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String updateProduct(
            @PathVariable("id") Long id,
            Model model) {
        Product product = productService.getSingleProduct(id);
        List<Option> options = optionService.getOptionsByCategory(product.getCategory());

        if (product.getUser().equals(userService.getUser()) || userService.isAdmin()) {
            model.addAttribute("product", product);
            model.addAttribute("options", options);
            return "product_update";
        } else {
            return "redirect:/forbidden";
        }
    }

    @PostMapping("/edit")
    public String saveUpdatedProduct(
            @RequestParam long productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) List<String> updatedValues,
            RedirectAttributes ra) {
        Product product = productService.getSingleProduct(productId);
        if (product.getUser().equals(userService.getUser()) || userService.isAdmin()) {
            productService.updateProduct(productId, name, price, updatedValues);
            ra.addFlashAttribute("message", "Товар '" + name + "' был обновлен");
            return "redirect:/products";
        } else {
            return "redirect:/forbidden";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") Long id,
            RedirectAttributes ra
    ) {
        Product product = productService.getSingleProduct(id);
        if (product.getUser().equals(userService.getUser()) || userService.isAdmin()) {
            productService.deleteProduct(id);
            ra.addFlashAttribute("message", "Товар с id: " + id + " был удален");
            return "redirect:/products";
        } else {
            return "redirect:/forbidden";
        }
    }
}
