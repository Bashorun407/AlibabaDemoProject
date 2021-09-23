package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.alibabademo.alibaba.Service.ProductService;
import com.alibabademo.alibaba.Service.ProductService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alibaba")
public class ProductApi {

    @Autowired
    private ProductService productService;

    //(1) Method to get product by Id
    public ResponsePojo<Product> getProductById(Long Id){
       return productService.getProductById(Id);
    }

    //(2) Method to get all the products in the database
    public ResponsePojo<List<Product>> getAllProducts(){
        return productService.getAllProducts();
    }

    //(3) Method to get Customized products
    public ResponsePojo<List<Product>> getCustomizedProducts(){
        return productService.getCustomizedProducts();
    }

    //(4) Method to get products by Colour
    public ResponsePojo<List<Product>> getProductsByColour(String colour){
        return productService.getProductsByColour(colour);
    }

    //(5) Method to get products by category
    public ResponsePojo<List<Product>> getProductByCategory(String category){
        return productService.getProductByCategory(category);
    }

    //(6) Method to get New Arrivals
    public ResponsePojo<List<Product>> newArrivals(){
        return productService.newArrivals();
    }

    //(7) Method to get Ready-To-Ship Products
    public ResponsePojo<List<Product>> readyToShipProducts(){
        return productService.readyToShipProducts();
    }

    //(8) Method to get Weekly Deals
    public ResponsePojo<List<Product>> weeklyDeals(){
        return productService.weeklyDeals();
    }

    //(9) Method to get small Commodities products
    public ResponsePojo<List<Product>> smallCommodities(){
        return productService.smallCommodities();
    }

    //(10) Method to get Top Ranking Products
    public ResponsePojo<List<Product>> topRankingProducts(){
        return productService.topRankingProducts();
    }

    //(11) Method to get top products by a company
    public ResponsePojo<List<Product>> topProductsByCompany(String companyName){
        return productService.topProductsByCompany(companyName);
    }

    //(12) Method to get products by category
    public ResponsePojo<List<Product>> getConsumerElectronics(String searchItem, String type){
        return productService.getConsumerElectronics(searchItem, type);
    }

    //(13) Method to get products by category
    public ResponsePojo<List<Product>> getApparel(String apparel, String type){
        return productService.getApparel(apparel, type);
    }

    //(14) Method to get products by category
    public ResponsePojo<List<Product>> getSportsAndEntertainment(String searchItem, String type){
        return productService.getSportsAndEntertainment(searchItem, type);
    }

    //(15) Method to get products by category
    public ResponsePojo<List<Product>> getTimepieces(String timePieces, String jewelry, String eyewears){
        return productService.getTimepieces(timePieces, jewelry, eyewears);
    }

    //(16) Method to get products by category
    public ResponsePojo<List<Product>> getHomeAndGarden(String searchItem, String type){
        return productService.getHomeAndGarden(searchItem, type);
    }

    //(17) Method to get products by category
    public ResponsePojo<List<Product>> getFashionAccessories(String searchItem, String type){
        return productService.getFashionAccessories(searchItem, type);
    }

    //(18) Method to get products by category
    public ResponsePojo<List<Product>> getBeautyAndPersonalCare(String searchItem, String type){
        return productService.getBeautyAndPersonalCare(searchItem, type);
    }

    //(19) Method to get products by category
    public ResponsePojo<List<Product>> getMenWear(String searchWord){
        return productService.getMenWear(searchWord);
    }

    //(20) Method to get products by category
    public ResponsePojo<List<Product>> getWomenWear(String searchWord){
        return productService.getWomenWear(searchWord);
    }

    //(21) Method to get products by category
    public ResponsePojo<List<Product>> getChildrenWear(String searchWord){
        return productService.getChildrenWear(searchWord);
    }

    //(22) Method to get products by category
    public ResponsePojo<List<Product>> getPPE(String searchItem){
        return productService.getPPE(searchItem);
    }

    //(23) Method to get products by category
    public ResponsePojo<List<Product>> getDisinfectants(String disinfectants){
        return productService.getDisinfectants(disinfectants);
    }

    //(24) Method to get products by category
    public ResponsePojo<List<Product>> getMedicalDevices(String medicalDevices){
        return productService.getMedicalDevices(medicalDevices);
    }

    //(25) Method to get products by category
    public ResponsePojo<List<Product>> getMedicalConsumables(String medicalConsumables){
        return productService.getMedicalConsumables(medicalConsumables);
    }

    //(26) Method to get products by category
    public ResponsePojo<List<Product>> getHomeAppliances(String homeAppliances){
        return productService.getHomeAppliances(homeAppliances);
    }

    //(27) Method to get products by category
    public ResponsePojo<List<Product>> getLightning(String searchItem){
        return productService.getLightning(searchItem);
    }

    //(28) Method to get products by category
    public ResponsePojo<List<Product>> getConstruction(String searchItem, String type){
        return productService.getConstruction(searchItem, type);
    }

    //(29) Method to get products by category
    public ResponsePojo<List<Product>> getRealEstate(String searchItem, String type){
        return productService.getRealEstate(searchItem, type);
    }

    //(30) Method to get products by category
    public ResponsePojo<List<Product>> getFabricTextile(String searchItem, String type){
        return productService.getFabricTextile(searchItem, type);
    }

    //(31) Method to get products by category
    public ResponsePojo<List<Product>> getHomeAppliances(String searchItem, String type){
        return productService.getHomeAppliances(searchItem, type);
    }

    //(32) Method to get products by category
    public ResponsePojo<List<Product>> getPackagingAndPrinting(String searchItem, String type){
        return productService.getPackagingAndPrinting(searchItem, type);
    }

    //(33) Method to get products by category
    public ResponsePojo<List<Product>> getOfficeAndSchoolSupplies(String searchItem, String type){
        return productService.getOfficeAndSchoolSupplies(searchItem, type);
    }

    //(34) Method to get products by category
    public ResponsePojo<List<Product>> getElectricalEquipments(String searchItem, String type){
        return productService.getElectricalEquipments(searchItem, type);
    }

    //(35) Method to get products by category
    public ResponsePojo<List<Product>> getProductDiscounts(String searchItem){
        return productService.getProductDiscounts(searchItem);
    }

    //(36) Method to get products by category
    public ResponsePojo<List<Product>> getShippingDiscounts(String searchItem){
        return productService.getShippingDiscounts(searchItem);
    }

    //(37) Method to get products by category
    public ResponsePojo<List<Product>> getTrendingProducts(String searchItem){
        return productService.getTrendingProducts(searchItem);
    }

    //(38) Method to get products by category
    public ResponsePojo<List<Product>> getProductsAndShippingSavings(String searchItem){
        return productService.getProductsAndShippingSavings(searchItem);
    }

    //(39) Method to get products by category
    public ResponsePojo<List<Product>> getToysAndHobbies(String searchItem, String type){
        return productService.getToysAndHobbies(searchItem, type);
    }

    //(40) Method to get products by category
    public ResponsePojo<List<Product>> getReadyToShip(String searchItem){
        return productService.getReadyToShip(searchItem);
    }

    //(41) Method to get products by category
    public ResponsePojo<List<Product>> getHygieneProducts(String searchItem){
        return productService.getHygieneProducts(searchItem);
    }

    //(42) Method to get products by category
    public ResponsePojo<List<Product>> getFragranceDeodorant(String searchItem){
        return productService.getFragranceDeodorant(searchItem);
    }

    //(43) Method to get products by category
    public ResponsePojo<List<Product>> getPersonalHygieneProducts(String searchWord){
        return productService.getPersonalHygieneProducts(searchWord);
    }

    //(44) Method to get products by category
    public ResponsePojo<List<Product>> getBathroomProducts(String searchWord){
        return productService.getBathroomProducts(searchWord);
    }

    //(45) Method to get products by category
    public ResponsePojo<List<Product>> getHomeDecor(String searchWord){
        return productService.getHomeDecor(searchWord);
    }

    //(46) Method to get products by category
    public ResponsePojo<List<Product>> getHomeStorage(String searchWord){
        return productService.getHomeStorage(searchWord);
    }

    //(47) Method to get products by category
    public ResponsePojo<List<Product>> getHouseholdCleaningTools(String searchWord){
        return productService.getHouseholdCleaningTools(searchWord);
    }

}
