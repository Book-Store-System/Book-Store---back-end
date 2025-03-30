package com.renigomes.api_livraria.offer.service;

import com.renigomes.api_livraria.offer.DTO.OfferReqDto;
import com.renigomes.api_livraria.offer.DTO.OfferRespDto;
import com.renigomes.api_livraria.offer.exceptions.OfferNotFoundException;
import com.renigomes.api_livraria.offer.model.Offer;
import com.renigomes.api_livraria.offer.repository.OfferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OfferService {

    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;

    @Transactional
    public OfferRespDto save(OfferReqDto offerReqDto) {
        Offer offer = modelMapper.map(offerReqDto, Offer.class);
        offer = offerRepository.save(offer);
        return modelMapper.map(offer, OfferRespDto.class);
    }

    @Transactional
    public OfferRespDto setActive(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(
                () -> {
                    log.error("Offer not found !");
                    return new OfferNotFoundException("Offer not found !", HttpStatus.NOT_FOUND);
                }
        );
        offer.setActive(!offer.getActive());
        return modelMapper.map(offer, OfferRespDto.class);
    }

    public List<OfferRespDto> findAll(){
        List<Offer> offers = offerRepository.findAll();
        return offers
                .stream()
                .map(offer -> modelMapper.map(offer, OfferRespDto.class))
                .toList();
    }

    public Offer findById(Long id) {
        return offerRepository.findById(id).orElseThrow( () ->{
                log.error("Offer not found !");
                return new OfferNotFoundException("Offer not found !", HttpStatus.NOT_FOUND);}
        );
    }
}
