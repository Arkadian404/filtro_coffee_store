package com.data.filtro.service;

import com.data.filtro.model.Staff;
import com.data.filtro.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    StaffRepository staffRepository;

    public Staff getById(int id) {
        return staffRepository.findById(id);
    }


    public List<Staff> getAll() {
        return staffRepository.findAll();
    }


    public Page<Staff> getAllPaging(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }


    public void create(Staff staff) {
        staffRepository.save(staff);
    }

    public void update(Staff staff) {
        Staff newStaff = getById(staff.getId());
        newStaff.setName(staff.getName());
        newStaff.setDob(staff.getDob());
        newStaff.setStatus(staff.getStatus());
        newStaff.setSex(staff.getSex());
        newStaff.setPhoneNumber(staff.getPhoneNumber());
        newStaff.setAccount(staff.getAccount());
        staffRepository.save(newStaff);
    }

    @Transactional
    public void delete(int id) {
        staffRepository.delete(id);
    }

}
