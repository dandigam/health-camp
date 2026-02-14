package health.camp.service;

import health.camp.dto.camp.CampRequest;
import health.camp.dto.camp.CampResponse;
import health.camp.model.Camp;
import health.camp.model.CampResources;
import health.camp.model.CampStart;
import health.camp.model.enums.CampStatus;
import health.camp.repository.*;
import health.camp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;
    private final CampStartRepository campStartRepository;
    private final CountersService countersService;
    private final CampResourcesRepository campResourcesRepository;

    public CampResponse create(CampRequest request) {

        Camp camp = new Camp();
        camp.setId(countersService.getNextSequence("camp_seq"));
        mapRequestToCamp(request, camp);
        camp = campRepository.save(camp);
        CampResources campResources = new CampResources();
        if (!CollectionUtils.isEmpty(request.getDoctorIds()) || !CollectionUtils.isEmpty(request.getStaffIds())) {
            campResources.setId(countersService.getNextSequence("camp_resource_seq"));
            campResources.setDoctorIds(request.getDoctorIds());
            campResources.setPharmacyIds(request.getPharmacyIds());
            campResources.setCampId(camp.getId());
            campResourcesRepository.save(campResources);
        }
        // Only create CampStart if ACTIVE
        CampStart campStart = null;
        if (CampStatus.start.equals(request.getCampStatus())) {
            campStart = createCampStart(camp.getId(), request);
        }

        return toResponse(camp, campStart, campResources);
    }

    public CampResponse startCamp(Long campId, CampRequest request) {

        Camp camp = campRepository.findById(campId)
                .orElseThrow(() -> new ResourceNotFoundException("Camp", campId + ""));

        CampStart campStart = createCampStart(campId, request);
        return toResponse(camp, campStart, null);
    }

    private void mapRequestToCamp(CampRequest request, Camp camp) {

        if (request.getName() != null)
            camp.setName(request.getName());

        if (request.getOrganizerName() != null)
            camp.setOrganizerName(request.getOrganizerName());

        if (request.getOrganizerPhone() != null)
            camp.setOrganizerPhone(request.getOrganizerPhone());

        if (request.getOrganizerEmail() != null)
            camp.setOrganizerEmail(request.getOrganizerEmail());

        // Location (from parent class)
        if (request.getState() != null)
            camp.setState(request.getState());

        if (request.getDistrict() != null)
            camp.setDistrict(request.getDistrict());

        if (request.getMandal() != null)
            camp.setMandal(request.getMandal());

        if (request.getVillage() != null)
            camp.setVillage(request.getVillage());

        if (request.getAddress() != null)
            camp.setAddress(request.getAddress());

        if (request.getPinCode() != null)
            camp.setPinCode(request.getPinCode());

    }

    private CampStart createCampStart(Long campId, CampRequest request) {

        CampStart campStart = new CampStart();
        campStart.setId(countersService.getNextSequence("camp_start_seq"));
        campStart.setCampId(campId);
        campStart.setCampStatus(request.getCampStatus()); // ACTIVE / CLOSED
        campStart.setPlanDate(request.getPlanDate());

        return campStartRepository.save(campStart);
    }

    public CampResponse update(Long id, CampRequest request) {

        Camp camp = campRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Camp", id + ""));

        mapRequestToCamp(request, camp);
        camp = campRepository.save(camp);

        // Only create CampStart if ACTIVE
        CampStart campStart = createCampStart(camp.getId(), request);

        return toResponse(camp, campStart,null);
    }

    public CampResponse getById(Long id) {

        Camp camp = campRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Camp", id + ""));

        List<CampStart> starts = campStartRepository.findByCampId(id);
        CampStart latest = starts.stream()
                .reduce((first, second) -> second)
                .orElse(null);



        List<CampResources> campResourcesList = campResourcesRepository.findByCampId(id);

        CampResources campResources = campResourcesList.stream()
                .reduce((first, second) -> second)
                .orElse(null);

        return toResponse(camp, latest, campResources);
    }

    private CampResponse toResponse(Camp camp, CampStart campStart, CampResources campResources ) {

        CampResponse dto = new CampResponse();
        dto.setId(camp.getId());
        dto.setName(camp.getName());
        dto.setVillage(camp.getVillage());
        dto.setDistrict(camp.getDistrict());
        dto.setOrganizerName(camp.getOrganizerName());
        dto.setOrganizerPhone(camp.getOrganizerPhone());
        dto.setOrganizerEmail(camp.getOrganizerEmail());
        dto.setState(camp.getState());
        dto.setMandal(camp.getMandal());
        dto.setAddress(camp.getAddress());
        dto.setPinCode(camp.getPinCode());

        if (campStart != null) {
            dto.setCampStatus(campStart.getCampStatus());
            dto.setPlanDate(campStart.getPlanDate());
        }

        if (campResources != null) {
            dto.setDoctorIds(campResources.getDoctorIds());
            dto.setPharmacyIds(campResources.getPharmacyIds());
            dto.setStaffIds(campResources.getStaffIds());
        }

        return dto;
    }

    public List<CampResponse> getAllCamps() {

        List<Camp> camps = campRepository.findAll();

        return camps.stream().map(camp -> {

            CampStart latestStart = campStartRepository
                    .findTopByCampIdOrderByPlanDateDesc(camp.getId())
                    .orElse(null);
            List<CampResources> campResourcesList = campResourcesRepository.findByCampId(camp.getId());

            CampResources campResources = campResourcesList.stream()
                    .reduce((first, second) -> second)
                    .orElse(null);

            return  toResponse(camp, latestStart,campResources);


        }).collect(Collectors.toList());
    }

}
