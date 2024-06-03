package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.backend.models.CartItem;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductDetail;
import vn.edu.iuh.fit.backend.repositories.ProductImageRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;
import vn.edu.iuh.fit.backend.services.ProductServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
//@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @Autowired
    private ProductRepository productRepository;

    private ProductImageRepository productImageRepository;


    @GetMapping("/product")
    public String productListing (Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<ProductDetail> productPage = productServices.findAll(currentPage-1, pageSize, "productId", "asc");

        model.addAttribute("productPage", productPage);
        int totalPages = productPage.getTotalPages();
        if(totalPages >0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute(("pageNumbers"),pageNumbers);
        }
        return "Product/Product";
    }


    @GetMapping("/{productId}/detail")
    public String showProductDetails(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productServices.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDetail productDetail = productServices.ProductDetail(product);

            model.addAttribute("productDetail", productDetail);
            return "Product/DetailProduct";
        } else {

            return "ProductNotFound";
        }
    }

    @PostMapping("/{productId}/addToCart")
    public String addToCart(
            @PathVariable("productId") Long productId,
            @RequestParam("quantity") int quantity,
            HttpSession session
    ) {
        Optional<Product> productOptional = productServices.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDetail productDetail = productServices.ProductDetail(product);

            CartItem cartItem = new CartItem();
            cartItem.setProductDetail(productDetail);
            cartItem.setQuantity(quantity);

            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }

            cartItems.add(cartItem);
            session.setAttribute("cart", cartItems);

            return "redirect:/product";
        } else {
            return "ProductNotFound"; // Trả về trang thông báo không tìm thấy sản phẩm nếu không có sản phẩm
        }
    }


    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

        double total = 0;
        double totalPrice = 0;

        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                double itemPrice = item.getProductDetail().getPrice() * item.getQuantity();
                total += itemPrice;
            }
        }

        totalPrice = total + 5;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("totalPrice", totalPrice);
        return "Product/cart";
    }



    @GetMapping("/cart/remove/{productId}")
    public String removeItemFromCart(@PathVariable("productId") Long productId, HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

        if (cartItems != null) {
            cartItems = cartItems.stream()
                    .filter(item -> !Objects.equals(item.getProductDetail().getId(), productId))
                    .collect(Collectors.toList());

            session.setAttribute("cart", cartItems);
        }

        return "redirect:/cart";
    }



}
