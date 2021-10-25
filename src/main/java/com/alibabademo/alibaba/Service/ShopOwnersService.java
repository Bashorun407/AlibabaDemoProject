package com.alibabademo.alibaba.Service;

import com.alibabademo.alibaba.Dao.ShopOwnersDto;
import com.alibabademo.alibaba.Entity.QShopOwners;
import com.alibabademo.alibaba.Entity.ShopOwners;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.Exception.ApiRequestException;
import com.alibabademo.alibaba.Repository.ShopOwnerReppo;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShopOwnersService {

    @Autowired
    private ShopOwnerReppo shopOwnerReppo;

    @Autowired
    private EntityManager entityManager;


    //(1) Method to create Shop or Shop Owner detail
    public ResponsePojo<ShopOwners> createShopOwner(ShopOwnersDto shopOwnersDto){

        Optional<ShopOwners> shopNumber = shopOwnerReppo.findByShopNumber(shopOwnersDto.getShopNumber());

        if(!ObjectUtils.isEmpty(shopNumber))
            throw new ApiRequestException(String.format("Shop with this number: %s exists...Shop can not be created with this number",
                    shopOwnersDto.getShopNumber()));


        if(!StringUtils.hasText(shopOwnersDto.getShopName()))
            throw new ApiRequestException("Shop name is required to create Shop!!");

        if(!StringUtils.hasText(shopOwnersDto.getShopNumber()))
            throw new ApiRequestException("Shop number is required to create Shop!!");

        ShopOwners shopOwners = new ShopOwners();

        shopOwners.setShopName(shopOwnersDto.getShopName());
        shopOwners.setShopNumber(shopOwnersDto.getShopNumber());
        shopOwners.setShopOwnerFirstName(shopOwnersDto.getShopOwnerFirstName());
        shopOwners.setShopOwnerLastName(shopOwnersDto.getShopOwnerLastName());
        shopOwners.setPhoneNumber(shopOwnersDto.getPhoneNumber());
        shopOwners.setEmailAddress(shopOwnersDto.getEmailAddress());
        shopOwners.setDateCreated(new Date());

        //saving the details in the repository
        shopOwnerReppo.save(shopOwners);

        ResponsePojo<ShopOwners> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shopOwners);
        responsePojo.setMessage("A new Shop created!!");

        return responsePojo;
    }

    //(2) Method to get all Shop Owners
    public ResponsePojo<List<ShopOwners>> getAllShopOwners(){

        List<ShopOwners> allShopOwners = shopOwnerReppo.findAll();

        if(allShopOwners.isEmpty())
            throw new ApiRequestException("Shop Owners List is empty");

        ResponsePojo<List<ShopOwners>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(allShopOwners);
        responsePojo.setMessage("Here is the Shop Owners List.");

        return responsePojo;
    }

    //(3) Method to get a Shop Owner
    public ResponsePojo<List<ShopOwners>> findShopOwner(String searchItem){

        //Using QueryDsl
        QShopOwners qShopOwners = QShopOwners.shopOwners;
        BooleanBuilder predicate = new BooleanBuilder();

        if(StringUtils.hasText(searchItem))
            predicate.and(qShopOwners.shopNumber.likeIgnoreCase("%" + searchItem + "%")
                    .or(qShopOwners.shopName.likeIgnoreCase("%" + searchItem + "%"))
                    .or(qShopOwners.shopOwnerFirstName.likeIgnoreCase("%" + searchItem + "%"))
                    .or(qShopOwners.shopOwnerLastName.likeIgnoreCase("%" + searchItem + "%"))
                    .or(qShopOwners.emailAddress.likeIgnoreCase( searchItem + "@gmail.com")));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ShopOwners> jpaQuery = jpaQueryFactory.selectFrom(qShopOwners)
                .where(predicate)
                .orderBy(qShopOwners.Id.desc());

        List<ShopOwners> shopOwnerSearched = jpaQuery.fetch();

       if(shopOwnerSearched.isEmpty())
              throw new ApiRequestException("Shop with this Detail does not exist.");

        ResponsePojo<List<ShopOwners>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shopOwnerSearched);
        responsePojo.setMessage(String.format("Shop with this number: %s found!!", searchItem));

        return responsePojo;
    }

    //(4) Method to update Shop
    public ResponsePojo<ShopOwners> updateShopOwner(ShopOwnersDto shopOwnersDto){

        Optional<ShopOwners> shopOwnersOptional1 = shopOwnerReppo.findByShopNumber(shopOwnersDto.getShopNumber());
        shopOwnersOptional1.orElseThrow(()-> new ApiRequestException("Shop with this Shop Number does not exist."));

        Optional<ShopOwners> shopOwnersOptional2 = shopOwnerReppo.findByShopName(shopOwnersDto.getShopName());
        shopOwnersOptional2.orElseThrow(()-> new ApiRequestException("Shop with this Shop Name does not exist."));

        ShopOwners shopOwners1 = shopOwnersOptional1.get();
        ShopOwners shopOwners2 = shopOwnersOptional2.get();

        if( shopOwners1 == shopOwners2)
            throw new ApiRequestException("The Shop Number and Shop Name entered are for different Shop Owners");

        ShopOwners shopOwners = shopOwnersOptional1.get();
        shopOwners.setShopName(shopOwnersDto.getShopName());
        shopOwners.setShopNumber(shopOwnersDto.getShopNumber());
        shopOwners.setEmailAddress(shopOwnersDto.getEmailAddress());
        shopOwners.setUpdatedStatus(true);
        shopOwners.setDateUpdated(new Date());

        shopOwnerReppo.save(shopOwners);

        ResponsePojo<ShopOwners> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shopOwners);
        responsePojo.setMessage("Shop has been updated.");

        return responsePojo;
    }
}
