package com.alibabademo.alibaba.Service;

import com.alibabademo.alibaba.Dao.ProductDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.Entity.QProduct;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.Repository.ProductReppo;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private EntityManager entityManager;

    //The following SERVLETS are all for GET APIs

    //(1) Method to get product by Id
    public ResponsePojo<Product> getProductById(Long Id){
        if(ObjectUtils.isEmpty(Id))
            throw new ApiException("Id is empty...insert Id");

        Optional<Product> productOptional1= productReppo.findById(Id);
        productOptional1.orElseThrow(()->new ApiException(String.format("Product with Id %s does not exist", Id )));

        Product product = productOptional1.get();
        ResponsePojo<Product> responsePojo = new ResponsePojo<>();
        responsePojo.setData(product);
        responsePojo.setMessage("Product found!");

        return responsePojo;
    }

    //(2) Method to get all the products in the database
    public ResponsePojo<List<Product>> getAllProducts(){
        List<Product> productList = productReppo.findAll();

        if(productList.isEmpty())
            throw new ApiException("List is empty!!");

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("List Result!");

        return responsePojo;
    }

    //(3) Method to get Customized products
    public ResponsePojo<List<Product>> getCustomizedProducts(){
        QProduct qProduct = QProduct.product;
       // BooleanBuilder predicate = new BooleanBuilder();
        //I don't need a predicate here

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.customized.eq(true))
                .orderBy(qProduct.Id.desc());

        List<Product> customizedProductList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(customizedProductList);
        responsePojo.setMessage("Customized Products List");

        return responsePojo;
    }

    //(4) Method to get products by Colour
    public ResponsePojo<List<Product>> getProductsByColour(String colour){
        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();

        if(StringUtils.hasText(colour))
            predicate.and(qProduct.colour.equalsIgnoreCase(colour));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.Id.desc());

        List<Product> productList = jpaQuery.fetch();
        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Products selected by colour.");

        return responsePojo;
    }

    //(5) Method to get products by category
    public ResponsePojo<List<Product>> getProductByCategory(String category){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(category))
            predicate.and(qProduct.category.likeIgnoreCase("%" + category + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.Id.asc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Products selected by category");

        return responsePojo;
    }

    //(6) Method to get New Arrivals
    public ResponsePojo<List<Product>> newArrivals(){
        QProduct qProduct = QProduct.product;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.productName.isNotNull())//I need to use time function to specify time & date range
                .orderBy(qProduct.timeListed.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("New Arrivals");

        return responsePojo;
    }

    //(7) Method to get Ready-To-Ship Products
    public ResponsePojo<List<Product>> readyToShipProducts(){
        QProduct qProduct = QProduct.product;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.saleStatus.eq(true))
                .orderBy(qProduct.dateSold.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Ready To Ship");

        return responsePojo;
    }

    //(8) Method to get Weekly Deals
    public ResponsePojo<List<Product>> weeklyDeals(){
        QProduct qProduct = QProduct.product;
        //BooleanBuilder predicate = new BooleanBuilder();
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.saleStatus.eq(true).and(qProduct.dateSold.gt(qProduct.dateListed)))
                .orderBy(qProduct.quantityOrdered.asc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Weekly Deals");

        return responsePojo;
    }

    //(9) Method to get small Commodities products
    public ResponsePojo<List<Product>> smallCommodities(){
        QProduct qProduct = QProduct.product;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.quantityOrdered.between(1, 10).and(qProduct.price.between(1000,3000)))
                .orderBy(qProduct.saleStatus.asc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Small Commodities");

        return responsePojo;
    }

    //(10) Method to get Top Ranking Products
    public ResponsePojo<List<Product>> topRankingProducts(){
        QProduct qProduct = QProduct.product;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.ratings.between(80, 100))
                .orderBy(qProduct.reviews.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Top Ranking Products");

        return responsePojo;
    }

    //(11) Method to get top products by a company
    public ResponsePojo<List<Product>> topProductsByCompany(String companyName){
        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();

        if(StringUtils.hasText(companyName))
            predicate.and(qProduct.companyName.likeIgnoreCase("%" + companyName + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.saleStatus.eq(true).and(qProduct.ratings.between(80, 100))))
                .orderBy(qProduct.reviews.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Top Products by Company");

        return responsePojo;
    }

    //(12) Method to get products by category
    public ResponsePojo<List<Product>> getConsumerElectronics(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Consumer Electronics");

        return responsePojo;
    }

    //(13) Method to get products by category
    public ResponsePojo<List<Product>> getApparel(String apparel, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(apparel))
            predicate.and(qProduct.category.likeIgnoreCase("%" + apparel + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Apparel");

        return responsePojo;
    }

    //(14) Method to get products by category
    public ResponsePojo<List<Product>> getSportsAndEntertainment(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Sports and Entertainment");

        return responsePojo;
    }

    //(15) Method to get products by category
    public ResponsePojo<List<Product>> getTimepieces(String timePieces, String jewelry, String eyewears){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(timePieces))
            predicate.and(qProduct.category.likeIgnoreCase("%" + timePieces + "%"));

        if(StringUtils.hasText(jewelry))
            predicate.and(qProduct.category.likeIgnoreCase("%" + jewelry + "%"));

        if(StringUtils.hasText(eyewears))
            predicate.and(qProduct.category.likeIgnoreCase("%" + eyewears + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.Id.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Timepieces, Jewelry and Eye-wears");

        return responsePojo;
    }

    //(16) Method to get products by category
    public ResponsePojo<List<Product>> getHomeAndGarden(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Home and Garden");

        return responsePojo;
    }

    //(17) Method to get products by category
    public ResponsePojo<List<Product>> getFashionAccessories(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.quantityOrdered.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Fashion Accessories");

        return responsePojo;
    }

    //(18) Method to get products by category
    public ResponsePojo<List<Product>> getBeautyAndPersonalCare(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.quantityOrdered.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Beauty and Personal Care");

        return responsePojo;
    }

    //(19) Method to get products by category
    public ResponsePojo<List<Product>> getMenWear(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchWord + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.dateListed.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Men's Wear");

        return responsePojo;
    }

    //(20) Method to get products by category
    public ResponsePojo<List<Product>> getWomenWear(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchWord+ "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.dateListed.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Women's Wear");

        return responsePojo;
    }

    //(21) Method to get products by category
    public ResponsePojo<List<Product>> getChildrenWear(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchWord + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.dateListed.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Children's Wear");

        return responsePojo;
    }

    //(22) Method to get products by category
    public ResponsePojo<List<Product>> getPPE(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.asc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Personal Protective Equipment");

        return responsePojo;
    }

    //(23) Method to get products by category
    public ResponsePojo<List<Product>> getDisinfectants(String disinfectants){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(disinfectants))
            predicate.and(qProduct.category.likeIgnoreCase("%" + disinfectants + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Disinfectants");

        return responsePojo;
    }

    //(24) Method to get products by category
    public ResponsePojo<List<Product>> getMedicalDevices(String medicalDevices){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(medicalDevices))
            predicate.and(qProduct.category.likeIgnoreCase("%" + medicalDevices + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Medical Devices");

        return responsePojo;
    }

    //(25) Method to get products by category
    public ResponsePojo<List<Product>> getMedicalConsumables(String medicalConsumables){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(medicalConsumables))
            predicate.and(qProduct.category.likeIgnoreCase("%" + medicalConsumables + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Medical Consumables");

        return responsePojo;
    }

    //(26) Method to get products by category
    public ResponsePojo<List<Product>> getHomeAppliances(String homeAppliances){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(homeAppliances))
            predicate.and(qProduct.category.likeIgnoreCase("%" + homeAppliances + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Home Appliances");

        return responsePojo;
    }

    //(27) Method to get products by category
    public ResponsePojo<List<Product>> getLightning(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.availableQuantity.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Lightning Accessories");

        return responsePojo;
    }

    //(28) Method to get products by category
    public ResponsePojo<List<Product>> getConstruction(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Construction Equipments");

        return responsePojo;
    }

    //(29) Method to get products by category
    public ResponsePojo<List<Product>> getRealEstate(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Real Estate");

        return responsePojo;
    }

    //(30) Method to get products by category
    public ResponsePojo<List<Product>> getFabricTextile(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.availableQuantity.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Fabric and Textile");

        return responsePojo;
    }

    //(31) Method to get products by category
    public ResponsePojo<List<Product>> getHomeAppliances(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Home Appliances");

        return responsePojo;
    }

    //(32) Method to get products by category
    public ResponsePojo<List<Product>> getPackagingAndPrinting(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Packaging and Printing");

        return responsePojo;
    }

    //(33) Method to get products by category
    public ResponsePojo<List<Product>> getOfficeAndSchoolSupplies(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Home Appliances");

        return responsePojo;
    }

    //(34) Method to get products by category
    public ResponsePojo<List<Product>> getElectricalEquipments(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase("%" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Electrical Equipments");

        return responsePojo;
    }

    //(35) Method to get products by category
    public ResponsePojo<List<Product>> getProductDiscounts(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.discount.between(1, 15));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Product Discounts");

        return responsePojo;
    }

    //(36) Method to get products by category
    public ResponsePojo<List<Product>> getShippingDiscounts(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.shippingDiscount.between(5, 15));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Shipping Discounts");

        return responsePojo;
    }

    //(37) Method to get products by category
    public ResponsePojo<List<Product>> getTrendingProducts(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.ratings.between(70, 100));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.reviews.gt(100)))
                .orderBy(qProduct.quantityOrdered.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Trending Products");

        return responsePojo;
    }

    //(38) Method to get products by category
    public ResponsePojo<List<Product>> getProductsAndShippingSavings(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.shippingDiscount.between(10, 20));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Product and Shipping savings");

        return responsePojo;
    }

    //(39) Method to get products by category
    public ResponsePojo<List<Product>> getToysAndHobbies(String searchItem, String type){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase(searchItem));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.productType.likeIgnoreCase(type)))
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Toys and Hobbies");

        return responsePojo;
    }

    //(40) Method to get products by category
    public ResponsePojo<List<Product>> getReadyToShip(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.saleStatus.eq(true));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.dateSold.gt(qProduct.dateListed)))
                .orderBy(qProduct.quantityOrdered.desc());//I need to use a Date function which I can use to select day intervals

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Ready To Ship Between 7 Days");

        return responsePojo;
    }

    //(41) Method to get products by category
    public ResponsePojo<List<Product>> getHygieneProducts(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase(" %" + searchItem + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Hygiene Products");

        return responsePojo;
    }

    //(42) Method to get products by category
    public ResponsePojo<List<Product>> getFragranceDeodorant(String searchItem){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProduct.category.likeIgnoreCase(" %" + searchItem + "%"));


        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.totalCost.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Fragrance and Deodorants");

        return responsePojo;
    }

    //(43) Method to get products by category
    public ResponsePojo<List<Product>> getPersonalHygieneProducts(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase(" %" + searchWord + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Personal Hygiene Products");

        return responsePojo;
    }

    //(44) Method to get products by category
    public ResponsePojo<List<Product>> getBathroomProducts(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase(" %" + searchWord + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Bathroom Products");

        return responsePojo;
    }

    //(45) Method to get products by category
    public ResponsePojo<List<Product>> getHomeDecor(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase(" %" + searchWord + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Home Decor");

        return responsePojo;
    }

    //(46) Method to get products by category
    public ResponsePojo<List<Product>> getHomeStorage(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase(" %" + searchWord + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Home Storage and Organization");

        return responsePojo;
    }

    //(47) Method to get products by category
    public ResponsePojo<List<Product>> getHouseholdCleaningTools(String searchWord){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchWord))
            predicate.and(qProduct.category.likeIgnoreCase(" %" + searchWord + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.price.desc());

        List<Product> productList = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Household Cleaning Tools and Accessories");

        return responsePojo;
    }


}
