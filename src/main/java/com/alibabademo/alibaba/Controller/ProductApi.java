package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.alibabademo.alibaba.Service.ProductService;
import com.alibabademo.alibaba.Service.ProductService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alibaba")
public class ProductApi {

    @Autowired
    private ProductService productService;

    //(1) Method to get product by Id
    @GetMapping("/getProductById/{Id}")
    public ResponsePojo<Product> getProductById(@PathVariable Long Id){
       return productService.getProductById(Id);
    }

    //(2) Method to get all the products in the database
    @GetMapping("/getAllProducts")
    public ResponsePojo<List<Product>> getAllProducts(){
        return productService.getAllProducts();
    }

    //(3) Method to get Customized products
    @GetMapping("/getCustomizedProducts")
    public ResponsePojo<List<Product>> getCustomizedProducts(){
        return productService.getCustomizedProducts();
    }

    //(4) Method to get products by Colour
    @GetMapping("/getProductByColour/{colour}")
    public ResponsePojo<List<Product>> getProductsByColour(@PathVariable String colour){
        return productService.getProductsByColour(colour);
    }

    //(5) Method to get products by category
    @GetMapping("/getProductByCategory/{category}")
    public ResponsePojo<List<Product>> getProductByCategory(@PathVariable String category){
        return productService.getProductByCategory(category);
    }

    //(6) Method to get products by category
    @GetMapping("/getToyAndHobbies/{searchItem}")
    public ResponsePojo<List<Product>> getToysAndHobbies(@PathVariable String searchItem, String type){
        return productService.getToysAndHobbies(searchItem, type);
    }

    //(7) Method to get products by category
    @GetMapping("/getFashionAccessories/{searchItem}")
    public ResponsePojo<List<Product>> getFashionAccessories(@PathVariable String searchItem, String type){
        return productService.getFashionAccessories(searchItem, type);
    }

    //(8) Method to get products by category
    @GetMapping("/getBeautyPersonalCare/{searchItem}")
    public ResponsePojo<List<Product>> getBeautyAndPersonalCare(@PathVariable String searchItem, String type){
        return productService.getBeautyAndPersonalCare(searchItem, type);
    }

    //(9) Method to get products by category
    @GetMapping("/getConsumerElectronics/{searchItem}")
    public ResponsePojo<List<Product>> getConsumerElectronics(@PathVariable String searchItem, String type){
        return productService.getConsumerElectronics(searchItem, type);
    }

    //(10) Method to get products by category
    @GetMapping("/getApparel/{apparel}")
    public ResponsePojo<List<Product>> getApparel(@PathVariable String apparel, String type){
        return productService.getApparel(apparel, type);
    }

    //(11) Method to get products by category
    @GetMapping("/getSportsEntertainment/{searchItem}")
    public ResponsePojo<List<Product>> getSportsAndEntertainment(@PathVariable String searchItem, String type){
        return productService.getSportsAndEntertainment(searchItem, type);
    }

    //(12) Method to get products by category
    @GetMapping("/getTimePieces")
    public ResponsePojo<List<Product>> getTimepieces(@RequestParam(name = "timePieces", required = false) String timePieces,
                                                     @RequestParam(name = "jewelry", required = false) String jewelry,
                                                     @RequestParam(name = "eyewears", required = false) String eyewears){
        return productService.getTimepieces(timePieces, jewelry, eyewears);
    }

    //(13) Method to get products by category
    @GetMapping("/getHomeAndGarden/{searchItem}")
    public ResponsePojo<List<Product>> getHomeAndGarden(@PathVariable String searchItem, String type){
        return productService.getHomeAndGarden(searchItem, type);
    }

    //(14) Method to get products by category
    @GetMapping("/getMenWear/{searchWord}")
    public ResponsePojo<List<Product>> getMenWear(@PathVariable String searchWord){
        return productService.getMenWear(searchWord);
    }

    //(15) Method to get products by category
    @GetMapping("/getWomenWear/{searchWord}")
    public ResponsePojo<List<Product>> getWomenWear(@PathVariable String searchWord){
        return productService.getWomenWear(searchWord);
    }
    //(16) Method to get products by category
    @GetMapping("/getChildrenWear/{searchWord}")
    public ResponsePojo<List<Product>> getChildrenWear(@PathVariable String searchWord){
        return productService.getChildrenWear(searchWord);
    }

    //(17) Method to get products by category
    @GetMapping("/getPPE/{searchWord}")
    public ResponsePojo<List<Product>> getPPE(@PathVariable String searchWord){
        return productService.getPPE(searchWord);
    }

    //(18) Method to get products by category
    @GetMapping("/getDisinfectants/{disinfectants}")
    public ResponsePojo<List<Product>> getDisinfectants(@PathVariable String disinfectants){
        return productService.getDisinfectants(disinfectants);
    }

    //(19) Method to get products by category
    @GetMapping("/getMedicalDevices/{medicalDevices}")
    public ResponsePojo<List<Product>> getMedicalDevices(@PathVariable String medicalDevices){
        return productService.getMedicalDevices(medicalDevices);
    }

    //(20) Method to get products by category
    @GetMapping("/getMedicalConsumables/{medicalConsumables}")
    public ResponsePojo<List<Product>> getMedicalConsumables(@PathVariable String medicalConsumables){
        return productService.getMedicalConsumables(medicalConsumables);
    }

    //(21) Method to get products by category
    @GetMapping("/getHomeAppliances/{searchItem}")
    public ResponsePojo<List<Product>> getHomeAppliances(@PathVariable String searchItem, String type){
        return productService.getHomeAppliances(searchItem, type);
    }

    //(22) Method to get products by category
    @GetMapping("/getLightning/{searchWord}")
    public ResponsePojo<List<Product>> getLightning(@PathVariable String searchWord){
        return productService.getLightning(searchWord);
    }

    //(23) Method to get products by category
    @GetMapping("/getConstructionTools/{searchItem}")
    public ResponsePojo<List<Product>> getConstruction(@PathVariable String searchItem, String type){
        return productService.getConstruction(searchItem, type);
    }

    //(24) Method to get products by category
    @GetMapping("/getRealEstate/{searchItem}")
    public ResponsePojo<List<Product>> getRealEstate(@PathVariable String searchItem, String type){
        return productService.getRealEstate(searchItem, type);
    }

    //(25) Method to get products by category
    @GetMapping("/getFabricTextile/{searchItem}")
    public ResponsePojo<List<Product>> getFabricTextile(@PathVariable String searchItem, String type){
        return productService.getFabricTextile(searchItem, type);
    }


    //(26) Method to get products by category
    @GetMapping("/getPackagingAndPrinting/{searchItem}")
    public ResponsePojo<List<Product>> getPackagingAndPrinting(@PathVariable String searchItem, String type){
        return productService.getPackagingAndPrinting(searchItem, type);
    }


    //(27) Method to get products by category
    @GetMapping("/getOfficeAndSchoolSupplies/{searchItem}")
    public ResponsePojo<List<Product>> getOfficeAndSchoolSupplies(@PathVariable String searchItem, String type){
        return productService.getOfficeAndSchoolSupplies(searchItem, type);
    }

    //(28) Method to get products by category
    @GetMapping("/getElectricalEquipments/{searchItem}")
    public ResponsePojo<List<Product>> getElectricalEquipments(@PathVariable String searchItem, String type){
        return productService.getElectricalEquipments(searchItem, type);
    }

    //(29) Method to get products by category
    @GetMapping("/getHygieneProducts/{searchItem}")
    public ResponsePojo<List<Product>> getHygieneProducts(@PathVariable String searchItem){
        return productService.getHygieneProducts(searchItem);
    }

    //(30) Method to get products by category
    @GetMapping("/getFragranceDeodorant/{searchItem}")
    public ResponsePojo<List<Product>> getFragranceDeodorant(@PathVariable String searchItem){
        return productService.getFragranceDeodorant(searchItem);
    }

    //(31) Method to get products by category
    @GetMapping("/getPersonalHygieneProducts/{searchWord}")
    public ResponsePojo<List<Product>> getPersonalHygieneProducts(@PathVariable String searchWord){
        return productService.getPersonalHygieneProducts(searchWord);
    }

    //(32) Method to get products by category
    @GetMapping("/getBathroomProducts/{searchWord}")
    public ResponsePojo<List<Product>> getBathroomProducts(@PathVariable String searchWord){
        return productService.getBathroomProducts(searchWord);
    }

    //(33) Method to get products by category
    @GetMapping("/getHomeDecor/{searchWord}")
    public ResponsePojo<List<Product>> getHomeDecor(@PathVariable String searchWord){
        return productService.getHomeDecor(searchWord);
    }

    //(34) Method to get products by category
    @GetMapping("/getHomeStorage/{searchWord}")
    public ResponsePojo<List<Product>> getHomeStorage(@PathVariable String searchWord){
        return productService.getHomeStorage(searchWord);
    }

    //(35) Method to get products by category
    @GetMapping("/getHouseholdCleaningTools/{searchWord}")
    public ResponsePojo<List<Product>> getHouseholdCleaningTools(@PathVariable String searchWord){
        return productService.getHouseholdCleaningTools(searchWord);
    }

    //(36) Method to get New Arrivals
    //***This API is not fully developed with its required features***
    @GetMapping("/newArrivals")
    public ResponsePojo<List<Product>> newArrivals(){
        return productService.newArrivals();
    }

    //(37) Method to get small Commodities products
    @GetMapping("/smallCommodities")
    public ResponsePojo<List<Product>> smallCommodities(){
        return productService.smallCommodities();
    }

    //(38) Method to get products by category
    @GetMapping("/getProductDiscounts")
    public ResponsePojo<List<Product>> getProductDiscounts(){
        return productService.getProductDiscounts();
    }

}
