package com.renigomes.api_livraria.cupom.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.cupom.DTO.CupomReqDto;
import com.renigomes.api_livraria.cupom.DTO.CupomRespAdmDto;
import com.renigomes.api_livraria.cupom.DTO.CupomRespDto;
import com.renigomes.api_livraria.cupom.enums.TypeCupom;
import com.renigomes.api_livraria.cupom.exceptions.CupomNotFoundException;
import com.renigomes.api_livraria.cupom.model.Cupom;
import com.renigomes.api_livraria.cupom.repository.CupomRepository;
import com.renigomes.api_livraria.user.component.UserComponent;
import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CupomService {

    private final CupomRepository cupomRepository;
    private final ModelMapper modelMapper;
    private final UserComponent userComponent;

    public Cupom findCupomByCode(String code) {
        Cupom cupom =  cupomRepository.findByCode(code)
                .orElseThrow(
                        () -> {
                            log.error("Cupom not found");
                            return new CupomNotFoundException("Cupom not found", HttpStatus.NOT_FOUND);
                        }
                );
        if (!cupom.getActive())
            return cupom;
        log.error("Cupom not found");
        throw new CupomNotFoundException("Cupom not found", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public RespIdDto saveCupom(CupomReqDto cupomReqDto) {
        Cupom cupom = new Cupom();
        BeanUtils.copyProperties(cupomReqDto, cupom);
        switch (cupomReqDto.typeCupom()){
            case SHIPPING:
                cupom.setTypeCupom(TypeCupom.SHIPPING);
                break;
            case PERCENTAGE:
                cupom.setTypeCupom(TypeCupom.PERCENTAGE);
                break;
        }
        cupom = cupomRepository.save(cupom);
        return new RespIdDto(cupom.getId());
    }

    @Transactional
    public Cupom setCupomActive(Long id) {
        Cupom cupom = cupomRepository.findById(id).orElseThrow();
        cupom.setActive(!cupom.getActive());
        return cupomRepository.save(cupom);
    }

    public List<?> findCupomAll(HttpServletRequest request) {
        User user = userComponent.extractUserByToker(request);
        return user.getRole() == Role.ADMIN ?
                cupomRepository.findAll()
                        .stream()
                        .map(cupom -> modelMapper.map(cupom, CupomRespAdmDto.class))
                        .toList() :
                cupomRepository.findAll()
                .stream()
                .filter(Cupom::getActive)
                .map(cupom -> modelMapper.map(cupom, CupomRespDto.class))
                .toList();
    }
}
