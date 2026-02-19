package health.camp.service;

import health.camp.dto.WareHouseDTO;
import health.camp.entity.User;
import health.camp.entity.WareHouse;
import health.camp.model.enums.UserRole;
import health.camp.repository.UserRepository;
import health.camp.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WareHouseService {

    private final WareHouseRepository repository;

    private final UserRepository userRepository;


    public WareHouseDTO createWareHouse(WareHouseDTO dto) {
        WareHouse wareHouse = mapToEntity(dto);
        WareHouse saved = repository.save(wareHouse);
        User user = new User();
        user.setName(saved.getName());
        user.setEmail(saved.getEmail());
        user.setPhone(saved.getPhoneNumber());
        user.setUserName("Warehouse");
        user.setPasswordHash("Warehouse");
        user.setRole(UserRole.WARE_HOUSE);
       user =  userRepository.save(user);
        wareHouse.setUserId(user.getId());
        saved = repository.save(wareHouse);
        return mapToDTO(saved);
    }

    public WareHouseDTO updateWareHouse(Long id, WareHouseDTO dto) {
        WareHouse existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setAuthorizedPerson(dto.getAuthorizedPerson());
        existing.setLicenceNumber(dto.getLicenceNumber());
        existing.setState(dto.getState());
        existing.setDistrict(dto.getDistrict());
        existing.setMandal(dto.getMandal());
        existing.setVillage(dto.getVillage());
        existing.setPinCode(dto.getPinCode());

        WareHouse updated = repository.save(existing);
        return mapToDTO(updated);
    }

    public WareHouseDTO getWareHouseById(Long id) {
        WareHouse wareHouse = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return mapToDTO(wareHouse);
    }

    public WareHouseDTO getWareHouseByUserId(Long id) {
        WareHouse wareHouse = repository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return mapToDTO(wareHouse);
    }

    public List<WareHouseDTO> getAllWareHouses() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteWareHouse(Long id) {
        repository.deleteById(id);
    }

    private WareHouse mapToEntity(WareHouseDTO dto) {
        return WareHouse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .authorizedPerson(dto.getAuthorizedPerson())
                .licenceNumber(dto.getLicenceNumber())
                .state(dto.getState())
                .district(dto.getDistrict())
                .mandal(dto.getMandal())
                .village(dto.getVillage())
                .pinCode(dto.getPinCode())
                .build();
    }

    private WareHouseDTO mapToDTO(WareHouse entity) {
        return WareHouseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .authorizedPerson(entity.getAuthorizedPerson())
                .licenceNumber(entity.getLicenceNumber())
                .state(entity.getState())
                .district(entity.getDistrict())
                .mandal(entity.getMandal())
                .village(entity.getVillage())
                .pinCode(entity.getPinCode())
                .userId(entity.getUserId())
                .build();
    }
}
