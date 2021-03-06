package com.nile.Nile_Product.controller;

import com.nile.Nile_Product.dto.ProductDTO;
import com.nile.Nile_Product.entity.ProductEntity;
import com.nile.Nile_Product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController  {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "/getProductsByCategory/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable("category") String category){

        List<ProductEntity> productEntityList = productService.findByCategory(category);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList) {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(productEntity, productDTO);
            productDTOS.add(productDTO);
        }
        return new ResponseEntity<List<ProductDTO>>(productDTOS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getProductDetailsById/{productId}")
    public ResponseEntity<?> getProductDetailsById(@PathVariable("productId") String productId){

        ProductEntity productEntity = productService.findByProductId(productId);
        if(productEntity == null){
            return new ResponseEntity<String>("No data found", HttpStatus.OK);
        }
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(productEntity, productDTO);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAll(){

        List<ProductEntity> productEntityList = productService.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList) {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(productEntity, productDTO);
            productDTOS.add(productDTO);
        }
        return new ResponseEntity<List<ProductDTO>>(productDTOS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{productId}")
    public ResponseEntity<ProductDTO> delete(@PathVariable("productId") String productId){

        ProductEntity productEntity = productService.delete(productId);
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(productEntity, productDTO);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insert")
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDTO, productEntity);
        productEntity = productService.insert(productEntity);
        BeanUtils.copyProperties(productEntity, productDTO);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insertAll")
    public boolean insertAll(@RequestBody List<ProductEntity> productDTOList){

        List<ProductEntity> productEntityList = new ArrayList<>();
        for(ProductEntity productDTO: productDTOList){
            ProductEntity productEntity = new ProductEntity();
            BeanUtils.copyProperties(productDTO, productEntity);
            productEntityList.add(productEntity);
        };
        return productDTOList.addAll(productEntityList);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAll")
    public void deleteAll(){

        productService.deleteAll();
    }
}
