//package com.br.foodfacil.controllers;
//
//import com.br.foodfacil.services.impl.SalgadoServiceImpl;
//import utils.pitslify.api.AppUtils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@CrossOrigin(origins = {"http://localhost:5173",
//        "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app",
//        "https://food-facil-painel-admin.vercel.app", "https://foodfacil-website.vercel.app"})
//@RestController
//@RequestMapping(AppUtils.baseUrl + "/salgado")
//
//
//public class SalgadoController {
//
//    @Autowired
//    SalgadoServiceImpl salgadoServiceImpl;
//
//    @DeleteMapping()
//    ResponseEntity<Object> deletaTudo(){
//       return salgadoServiceImpl.deleteAll();
//    }
//
////    @PostMapping
////    ResponseEntity<Object> registerSalgado(
////            @RequestBody SalgadoRequestDto salgadoRequestDto){
////
////        return salgadoServiceImpl.registrar(salgadoRequestDto);
////    }
//
////    @PutMapping("/{id}")
////    ResponseEntity<Object> editaSalgado(
////            @PathVariable String id,
////            @RequestBody SalgadoRequestEditDto salgadoRequestEditDto){
////
////        return salgadoServiceImpl.editaSalgado(salgadoRequestEditDto, id);
////    }
//
////    @DeleteMapping("/{id}")
////    ResponseEntity<Object> excluiSalgado(
////            @PathVariable String id){
////
////        return salgadoServiceImpl.excluiSalgado(id);
////    }
//
//    @GetMapping()
//    ResponseEntity<Object> getAll(){
//        return salgadoServiceImpl.salgadosList();
//    }
//
//}
