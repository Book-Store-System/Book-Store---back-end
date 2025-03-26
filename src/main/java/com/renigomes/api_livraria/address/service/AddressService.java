package com.renigomes.api_livraria.address.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.address.DTO.AddressReqDto;
import com.renigomes.api_livraria.address.DTO.AddressRespDto;
import com.renigomes.api_livraria.address.exceptions.AddressException;
import com.renigomes.api_livraria.address.exceptions.AddressNotFound;
import com.renigomes.api_livraria.address.exceptions.AddressUpdateError;
import com.renigomes.api_livraria.address.exceptions.UserHasNoAddressException;
import com.renigomes.api_livraria.address.model.Address;
import com.renigomes.api_livraria.address.repository.AddressRepository;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.service.UserService;
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
@Slf4j
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public static final String ADDRESS_NOT_FOUND = "Address not found !";
    public static final String ADDRESS_COULD_NOT_BE_UPDATED = "Address could not be updated";


    private static final String THIS_USER_HAS_NO_ADDRESS = "This user has no address !";

    public static AddressException getLogErrorNotFound(){
        log.error(ADDRESS_NOT_FOUND);
        return new AddressNotFound(ADDRESS_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }

    public AddressRespDto findAddressDefault(HttpServletRequest request){
        User user = userService.extractUserByToker(request);
        Address address = addressRepository.findAddressDefaultByUserId(user.getId());
        return modelMapper.map(address, AddressRespDto.class);
    }

    public List<AddressRespDto> getAddressById(HttpServletRequest request) {
        User user = userService.extractUserByToker(request);
        List<Address> addresses = addressRepository.findByUserId(user.getId());
        if (addresses.isEmpty()) {
            log.error(THIS_USER_HAS_NO_ADDRESS);
            throw new UserHasNoAddressException(THIS_USER_HAS_NO_ADDRESS, HttpStatus.BAD_REQUEST);
        }
        return addresses.stream()
                .map(
                        address -> modelMapper
                                .map(address, AddressRespDto.class))
                .toList();
    }

    @Transactional
    public RespIdDto createAddress(HttpServletRequest request, AddressReqDto addressReqDto) {
        User user = userService.extractUserByToker(request);
        Address addressSaved = modelMapper.map(addressReqDto, Address.class);
        addressSaved.setUser(user);
        addressSaved.setAddressDefault(addressRepository.countAddressByUserId(user.getId()) == 0);
        addressSaved = addressRepository.save(addressSaved);
        return new RespIdDto(addressSaved.getId());
    }

    @Transactional
    public Address updateAddress(long id,  AddressReqDto addressReqDto) {
        Address address = addressRepository.findById(id).orElseThrow(
                AddressService::getLogErrorNotFound
        );
        BeanUtils.copyProperties(addressReqDto, address);
        address = addressRepository.save(address);
        if (
                !address.getCity().equals(addressReqDto.getCity()) ||
                        !address.getStreet().equals(addressReqDto.getStreet()) ||
                        !address.getState().equals(addressReqDto.getState()) ||
                        !address.getNeighborhood().equals(addressReqDto.getNeighborhood()) ||
                        !address.getCEP().equals(addressReqDto.getCEP()) ||
                        !address.getNumber().equals(addressReqDto.getNumber()) ||
                        !address.getReference().equals(addressReqDto.getReference())
        ){
            log.error(ADDRESS_COULD_NOT_BE_UPDATED);
            throw new AddressUpdateError(ADDRESS_COULD_NOT_BE_UPDATED, HttpStatus.INTERNAL_SERVER_ERROR);}
        return address;
    }

    @Transactional
    public Address updateAddressDefault(long id) {
      addressRepository.findAll()
                .forEach(
                        (address) -> {
                            address.setAddressDefault(false);
                            addressRepository.save(address);
                        }
                );
        Address address = addressRepository.findById(id).orElseThrow(
                AddressService::getLogErrorNotFound
        );
        address.setAddressDefault(true);
        address = addressRepository.save(address);
        return address;
    }

    @Transactional
    public void deleteAddress(long id) {
        Address address = addressRepository.findById(id).orElseThrow(
                AddressService::getLogErrorNotFound
        );
        addressRepository.delete(address);
    }

}
