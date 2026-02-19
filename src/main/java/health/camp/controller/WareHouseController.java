package health.camp.controller;

import health.camp.dto.WareHouseDTO;
import health.camp.service.WareHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WareHouseController {

    private final WareHouseService service;

    @PostMapping
    public WareHouseDTO create(@RequestBody WareHouseDTO dto) {
        return service.createWareHouse(dto);
    }

    @PutMapping("/{id}")
    public WareHouseDTO update(@PathVariable Long id, @RequestBody WareHouseDTO dto) {
        return service.updateWareHouse(id, dto);
    }

    @GetMapping("/{id}")
    public WareHouseDTO getById(@PathVariable Long id) {
        return service.getWareHouseById(id);
    }

    @GetMapping
    public List<WareHouseDTO> getAll() {
        return service.getAllWareHouses();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteWareHouse(id);
        return "Warehouse deleted successfully";
    }
}
