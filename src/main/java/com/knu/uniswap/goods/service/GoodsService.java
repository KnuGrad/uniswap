//package com.knu.uniswap.goods.service;
//
//import com.knu.uniswap.goods.domain.Goods;
//import com.knu.uniswap.goods.domain.GoodsRepository;
//import com.knu.uniswap.goods.dto.GoodsDto;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class GoodsService {
//
//    private final GoodsRepository goodsRepository;
//
//    @Transactional(readOnly = true)
//    public List<Goods> getAllGoods() {
//        return goodsRepository.findAllByOrderByCreatedAtDesc();
//    }
//
//    @Transactional(readOnly = true)
//    public Goods getGoodsById(Long goodsId) {
//        return goodsRepository.findById(goodsId)
//                .orElseThrow(() -> new EntityNotFoundException("Goods not found"));
//    }
//
//    @Transactional
//    public Goods createGoods(GoodsDto goodsDto) {
//        Goods goods = Goods.builder()
//                .name(goodsDto.getName())
//                .category(goodsDto.getCategory())
//                .status(goodsDto.getStatus())
//                .description(goodsDto.getDescription())
//                .price(goodsDto.getPrice())
//                .quantity(goodsDto.getQuantity())
//                .location(goodsDto.getLocation())
//                .imageUrl(goodsDto.getImageUrl())
//                .likes(0)
//                .build();
//        return goodsRepository.save(goods);
//    }
//
//    @Transactional
//    public Goods updateGoods(Long goodsId, GoodsDto goodsDto, Long userId) {
//        Goods goods = goodsRepository.findById(goodsId)
//                .orElseThrow(() -> new EntityNotFoundException("Goods not found"));
//
//        if (!goods.getCreatedBy().equals(userId)) {
//            throw new AccessDeniedException("You do not have permission to edit this goods");
//        }
//
//        goods.update(goodsDto);
//        return goodsRepository.save(goods);
//    }
//
//    @Transactional
//    public void deleteGoods(Long goodsId, Long userId) {
//        Goods goods = goodsRepository.findById(goodsId)
//                .orElseThrow(() -> new EntityNotFoundException("Goods not found"));
//
//        if (!goods.getCreatedBy().equals(userId)) {
//            throw new AccessDeniedException("You do not have permission to delete this goods");
//        }
//
//        goodsRepository.delete(goods);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Goods> searchGoodsByName(String name) {
//        return goodsRepository.findByNameContainingIgnoreCase(name);
//    }
//}
